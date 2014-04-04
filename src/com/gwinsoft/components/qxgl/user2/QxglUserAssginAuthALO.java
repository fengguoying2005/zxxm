package com.gwinsoft.components.qxgl.user2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class QxglUserAssginAuthALO extends BaseALO {

	protected void doService() {
		String account = this.getData("account");
		DBPersistenceManager pm = this.getPM();
		pm.executeUpdate("DELETE FROM QX_USER_AUTH WHERE USER_DM=?", new String[] { account });
		String assignAuth = this.getData("assignAuth");
		if (assignAuth != null && !"".equals(assignAuth)) {
			String sql = "INSERT INTO QX_USER_AUTH(USER_AUTH_LSH,USER_DM,AUTH_DM) VALUES (?,?,?)";
			String[] userAuth = new String[]{"",account,""};
			String[] auths = assignAuth.split(",");
			Map dmb = DMB.getDMB("AUTH_CACHE","SJ_AUTH_DM");
			Set<String> sjauths = new HashSet<String>();
			for(String auth : auths) {
				if (auth != null && !"".equals(auth)) {
					String sjauth = (String) dmb.get(auth);
					sjauths.add(sjauth);
					userAuth[0]=UUID.randomUUID().toString();
					userAuth[2]=auth;
					pm.executeUpdate(sql, userAuth);
				}
			}
			for(String auth:sjauths) {
				if (auth != null && !"".equals(auth)) {
					userAuth[0]=UUID.randomUUID().toString();
					userAuth[2]=auth;
					pm.executeUpdate(sql, userAuth);
				}
			}
			CacheServlet.freshCache("USER_AUTH_CACHE", this.getPM());
		}
	}

}
