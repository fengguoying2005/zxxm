package com.gwinsoft.components.nsrsms.group;

import java.util.List;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;

public class GroupMXSeeALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("LSH");
		PageBean pagebean = this.getData("pagebean");
		DBPersistenceManager pm = this.getPM();
		String sql2 = "SELECT * FROM SMS_NSRDATAMX WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
		List<NSRDATAMX> tablemx = pm.queryPageList(sql2, NSRDATAMX.class, new String[] { LSH }, pagebean);
		this.putData("tablemx", tablemx);
	}
}