package com.gwinsoft.components.nsrsms.smssend;

import java.util.Date;
import java.util.List;

import com.gwinsoft.components.nsrsms.group.Group;
import com.gwinsoft.components.nsrsms.group.NSRDATAMX;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class SmssendAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Group group = reqEnv.getData("group");
		List<NSRDATAMX> tablemx=reqEnv.getData("tablemx");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		group.setLR_SJ(time);
		group.setXG_SJ(time);
		group.setLRRY_DM(user.getUSER_DM());
		group.setXGRY_DM(user.getUSER_DM());
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
			pm.executeUpdate("INSERT INTO SMS_NSRDATA(NSRDATA_LSH,SMSTYPE_DM,GROUP_NAME,ORG_DM_JG,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,SMSZT_DM) VALUES(?,?,?,?,?,?,?,?,?)", group);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
