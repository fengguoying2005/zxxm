package com.gwinsoft.components.nsrsms.smssend;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrsms.group.NSRDATAMX;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class SmssendMXSeeALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("LSH");
		String SMSZT_DM = this.getData("SMSZT_DM");
		String MODTYPE = this.getData("MODTYPE");
		PageBean pagebean = this.getData("pagebean");
		DBPersistenceManager pm = this.getPM();
		if ("01".equals(SMSZT_DM) || "1".equals(MODTYPE)) {
			String sql2 = "SELECT * FROM SMS_NSRDATAMX WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
			List<NSRDATAMX> tablemx = pm.queryPageList(sql2, NSRDATAMX.class, new String[] { LSH }, pagebean);
			this.putData("tablemx", tablemx);
		} else {
			String sql = "SELECT * FROM SMS_NSRMSG WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
			List<Nsrmsg> msg = pm.queryPageList(sql, Nsrmsg.class, new String[] { LSH }, pagebean);
			try {
				GwinSoftUtil.translate(msg, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.putData("tablemx", msg);
		}
	}
}