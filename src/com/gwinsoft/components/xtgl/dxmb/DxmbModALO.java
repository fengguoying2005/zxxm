package com.gwinsoft.components.xtgl.dxmb;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class DxmbModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Dxmb dxmb = reqEnv.getData("dxmb");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("DXMB_LSH");
		DBPersistenceManager pm = this.getPM();
        dxmb.setDXMB_LSH(BH);
		dxmb.setXG_SJ(time);
		dxmb.setXGRY_DM(user.getUSER_DM());
			pm.executeUpdate("UPDATE SMS_DXMB SET ORG_DM_JG=?,SMSTYPE_DM=?,DXMB_MC=?,INFO=?,BZ=?,XG_SJ=?,XGRY_DM=? WHERE DXMB_LSH=?", dxmb);
			message="修改成功！";
			CacheServlet.freshCache("DXMB_CACHE", pm);
		resEnv.putData("message", message);
	}
}
