package com.gwinsoft.components.nsrgl.nsrgroup;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class NsrgroupSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM NSR_GROUP WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Nsrgroup nsrgroup = pm.queryForObject(sql, Nsrgroup.class, new Object[] { bh });
		this.putData("nsrgroup", nsrgroup);
//		String sql2 = "SELECT * FROM NSR_GROUPMX WHERE GROUP_LSH=?";
//		List<GROUPMX> tablemx = pm.queryForList(sql2, GROUPMX.class, new String[]{bh});
//		this.putData("tablemx", tablemx);
	}
}
