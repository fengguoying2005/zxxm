package com.gwinsoft.components.xtgl.dxqm;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class DxqmSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DM_DXQM WHERE DXQM_DM=?";
		String bh = reqEnv.getData("DXQM_DM");
		DBPersistenceManager pm = this.getPM();
		Dxqm dxqm = pm.queryForObject(sql, Dxqm.class, new Object[] { bh });
		this.putData("dxqm", dxqm);
	}
}
