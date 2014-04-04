package com.gwinsoft.components.qxgl.org;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;

public class OrgDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("ORG_DM");
		String sql = "DELETE FROM QX_ORG WHERE ORG_DM=?";
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		CacheServlet.freshCache("ORG_CACHE", this.getPM());
		resEnv.putData("message", message);
	}
}
