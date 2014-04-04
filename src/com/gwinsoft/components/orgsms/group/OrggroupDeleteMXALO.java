package com.gwinsoft.components.orgsms.group;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class OrggroupDeleteMXALO extends BaseALO {

	protected void doService() {
		String GROUP_LSH = this.getData("GROUP_LSH");
		String KEYIDS = this.getData("KEYIDS");
		KEYIDS = KEYIDS.replaceAll(",", "','");
		DBPersistenceManager pm = this.getPM();
		String sql = "DELETE FROM ORG_GROUPMX WHERE GROUPMX_LSH IN ('"+KEYIDS+"')";
		int n = pm.executeUpdate(sql);
		pm.executeUpdate("UPDATE ORG_GROUP SET GROUP_COUNT=GROUP_COUNT-"+n+" WHERE GROUP_LSH='"+GROUP_LSH+"'");
		CacheServlet.freshCache("ORGGROUP_CACHE", pm);
		CacheServlet.freshCache("ORGGROUPMX_CACHE", pm);
	}

}
