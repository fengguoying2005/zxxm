package com.gwinsoft.components.qxgl.role;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class QxglRoleLoadALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM QX_ROLE WHERE ROLE_LSH=?";
		String bh = reqEnv.getData("ROLE_LSH");
		DBPersistenceManager pm = this.getPM();
		Role role = pm.queryForObject(sql, Role.class, new Object[] { bh });
		this.putData("role", role);
	}
}