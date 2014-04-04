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
import com.gwinsoft.components2.job.beans.KKSBTX_BEAN;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class KKSBTX implements InterruptableJob {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String JSRLX_DM = null;
		String INFO = null;
		Map<String, Map<String, Object>> DXMB2_CACHE = CacheManager.getCache("DXMB2_CACHE");
		Iterator<String> it = DXMB2_CACHE.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if("KKSBTX".equals(key)) {
				JSRLX_DM = DXMB2_CACHE.get(key).get("JSRLX_DM").toString();
				INFO = DXMB2_CACHE.get(key).get("INFO").toString();
				break;
			}
		}
		String from = JobUtils.getFsJobStart("KKSBTX");
		String to = JobUtils.getZjkDate();
		System.out.println("缴款失败提醒,"+from+"->"+to);
		String sql = "SELECT A.*,B.NSRMC,B.ORG_DM AS SWJGBM,B.FRSJH,B.CWJLSJH,B.BSRYSJH,SYSDATE AS FS_SJ FROM (select to_char(XTSPHM) as XTSPHM,JKLX_DM,NSRSBM,JK_RQ,SKSSQ,SKSSZ,SUM(SF_JE) AS SF_JE,count(0) AS CNT,lob_to_char(WMSYS.WM_CONCAT(ZSXM_DM)) AS ZSXM_DMS,lob_to_char(WMSYS.WM_CONCAT(ZSPM_DM)) AS ZSPM_DMS,lob_to_char(WMSYS.WM_CONCAT(SF_JE)) AS SF_JES,MAX(A.LR_SJ) AS LR_SJ from T_SMS_KKSBTX A WHERE A.LR_SJ >= TO_DATE('"+from+"', 'yyyy-mm-dd hh24:mi:ss') and A.LR_SJ < TO_DATE('"+to+"', 'yyyy-mm-dd hh24:mi:ss') group by XTSPHM, JKLX_DM, NSRSBM, JK_RQ, SKSSQ, SKSSZ HAVING COUNT(0) < 20) A,NSR_JBXX B WHERE A.NSRSBM = B.NSRBM";
		String sql_insert = "insert into DX_KKSBTX(LSH,XTSPHM,JKLX_DM,NSRSBM,JK_RQ,SKSSQ,SKSSZ,SF_JE,CNT,ZSXM_DMS,ZSPM_DMS,SF_JES,NSRMC,SWJGBM,SJHM,SJLX,SMSINFO,LR_SJ,BZ,FSZT_DM,FS_SJ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			List<KKSBTX_BEAN> beans = pm.queryForList(sql, KKSBTX_BEAN.class);
			for(KKSBTX_BEAN bean : beans) {
				try {
					bean.setFSZT_DM("06");
					bean.setBZ(bean.getXTSPHM());
					if("ALL".equals(JSRLX_DM)) {
						bean.setLSH("SBFR"+JobUtils.getLSH());
						bean.setSJLX("FR");
						bean.setSJHM(bean.getFRSJH());
						String msg = DxmbUtil.genSMS(bean, INFO);
						bean.setSMSINFO(msg);
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
						bean.setLSH("SBCW"+JobUtils.getLSH());
						bean.setSJLX("CWJL");
						bean.setSJHM(bean.getCWJLSJH());
						msg = DxmbUtil.genSMS(bean, INFO);
						bean.setSMSINFO(msg);
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
						bean.setSJHM(bean.getBSRYSJH());
						bean.setLSH("SBBS"+JobUtils.getLSH());
						bean.setSJLX("BSRY");
						msg = DxmbUtil.genSMS(bean, INFO);
						bean.setSMSINFO(msg);
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
					} else if("CWJL".equals(JSRLX_DM)) {
						bean.setLSH("SBCW"+JobUtils.getLSH());
						bean.setSJLX("CWJL");
						bean.setSJHM(bean.getCWJLSJH());
						String msg = DxmbUtil.genSMS(bean, INFO);
						bean.setSMSINFO(msg);
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
					} else if("BSRY".equals(JSRLX_DM)) {
						bean.setLSH("SBBS"+JobUtils.getLSH());
						bean.setSJLX("BSRY");
						bean.setSJHM(bean.getBSRYSJH());
						String msg = DxmbUtil.genSMS(bean, INFO);
						bean.setSMSINFO(msg);
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
					} else if("FR".equals(JSRLX_DM)) {
						bean.setLSH("SBFR"+JobUtils.getLSH());
						bean.setSJLX("FR");
						bean.setSJHM(bean.getFRSJH());
						String msg = DxmbUtil.genSMS(bean, INFO);
						bean.setSMSINFO(msg);
						pm.executeUpdate(sql_insert, bean);
						//启动短信发送队列
						SmsSender.schedule(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			JobUtils.saveFsJobEnd("KKSBTX", to);
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