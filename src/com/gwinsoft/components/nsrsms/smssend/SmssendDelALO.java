package com.gwinsoft.components.nsrsms.smssend;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;

public class SmssendDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("NSRDATA_LSH");
		String sql = "UPDATE SMS_NSRDATA SET SMSZT_DM='01' WHERE SMSZT_DM='02' AND NSRDATA_LSH=?";
		String sql2 = "DELETE FROM SMS_NSRMSG WHERE NSRDATA_LSH IN (SELECT NSRDATA_LSH FROM SMS_NSRDATA WHERE SMSZT_DM in ('02','05') AND NSRDATA_LSH=?)";
		this.getPM().executeUpdate(sql2, new Object[] { BH });
		int n = this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		if(n>0)
			message="删除成功！";
		else
			throw new APPException("状态不符，删除失败。");
		resEnv.putData("message", message);
	}
}
