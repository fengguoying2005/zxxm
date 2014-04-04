package com.gwinsoft.components.czysms.czysmssend;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CzysmssendSeeCallbackALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM SMS_NSRDATA WHERE NSRDATA_LSH=?";
		String bh = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
		Czysmssend orgsmssend = pm.queryForObject(sql, Czysmssend.class, new Object[] { bh });
		this.putData("orgsmssend", orgsmssend);
		String sql2 = "SELECT A.*,B.MSG AS MSG2,B.RECEIVETIME AS RECEIVETIME FROM (SELECT T.*,U.ZW_DM,U.ZW_DM AS ZW_MC FROM SMS_ORGMSG T LEFT OUTER JOIN QX_USER U ON T.USER_DM=U.USER_DM WHERE NSRDATA_LSH=?) A," +
				"(SELECT PHONE,RECEIVETIME,MSG FROM SMS_INBOX WHERE KZM='"+orgsmssend.getDXQM()+"') B WHERE A.SJHM=B.PHONE";
		List<CZYMSG> tablemx = pm.queryForList(sql2, CZYMSG.class, new String[]{bh});
		try {
			GwinSoftUtil.translate(tablemx, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
			GwinSoftUtil.translate(tablemx, "ZW_MC", DMB.getTranslateStr(DMB.getDMB("ZW_CACHE", "ZW_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("tablemx", tablemx);
	}
}