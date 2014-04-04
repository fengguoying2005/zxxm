package com.gwinsoft.components.qxgl.user;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class UserSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM QX_USER WHERE USER_DM=?";
		String bh = reqEnv.getData("USER_DM");
		DBPersistenceManager pm = this.getPM();
		User user = pm.queryForObject(sql, User.class, new Object[] { bh });
		this.putData("user", user);
	}
}
