package com.gwinsoft.components.xtgl.zw;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class ZwModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Zw zw = reqEnv.getData("zw");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("ZW_DM");
		DBPersistenceManager pm = this.getPM();
        zw.setZW_DM(BH);
		zw.setXG_SJ(time);
		zw.setXGRY_DM(user.getUSER_DM());
        SqlRowSet r = pm.quereyForRowSet("SELECT CASE WHEN ZW_MC = '"+zw.getZW_MC()+"' THEN '<职务名称>已经存在。' END MSG FROM DM_ZW WHERE ZW_DM !='"+BH+"' AND (ZW_MC = '"+zw.getZW_MC()+"')");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("UPDATE DM_ZW SET ZW_MC=?,BZ=?,XG_SJ=?,XGRY_DM=? WHERE ZW_DM=?", zw);
			message="修改成功！";
		}
		resEnv.putData("message", message);
	}
}
