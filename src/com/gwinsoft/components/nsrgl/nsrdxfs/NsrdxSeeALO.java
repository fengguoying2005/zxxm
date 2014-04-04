package com.gwinsoft.components.nsrgl.nsrdxfs;

import java.util.List;

import com.gwinsoft.components.nsrgl.nsrgroup.GROUPMX;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class NsrdxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM SMS_NSRDATA WHERE NSRDATA_LSH=?";
		String bh = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
		Nsrdx nsrdx = pm.queryForObject(sql, Nsrdx.class, new Object[] { bh });
		this.putData("nsrdx", nsrdx);
		String sql2 = "SELECT * FROM NSR_GROUPMX WHERE GROUP_LSH=?";
		List<GROUPMX> tablemx = pm.queryForList(sql2, GROUPMX.class, new String[]{bh});
		this.putData("tablemx", tablemx);
	}
}
