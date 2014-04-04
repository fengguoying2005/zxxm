package com.gwinsoft.components.nsrgl.nsrgroup;

import java.util.List;

import com.gwinsoft.components.nsrsms.group.NSRDATAMX;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;

public class NsrgroupListMXALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("LSH");
		PageBean pagebean = this.getData("pagebean");
		DBPersistenceManager pm = this.getPM();
		String sql = "SELECT * FROM NSR_GROUPMX WHERE GROUP_LSH=?";
		List<GROUPMX> tablemx = pm.queryPageList(sql, GROUPMX.class, new String[] { LSH }, pagebean);
		this.putData("tablemx", tablemx);
	}

}
