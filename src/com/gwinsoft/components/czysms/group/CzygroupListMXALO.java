package com.gwinsoft.components.czysms.group;

import java.util.List;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;

public class CzygroupListMXALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("GROUP_LSH");
		PageBean pagebean = this.getData("pagebean");
		DBPersistenceManager pm = this.getPM();
		String sql = "SELECT * FROM CZY_GROUPMX WHERE GROUP_LSH=?";
		List<GROUPMX> tablemx = pm.queryPageList(sql, GROUPMX.class, new String[] { LSH }, pagebean);
		this.putData("tablemx", tablemx);
	}
}