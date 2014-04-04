package com.gwinsoft.components.tjfx.business;

import java.util.Date;
import java.util.List;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class BusinessAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Business business = reqEnv.getData("business");
		List<NSRDATAMX> tablemx=reqEnv.getData("tablemx");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		business.setLR_SJ(time);
		business.setXG_SJ(time);
		business.setLRRY_DM(user.getUSER_DM());
		business.setXGRY_DM(user.getUSER_DM());
		String message = "";
		for(NSRDATAMX mx:tablemx) {
			String uid2=GwinSoftUtil.getLSH();
			mx.setNSRDATAMX_LSH(uid2);
			mx.setNSRDATA_LSH(uid);
			mx.setLR_SJ(time);
			mx.setXG_SJ(time);
			mx.setLRRY_DM(user.getUSER_DM());
			mx.setXGRY_DM(user.getUSER_DM());
			pm.executeUpdate("INSERT INTO SMS_NSRDATAMX(NSRDATAMX_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,CBRQQ,CBRQZ,ZSXM,JE,ZSPM,SKSSQ_Q,SKSSQ_Z,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", mx);
		}
			pm.executeUpdate("INSERT INTO SMS_NSRDATA(NSRDATA_LSH,ORG_DM_JG,SMSTYPE_DM,GROUP_NAME,SMSZT_DM,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?,?,?)", business);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
