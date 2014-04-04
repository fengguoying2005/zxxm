package com.gwinsoft.components.nsrgl.jbxx;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class NsrjbxxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM NSR_JBXX WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Nsrjbxx nsrjbxx = pm.queryForObject(sql, Nsrjbxx.class, new Object[] { bh });
		this.putData("nsrjbxx", nsrjbxx);
	}
}
