package com.gwinsoft.components2.job.fs;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import com.gwinsoft.components2.dxmb.DxmbUtil;
import com.gwinsoft.components2.job.JobUtils;
import com.gwinsoft.components2.job.SmsSender;
import com.gwinsoft.components2.job.beans.TYDQTX_BEAN;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class TYDQTX implements InterruptableJob {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		String JSRLX_DM = null;
		String INFO = null;
		Map<String, Map<String, Object>> DXMB2_CACHE = CacheManager.getCache("DXMB2_CACHE");
		Iterator<String> it = DXMB2_CACHE.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if("TYDQTX".equals(key)) {
				JSRLX_DM = DXMB2_CACHE.get(key).get("JSRLX_DM").toString();
				INFO = DXMB2_CACHE.get(key).get("INFO").toString();
				break;
			}
		}
		String from = JobUtils.getFsJobStart("TYDQTX");
		String to = JobUtils.getZjkDate();
		System.out.println("停业到期提醒,"+from+"->"+to);
		String sql = "select NSRSBM,NSR.NSRMC,NSR.FRSJH,NSR.CWJLSJH,NSR.BSRYSJH,TY.LR_SJ,TY.YXQ_Q AS TY_RQ,TY.YXQ_Z AS TYDQ_RQ,NSR.ORG_DM AS SWJGBM,'06' AS FSZT_DM,SYSDATE AS FS_SJ,TY.LSH AS DQTX_LSH from T_SMS_TYDQTX TY, NSR_JBXX NSR WHERE TY.NSRSBM = NSR.NSRBM AND TY.LR_SJ>=TO_DATE('"+from+"','yyyy-mm-dd hh24:mi:ss') and TY.LR_SJ<TO_DATE('"+to+"','yyyy-mm-dd hh24:mi:ss')";
		String sql_insert = "insert into DX_TYDQTX(LSH,NSRSBM,NSRMC,SJHM,SMSINFO,LR_SJ,TYDQ_RQ,TY_RQ,BZ,SWJGBM,FSZT_DM,FS_SJ,DQTX_LSH,SJLX) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			List<TYDQTX_BEAN> beans = pm.queryForList(sql, TYDQTX_BEAN.class);
			for(TYDQTX_BEAN bean : beans) {
				try {
					String msg = DxmbUtil.genSMS(bean, INFO);
					bean.setSMSINFO(msg);
					if("ALL".equals(JSRLX_DM)) {
						bean.setLSH("TYFR"+JobUtils.getLSH());
						bean.setSJHM(bean.getFRSJH());
						bean.setSJLX("FR");
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
						
						bean.setLSH("TYCW"+JobUtils.getLSH());
						bean.setSJHM(bean.getCWJLSJH());
						bean.setSJLX("CWJL");
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
						
						bean.setSJHM(bean.getBSRYSJH());
						bean.setSJLX("BSRY");
						bean.setLSH("TYBS"+JobUtils.getLSH());
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
					} else if("CWJL".equals(JSRLX_DM)) {
						bean.setLSH("TYCW"+JobUtils.getLSH());
						bean.setSJLX("CWJL");
						bean.setSJHM(bean.getCWJLSJH());
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
					} else if("BSRY".equals(JSRLX_DM)) {
						bean.setLSH("TYBS"+JobUtils.getLSH());
						bean.setSJLX("BSRY");
						bean.setSJHM(bean.getBSRYSJH());
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
					} else if("FR".equals(JSRLX_DM)) {
						bean.setLSH("TYFR"+JobUtils.getLSH());
						bean.setSJLX("FR");
						bean.setSJHM(bean.getFRSJH());
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			JobUtils.saveFsJobEnd("TYDQTX", to);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
	
	}

	public void interrupt() throws UnableToInterruptJobException {
		
	}
}