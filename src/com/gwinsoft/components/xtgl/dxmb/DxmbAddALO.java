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

public class DxmbAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Dxmb dxmb = reqEnv.getData("dxmb");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		dxmb.setLR_SJ(time);
		dxmb.setXG_SJ(time);
		dxmb.setLRRY_DM(user.getUSER_DM());
		dxmb.setXGRY_DM(user.getUSER_DM());
		dxmb.setDXMB_LSH(uid);
		String message = "";
        SqlRowSet r = pm.quereyForRowSet("SELECT CASE WHEN DXMB_LSH = '"+dxmb.getDXMB_LSH()+"' THEN '<用户编码>已经存在。' END MSG FROM SMS_DXMB WHERE DXMB_LSH = '"+dxmb.getDXMB_LSH()+"'");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("INSERT INTO SMS_DXMB(DXMB_LSH,ORG_DM_JG,SMSTYPE_DM,DXMB_MC,INFO,BZ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?,?,?,?)", dxmb);
			message="保存成功！";
			CacheServlet.freshCache("DXMB_CACHE", pm);
		}
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
