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

public class QxglRoleSaveModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Role role = reqEnv.getData("role");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("ROLE_LSH");
		DBPersistenceManager pm = this.getPM();
        role.setROLE_LSH(BH);
		role.setXG_SJ(time);
		role.setXGRY_DM(user.getUSER_DM());
        SqlRowSet r = pm.quereyForRowSet("SELECT CASE WHEN ROLE_MC = '"+role.getROLE_MC()+"' THEN '<角色名称>已经存在。' END MSG FROM QX_ROLE WHERE ROLE_LSH !='"+BH+"' AND ORG_DM_JG='"+role.getORG_DM_JG()+"' AND (ROLE_MC = '"+role.getROLE_MC()+"')");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("UPDATE QX_ROLE SET ROLE_MC=?,ORG_DM_JG=?,ROLE_MS=?,YX_BJ=?,XG_SJ=?,XGRY_DM=? WHERE ROLE_LSH=?", role);
			message="修改成功！";
			CacheServlet.freshCache("ROLE_AUTH_CACHE", this.getPM());
			CacheServlet.freshCache("ROLE_CACHE", this.getPM());
		}
		resEnv.putData("message", message);
	}
}
