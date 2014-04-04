package com.gwinsoft.components.orgsms.orgsmssend;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class OrgsmssendListMXALO extends BaseALO {

	protected void doService() {

		String sql = "SELECT T.*,U.ZW_DM,U.ZW_DM AS ZW_MC FROM SMS_ORGMSG T LEFT OUTER JOIN QX_USER U ON T.USER_DM=U.USER_DM WHERE NSRDATA_LSH=?";
		String LSH = this.getData("NSRDATA_LSH");
		PageBean pagebean = this.getData("pagebean");
		DBPersistenceManager pm = this.getPM();
		List<ORGMSG> tablemx = pm.queryPageList(sql, ORGMSG.class, new String[] { LSH }, pagebean);
		try {
			GwinSoftUtil.translate(tablemx, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
			GwinSoftUtil.translate(tablemx, "ZW_MC", DMB.getTranslateStr(DMB.getDMB("ZW_CACHE", "ZW_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("tablemx", tablemx);
	}

}
