package com.gwinsoft.components2.job.beans_js;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components2.dxmb.DxmbUtil;
import com.gwinsoft.components2.job.JobUtils;
import com.gwinsoft.components2.job.beans.plian.FPXX_BEAN;
import com.gwinsoft.components2.job.beans.plian.FPZJXX_BEAN;
import com.gwinsoft.components2.job.beans.plian.SFXX_BEAN;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class InboxUtil {

	public static FPZJCX_BEAN SAVE_FPZJCX_BEAN(SMS_INBOX_BEAN bean, DBPersistenceManager pm) {
		FPZJCX_BEAN tobean = new FPZJCX_BEAN();

		String MBINFO = null;
		Map<String, Map<String, Object>> DXMB2_CACHE = CacheManager.getCache("DXMB2_CACHE");
		Iterator<String> it = DXMB2_CACHE.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if("FPZJCX".equals(key)) {
				MBINFO = DXMB2_CACHE.get(key).get("INFO").toString();
				break;
			}
		}
		String sss = bean.getEXT1();
		String kjny = bean.getEXT2();
		String sql = "select * from DX_FPZJXX t where t.ssx='" + sss + "' and t.kjny='" + kjny + "'";
		FPZJXX_BEAN zjxx = pm.queryForObject(sql, FPZJXX_BEAN.class);
		
		String INFO = DxmbUtil.genSMS(zjxx, MBINFO);
		
		tobean.setLSH("FPZJ"+JobUtils.getLSH());
		tobean.setSJHM(bean.getPHONE());
		tobean.setSMSINFO(bean.getMSG());//NSRDX
		tobean.setLR_SJ(JobUtils.getFormatDate(bean.getJS_SJ(), "yyyy-MM-dd HH:mm:ss"));
		//		private String SWJGBM;//
		tobean.setKJNY(kjny);
		tobean.setSSX(sss);
		tobean.setFSZT_DM("06");//发送中
		tobean.setFS_SJ(GwinSoftUtil.getSysDate());
		tobean.setSMSINFO2(INFO);//SWDX
		pm.executeUpdate("INSERT INTO DX_FPZJCX(LSH,SJHM,SMSINFO,LR_SJ,BZ,SWJGBM,KJNY,SSX,FSZT_DM,FS_SJ,SMSINFO2) VALUES (?,?,?,?,?,?,?,?,?,?,?)", tobean);
		tobean.setSMSINFO(INFO);
		return tobean;
	}

	public static FPZWCX_BEAN SAVE_FPZWCX_BEAN(SMS_INBOX_BEAN bean, DBPersistenceManager pm) {
		FPZWCX_BEAN tobean = new FPZWCX_BEAN();

		String MBINFO = null;
		Map<String, Map<String, Object>> DXMB2_CACHE = CacheManager.getCache("DXMB2_CACHE");
		Iterator<String> it = DXMB2_CACHE.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if("FPZWCX".equals(key)) {
				MBINFO = DXMB2_CACHE.get(key).get("INFO").toString();
				break;
			}
		}
		String fpdm = bean.getEXT1();
		String fphm = bean.getEXT2();
		String sql = "SELECT F.*,B.NSRBM AS NSRSBM,B.NSRBM,B.NSRMC,B.ORG_DM AS SWJGBM,(F.FPQH||'-'||F.FPZH) AS FPHM FROM T_SMS_FPXX F,NSR_JBXX B WHERE F.NSRBM = B.NSRBM AND F.FPDM='"+fpdm+"' AND FPZH>='"+fphm+"' AND FPQH<='"+fphm+"'";
		FPXX_BEAN fpxx = pm.queryForObject(sql, FPXX_BEAN.class);
		
		tobean.setLSH("FPZW"+JobUtils.getLSH());
		tobean.setNSRSBM(fpxx.getNSRSBM());
		tobean.setNSRMC(fpxx.getNSRMC());
		tobean.setSJHM(bean.getPHONE());
		tobean.setSMSINFO(bean.getMSG());
		tobean.setLR_SJ(JobUtils.getFormatDate(bean.getJS_SJ(), "yyyy-MM-dd HH:mm:ss"));
		tobean.setFP_RQ(fpxx.getFP_RQ());
