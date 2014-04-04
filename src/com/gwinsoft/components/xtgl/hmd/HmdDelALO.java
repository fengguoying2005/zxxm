package com.gwinsoft.components.xtgl.hmd;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class HmdDelALO extends BaseALO {

	protected void doService() {
		String KEYIDS = this.getData("KEYIDS");
		KEYIDS = KEYIDS.replaceAll(",", "','");
		DBPersistenceManager pm = this.getPM();
		String sql = "DELETE FROM T_HMD WHERE LSH IN ('"+KEYIDS+"')";
		pm.executeUpdate(sql);
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
	}
}