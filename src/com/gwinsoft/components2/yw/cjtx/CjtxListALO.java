package com.gwinsoft.components2.yw.cjtx;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class CjtxListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,LSH,NSRSBM,NSRMC,SJHM,SMSINFO,TO_CHAR(JKQX_RQ,'YYYY-MM-DD') AS JKQX_RQ,LR_SJ,SKSSQ,BZ,SWJGBM,FSZT_DM,FS_SJ,YZPZXH,SKSSZ,SF_JE,CNT,ZSXM_DMS,ZSPM_DMS,SF_JES,SJLX FROM DX_SFCJTX T WHERE 1=1 ");
		String FIND_SWJGBM = reqEnv.getData("FIND_SWJGBM");
		if(FIND_SWJGBM != null && !"".equals(FIND_SWJGBM.trim())) {
			sql.append(" AND SWJGBM = '"+FIND_SWJGBM+"'");
		}
		String FIND_NSRSBM = reqEnv.getData("FIND_NSRSBM");
		if(FIND_NSRSBM != null && !"".equals(FIND_NSRSBM.trim())) {
			sql.append(" AND NSRSBM LIKE '%"+FIND_NSRSBM+"%'");
		}
		String FIND_NSRMC = reqEnv.getData("FIND_NSRMC");
		if(FIND_NSRMC != null && !"".equals(FIND_NSRMC.trim())) {
			sql.append(" AND NSRMC LIKE '%"+FIND_NSRMC+"%'");
		}
		String FIND_SJLX = reqEnv.getData("FIND_SJLX");
		if(FIND_SJLX != null && !"".equals(FIND_SJLX.trim()) && !"ALL".equals(FIND_SJLX.trim())) {
			sql.append(" AND SJLX = '"+FIND_SJLX+"'");
		}

		String FIND_KS_RQ = reqEnv.getData("FIND_KS_RQ");
		if(FIND_KS_RQ != null && !"".equals(FIND_KS_RQ.trim())) {
			sql.append(" AND JKQX_RQ >= TO_DATE('"+FIND_KS_RQ+"','YYYY-MM-DD')");
		}
		String FIND_JZ_RQ = reqEnv.getData("FIND_JZ_RQ");
		if(FIND_JZ_RQ != null && !"".equals(FIND_JZ_RQ.trim())) {
			sql.append(" AND JKQX_RQ < (TO_DATE('"+FIND_JZ_RQ+"','YYYY-MM-DD')+1)");
		}
		Object[] args = new Object[] {};
		List<Cjtx> cjtxs = null;
		if(pageBean!=null) {
			cjtxs = pm.queryPageList(sql.toString() , Cjtx.class, args , pageBean);
		} else {
			cjtxs = pm.queryForList(sql.toString() , Cjtx.class, args);
		}
		try {
			GwinSoftUtil.translate(cjtxs, "SWJGBM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(cjtxs, "SJLX", DMB.getTranslateStr(DMB.getDMB("JSRLX_CACHE", "JSRLX_MC")));
			GwinSoftUtil.translate(cjtxs, "FSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("cjtxs", cjtxs);
	}
}
