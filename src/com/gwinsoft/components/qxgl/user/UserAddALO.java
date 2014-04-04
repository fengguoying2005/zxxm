package com.gwinsoft.components.qxgl.user;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class UserAddALO extends BaseALO {

	protected void doService() {
		User loginuser = reqEnv.getData("loginuser");
		User user = reqEnv.getData("user");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		user.setLR_SJ(time);
		user.setXG_SJ(time);
		user.setLRRY_DM(loginuser.getUSER_DM());
		user.setXGRY_DM(loginuser.getUSER_DM());
		user.setCZY_BJ("1");
		String message = "";
        SqlRowSet r = pm.quereyForRowSet("SELECT CASE WHEN USER_DM = '"+user.getUSER_DM()+"' THEN '<用户编码>已经存在。' END MSG FROM QX_USER WHERE USER_DM = '"+user.getUSER_DM()+"'");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("INSERT INTO QX_USER(USER_DM,USER_MC,PASSWORD,XB_DM,ORG_DM_JG,ORG_DM_BM,SFZHM,TEL,JG,YZBM,EMAIL,ADDR,BZ,YX_BJ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,ZW_DM,JGPX,CZY_BJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", user);
			message="保存成功！";
			CacheServlet.freshCache("USER_CACHE", pm);
		}
		resEnv.putData("message", message);
		resEnv.putData("uid", user.getUSER_DM());
	}
}
