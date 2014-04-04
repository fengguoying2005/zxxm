package com.gwinsoft.components2.yw.fpzjwh;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class FpzjwhSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT T.LSH AS HID_LSH,LSH,INFO,BZ,SWJGBM,KJNY,SSX,LR_SJ,XG_SJ,LRRY_DM,XGRY_DM,TO_CHAR(LR_SJ,'YYYY-MM-DD HH24:MI:SS') AS LRSJ,TO_CHAR(XG_SJ,'YYYY-MM-DD HH24:MI:SS') AS XGSJ FROM DX_FPZJXX T WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Fpzjwh fpzjwh = pm.queryForObject(sql, Fpzjwh.class, new Object[] { bh });
		try {
			GwinSoftUtil.translate(fpzjwh, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(fpzjwh, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("fpzjwh", fpzjwh);
	}
}
