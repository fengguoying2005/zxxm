package com.gwinsoft.components.nsrsms.smssend;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class SmssendSendALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		String SHY = this.getData("SHY");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String BH = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
		int n = pm.executeUpdate("UPDATE SMS_NSRDATA SET SHY='"+SHY+"',SMSZT_DM='03',XG_SJ=TO_DATE('"+time+"','YYYY-MM-DD HH24:MI:SS'),XGRY_DM='"+user.getUSER_DM()+"' WHERE SMSZT_DM in ('02','05') AND NSRDATA_LSH='"+BH+"'");
		if(n>0) {
			pm.executeUpdate("UPDATE SMS_NSRMSG SET SMSTYPE_DM='03' WHERE NSRDATA_LSH='"+BH+"'");
			message="短信提交发送申请成功！";
		}
		resEnv.putData("message", message);
	}
}
