package com.gwinsoft.components.orgsms.group;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class OrggroupSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM ORG_GROUP WHERE GROUP_LSH=?";
		String bh = reqEnv.getData("GROUP_LSH");
		DBPersistenceManager pm = this.getPM();
		Orggroup orggroup = pm.queryForObject(sql, Orggroup.class, new Object[] { bh });
		this.putData("orggroup", orggroup);
		String sql2 = "SELECT * FROM ORG_GROUPMX WHERE GROUP_LSH=?";
		List<GROUPMX> tablemx = pm.queryForList(sql2, GROUPMX.class, new String[]{bh});
		this.putData("tablemx", tablemx);
	}
}
