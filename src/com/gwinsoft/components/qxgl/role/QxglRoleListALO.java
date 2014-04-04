package com.gwinsoft.components.qxgl.role;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class QxglRoleListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.ROLE_LSH AS HID_LSH,T.* FROM QX_ROLE T WHERE 1=1 ");
		String FIND_ROLE_MC = reqEnv.getData("FIND_ROLE_MC");
		if(FIND_ROLE_MC != null && !"".equals(FIND_ROLE_MC.trim())) {
			sql.append(" AND ROLE_MC LIKE '%"+FIND_ROLE_MC+"%'");
		}
		
		String FIND_ROLE_MS = reqEnv.getData("FIND_ROLE_MS");
		if(FIND_ROLE_MS != null && !"".equals(FIND_ROLE_MS.trim())) {
			sql.append(" AND ROLE_MS LIKE '%"+FIND_ROLE_MS+"%'");
		}
		String FIND_YX_BJ = reqEnv.getData("FIND_YX_BJ");
		if(FIND_YX_BJ != null && !"".equals(FIND_YX_BJ.trim())) {
			sql.append(" AND YX_BJ = '"+FIND_YX_BJ+"'");
		}
		User user = this.getData("user");
		String FIND_ORG_DM_JG = user.getORG_DM_JG();
		if(FIND_ORG_DM_JG != null && !"".equals(FIND_ORG_DM_JG.trim())) {
			sql.append(" AND ORG_DM_JG = '"+FIND_ORG_DM_JG+"'");
		}
		Object[] args = new Object[] {};
		List<Role> roles = null;
		if(pageBean!=null) {
			roles = pm.queryPageList(sql.toString() , Role.class, args , pageBean);
		} else {
			roles = pm.queryForList(sql.toString() , Role.class, args);
		}
		try {
			GwinSoftUtil.translate(roles, "ORG_DM_JG", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(roles, "YX_BJ", "0:无效;1:有效;");
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("roles", roles);
	}
}
