package com.gwinsoft.components2.job.js;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.jobs.Smsdata;
import com.gwinsoft.components.smssh.sms.ReceiveSMS;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class DXZT implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		System.out.println("执行取短信自动任务，当前时间："+GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate()));
		String YYYYMMDD = GwinSoftUtil.getFormatDate(GwinSoftUtil.getSysDate(), "yyyyMMdd");
		String TABLENAME = "SMS_SFXCMSG"+YYYYMMDD;
		DBPersistenceManager pm = null;
		try {
			Date day = GwinSoftUtil.getdateAdded(GwinSoftUtil.getSysDate(), Calendar.DAY_OF_MONTH, -2);
			String daystr = GwinSoftUtil.getFormatDate(day, "yyyy-MM-dd");
			pm = DBHelper.getPm();
			String sql = 
				    "SELECT T.NSRDATA_LSH ,T.SMSZT_DM FROM (" +
						"SELECT T.NSRDATA_LSH ,T.SMSZT_DM FROM (SELECT A.NSRDATA_LSH,A.SMSZT_DM AS SMSZT_DM FROM sms_nsrdata A,sms_orgmsg B WHERE A.NSRDATA_LSH=B.NSRDATA_LSH AND B.SMSTYPE_DM='06' AND B.FSCS<10 AND TO_CHAR(A.LR_SJ,'YYYY-MM-DD')>'"+daystr+"') T GROUP BY T.NSRDATA_LSH ,T.SMSZT_DM " +
						"UNION " +
						"SELECT T.NSRDATA_LSH ,T.SMSZT_DM FROM (SELECT A.NSRDATA_LSH,A.SMSZT_DM FROM sms_nsrdata A,sms_nsrmsg B WHERE A.NSRDATA_LSH=B.NSRDATA_LSH AND B.SMSTYPE_DM='06' AND B.FSCS<10 AND TO_CHAR(A.LR_SJ,'YYYY-MM-DD')>'"+daystr+"') T GROUP BY T.NSRDATA_LSH ,T.SMSZT_DM " +
						"UNION " +
						"SELECT DISTINCT NSRDATA_LSH,SMSZT_DM FROM sms_nsrdata A WHERE EXISTS (SELECT 1 FROM "+TABLENAME+" B WHERE A.NSRDATA_LSH=B.NSRDATA_LSH AND B.SMSTYPE_DM='06' AND B.FSCS<10) AND ROWNUM<=100" +// AND A.LR_SJ>'"+daystr+"'
					") T  WHERE ROWNUM<=100";
			//System.out.println(sql);
			SqlRowSet rs = pm.quereyForRowSet(sql);
			while(rs.next()) {
				String NSRDATA_LSH = rs.getString("NSRDATA_LSH");
				String SMSZT_DM = rs.getString("SMSZT_DM");
				List<Smsdata> smsdata = ReceiveSMS.receivesms(NSRDATA_LSH);
				new Receiver(smsdata, NSRDATA_LSH, SMSZT_DM, TABLENAME).start();
			}
			//更新发送次数
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
	}
	
	
}
class Receiver extends Thread {
	private List<Smsdata> datas;
	private String SMSZT_DM;
	private String NSRDATA_LSH;
	private String TABLENAME;
	public Receiver(List<Smsdata> datas, String NSRDATA_LSH, String SMSZT_DM, String TABLENAME) {
		this.datas = datas;
		this.NSRDATA_LSH = NSRDATA_LSH;
		this.SMSZT_DM = SMSZT_DM;
		this.TABLENAME = TABLENAME;
	}
	public void run() {
		List<String> listsql = new ArrayList<String>();
		String sql = null;
		if(datas.size()>0) {
			if(!"07".equals(SMSZT_DM)) {
				listsql.add("UPDATE SMS_NSRDATA SET SMSZT_DM='07' WHERE NSRDATA_LSH='"+NSRDATA_LSH+"'");
			}
			listsql.add("UPDATE SMS_NSRMSG SET FSCS=FSCS+1 WHERE NSRDATA_LSH='"+NSRDATA_LSH+"' AND SMSTYPE_DM<>'07' AND length(SJHM)=11");
			listsql.add("UPDATE SMS_ORGMSG SET FSCS=FSCS+1 WHERE NSRDATA_LSH='"+NSRDATA_LSH+"' AND SMSTYPE_DM<>'07' AND length(SJHM)=11");
			listsql.add("UPDATE "+TABLENAME+" SET FSCS=FSCS+1 WHERE NSRDATA_LSH='"+NSRDATA_LSH+"' AND SMSTYPE_DM<>'07' AND length(SJHM)=11");
		}
		for(Smsdata data:datas) {
			String lsh = data.lsh.substring(0, data.lsh.length()-2);
			String time = data.time;
			if(time!=null && time.length()>19) {
				time = time.substring(0, 19);
			}
			String smszt = data.smszt;
			if(data.lsh.endsWith("#1")) {
				sql = "UPDATE SMS_NSRMSG SET SMSTYPE_DM='"+smszt+"',FS_SJ=TO_DATE('"+time+"','YYYY-MM-DD HH24:MI:SS') WHERE NSRMSG_LSH='"+lsh+"'";
			} else if(data.lsh.endsWith("#2")) {
				sql = "UPDATE SMS_ORGMSG SET SMSTYPE_DM='"+smszt+"',FS_SJ=TO_DATE('"+time+"','YYYY-MM-DD HH24:MI:SS') WHERE NSRDATAMX_LSH='"+lsh+"'";
			} else if(data.lsh.endsWith("#3")) {
				sql = "UPDATE "+TABLENAME+" SET SMSTYPE_DM='"+smszt+"',FS_SJ=TO_DATE('"+time+"','YYYY-MM-DD HH24:MI:SS') WHERE SFXCMSG_LSH='"+lsh+"'";
			}
			listsql.add(sql);
		}
		DBPersistenceManager pm = null;
		try {
			String[] ss = new String[listsql.size()];
			int i = 0;
			for(String s : listsql) {
				ss[i++] = s;
			}
			pm = DBHelper.getPm();
			pm.executeUpdateBatch(ss);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
	}
}