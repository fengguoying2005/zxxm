package com.gwinsoft.components.qxgl.org;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class OrgAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Org org = reqEnv.getData("org");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		org.setLR_SJ(time);
		org.setXG_SJ(time);
		org.setLRRY_DM(user.getUSER_DM());
		org.setXGRY_DM(user.getUSER_DM());
		String message = "";
        SqlRowSet r = pm.quereyForRowSet("SELECT CASE WHEN ORG_DM = '"+org.getORG_DM()+"' THEN '<机构代码>已经存在。' WHEN ORG_MC = '"+org.getORG_MC()+"' THEN '<机构名称>已经存在。' END MSG FROM QX_ORG WHERE ORG_DM = '"+org.getORG_DM()+"' OR ORG_MC = '"+org.getORG_MC()+"'");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("INSERT INTO QX_ORG(ORG_DM,ORG_MC,ORG_TYPE,ORG_DESC,SJ_ORG_DM,YX_BJ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,JGPX) VALUES(?,?,?,?,?,?,?,?,?,?,?)", org);
			message="保存成功！";
			CacheServlet.freshCache("ORG_CACHE", this.getPM());
		}
		resEnv.putData("message", message);
		resEnv.putData("uid", org.getORG_DM());
	}
}
