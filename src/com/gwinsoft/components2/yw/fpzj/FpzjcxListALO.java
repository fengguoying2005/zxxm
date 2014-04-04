package com.gwinsoft.components2.yw.fpzj;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class FpzjcxListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,T.* FROM DX_FPZJCX T WHERE 1=1 ");
		String FIND_SSX = reqEnv.getData("FIND_SSX");
		if(FIND_SSX != null && !"".equals(FIND_SSX.trim())) {
			sql.append(" AND SSX = '"+FIND_SSX+"'");
		}
		String FIND_KJNY = reqEnv.getData("FIND_KJNY");
		if(FIND_KJNY != null && !"".equals(FIND_KJNY.trim())) {
			sql.append(" AND KJNY LIKE '%"+FIND_KJNY+"%'");
		}
		String FIND_SJHM = reqEnv.getData("FIND_SJHM");
		if(FIND_SJHM != null && !"".equals(FIND_SJHM.trim())) {
			sql.append(" AND SJHM LIKE '%"+FIND_SJHM+"%'");
		}
		String FIND_LR_SJ = reqEnv.getData("FIND_LR_SJ");
		if(FIND_LR_SJ != null && !"".equals(FIND_LR_SJ.trim())) {
			sql.append(" AND LR_SJ >= TO_DATE('"+FIND_LR_SJ+"','YYYY-MM-DD')");
		}
		String FIND_LR_SJ2 = reqEnv.getData("FIND_LR_SJ2");
		if(FIND_LR_SJ2 != null && !"".equals(FIND_LR_SJ2.trim())) {
			sql.append(" AND LR_SJ < (TO_DATE('"+FIND_LR_SJ2+"','YYYY-MM-DD')+1)");
		}
		Object[] args = new Object[] {};
		List<Fpzjcx> fpzjcxs = null;
		if(pageBean!=null) {
			fpzjcxs = pm.queryPageList(sql.toString() , Fpzjcx.class, args , pageBean);
		} else {
			fpzjcxs = pm.queryForList(sql.toString() , Fpzjcx.class, args);
		}
		try {
			GwinSoftUtil.translate(fpzjcxs, "SSX", DMB.getTranslateStr(DMB.getDMB("SJSX_CACHE", "SJSX_MC")));
			GwinSoftUtil.translate(fpzjcxs, "FSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("fpzjcxs", fpzjcxs);
	}
}
