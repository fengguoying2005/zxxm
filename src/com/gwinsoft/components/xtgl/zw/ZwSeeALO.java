package com.gwinsoft.components.xtgl.zw;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class ZwSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DM_ZW WHERE ZW_DM=?";
		String bh = reqEnv.getData("ZW_DM");
		DBPersistenceManager pm = this.getPM();
		Zw zw = pm.queryForObject(sql, Zw.class, new Object[] { bh });
		this.putData("zw", zw);
	}
}
