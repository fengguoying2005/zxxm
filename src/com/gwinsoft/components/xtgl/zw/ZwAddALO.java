package com.gwinsoft.components.xtgl.zw;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class ZwAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Zw zw = reqEnv.getData("zw");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		zw.setLR_SJ(time);
		zw.setXG_SJ(time);
		zw.setLRRY_DM(user.getUSER_DM());
		zw.setXGRY_DM(user.getUSER_DM());
		String message = "";
        SqlRowSet r = pm.quereyForRowSet("SELECT CASE WHEN ZW_DM = '"+zw.getZW_DM()+"' THEN '<职务代码>已经存在。' WHEN ZW_MC = '"+zw.getZW_MC()+"' THEN '<职务名称>已经存在。' END MSG FROM DM_ZW WHERE ZW_DM = '"+zw.getZW_DM()+"' OR ZW_MC = '"+zw.getZW_MC()+"'");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("INSERT INTO DM_ZW(ZW_DM,ZW_MC,BZ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?)", zw);
			message="保存成功！";
		}
		resEnv.putData("message", message);
		resEnv.putData("uid", zw.getZW_DM());
	}
}
