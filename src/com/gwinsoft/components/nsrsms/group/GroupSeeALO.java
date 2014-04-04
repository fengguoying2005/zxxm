package com.gwinsoft.components.nsrsms.group;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class GroupSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM SMS_NSRDATA WHERE NSRDATA_LSH=?";
		String bh = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
		Group group = pm.queryForObject(sql, Group.class, new Object[] { bh });
		this.putData("group", group);
//		String sql2 = "SELECT * FROM SMS_NSRDATAMX WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
//		List<NSRDATAMX> tablemx = pm.queryForList(sql2, NSRDATAMX.class, new String[]{bh});
//		this.putData("tablemx", tablemx);
	}
}
