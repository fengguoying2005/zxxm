package com.gwinsoft.components2.yw.wjdc;

import java.util.List;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.components2.job.SmsSender;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class WjdcSendMxALO extends BaseALO {

	protected void doService() {
		String BH = reqEnv.getData("LSH");
		User user = reqEnv.getData("user");
		String message = "短信已发送";
		String sql = "UPDATE DX_WJDC SET SFJS='2',XG_SJ=SYSDATE,XGRY_DM='"+user.getUSER_DM()+"' WHERE LSH='"+BH+"'";
		String sql2 = "SELECT M.LSH,M.SJHM,Z.INFO AS SMSINFO FROM DX_WJDC Z,DX_WJDCMX M WHERE Z.LSH=M.ZB_LSH AND Z.LSH='"+BH+"'";
		DBPersistenceManager pm = this.getPM();
		List<WjdcSMS> smss = pm.queryForList(sql2, WjdcSMS.class);
		for(WjdcSMS sms : smss) {
			//启动短信发送队列
			SmsSender.schedule(sms);
		}
		pm.executeUpdate(sql);
		resEnv.putData("message", message);
	}
}