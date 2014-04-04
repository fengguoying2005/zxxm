package com.gwinsoft.components2.yw.gmfptx;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class GmfptxSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT T.LSH AS HID_LSH,LSH,NSRSBM,NSRMC,SJHM,SMSINFO,LR_SJ,TO_CHAR(FP_RQ,'YYYY-MM-DD') AS TY_RQ,BZ,FP_JE,FPDM,FPHM,FPZL,SWJGBM,FSZT_DM,FS_SJ,FPTX_LSH,SJLX FROM DX_GMFPTX T WHERE 1=1  AND LSH=?";
		String bh = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		Gmfptx gmfptx = pm.queryForObject(sql, Gmfptx.class, new Object[] { bh });
		try {
			GwinSoftUtil.translate(gmfptx, "FSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("gmfptx", gmfptx);
	}
}
