package com.gwinsoft.components.tjfx.business;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class BusinessListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.NSRDATA_LSH AS HID_LSH,T.* FROM SMS_NSRDATA T WHERE 1=1 ");
		String FIND_ORG_DM_JG = reqEnv.getData("FIND_ORG_DM_JG");
		if(FIND_ORG_DM_JG != null && !"".equals(FIND_ORG_DM_JG.trim())) {
			sql.append(" AND ORG_DM_JG = '"+FIND_ORG_DM_JG+"'");
		} else {
			User loginuser = this.getData("user");
			Map jg = DMB.getOrgDMB(loginuser.getORG_DM_JG(), 1, "J");
			if(jg!=null) {
				sql.append(" AND ORG_DM_JG IN (''");
				Iterator it = jg.keySet().iterator();
				while(it.hasNext()) {
					String swjg = (String) it.next();
					sql.append(",'").append(swjg).append("'");
				}
				sql.append(")");
			}
		}
		String FIND_SMSTYPE_DM = reqEnv.getData("FIND_SMSTYPE_DM");
		if(FIND_SMSTYPE_DM != null && !"".equals(FIND_SMSTYPE_DM.trim())) {
			sql.append(" AND SMSTYPE_DM = '"+FIND_SMSTYPE_DM+"'");
		}
		String FIND_GROUP_NAME = reqEnv.getData("FIND_GROUP_NAME");
		if(FIND_GROUP_NAME != null && !"".equals(FIND_GROUP_NAME.trim())) {
			sql.append(" AND GROUP_NAME LIKE '%"+FIND_GROUP_NAME+"%'");
		}
		String FIND_SMSZT_DM = reqEnv.getData("FIND_SMSZT_DM");
		if(FIND_SMSZT_DM != null && !"".equals(FIND_SMSZT_DM.trim())) {
			sql.append(" AND SMSZT_DM = '"+FIND_SMSZT_DM+"'");
		}

		String FIND_KS_RQ = reqEnv.getData("FIND_KS_RQ");
		if(FIND_KS_RQ != null && !"".equals(FIND_KS_RQ.trim())) {
			sql.append(" AND TO_CHAR(LR_SJ,'YYYY-MM-DD HH24:MI:SS')>='"+FIND_KS_RQ+"'");
		}
		String FIND_JZ_RQ = reqEnv.getData("FIND_JZ_RQ");
		if(FIND_JZ_RQ != null && !"".equals(FIND_JZ_RQ.trim())) {
			sql.append(" AND TO_CHAR(LR_SJ,'YYYY-MM-DD HH24:MI:SS')<='"+FIND_JZ_RQ+" 23:59:59'");
		}
		sql.append(" ORDER BY LR_SJ DESC");
		Object[] args = new Object[] {};
		List<Business> businesss = null;
		if(pageBean!=null) {
			businesss = pm.queryPageList(sql.toString() , Business.class, args , pageBean);
		} else {
			businesss = pm.queryForList(sql.toString() , Business.class, args);
		}
		try {
			GwinSoftUtil.translate(businesss, "ORG_DM_JG", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(businesss, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC")));
			GwinSoftUtil.translate(businesss, "SMSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
			GwinSoftUtil.translate(businesss, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(businesss, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("businesss", businesss);
	}
}
