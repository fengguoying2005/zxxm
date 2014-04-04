package com.gwinsoft.components2.yw.jktx;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class JktxListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,T.* FROM DX_JKTX T WHERE 1=1 ");
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
		String FIND_SBQX = reqEnv.getData("FIND_SBQX");
		if(FIND_SBQX != null && !"".equals(FIND_SBQX.trim())) {
			sql.append(" AND JK_RQ=TO_DATE('"+FIND_SBQX+"','YYYY-MM-DD')");
		}
		String FIND_JKLX_DM = reqEnv.getData("FIND_JKLX_DM");
		if(FIND_JKLX_DM != null && !"".equals(FIND_JKLX_DM.trim())) {
			sql.append(" AND JKLX_DM = '"+FIND_JKLX_DM+"'");
		}
		Object[] args = new Object[] {};
		List<Jktx> jktxs = null;
		if(pageBean!=null) {
			jktxs = pm.queryPageList(sql.toString() , Jktx.class, args , pageBean);
		} else {
			jktxs = pm.queryForList(sql.toString() , Jktx.class, args);
		}
		try {
			GwinSoftUtil.translate(jktxs, "SWJGBM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(jktxs, "SJLX", DMB.getTranslateStr(DMB.getDMB("JSRLX_CACHE", "JSRLX_MC")));
			GwinSoftUtil.translate(jktxs, "JKLX_DM", DMB.getTranslateStr(DMB.getDMB("JKLX_CACHE", "JKLX_MC")));
			GwinSoftUtil.translate(jktxs, "FSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("jktxs", jktxs);
	}
}
