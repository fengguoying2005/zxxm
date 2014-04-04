package com.gwinsoft.components.tjfx.business;

import java.util.Date;
import java.util.List;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class BusinessModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Business business = reqEnv.getData("business");
		List<NSRDATAMX> tablemx=reqEnv.getData("tablemx");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
        business.setNSRDATA_LSH(BH);
		business.setXG_SJ(time);
		business.setXGRY_DM(user.getUSER_DM());
			pm.executeUpdate("DELETE FROM SMS_NSRDATAMX WHERE NSRDATA_LSH='"+BH+"'");
			for(NSRDATAMX mx:tablemx) {
				String uid2=GwinSoftUtil.getLSH();
				mx.setNSRDATAMX_LSH(uid2);
				mx.setNSRDATA_LSH(BH);
				mx.setLR_SJ(time);
				mx.setXG_SJ(time);
				mx.setLRRY_DM(user.getUSER_DM());
				mx.setXGRY_DM(user.getUSER_DM());
				pm.executeUpdate("INSERT INTO SMS_NSRDATAMX(NSRDATAMX_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,CBRQQ,CBRQZ,ZSXM,JE,ZSPM,SKSSQ_Q,SKSSQ_Z,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", mx);
			}
			pm.executeUpdate("UPDATE SMS_NSRDATA SET ORG_DM_JG=?,SMSTYPE_DM=?,GROUP_NAME=?,SMSZT_DM=?,LRRY_DM=?,LR_SJ=?,XG_SJ=?,XGRY_DM=? WHERE NSRDATA_LSH=?", business);
			message="修改成功！";
		resEnv.putData("message", message);
	}
}