//		private String BZ;
		tobean.setFP_JE(fpxx.getFP_JE());
		tobean.setFPDM(fpdm);
		tobean.setFPHM(fphm);
		tobean.setFPZL(fpxx.getFPZL());
		tobean.setSWJGBM(fpxx.getSWJGBM());
		tobean.setFSZT_DM("06");
		tobean.setFS_SJ(GwinSoftUtil.getSysDate());
		String INFO = DxmbUtil.genSMS(fpxx, MBINFO);
		tobean.setSMSINFO2(INFO);
		pm.executeUpdate("INSERT INTO DX_FPZWCX(LSH,NSRSBM,NSRMC,SJHM,SMSINFO,LR_SJ,FP_RQ,BZ,FP_JE,FPDM,FPHM,FPZL,SWJGBM,FSZT_DM,FS_SJ,SMSINFO2) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", tobean);
		tobean.setSMSINFO(tobean.getSMSINFO2());
		return tobean;
	}

	public static SFCX_BEAN SAVE_SFCX_BEAN(SMS_INBOX_BEAN bean, DBPersistenceManager pm) {
		SFCX_BEAN tobean = new SFCX_BEAN();

		String MBINFO = null;
		Map<String, Map<String, Object>> DXMB2_CACHE = CacheManager.getCache("DXMB2_CACHE");
		Iterator<String> it = DXMB2_CACHE.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if("SFCX".equals(key)) {
				MBINFO = DXMB2_CACHE.get(key).get("INFO").toString();
				break;
			}
		}
		String NSRSBM = bean.getEXT1();
		String SSNY = bean.getEXT2();
		String SSRQ_Q = SSNY+"01";
		Date datestart = JobUtils.getFormatDate(SSRQ_Q, "yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(datestart);
		int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String SSRQ_Z = "" + SSNY+(day>10?day:("0"+day));
		String sql = "SELECT B.NSRBM,B.NSRMC,B.ORG_DM,B.FRSJH,B.CWJLSJH,B.BSRYSJH,A.SKSSQ,A.SKSSZ,A.ZSXM_DM,A.ZSPM_DM,A.SF_JE FROM T_SMS_SFXX A,NSR_JBXX B WHERE A.NSRSBM = B.NSRBM AND A.NSRSBM='"+NSRSBM+"' AND ((A.SKSSZ<=TO_DATE('"+SSRQ_Z+"','YYYYMMDD') AND A.SKSSZ>=TO_DATE('"+SSRQ_Q+"','YYYYMMDD')) OR (TO_DATE('"+SSRQ_Q+"','YYYYMMDD')<=A.SKSSQ AND A.SKSSQ<=TO_DATE('"+SSRQ_Z+"','YYYYMMDD')) OR (A.SKSSQ<=TO_DATE('"+SSRQ_Q+"','YYYYMMDD') AND TO_DATE('"+SSRQ_Z+"','YYYYMMDD')<=A.SKSSZ)) ORDER BY A.ZSXM_DM,A.ZSPM_DM";
		List<SFXX_BEAN> beans = pm.queryForList(sql, SFXX_BEAN.class);
		
		if(beans.size()>0) {
			tobean.setNSRMC(beans.get(0).getNSRMC());
			tobean.setSWJGBM(beans.get(0).getORG_DM());
		}
		String INFO = DxmbUtil.genSMS(beans, MBINFO);
		tobean.setLSH("SFCX"+JobUtils.getLSH());
		tobean.setNSRSBM(NSRSBM);
		double SF_JE = 0;
		for(SFXX_BEAN sfxx: beans) {
			SF_JE += sfxx.getSF_JE();
		}
		tobean.setSF_JE(SF_JE);
		tobean.setSJHM(bean.getPHONE());
		tobean.setSMSINFO(bean.getMSG());
		tobean.setLR_SJ(JobUtils.getFormatDate(bean.getJS_SJ(), "yyyy-MM-dd HH:mm:ss"));
		tobean.setSSNY(SSNY);
		
		tobean.setFSZT_DM("06");//发送中
		tobean.setFS_SJ(GwinSoftUtil.getSysDate());
		tobean.setSMSINFO2(INFO);
		pm.executeUpdate("INSERT INTO DX_SFCX(LSH,NSRSBM,NSRMC,SJHM,SMSINFO,LR_SJ,SSNY,BZ,SF_JE,SWJGBM,FSZT_DM,FS_SJ,SMSINFO2) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", tobean);
		//发短信时需要转换一下。
		tobean.setSMSINFO(tobean.getSMSINFO2());
		return tobean;
	}

	public static void SAVE_WJDCMX_BEAN(SMS_INBOX_BEAN bean, DBPersistenceManager pm) {
		WJDCMX_BEAN tobean = new WJDCMX_BEAN();
		//如何通过问卷调查回复内容确定隶属于是哪次问卷调查
		tobean.setSMSINFO(bean.getMSG());
		tobean.setFK_SJ(JobUtils.getFormatDate(bean.getJS_SJ(), "yyyy-MM-dd HH:mm:ss"));
		String sql = "UPDATE DX_WJDCMX SET SMSINFO=?,FK_SJ=? WHERE ZB_LSH='"+bean.getEXT5()+"' AND SJHM='"+bean.getPHONE()+"'";
		pm.executeUpdate(sql, tobean);
	}

	public static void SAVE_TSJBCX_BEAN(SMS_INBOX_BEAN bean, DBPersistenceManager pm) {
		TSJBCX_BEAN tobean = new TSJBCX_BEAN();
		tobean.setLSH("TSJB"+JobUtils.getLSH());
		tobean.setSJHM(bean.getPHONE());
		tobean.setTSINFO(bean.getMSG());
		tobean.setTS_SJ(JobUtils.getFormatDate(bean.getJS_SJ(), "yyyy-MM-dd HH:mm:ss"));
		tobean.setHF_BJ("0");
		pm.executeUpdate("INSERT INTO DX_TSJBCX(LSH,SJHM,TSINFO,TS_SJ,HF_BJ) VALUES (?,?,?,?,?)", tobean);
	}
}