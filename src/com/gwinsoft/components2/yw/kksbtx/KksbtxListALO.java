package com.gwinsoft.components2.yw.kksbtx;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class KksbtxListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,T.* FROM DX_KKSBTX T WHERE 1=1 ");
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
			sql.append(" AND FS_SJ >= TO_DATE('"+FIND_KS_RQ+"','YYYY-MM-DD')");
		}
		String FIND_JZ_RQ = reqEnv.getData("FIND_JZ_RQ");
		if(FIND_JZ_RQ != null && !"".equals(FIND_JZ_RQ.trim())) {
			sql.append(" AND FS_SJ < (TO_DATE('"+FIND_JZ_RQ+"','YYYY-MM-DD')+1)");
		}
		Object[] args = new Object[] {};
		List<Kksbtx> kksbtxs = null;
		if(pageBean!=null) {
			kksbtxs = pm.queryPageList(sql.toString() , Kksbtx.class, args , pageBean);
		} else {
			kksbtxs = pm.queryForList(sql.toString() , Kksbtx.class, args);
		}
		try {
			GwinSoftUtil.translate(kksbtxs, "SWJGBM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(kksbtxs, "SJLX", DMB.getTranslateStr(DMB.getDMB("JSRLX_CACHE", "JSRLX_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("kksbtxs", kksbtxs);
	}
}
