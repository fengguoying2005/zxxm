package com.gwinsoft.components.orgsms.group;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;

public class OrggroupDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("GROUP_LSH");
		String sql = "DELETE FROM ORG_GROUP WHERE GROUP_LSH=?";
		String sql2 = "DELETE FROM ORG_GROUPMX WHERE GROUP_LSH=?";
		this.getPM().executeUpdate(sql2, new Object[] { BH });
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		CacheServlet.freshCache("ORGGROUP_CACHE", this.getPM());
		CacheServlet.freshCache("ORGGROUPMX_CACHE", this.getPM());
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
