package com.gwinsoft.components2.yw.sfcx;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class SfcxListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,T.* FROM DX_SFCX T WHERE 1=1 ");
		String FIND_SWJGBM = reqEnv.getData("FIND_SWJGBM");
		if(FIND_SWJGBM != null && !"".equals(FIND_SWJGBM.trim())) {
			sql.append(" AND SWJGBM = '"+FIND_SWJGBM+"'");
		}
		String FIND_SSNY = reqEnv.getData("FIND_SSNY");
		if(FIND_SSNY != null && !"".equals(FIND_SSNY.trim())) {
			sql.append(" AND SSNY LIKE '%"+FIND_SSNY+"%'");
		}
		String FIND_NSRSBM = reqEnv.getData("FIND_NSRSBM");
		if(FIND_NSRSBM != null && !"".equals(FIND_NSRSBM.trim())) {
			sql.append(" AND NSRSBM LIKE '%"+FIND_NSRSBM+"%'");
		}
		String FIND_NSRMC = reqEnv.getData("FIND_NSRMC");
		if(FIND_NSRMC != null && !"".equals(FIND_NSRMC.trim())) {
			sql.append(" AND NSRMC LIKE '%"+FIND_NSRMC+"%'");
		}
		String FIND_SJHM = reqEnv.getData("FIND_SJHM");
		if(FIND_SJHM != null && !"".equals(FIND_SJHM.trim())) {
			sql.append(" AND SJHM LIKE '%"+FIND_SJHM+"%'");
		}
		String FIND_LR_SJ = reqEnv.getData("FIND_LR_SJ");
		if(FIND_LR_SJ != null && !"".equals(FIND_LR_SJ.trim())) {
			sql.append(" AND LR_SJ >= TO_DATE('"+FIND_LR_SJ+"','YYYY-MM-DD')");
		}
		String FIND_LR_SJ2 = reqEnv.getData("FIND_LR_SJ2");
		if(FIND_LR_SJ2 != null && !"".equals(FIND_LR_SJ2.trim())) {
			sql.append(" AND LR_SJ < (TO_DATE('"+FIND_LR_SJ2+"','YYYY-MM-DD')+1)");
		}
		Object[] args = new Object[] {};
		List<Sfcx> sfcxs = null;
		if(pageBean!=null) {
			sfcxs = pm.queryPageList(sql.toString() , Sfcx.class, args , pageBean);
		} else {
			sfcxs = pm.queryForList(sql.toString() , Sfcx.class, args);
		}
		try {
			GwinSoftUtil.translate(sfcxs, "SWJGBM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(sfcxs, "FSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("sfcxs", sfcxs);
	}
}
