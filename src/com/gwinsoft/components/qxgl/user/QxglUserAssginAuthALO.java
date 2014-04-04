package com.gwinsoft.components.qxgl.user;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class QxglUserAssginAuthALO extends BaseALO {

	protected void doService() {
		boolean isDJORG = false;
		String account = this.getData("account");
		DBPersistenceManager pm = this.getPM();
		try {
			SqlRowSet row = pm.quereyForRowSet("SELECT 1 FROM QX_USER A,QX_USER B WHERE A.USER_DM='admin' AND B.USER_DM='"+account+"' and A.ORG_DM_JG=B.ORG_DM_JG");
			if(row.next()) {
				isDJORG = true;
			}
		} catch (Exception e) {
		}
		
		pm.executeUpdate("DELETE FROM QX_USER_AUTH WHERE USER_DM=?", new String[] { account });
		String assignAuth = this.getData("assignAuth");
		if (assignAuth != null && !"".equals(assignAuth)) {
			String sql = "INSERT INTO QX_USER_AUTH(USER_AUTH_LSH,USER_DM,AUTH_DM) VALUES (?,?,?)";
			String[] userAuth = new String[]{"",account,""};
			String[] auths = assignAuth.split(",");
			Map dmb = DMB.getDMB("AUTH_CACHE","SJ_AUTH_DM");
			Map djdmb = DMB.getDMB("AUTH_CACHE","SFDJ_BJ");
			Set<String> sjauths = new HashSet<String>();
			for(String auth : auths) {
				if (auth != null && !"".equals(auth)) {
					if(!isDJORG) {
						if("1".equals(djdmb.get(auth))) {
							continue;
						}
					}
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
