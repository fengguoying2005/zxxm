package com.gwinsoft.components2.yw.fpzw;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class FpzwcxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DX_FPZWCX WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Fpzwcx fpzwcx = pm.queryForObject(sql, Fpzwcx.class, new Object[] { bh });
		this.putData("fpzwcx", fpzwcx);
	}
}
