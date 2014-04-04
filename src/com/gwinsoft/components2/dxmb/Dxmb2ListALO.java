package com.gwinsoft.components2.dxmb;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class Dxmb2ListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,LSH,DXLX_DM,INFO,INFO2,JSRLX_DM,MBMC,BZ,XGRY_DM,TO_CHAR(XG_SJ,'yyyy-mm-dd hh24:mi:ss') AS XG_SJ FROM DX_DXMB T ORDER BY MBMC");
		Object[] args = new Object[] {};
		List<Dxmb2> dxmbs = null;
		if(pageBean!=null) {
			dxmbs = pm.queryPageList(sql.toString() , Dxmb2.class, args , pageBean);
		} else {
			dxmbs = pm.queryForList(sql.toString() , Dxmb2.class, args);
		}
		try {
			GwinSoftUtil.translate(dxmbs, "DXLX_DM", DMB.getTranslateStr(DMB.getDMB("DXLX_CACHE", "DXLX_MC")));
			GwinSoftUtil.translate(dxmbs, "JSRLX_DM", DMB.getTranslateStr(DMB.getDMB("JSRLX_CACHE", "JSRLX_MC")));
			GwinSoftUtil.translate(dxmbs, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("dxmbs", dxmbs);
	}
}
