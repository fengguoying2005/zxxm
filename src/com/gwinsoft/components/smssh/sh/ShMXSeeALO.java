package com.gwinsoft.components.smssh.sh;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrgl.nsrdxfs.SFXCMSG;
import com.gwinsoft.components.nsrsms.smssend.Nsrmsg;
import com.gwinsoft.components.orgsms.orgsmssend.ORGMSG;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class ShMXSeeALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("LSH");
		String SMSTYPE_DM = this.getData("SMSTYPE_DM");
		PageBean pagebean = this.getData("pagebean");
		DBPersistenceManager pm = this.getPM();
		if ("03".equals(SMSTYPE_DM) || "05".equals(SMSTYPE_DM)) {
			String sql2 = "SELECT * FROM SMS_ORGMSG WHERE NSRDATA_LSH=? ORDER BY ORG_MC_JG ASC";
			List<ORGMSG> tablemx = pm.queryPageList(sql2, ORGMSG.class, new String[] { LSH }, pagebean);
			this.putData("tablemx", tablemx);
			try {
				GwinSoftUtil.translate(tablemx, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("04".equals(SMSTYPE_DM)) {
			String selectSQL = "SELECT YYYYMMDD FROM SMS_NSRDATA WHERE NSRDATA_LSH='"+LSH+"'";
			SqlRowSet row = pm.quereyForRowSet(selectSQL);
			if(row.next()) {
				String YYYYMMDD = row.getString("YYYYMMDD");
				String TABLENAME = "SMS_SFXCMSG"+YYYYMMDD;
				String sql2 = "SELECT * FROM "+TABLENAME+" WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
				List<SFXCMSG> tablemx = pm.queryPageList(sql2, SFXCMSG.class, new String[] { LSH }, pagebean);
				this.putData("tablemx", tablemx);
				try {
					GwinSoftUtil.translate(tablemx, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			String sql2 = "SELECT * FROM SMS_NSRMSG WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
			List<Nsrmsg> tablemx = pm.queryPageList(sql2, Nsrmsg.class, new String[] { LSH }, pagebean);
			this.putData("tablemx", tablemx);
			try {
				GwinSoftUtil.translate(tablemx, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
