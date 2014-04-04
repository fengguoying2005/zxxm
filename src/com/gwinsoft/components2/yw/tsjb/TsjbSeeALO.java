package com.gwinsoft.components2.yw.tsjb;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class TsjbSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT T.LSH AS HID_LSH,LSH,SJHM,TSINFO,TS_SJ,HF_BJ,TO_CHAR(TS_SJ,'YYYY-MM-DD') AS TSSJ FROM DX_TSJBCX T WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Tsjb tsjb = pm.queryForObject(sql, Tsjb.class, new Object[] { bh });
		this.putData("tsjb", tsjb);
		String sql2 = "SELECT LSH,ZBLSH,HFINFO,HFR_DM,HF_SJ,TO_CHAR(HF_SJ,'YYYY-MM-DD HH24:MI:SS') AS HFSJ FROM DX_TSJBHF WHERE ZBLSH=?";
		List<TSJBHF> tablemx = pm.queryForList(sql2, TSJBHF.class, new String[]{bh});
		this.putData("tablemx", tablemx);
		try {
			GwinSoftUtil.translate(tsjb, "HF_BJ", "0:未回复;1:已回复");
			GwinSoftUtil.translate(tablemx, "HFR_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
