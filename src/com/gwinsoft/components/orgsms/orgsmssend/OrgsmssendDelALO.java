package com.gwinsoft.components.orgsms.orgsmssend;

import com.gwinsoft.framework.alo.BaseALO;

public class OrgsmssendDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("NSRDATA_LSH");
		String sql = "DELETE FROM SMS_NSRDATA WHERE SMSZT_DM IN('01','02','05') AND NSRDATA_LSH=?";
		String sql2 = "DELETE FROM SMS_ORGMSG WHERE NSRDATA_LSH IN (SELECT NSRDATA_LSH FROM SMS_NSRDATA WHERE SMSZT_DM IN('01','02','05') AND NSRDATA_LSH=?)";
		this.getPM().executeUpdate(sql2, new Object[] { BH });
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
