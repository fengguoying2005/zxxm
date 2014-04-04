package com.gwinsoft.components2.dxmb;

import java.util.Date;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class Dxmb2ModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Dxmb2 dxmb = reqEnv.getData("dxmb");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("DXMB_LSH");
		DBPersistenceManager pm = this.getPM();
        dxmb.setLSH(BH);
		dxmb.setXG_SJ(GwinSoftUtil.getFormatDate(time, "yyyy-MM-dd HH:mm:ss"));
		dxmb.setXGRY_DM(user.getUSER_DM());
			pm.executeUpdate("UPDATE DX_DXMB SET INFO=?,BZ=?,XG_SJ=to_date('"+dxmb.getXG_SJ()+"','yyyy-mm-dd hh24:mi:ss'),XGRY_DM=? WHERE LSH=?", dxmb);
			message="修改成功！";
			CacheServlet.freshCache("DXMB2_CACHE", pm);
		resEnv.putData("message", message);
	}
}
