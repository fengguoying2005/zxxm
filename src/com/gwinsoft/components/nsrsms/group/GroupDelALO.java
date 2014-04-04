package com.gwinsoft.components.nsrsms.group;

import com.gwinsoft.framework.alo.BaseALO;

public class GroupDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("NSRDATA_LSH");
		String sql = "DELETE FROM SMS_NSRDATA WHERE SMSZT_DM='01' AND NSRDATA_LSH=?";
		String sql2 = "DELETE FROM SMS_NSRDATAMX WHERE NSRDATA_LSH IN (SELECT NSRDATA_LSH FROM SMS_NSRDATA WHERE SMSZT_DM='01' AND NSRDATA_LSH=?)";
		this.getPM().executeUpdate(sql2, new Object[] { BH });
		int n = this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		if(n>0)
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
