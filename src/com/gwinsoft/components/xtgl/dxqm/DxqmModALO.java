package com.gwinsoft.components.xtgl.dxqm;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class DxqmModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Dxqm dxqm = reqEnv.getData("dxqm");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("DXQM_DM");
		DBPersistenceManager pm = this.getPM();
        dxqm.setDXQM_DM(user.getORG_DM_JG());
		if(BH==null || "".equals(BH.trim())) {
			pm.executeUpdate("INSERT INTO DM_DXQM(DXQM_MC,DXQM_DM) VALUES (?,?)", dxqm);
			message="修改成功！";
		} else {
			pm.executeUpdate("UPDATE DM_DXQM SET DXQM_MC=? WHERE DXQM_DM=?", dxqm);
			message="修改成功！";
		}
		CacheServlet.freshCache("DXQM_CACHE", pm);
		resEnv.putData("message", message);
	}
}
