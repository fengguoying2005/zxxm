package com.gwinsoft.components.qxgl.role;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class QxglRoleAssginAuthALO extends BaseALO {

	protected void doService() {
		/*boolean isDJORG = false;
		User loginUser = reqEnv.getLoginUser();
		try {
			Map<String, Map<String, Object>> cache = CacheManager.getCache("DJORG_CACHE");
			Iterator<String> it = cache.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				if(key.equals(loginUser.getORG_DM_JG())) {
					isDJORG = true;
				}
			}
		} catch (Exception e) {
		}*/
		String roledm = this.getData("roledm");
		DBPersistenceManager pm = this.getPM();
		pm.executeUpdate("DELETE FROM QX_ROLE_AUTH WHERE ROLE_LSH=?", new String[] { roledm });
		String assignAuth = this.getData("assignAuth");
		if (assignAuth != null && !"".equals(assignAuth)) {
			String sql = "INSERT INTO QX_ROLE_AUTH(ROLE_AUTH_LSH,AUTH_DM,ROLE_LSH,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES (?,?,?,?,?,?,?)";
			Date time = (GwinSoftUtil.getSysDate());
			Object[] roleAuth = new Object[]{"","",roledm,"admin","admin",time,time};
			String[] auths = assignAuth.split(",");
			Map dmb = DMB.getDMB("AUTH_CACHE","SJ_AUTH_DM");
			//Map djdmb = DMB.getDMB("AUTH_CACHE","SFDJ_BJ");
			Set<String> sjauths = new HashSet<String>();
			for(String auth : auths) {
				if (auth != null && !"".equals(auth)) {
/*					if(!isDJORG) {
						if("1".equals(djdmb.get(auth))) {
							continue;
						}
					}*/
					String sjauth = (String) dmb.get(auth);
					sjauths.add(sjauth);
					roleAuth[0]=UUID.randomUUID().toString();
					roleAuth[1]=auth;
					pm.executeUpdate(sql, roleAuth);
				}
			}
			for(String auth:sjauths) {
				if (auth != null && !"".equals(auth)) {
					roleAuth[0]=UUID.randomUUID().toString();
					roleAuth[1]=auth;
					pm.executeUpdate(sql, roleAuth);
				}
			}
			CacheServlet.freshCache("ROLE_AUTH_CACHE", pm);
		}
	}
}