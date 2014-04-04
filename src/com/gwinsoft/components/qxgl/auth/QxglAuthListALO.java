package com.gwinsoft.components.qxgl.auth;

import java.util.List;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class QxglAuthListALO extends BaseALO {

	protected void doService() {
		DBPersistenceManager pm = this.getPM();
		String sql = "SELECT AUTH_LJ,TARGET,AUTH_MC,AUTH_DM,AUTH_CC,AUTH_MS,YX_BJ,SJ_AUTH_DM,XS_SX FROM QX_AUTH WHERE AUTH_CC='1' ORDER BY XS_SX";
		List<Auth> auths = pm.queryForList(sql, Auth.class);
		this.putData("auths", auths);
	}
}