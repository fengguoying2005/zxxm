package com.gwinsoft.components.xtgl.dxmb;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class DxmbSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM SMS_DXMB WHERE DXMB_LSH=?";
		String bh = reqEnv.getData("DXMB_LSH");
		DBPersistenceManager pm = this.getPM();
		Dxmb dxmb = pm.queryForObject(sql, Dxmb.class, new Object[] { bh });
		this.putData("dxmb", dxmb);
	}
}
