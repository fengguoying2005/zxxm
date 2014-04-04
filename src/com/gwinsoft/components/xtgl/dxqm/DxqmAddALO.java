package com.gwinsoft.components.xtgl.dxqm;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class DxqmAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Dxqm dxqm = reqEnv.getData("dxqm");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		String message = "";
        SqlRowSet r = pm.quereyForRowSet("SELECT CASE WHEN DXQM_DM = '"+dxqm.getDXQM_DM()+"' THEN '<用户编码>已经存在。' WHEN DXQM_MC = '"+dxqm.getDXQM_MC()+"' THEN '<用户名称>已经存在。' END MSG FROM DM_DXQM WHERE DXQM_DM = '"+dxqm.getDXQM_DM()+"' OR DXQM_MC = '"+dxqm.getDXQM_MC()+"'");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("INSERT INTO DM_DXQM(DXQM_DM,DXQM_MC) VALUES(?,?)", dxqm);
			message="保存成功！";
			CacheServlet.freshCache("DXQM_CACHE", pm);
		}
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
