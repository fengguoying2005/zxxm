package com.gwinsoft.components.qxgl.user2;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class User2SeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM QX_USER WHERE USER_DM=?";
		String bh = reqEnv.getData("USER_DM");
		DBPersistenceManager pm = this.getPM();
		User user = pm.queryForObject(sql, User.class, new Object[] { bh });
		this.putData("user", user);
	}
}
