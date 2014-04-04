package com.gwinsoft.components.nsrgl.nsrgroup;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class NsrgroupListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		User user = this.getData("user");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,T.* FROM NSR_GROUP T WHERE ORG_DM='"+user.getORG_DM_JG()+"' ");
		String FIND_KS_RQ = reqEnv.getData("FIND_KS_RQ");
		String FIND_JZ_RQ = reqEnv.getData("FIND_JZ_RQ");
		if(FIND_KS_RQ != null && !"".equals(FIND_KS_RQ.trim())) {
			sql.append(" AND TO_CHAR(LR_SJ,'YYYY-MM-DD')>='"+FIND_KS_RQ+"'");
		}
		if(FIND_JZ_RQ != null && !"".equals(FIND_JZ_RQ.trim())) {
			sql.append(" AND TO_CHAR(LR_SJ,'YYYY-MM-DD')<='"+FIND_JZ_RQ+"'");
		}
		String FIND_GROUP_NAME = reqEnv.getData("FIND_GROUP_NAME");
		if(FIND_GROUP_NAME != null && !"".equals(FIND_GROUP_NAME.trim())) {
			sql.append(" AND GROUP_NAME LIKE '%"+FIND_GROUP_NAME+"%'");
		}
		String FIND_INFO = reqEnv.getData("FIND_INFO");
		if(FIND_INFO != null && !"".equals(FIND_INFO.trim())) {
			sql.append(" AND INFO LIKE '%"+FIND_INFO+"%'");
		}
		Object[] args = new Object[] {};
		sql.append(" ORDER BY LR_SJ DESC");
		List<Nsrgroup> nsrgroups = null;
		if(pageBean!=null) {
			nsrgroups = pm.queryPageList(sql.toString() , Nsrgroup.class, args , pageBean);
		} else {
			nsrgroups = pm.queryForList(sql.toString() , Nsrgroup.class, args);
		}
		try {
			GwinSoftUtil.translate(nsrgroups, "ORG_DM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(nsrgroups, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(nsrgroups, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("nsrgroups", nsrgroups);
	}
}
