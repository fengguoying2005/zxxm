package com.gwinsoft.components.qxgl.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class QxglAuthLoadALO extends BaseALO {

	protected void doService() {
		String authdm = this.getData("authdm");
		String sql = "SELECT * FROM QX_AUTH WHERE AUTH_DM=?";
		String sql2 = "SELECT * FROM QX_AUTH WHERE SJ_AUTH_DM=? ORDER BY XS_SX ASC";
		DBPersistenceManager pm = this.getPM();
		SqlRowSet row1 = pm.quereyForRowSet(sql, new String[]{authdm});
		Auth auth = new Auth();
		if(row1.next()) {
			String AUTH_LJ = row1.getString("AUTH_LJ");
			String TARGET = row1.getString("TARGET");
			String AUTH_MC = row1.getString("AUTH_MC");
			String AUTH_DM = row1.getString("AUTH_DM");
			String AUTH_CC = row1.getString("AUTH_CC");
			String AUTH_MS = row1.getString("AUTH_MS");
			String YX_BJ = row1.getString("YX_BJ");
			String SJ_AUTH_DM = row1.getString("SJ_AUTH_DM");
			String XS_SX = row1.getString("XS_SX");
			String SFDJ_BJ = row1.getString("SFDJ_BJ");
			auth.setAUTH_CC(AUTH_CC);
			auth.setAUTH_DM(AUTH_DM);
			auth.setAUTH_LJ(AUTH_LJ);
			auth.setAUTH_MC(AUTH_MC);
			auth.setAUTH_MS(AUTH_MS);
			auth.setYX_BJ(YX_BJ);
			auth.setSFDJ_BJ(SFDJ_BJ);
			auth.setSJ_AUTH_DM(SJ_AUTH_DM);
			auth.setTARGET(TARGET);
			auth.setXS_SX(XS_SX);
		}
		SqlRowSet row = pm.quereyForRowSet(sql2, new String[]{authdm});
		List<Auth> auths = new ArrayList<Auth>();
		while(row.next()) {
			String AUTH_LJ = row.getString("AUTH_LJ");
			String TARGET = row.getString("TARGET");
			String AUTH_MC = row.getString("AUTH_MC");
			String AUTH_DM = row.getString("AUTH_DM");
			String AUTH_CC = row.getString("AUTH_CC");
			String AUTH_MS = row.getString("AUTH_MS");
			String YX_BJ = row.getString("YX_BJ");
			String SFDJ_BJ = row.getString("SFDJ_BJ");
			String SJ_AUTH_DM = row.getString("SJ_AUTH_DM");
			String XS_SX = row.getString("XS_SX");
			Auth _auth = new Auth();
			_auth.setAUTH_CC(AUTH_CC);
			_auth.setAUTH_DM(AUTH_DM);
			_auth.setAUTH_LJ(AUTH_LJ);
			_auth.setAUTH_MC(AUTH_MC);
			_auth.setAUTH_MS(AUTH_MS);
			_auth.setYX_BJ(YX_BJ);
			_auth.setSFDJ_BJ(SFDJ_BJ);
			_auth.setSJ_AUTH_DM(SJ_AUTH_DM);
			_auth.setTARGET(TARGET);
			_auth.setXS_SX(XS_SX);
			auths.add(_auth);
		}
		this.putData("auth", auth);
		this.putData("auths", auths);
	}
}