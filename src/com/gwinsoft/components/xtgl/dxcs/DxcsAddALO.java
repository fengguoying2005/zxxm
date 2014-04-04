package com.gwinsoft.components.xtgl.dxcs;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class DxcsAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Dxcs dxcs = reqEnv.getData("dxcs");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		dxcs.setLR_SJ(time);
		dxcs.setXG_SJ(time);
		dxcs.setLRRY_DM(user.getUSER_DM());
		dxcs.setXGRY_DM(user.getUSER_DM());
		String message = "";
			pm.executeUpdate("INSERT INTO DM_DXCS(YYS_DM,DXJG,BZ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?)", dxcs);
			message="保存成功！";
		resEnv.putData("message", message);
		CacheServlet.freshCache("DXCS_CACHE", pm);
		resEnv.putData("uid", uid);
	}
}
