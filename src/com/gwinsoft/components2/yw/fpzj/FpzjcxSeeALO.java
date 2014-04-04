package com.gwinsoft.components2.yw.fpzj;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class FpzjcxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DX_FPZJCX WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Fpzjcx fpzjcx = pm.queryForObject(sql, Fpzjcx.class, new Object[] { bh });
		this.putData("fpzjcx", fpzjcx);
	}
}
