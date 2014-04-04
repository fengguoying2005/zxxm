package com.gwinsoft.components.qxgl.org;

import java.util.List;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class OrgSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM QX_ORG WHERE ORG_DM=?";
		String bh = reqEnv.getData("ORG_DM");
		DBPersistenceManager pm = this.getPM();
		Org org = pm.queryForObject(sql, Org.class, new Object[] { bh });
		this.putData("org", org);
	}
}
