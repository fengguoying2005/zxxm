package com.gwinsoft.components.qxgl.role;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;

public class QxglRoleDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("ROLE_LSH");
		String sql = "DELETE FROM QX_ROLE WHERE ROLE_LSH=?";
		this.getPM().executeUpdate(sql, new Object[] { BH });
		this.getPM().executeUpdate("DELETE FROM QX_ROLE_AUTH WHERE ROLE_LSH=?", new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
		CacheServlet.freshCache("ROLE_AUTH_CACHE", this.getPM());
		CacheServlet.freshCache("ROLE_CACHE", this.getPM());
	}
}