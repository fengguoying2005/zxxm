package com.gwinsoft.components2.yw.xydz;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class XydzLoadNsrALO extends BaseALO {

	protected void doService() {
		String _NSRBM = this.getData("NSRBM");
		String message = "";
		String NSRBM = null;
		String NSRMC = null;
		String ORG_DM = null;
		String sql = "SELECT NSRBM,NSRMC,ORG_DM FROM NSR_JBXX WHERE NSRBM=?";
		DBPersistenceManager pm = this.getPM();
		SqlRowSet row = pm.quereyForRowSet(sql, new String[]{_NSRBM});
		if(row.next()) {
			NSRBM = row.getString("NSRBM");
			NSRMC = row.getString("NSRMC");
			ORG_DM = row.getString("ORG_DM");
		} else {
			message = "未找到相关纳税人。";
		}
		this.putData("NSRBM", NSRBM);
		this.putData("NSRMC", NSRMC);
		this.putData("ORG_DM", ORG_DM);
		this.putData("message", message);
	}
}