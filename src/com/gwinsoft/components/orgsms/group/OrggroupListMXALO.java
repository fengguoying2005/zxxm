package com.gwinsoft.components.orgsms.group;

import java.util.List;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;

public class OrggroupListMXALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("GROUP_LSH");
		PageBean pagebean = this.getData("pagebean");
		DBPersistenceManager pm = this.getPM();
		String sql = "SELECT * FROM ORG_GROUPMX WHERE GROUP_LSH=?";
		List<GROUPMX> tablemx = pm.queryPageList(sql, GROUPMX.class, new String[] { LSH }, pagebean);
		this.putData("tablemx", tablemx);
	}
}