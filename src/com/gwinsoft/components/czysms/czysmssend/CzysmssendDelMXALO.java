package com.gwinsoft.components.czysms.czysmssend;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class CzysmssendDelMXALO extends BaseALO {

	protected void doService() {
		String KEYIDS = this.getData("KEYIDS");
		KEYIDS = KEYIDS.replaceAll(",", "','");
		DBPersistenceManager pm = this.getPM();
		String sql = "DELETE FROM SMS_ORGMSG WHERE NSRDATAMX_LSH IN ('"+KEYIDS+"')";
		pm.executeUpdate(sql);
	}

}
