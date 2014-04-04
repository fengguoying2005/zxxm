package com.gwinsoft.components.qxgl.user;

import java.util.Date;
import java.util.UUID;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class QxglUserAssginRoleALO extends BaseALO {

	protected void doService() {
//		boolean isDJORG = false;
		String account = this.getData("account");
		DBPersistenceManager pm = this.getPM();
//		try {
//			SqlRowSet row = pm.quereyForRowSet("SELECT 1 FROM QX_USER A,QX_USER B WHERE A.USER_DM='admin' AND B.USER_DM='"+account+"' and A.ORG_DM_JG=B.ORG_DM_JG");
//			if(row.next()) {
//				isDJORG = true;
//			}
//		} catch (Exception e) {
//		}
		
		String assignRole = this.getData("assignRole");
		String sql = "INSERT INTO QX_USER_ROLE(USER_ROLE_LSH,USER_DM,ROLE_LSH,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES (?,?,?,?,?,?,?)";
		pm.executeUpdate("DELETE FROM QX_USER_ROLE WHERE USER_DM=?", new String[] { account });
		if (assignRole != null && !"".equals(assignRole)) {
			Date time = (GwinSoftUtil.getSysDate());
			Object[] userRole = new Object[]{"",account,"","admin","admin",time,time};
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