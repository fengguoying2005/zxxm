package com.gwinsoft.components.tjfx.business;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class BusinessSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM SMS_NSRDATA WHERE NSRDATA_LSH=?";
		String bh = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
		Business business = pm.queryForObject(sql, Business.class, new Object[] { bh });

		try {
			GwinSoftUtil.translate(business, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("business", business);
//		String sql2 = "SELECT * FROM SMS_NSRDATAMX WHERE NSRDATA_LSH=?";
//		List<NSRDATAMX> tablemx = pm.queryForList(sql2, NSRDATAMX.class, new String[]{bh});
//		this.putData("tablemx", tablemx);
	}
}
