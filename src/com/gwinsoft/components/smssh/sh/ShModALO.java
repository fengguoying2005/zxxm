package com.gwinsoft.components.smssh.sh;

import com.gwinsoft.components.nsrsms.group.Group;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class ShModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String BH = reqEnv.getData("NSRDATA_LSH");
		String DXQM = reqEnv.getData("DXQM");
		String DXMB = reqEnv.getData("DXMB");
		String DXMBINFO = reqEnv.getData("DXMBINFO");
		DBPersistenceManager pm = this.getPM();
		pm.executeUpdate("UPDATE SMS_NSRDATA SET DXQM='"+DXQM+"',DXMB='"+DXMB+"',DXMBINFO='"+DXMBINFO+"',XG_SJ=TO_DATE('"+time+"','YYYY-MM-DD HH24:MI:SS'),XGRY_DM='"+user.getUSER_DM()+"' WHERE SMSZT_DM='01' AND NSRDATA_LSH='"+BH+"'");
		message="修改成功！";
		resEnv.putData("message", message);
	}
}
