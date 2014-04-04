package com.gwinsoft.components2.yw.kksbtx;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class KksbtxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DX_KKSBTX WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Kksbtx kksbtx = pm.queryForObject(sql, Kksbtx.class, new Object[] { bh });
		try {
			GwinSoftUtil.translate(kksbtx, "FSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("kksbtx", kksbtx);
	}
}
