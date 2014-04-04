package com.gwinsoft.components2.yw.cbtx;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CbtxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM DX_SFCBTX WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Cbtx cbtx = pm.queryForObject(sql, Cbtx.class, new Object[] { bh });
		try {
			GwinSoftUtil.translate(cbtx, "FSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("cbtx", cbtx);
	}
}
