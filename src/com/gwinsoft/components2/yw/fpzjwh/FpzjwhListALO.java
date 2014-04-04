package com.gwinsoft.components2.yw.fpzjwh;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class FpzjwhListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,LSH,INFO,BZ,SWJGBM,KJNY,SSX,LR_SJ,XG_SJ,LRRY_DM,XGRY_DM,TO_CHAR(LR_SJ,'YYYY-MM-DD HH24:MI:SS') AS LRSJ,TO_CHAR(XG_SJ,'YYYY-MM-DD HH24:MI:SS') AS XGSJ FROM DX_FPZJXX T WHERE 1=1 ");
		String FIND_SSX = reqEnv.getData("FIND_SSX");
		if(FIND_SSX != null && !"".equals(FIND_SSX.trim())) {
			sql.append(" AND SSX = '"+FIND_SSX+"'");
		}
		String FIND_KJNY = reqEnv.getData("FIND_KJNY");
		if(FIND_KJNY != null && !"".equals(FIND_KJNY.trim())) {
			sql.append(" AND KJNY LIKE '%"+FIND_KJNY+"%'");
		}
		Object[] args = new Object[] {};
		List<Fpzjwh> fpzjwhs = null;
		if(pageBean!=null) {
			fpzjwhs = pm.queryPageList(sql.toString() , Fpzjwh.class, args , pageBean);
		} else {
			fpzjwhs = pm.queryForList(sql.toString() , Fpzjwh.class, args);
		}
		try {
			GwinSoftUtil.translate(fpzjwhs, "SSX", DMB.getTranslateStr(DMB.getDMB("SJSX_CACHE", "SJSX_MC")));
			GwinSoftUtil.translate(fpzjwhs, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(fpzjwhs, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("fpzjwhs", fpzjwhs);
	}
}
