package com.gwinsoft.components.qxgl.user;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class UserDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("USER_DM");
		String sql = "DELETE FROM QX_USER WHERE USER_DM=?";
		DBPersistenceManager pm = this.getPM();
		pm.executeUpdate(sql, new Object[] { BH });
		pm.executeUpdate("DELETE FROM QX_USER_ROLE WHERE USER_DM=?", new Object[] { BH });
		pm.executeUpdate("DELETE FROM QX_USER_AUTH WHERE USER_DM=?", new Object[] { BH });
		String message = "";
		message="删除成功！";
		CacheServlet.freshCache("USER_CACHE", pm);
		resEnv.putData("message", message);
	}
}
