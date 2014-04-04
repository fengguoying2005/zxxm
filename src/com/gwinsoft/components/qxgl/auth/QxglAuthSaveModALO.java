package com.gwinsoft.components.qxgl.auth;

import java.util.Date;
import java.util.List;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class QxglAuthSaveModALO extends BaseALO {

	protected void doService() {
		Auth auth = this.getData("auth");
		Date xgsj = GwinSoftUtil.getSysDate();
		auth.setXG_SJ(xgsj );
		auth.setXGRY_DM("admin");
		List<Auth> auths = this.getData("auths");
		//修改一级Auth
		String sql1 = "UPDATE QX_AUTH SET AUTH_MC=?,AUTH_MS=?,AUTH_LJ=?,YX_BJ=?,SFDJ_BJ=?,XGRY_DM=?,XG_SJ=?,XS_SX=?,TARGET=? WHERE AUTH_DM=?";
		DBPersistenceManager pm = this.getPM();
		pm.executeUpdate(sql1, auth);
		//删除所有二级Auth
		String sql2 = "DELETE FROM QX_AUTH WHERE SJ_AUTH_DM=?";
		pm.executeUpdate(sql2, new String[]{auth.getAUTH_DM()});
		//增加二级Auth
		String sql3 = "INSERT INTO QX_AUTH(AUTH_DM,AUTH_MC,AUTH_CC,SJ_AUTH_DM,AUTH_MS,AUTH_LJ,YX_BJ,SFDJ_BJ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,XS_SX,TARGET) VALUES (?,?,'2','"+auth.getAUTH_DM()+"',?,?,?,?,'admin','admin','"+xgsj+"','"+xgsj+"',?,?)";
		if(auths  != null) {
			for(Auth _auth : auths) {
				if(_auth == null || _auth.getAUTH_DM() == null) {
					//TODO auths.remove(_auth);
					continue;
				}
				if(_auth.getYX_BJ()==null || "".equals(_auth.getYX_BJ())) {
					_auth.setYX_BJ("0");
				}
				pm.executeUpdate(sql3, _auth);
			}
			for (int i = auths.size() - 1; i >= 0 ; i --) {
				Auth _auth = auths.get(i);
				if(_auth == null) {
					auths.remove(i);
				} else {
					String dm = _auth.getAUTH_DM();
					if(dm == null || dm.trim().equals("")) {
						auths.remove(i);
					}
				}
			}
			CacheServlet.freshCache("AUTH_CACHE", pm);
		}
		this.resEnv.addMessage("修改成功!");
	}
}