package com.gwinsoft.components.xtgl.dxcs;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class DxcsSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DM_DXCS WHERE YYS_DM=?";
		String bh = reqEnv.getData("YYS_DM");
		DBPersistenceManager pm = this.getPM();
		Dxcs dxcs = pm.queryForObject(sql, Dxcs.class, new Object[] { bh });
		this.putData("dxcs", dxcs);
	}
}
