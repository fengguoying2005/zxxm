package com.gwinsoft.components2.yw.sfcx;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class SfcxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DX_SFCX WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Sfcx sfcx = pm.queryForObject(sql, Sfcx.class, new Object[] { bh });
		this.putData("sfcx", sfcx);
	}
}
