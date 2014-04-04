package com.gwinsoft.components.xtgl.dxqm;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class DxqmDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("DXQM_DM");
		String sql = "DELETE FROM DM_DXQM WHERE DXQM_DM=?";
		DBPersistenceManager pm = this.getPM();
		pm .executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		CacheServlet.freshCache("DXQM_CACHE", pm);
		resEnv.putData("message", message);
	}
}
