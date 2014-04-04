package com.gwinsoft.components2.dxmb;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class Dxmb2DelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("DXMB_LSH");
		String sql = "DELETE FROM SMS_DXMB WHERE DXMB_LSH=?";
		DBPersistenceManager pm = this.getPM();
		pm.executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
		CacheServlet.freshCache("DXMB_CACHE", pm);
	}
}
