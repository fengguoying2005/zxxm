package com.gwinsoft.components2.yw.xydz;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class XydzSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT T.LSH AS HID_LSH,NSRBM AS HIDNSRBM,LSH,NSRBM,NSRMC,ORG_DM,CBTX,CJTX,JKTX,JKSBTX,GMFPTX,TYDQTX,KTRQ_Q,KTRQ_Z,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,BZ,TO_CHAR(KTRQ_Q,'YYYY-MM-DD') AS KTRQQ,TO_CHAR(KTRQ_Z,'YYYY-MM-DD') AS KTRQZ FROM DX_XYDZ T WHERE LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Xydz xydz = pm.queryForObject(sql, Xydz.class, new Object[] { bh });
		try {
			GwinSoftUtil.translate(xydz, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(xydz, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("xydz", xydz);
	}
}
