package com.gwinsoft.components.qxgl.org;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class OrgModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Org org = reqEnv.getData("org");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("ORG_DM");
		DBPersistenceManager pm = this.getPM();
        org.setORG_DM(BH);
		org.setXG_SJ(time);
		org.setXGRY_DM(user.getUSER_DM());
        SqlRowSet r = pm.quereyForRowSet("SELECT CASE WHEN ORG_MC = '"+org.getORG_MC()+"' THEN '<机构名称>已经存在。' END MSG FROM QX_ORG WHERE ORG_DM !='"+BH+"' AND (ORG_MC = '"+org.getORG_MC()+"')");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("UPDATE QX_ORG SET ORG_MC=?,ORG_TYPE=?,ORG_DESC=?,SJ_ORG_DM=?,YX_BJ=?,XG_SJ=?,XGRY_DM=?,JGPX=? WHERE ORG_DM=?", org);
			message="修改成功！";
			CacheServlet.freshCache("ORG_CACHE", this.getPM());
		}
		resEnv.putData("message", message);
	}
}
