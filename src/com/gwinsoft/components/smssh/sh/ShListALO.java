package com.gwinsoft.components.smssh.sh;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrsms.group.Group;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class ShListALO extends BaseALO {

	protected void doService() {
		User user = this.getData("user");
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.NSRDATA_LSH AS HID_LSH,T.* FROM SMS_NSRDATA T WHERE SMSZT_DM IN('03','04','06','07') AND SHY='"+user.getUSER_DM()+"' ");
		String FIND_SMSTYPE_DM = reqEnv.getData("FIND_SMSTYPE_DM");
		if(FIND_SMSTYPE_DM != null && !"".equals(FIND_SMSTYPE_DM.trim())) {
			sql.append(" AND SMSTYPE_DM LIKE '%"+FIND_SMSTYPE_DM+"%'");
		}
		String FIND_SMSZT_DM = reqEnv.getData("FIND_SMSZT_DM");
		if(FIND_SMSZT_DM != null && !"".equals(FIND_SMSZT_DM.trim())) {
			sql.append(" AND SMSZT_DM = '"+FIND_SMSZT_DM+"'");
		}
		String FIND_GROUP_NAME = reqEnv.getData("FIND_GROUP_NAME");
		if(FIND_GROUP_NAME != null && !"".equals(FIND_GROUP_NAME.trim())) {
			sql.append(" AND GROUP_NAME LIKE '%"+FIND_GROUP_NAME+"%'");
		}
		String FIND_ORG_DM_JG = user.getORG_DM_JG();
		if(FIND_ORG_DM_JG != null && !"".equals(FIND_ORG_DM_JG.trim())) {
			sql.append(" AND ORG_DM_JG = '"+FIND_ORG_DM_JG+"'");
		}

		String FIND_KS_RQ = reqEnv.getData("FIND_KS_RQ");
		if(FIND_KS_RQ != null && !"".equals(FIND_KS_RQ.trim())) {
			sql.append(" AND TO_CHAR(LR_SJ,'YYYY-MM-DD HH24:MI:SS')>='"+FIND_KS_RQ+"'");
		}
		String FIND_JZ_RQ = reqEnv.getData("FIND_JZ_RQ");
		if(FIND_JZ_RQ != null && !"".equals(FIND_JZ_RQ.trim())) {
			sql.append(" AND TO_CHAR(LR_SJ,'YYYY-MM-DD HH24:MI:SS')<='"+FIND_JZ_RQ+" 23:59:59'");
		}
		Object[] args = new Object[] {};
		List<Group> groups = null;
		sql.append(" ORDER BY LR_SJ DESC");
		if(pageBean!=null) {
			groups = pm.queryPageList(sql.toString() , Group.class, args , pageBean);
		} else {
			groups = pm.queryForList(sql.toString() , Group.class, args);
		}
		try {
			GwinSoftUtil.translate(groups, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC")));
			GwinSoftUtil.translate(groups, "ORG_DM_JG", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(groups, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(groups, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(groups, "SMSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("groups", groups);
	}
}
