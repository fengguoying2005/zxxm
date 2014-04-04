package com.gwinsoft.components2.job.js;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.smssh.sms.ReceiveSMS;
import com.gwinsoft.components2.job.JobUtils;
import com.gwinsoft.components2.job.ScheduleTool;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class KKSBTX implements InterruptableJob {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//		System.out.println("失败提醒状态");
		String type_dm = "KKSBTX";
		String from = JobUtils.getJsJobStart(type_dm);
		String to = JobUtils.getFormatDate(GwinSoftUtil.getdateAdded(JobUtils.getFormatDate(JobUtils.getNowDate(), "yyyy-MM-dd HH:mm:ss"), Calendar.SECOND, -20), "yyyy-MM-dd HH:mm:ss");
		DBPersistenceManager pm = null;
		List<String> LSHS = new ArrayList<String>();
		try {
			String sql = "SELECT LSH FROM DX_KKSBTX WHERE FS_SJ>=TO_DATE('"+from+"','YYYY-MM-DD HH24:MI:SS') and FS_SJ<TO_DATE('"+to+"','YYYY-MM-DD HH24:MI:SS')";
			pm = DBHelper.getPm();
			SqlRowSet rows = pm.quereyForRowSet(sql);
			while(rows.next()) {
				String LSH = rows.getString("LSH");
				LSHS.add(LSH);
			}
			JobUtils.saveJsJobEnd(type_dm, to);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
		for(String LSH : LSHS) {
			ScheduleTool.schedule(new KKSBTX_T(LSH));
		}
	}

	public void interrupt() throws UnableToInterruptJobException {
		
	}
}

class KKSBTX_T extends Thread {
	private String LSH;
	public KKSBTX_T(String LSH) {
		this.LSH = LSH;
	}
	public void run() {
		try {
			String zt = ReceiveSMS.receivesmszt(LSH);
			if("DeliveryToTerminal".equals(zt)) {
				zt = "07";
			} else if(zt==null || "".equals(zt)) {
				zt = "未知";
			} else {
				zt = "08";
			}
			DBPersistenceManager pm = null;
			try {
				String sql = "UPDATE DX_KKSBTX SET FSZT_DM='"+zt+"' WHERE LSH='"+LSH+"'";
				pm = DBHelper.getPm();
				pm.executeUpdate(sql);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(pm !=null) {
					pm.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}