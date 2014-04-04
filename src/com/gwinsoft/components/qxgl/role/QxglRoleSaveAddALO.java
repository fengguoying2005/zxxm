package com.gwinsoft.components.qxgl.role;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class QxglRoleSaveAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Role role = reqEnv.getData("role");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		role.setROLE_LSH(uid);
		role.setLR_SJ(time);
		role.setXG_SJ(time);
		role.setLRRY_DM(user.getUSER_DM());
		role.setXGRY_DM(user.getUSER_DM());
		String message = "";
        SqlRowSet r = pm.quereyForRowSet("SELECT CASE WHEN ROLE_MC = '"+role.getROLE_MC()+"' THEN '<角色名称>已经存在。' END MSG FROM QX_ROLE WHERE ROLE_MC = '"+role.getROLE_MC()+"' AND ORG_DM_JG='"+role.getORG_DM_JG()+"'");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("INSERT INTO QX_ROLE(ROLE_LSH,ROLE_MC,ORG_DM_JG,ROLE_MS,YX_BJ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?,?,?)", role);
			message="保存成功！";
			CacheServlet.freshCache("ROLE_CACHE", this.getPM());
		}
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
