package com.gwinsoft.components.qxgl.login;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class QxglLoginLogoutALO extends BaseALO {

	protected void doService() {
		//设置最后登录时间
//		User user = this.getData("user");
//		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
//		user.setZHDL_SJ(time);
//		DBPersistenceManager pm = this.getPM();
//		pm.executeUpdate("UPDATE QX_USER SET ZHDL_SJ=? WHERE ACCOUNT=?", user);
	}
}