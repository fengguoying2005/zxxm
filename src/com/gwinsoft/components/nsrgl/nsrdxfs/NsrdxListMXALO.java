package com.gwinsoft.components.nsrgl.nsrdxfs;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class NsrdxListMXALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("LSH");
		PageBean pagebean = this.getData("pagebean");
		DBPersistenceManager pm = this.getPM();
		String selectSQL = "SELECT YYYYMMDD FROM SMS_NSRDATA WHERE NSRDATA_LSH='"+LSH+"'";
		SqlRowSet row = pm.quereyForRowSet(selectSQL);
		if(row.next()) {
			String YYYYMMDD = row.getString("YYYYMMDD");
			String TABLENAME = "SMS_SFXCMSG"+YYYYMMDD;
			String sql = "SELECT * FROM "+TABLENAME+" WHERE NSRDATA_LSH=? ORDER BY NSRBM,PHONETYPE";
			List<SFXCMSG> tablemx = pm.queryPageList(sql, SFXCMSG.class, new String[] { LSH }, pagebean);
			try {
				GwinSoftUtil.translate(tablemx, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
				GwinSoftUtil.translate(tablemx, "PHONETYPE", "BSRYSJH:办税人员;CWJLSJH:财务经理;FRSJH:法人;");
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.putData("tablemx", tablemx);
		}
	}

}
