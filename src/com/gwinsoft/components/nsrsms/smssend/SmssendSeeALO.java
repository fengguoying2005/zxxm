package com.gwinsoft.components.nsrsms.smssend;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrsms.group.Group;
import com.gwinsoft.components.nsrsms.group.NSRDATAMX;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class SmssendSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM SMS_NSRDATA WHERE NSRDATA_LSH=?";
		String bh = reqEnv.getData("NSRDATA_LSH");
		String FIND_SMSZT_DM = reqEnv.getData("FIND_SMSZT_DM");
		DBPersistenceManager pm = this.getPM();
		Group group = pm.queryForObject(sql, Group.class, new Object[] { bh });
		this.putData("group", group);
		/*if(!"01".equals(group.getSMSZT_DM()) && !"000".equals(FIND_SMSZT_DM)) {
			String sql2 = "SELECT * FROM SMS_NSRMSG WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
			List<Nsrmsg> tablemx = pm.queryForList(sql2, Nsrmsg.class, new String[]{bh});
			try {
				GwinSoftUtil.translate(tablemx, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.putData("tablemx", tablemx);
		} else {
			String sql2 = "SELECT * FROM SMS_NSRDATAMX WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
			List<NSRDATAMX> tablemx = pm.queryForList(sql2, NSRDATAMX.class, new String[]{bh});
			this.putData("tablemx", tablemx);
		}*/
	}
}
