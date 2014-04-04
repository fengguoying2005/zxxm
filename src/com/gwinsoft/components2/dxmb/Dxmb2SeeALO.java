package com.gwinsoft.components2.dxmb;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class Dxmb2SeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT LSH,DXLX_DM,INFO,INFO2,JSRLX_DM,MBMC,BZ,XGRY_DM,TO_CHAR(XG_SJ,'yyyy-mm-dd hh24:mi:ss') AS XG_SJ FROM DX_DXMB WHERE LSH=?";
		String bh = reqEnv.getData("DXMB_LSH");
		DBPersistenceManager pm = this.getPM();
		Dxmb2 dxmb = pm.queryForObject(sql, Dxmb2.class, new Object[] { bh });
		try {
			GwinSoftUtil.translate(dxmb, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("dxmb", dxmb);
	}
}
