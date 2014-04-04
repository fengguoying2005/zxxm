package com.gwinsoft.components.nsrsms.group;

import java.util.Date;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class GroupModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Group group = reqEnv.getData("group");
		Date time = GwinSoftUtil.getSysDate();
		String BH = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
        group.setNSRDATA_LSH(BH);
		group.setXG_SJ(time);
		group.setXGRY_DM(user.getUSER_DM());
		pm.executeUpdate("UPDATE SMS_NSRDATA SET GROUP_NAME=? WHERE SMSZT_DM='01' AND NSRDATA_LSH=?", group);
		message="修改成功！";
		resEnv.putData("message", message);
	}
}
