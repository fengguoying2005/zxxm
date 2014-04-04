package com.gwinsoft.components.czysms.group;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class CzygroupSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM CZY_GROUP WHERE GROUP_LSH=?";
		String bh = reqEnv.getData("GROUP_LSH");
		DBPersistenceManager pm = this.getPM();
		Czygroup orggroup = pm.queryForObject(sql, Czygroup.class, new Object[] { bh });
		this.putData("orggroup", orggroup);
		String sql2 = "SELECT * FROM CZY_GROUPMX WHERE GROUP_LSH=?";
		List<GROUPMX> tablemx = pm.queryForList(sql2, GROUPMX.class, new String[]{bh});
		this.putData("tablemx", tablemx);
	}
}
