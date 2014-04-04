package com.gwinsoft.components2.yw.wjdc;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class WjdcSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT T.LSH AS HID_LSH,LSH,INFO,BZ,SWJGBM,SFJS,LR_SJ,XG_SJ,LRRY_DM,XGRY_DM,DCJL,TO_CHAR(LR_SJ,'YYYY-MM-DD HH24:MI:SS') AS LRSJ,TO_CHAR(XG_SJ,'YYYY-MM-DD HH24:MI:SS') AS XGSJ FROM DX_WJDC T WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Wjdc wjdc = pm.queryForObject(sql, Wjdc.class, new Object[] { bh });
		try {
			GwinSoftUtil.translate(wjdc, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(wjdc, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("wjdc", wjdc);
	}
}
