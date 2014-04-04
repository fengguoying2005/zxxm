package com.gwinsoft.components2.yw.cjtx;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CjtxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DX_SFCJTX WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Cjtx cjtx = pm.queryForObject(sql, Cjtx.class, new Object[] { bh });
		try {
			GwinSoftUtil.translate(cjtx, "FSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("cjtx", cjtx);
	}
}
