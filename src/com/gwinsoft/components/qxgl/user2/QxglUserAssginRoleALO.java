package com.gwinsoft.components.qxgl.user2;

import java.util.UUID;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class QxglUserAssginRoleALO extends BaseALO {

	protected void doService() {
		String account = this.getData("account");
		String assignRole = this.getData("assignRole");
		String sql = "INSERT INTO QX_USER_ROLE(USER_ROLE_LSH,USER_DM,ROLE_LSH,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES (?,?,?,?,?,?,?)";
		DBPersistenceManager pm = this.getPM();
		pm.executeUpdate("DELETE FROM QX_USER_ROLE WHERE USER_DM=?", new String[] { account });
		if (assignRole != null && !"".equals(assignRole)) {
			String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
			String[] userRole = new String[]{"",account,"","admin","admin",time,time};
			String[] roles = assignRole.split(",");
			for(String role : roles) {
				if (role != null && !"".equals(role)) {
					userRole[0]=UUID.randomUUID().toString();
					userRole[2]=role;
					pm.executeUpdate(sql, userRole);
				}
			}
		}
		CacheServlet.freshCache("USER_ROLE_CACHE", pm);
	}
}