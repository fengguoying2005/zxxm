package com.gwinsoft.components2.yw.wjdc;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class WjdcDelMxALO extends BaseALO {

	protected void doService() {
		String KEYIDS = this.getData("KEYIDS");
		String LSH = this.getData("LSH");

		KEYIDS = (KEYIDS==null?"":(KEYIDS.trim().replaceAll(" ", "").replaceAll(",", "','")));
		DBPersistenceManager pm = this.getPM();
		int n = pm.executeUpdate("DELETE FROM DX_WJDCMX WHERE ZB_LSH='"+LSH+"' AND LSH IN ('"+KEYIDS+"')");
		String message = "删除了"+n+"条参与人。";
		resEnv.putData("message", message);
	}
}