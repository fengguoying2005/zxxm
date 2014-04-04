package com.gwinsoft.components.czysms.group;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class CzygroupListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.GROUP_LSH AS HID_LSH,T.* FROM CZY_GROUP T WHERE 1=1 ");
		String FIND_GROUP_NAME = reqEnv.getData("FIND_GROUP_NAME");
		if(FIND_GROUP_NAME != null && !"".equals(FIND_GROUP_NAME.trim())) {
			sql.append(" AND GROUP_NAME LIKE '%"+FIND_GROUP_NAME+"%'");
		}
		String FIND_GROUP_COUNT = reqEnv.getData("FIND_GROUP_COUNT");
		if(FIND_GROUP_COUNT != null && !"".equals(FIND_GROUP_COUNT.trim())) {
			sql.append(" AND GROUP_COUNT LIKE '%"+FIND_GROUP_COUNT+"%'");
		}
		User loginuser = this.getData("user");
		/*Map jg = DMB.getOrgDMB(loginuser.getORG_DM_JG(), 1, "J");
		if(jg!=null) {
			sql.append(" AND ORG_DM_JG IN (''");
			Iterator it = jg.keySet().iterator();
			while(it.hasNext()) {
				String swjg = (String) it.next();
				sql.append(",'").append(swjg).append("'");
			}
			sql.append(")");
		}*/
		sql.append(" AND ORG_DM_JG = '"+loginuser.getORG_DM_JG()+"'");
		Object[] args = new Object[] {};
		List<Czygroup> orggroups = null;
		if(pageBean!=null) {
			orggroups = pm.queryPageList(sql.toString() , Czygroup.class, args , pageBean);
		} else {
			orggroups = pm.queryForList(sql.toString() , Czygroup.class, args);
		}
		try {
			GwinSoftUtil.translate(orggroups, "ORG_DM_JG", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(orggroups, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(orggroups, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("orggroups", orggroups);
	}
}
