package com.gwinsoft.components.xtgl.zw;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class ZwListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.ZW_DM AS HID_LSH,T.* FROM DM_ZW T WHERE 1=1 ");
		Object[] args = new Object[] {};
		List<Zw> zws = null;
		if(pageBean!=null) {
			zws = pm.queryPageList(sql.toString() , Zw.class, args , pageBean);
		} else {
			zws = pm.queryForList(sql.toString() , Zw.class, args);
		}
		try {
			GwinSoftUtil.translate(zws, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(zws, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("zws", zws);
	}
}
