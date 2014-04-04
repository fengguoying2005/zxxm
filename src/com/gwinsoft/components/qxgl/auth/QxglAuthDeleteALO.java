package com.gwinsoft.components.qxgl.auth;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;

public class QxglAuthDeleteALO extends BaseALO {

	protected void doService() {
		String authdm = getData("authdm");
		this.getPM().executeUpdate(
				"DELETE FROM QX_AUTH WHERE AUTH_DM=? OR SJ_AUTH_DM=?",
				new String[] { authdm, authdm });
		CacheServlet.freshCache("AUTH_CACHE", this.getPM());
	}
}