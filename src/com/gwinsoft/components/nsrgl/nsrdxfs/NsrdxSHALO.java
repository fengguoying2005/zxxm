package com.gwinsoft.components.nsrgl.nsrdxfs;

import com.gwinsoft.framework.alo.BaseALO;

public class NsrdxSHALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("NSRDATA_LSH");
		String SHY = this.getData("SHY");
		String sql = "UPDATE SMS_NSRDATA SET SMSZT_DM='03',SHY=? WHERE NSRDATA_LSH=? AND SMSZT_DM IN ('01','02','05')";
		this.getPM().executeUpdate(sql, new Object[] { SHY, BH });
		String message = "";
		message="提交审核成功！";
		resEnv.putData("message", message);
	}
}