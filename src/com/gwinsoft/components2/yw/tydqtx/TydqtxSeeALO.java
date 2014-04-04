package com.gwinsoft.components2.yw.tydqtx;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class TydqtxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DX_TYDQTX WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Tydqtx tydqtx = pm.queryForObject(sql, Tydqtx.class, new Object[] { bh });
		try {
			GwinSoftUtil.translate(tydqtx, "FSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("tydqtx", tydqtx);
	}
}
