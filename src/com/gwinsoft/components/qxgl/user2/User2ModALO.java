package com.gwinsoft.components.qxgl.user2;

import java.util.Date;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class User2ModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User loginuser = reqEnv.getData("loginuser");
		User user = reqEnv.getData("user");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("USER_DM");
		DBPersistenceManager pm = this.getPM();
        user.setUSER_DM(BH);
		user.setXG_SJ(time);
		user.setXGRY_DM(loginuser.getUSER_DM());
//        SqlRowSet r = pm.quereyForRowSet("SELECT CASE WHEN USER_MC = '"+user.getUSER_MC()+"' THEN '<用户名称>已经存在。' END MSG FROM QX_USER WHERE USER_DM !='"+BH+"' AND (USER_MC = '"+user.getUSER_MC()+"')");
//		if(r.next()) {
//			String msg = r.getString("MSG");
//			this.resEnv.setAPPException(new APPException(msg));
//		} else {
			pm.executeUpdate("UPDATE QX_USER SET USER_MC=?,PASSWORD=?,XB_DM=?,ORG_DM_JG=?,ORG_DM_BM=?,SFZHM=?,TEL=?,JG=?,YZBM=?,EMAIL=?,ADDR=?,BZ=?,YX_BJ=?,XG_SJ=?,XGRY_DM=?,ZW_DM=?,JGPX=? WHERE USER_DM=?", user);
			message="修改成功！";
			CacheServlet.freshCache("USER_CACHE", pm);
//		}
		resEnv.putData("message", message);
	}
}
