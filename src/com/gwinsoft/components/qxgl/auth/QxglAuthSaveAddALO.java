package com.gwinsoft.components.qxgl.auth;

import java.util.List;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class QxglAuthSaveAddALO extends BaseALO {

	protected void doService() {
		Auth auth = this.getData("auth");
		List<Auth> auths = this.getData("auths");
		DBPersistenceManager pm = this.getPM();
		int n = pm.queryForInt("SELECT count(0) FROM QX_AUTH WHERE AUTH_DM=?", new String[] { auth.getAUTH_DM() });
		if (n > 0) {
			throw new APPException("权限代码已经存在！\n请更换！");
		}
		String xgsj = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String sql1 = "INSERT INTO QX_AUTH(AUTH_DM,AUTH_MC,AUTH_CC,SJ_AUTH_DM,AUTH_MS,AUTH_LJ,YX_BJ,SFDJ_BJ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,XS_SX,TARGET) VALUES (?,?,'1','0',?,?,?,?,'admin','admin','"+xgsj+"','"+xgsj+"',?,?)";
		String sql2 = "INSERT INTO QX_AUTH(AUTH_DM,AUTH_MC,AUTH_CC,SJ_AUTH_DM,AUTH_MS,AUTH_LJ,YX_BJ,SFDJ_BJ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,XS_SX,TARGET) VALUES (?,?,'2','"+auth.getAUTH_DM()+"',?,?,?,?,'admin','admin','"+xgsj+"','"+xgsj+"',?,?)";
		pm.executeUpdate(sql1 , auth);
		if(auths  != null) {
			for(Auth _auth : auths) {
				pm.executeUpdate(sql2 , _auth);
			}
			for (int i = 0, j = auths.size(); i < j; i++) {
				Auth _auth = auths.get(i);
				String dm = _auth.getAUTH_DM();
				if(dm == null || dm.trim().equals("")) {
					auths.remove(i);
				}
			}
		}
		CacheServlet.freshCache("AUTH_CACHE", pm);
		this.resEnv.addMessage("增加成功!");
	}
}