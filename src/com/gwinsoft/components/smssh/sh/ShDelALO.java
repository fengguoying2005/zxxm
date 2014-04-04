package com.gwinsoft.components.smssh.sh;

import com.gwinsoft.framework.alo.BaseALO;

public class ShDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("NSRDATA_LSH");
		BH = BH.replaceAll(",", "','");
		String THYY = this.getData("THYY");
		String sql = "UPDATE SMS_NSRDATA SET SMSZT_DM='05',THYY='"+THYY+"' WHERE SMSZT_DM='03' AND NSRDATA_LSH IN ('"+BH+"')";
		int n = this.getPM().executeUpdate(sql, new Object[] {});
		String message = "";
		if(n>0) {
			this.getPM().executeUpdate("UPDATE SMS_ORGMSG SET SMSTYPE_DM='05' WHERE NSRDATA_LSH IN ('"+BH+"')");
			this.getPM().executeUpdate("UPDATE SMS_NSRMSG SET SMSTYPE_DM='05' WHERE NSRDATA_LSH IN ('"+BH+"')");
			message="短信退回成功！";
		}
		else {
			message=("状态不符，退回失败。");
		}
		resEnv.putData("message", message);
	}
}
