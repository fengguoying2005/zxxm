package com.gwinsoft.components.nsrgl.nsrdxfs;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class NsrdxDelMXALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("LSH");
		String KEYIDS = this.getData("KEYIDS");
		KEYIDS = KEYIDS.replaceAll(",", "','");

		DBPersistenceManager pm = this.getPM();
		String selectSQL = "SELECT YYYYMMDD FROM SMS_NSRDATA WHERE NSRDATA_LSH='"+LSH+"'";
		SqlRowSet row = pm.quereyForRowSet(selectSQL);
		if(row.next()) {
			String YYYYMMDD = row.getString("YYYYMMDD");
			String TABLENAME = "SMS_SFXCMSG"+YYYYMMDD;
			String sql = "DELETE FROM "+TABLENAME+" WHERE SFXCMSG_LSH IN ('"+KEYIDS+"')";
			int n = pm.executeUpdate(sql);
		}
	}
}