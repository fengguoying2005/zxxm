package com.gwinsoft.components.nsrgl.nsrgroup;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class NsrgroupDelMXALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("LSH");
		String KEYIDS = this.getData("KEYIDS");
		KEYIDS = KEYIDS.replaceAll(",", "','");
		DBPersistenceManager pm = this.getPM();
		String sql = "DELETE FROM NSR_GROUPMX WHERE GROUPMX_LSH IN ('"+KEYIDS+"')";
		int n = pm.executeUpdate(sql);
		pm.executeUpdate("UPDATE NSR_GROUP SET GROUP_COUNT=GROUP_COUNT-"+n+" WHERE LSH='"+LSH+"'");
	}

}
