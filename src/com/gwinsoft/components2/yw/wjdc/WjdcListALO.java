package com.gwinsoft.components2.yw.wjdc;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class WjdcListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,LSH,INFO,BZ,SWJGBM,SFJS,LR_SJ,XG_SJ,LRRY_DM,XGRY_DM,DCJL,TO_CHAR(LR_SJ,'YYYY-MM-DD HH24:MI:SS') AS LRSJ,TO_CHAR(XG_SJ,'YYYY-MM-DD HH24:MI:SS') AS XGSJ FROM DX_WJDC T WHERE 1=1 ");
		String FIND_INFO = reqEnv.getData("FIND_INFO");
		if(FIND_INFO != null && !"".equals(FIND_INFO.trim())) {
			sql.append(" AND INFO LIKE '%"+FIND_INFO+"%'");
		}
		String FIND_BZ = reqEnv.getData("FIND_BZ");
		if(FIND_BZ != null && !"".equals(FIND_BZ.trim())) {
			sql.append(" AND BZ LIKE '%"+FIND_BZ+"%'");
		}
		String FIND_SFJS = reqEnv.getData("FIND_SFJS");
		if(FIND_SFJS != null && !"".equals(FIND_SFJS.trim())) {
			sql.append(" AND SFJS = '"+FIND_SFJS+"'");
		}
		String FIND_DCJL = reqEnv.getData("FIND_DCJL");
		if(FIND_DCJL != null && !"".equals(FIND_DCJL.trim())) {
			sql.append(" AND DCJL LIKE '%"+FIND_DCJL+"%'");
		}
		Object[] args = new Object[] {};
		List<Wjdc> wjdcs = null;
		if(pageBean!=null) {
			wjdcs = pm.queryPageList(sql.toString() , Wjdc.class, args , pageBean);
		} else {
			wjdcs = pm.queryForList(sql.toString() , Wjdc.class, args);
		}
		try {
			GwinSoftUtil.translate(wjdcs, "SFJS", "0:已录入;1:已结束;2:已发送短信;");
			GwinSoftUtil.translate(wjdcs, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(wjdcs, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("wjdcs", wjdcs);
	}
}
