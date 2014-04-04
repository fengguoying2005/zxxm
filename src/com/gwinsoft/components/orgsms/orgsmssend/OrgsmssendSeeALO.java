package com.gwinsoft.components.orgsms.orgsmssend;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class OrgsmssendSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM SMS_NSRDATA WHERE NSRDATA_LSH=?";
		String bh = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
		Orgsmssend orgsmssend = pm.queryForObject(sql, Orgsmssend.class, new Object[] { bh });
		this.putData("orgsmssend", orgsmssend);
		String sql2 = "SELECT T.*,U.ZW_DM,U.ZW_DM AS ZW_MC FROM SMS_ORGMSG T LEFT OUTER JOIN QX_USER U ON T.USER_DM=U.USER_DM WHERE NSRDATA_LSH=?";
		List<ORGMSG> tablemx = pm.queryForList(sql2, ORGMSG.class, new String[]{bh});
		try {
			GwinSoftUtil.translate(tablemx, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
			GwinSoftUtil.translate(tablemx, "ZW_MC", DMB.getTranslateStr(DMB.getDMB("ZW_CACHE", "ZW_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("tablemx", tablemx);
	}
}
