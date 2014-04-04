package com.gwinsoft.components.xtgl.dxcs;

import java.util.Date;
import java.util.List;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class DxcsModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		List<Dxcs> dxcss = reqEnv.getData("dxcss");
		Date time = GwinSoftUtil.getSysDate();
		DBPersistenceManager pm = this.getPM();
		String sql = "DELETE FROM DM_DXCS";
		pm.executeUpdate(sql);
		for(Dxcs cs: dxcss) {
			cs.setLR_SJ(time);
			cs.setXG_SJ(time);
			cs.setLRRY_DM(user.getUSER_DM());
			cs.setXGRY_DM(user.getUSER_DM());
			pm.executeUpdate("INSERT INTO DM_DXCS(YYS_DM,DXJG,BZ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES (?,?,?,?,?,?,?)", cs);
		}
		message="修改成功！";
		CacheServlet.freshCache("DXCS_CACHE", pm);
		resEnv.putData("message", message);
	}
}
