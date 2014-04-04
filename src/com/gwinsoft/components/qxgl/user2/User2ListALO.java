package com.gwinsoft.components.qxgl.user2;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class User2ListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.USER_DM AS HID_LSH,T.* FROM QX_USER T WHERE CZY_BJ='0' ");
		String FIND_USER_DM = reqEnv.getData("FIND_USER_DM");
		if(FIND_USER_DM != null && !"".equals(FIND_USER_DM.trim())) {
			sql.append(" AND USER_DM = '"+FIND_USER_DM+"'");
		}
		String FIND_USER_MC = reqEnv.getData("FIND_USER_MC");
		if(FIND_USER_MC != null && !"".equals(FIND_USER_MC.trim())) {
			sql.append(" AND USER_MC LIKE '%"+FIND_USER_MC+"%'");
		}
		String FIND_ORG_DM_JG = reqEnv.getData("FIND_ORG_DM_JG");
		if(FIND_ORG_DM_JG != null && !"".equals(FIND_ORG_DM_JG.trim())) {
			sql.append(" AND ORG_DM_JG = '"+FIND_ORG_DM_JG+"'");
		}
		String FIND_ORG_DM_BM = reqEnv.getData("FIND_ORG_DM_BM");
		if(FIND_ORG_DM_BM != null && !"".equals(FIND_ORG_DM_BM.trim())) {
			sql.append(" AND ORG_DM_BM = '"+FIND_ORG_DM_BM+"'");
		}
		User loginuser = this.getData("loginuser");
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
		Object[] args = new Object[] {};
		List<User> users = null;
		if(pageBean!=null) {
			users = pm.queryPageList(sql.toString() , User.class, args , pageBean);
		} else {
			users = pm.queryForList(sql.toString() , User.class, args);
		}
		try {
			GwinSoftUtil.translate(users, "XB_DM", DMB.getTranslateStr(DMB.getDMB("XB_CACHE", "XB_MC")));
			GwinSoftUtil.translate(users, "ORG_DM_JG", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(users, "ORG_DM_BM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(users, "YX_BJ", "0:无效;1:有效;");
			GwinSoftUtil.translate(users, "ZW_DM", DMB.getTranslateStr(DMB.getDMB("ZW_CACHE", "ZW_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("users", users);
	}
}
