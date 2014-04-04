package com.gwinsoft.components2.yw.jktx;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class JktxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DX_JKTX WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Jktx jktx = pm.queryForObject(sql, Jktx.class, new Object[] { bh });
		try {
			GwinSoftUtil.translate(jktx, "FSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("jktx", jktx);
	}
}
