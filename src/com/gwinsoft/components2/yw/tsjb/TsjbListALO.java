package com.gwinsoft.components2.yw.tsjb;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class TsjbListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,LSH,SJHM,TSINFO,TS_SJ,HF_BJ,TO_CHAR(TS_SJ,'YYYY-MM-DD HH24:MI:SS') AS TSSJ FROM DX_TSJBCX T WHERE 1=1 ");
		String FIND_SJHM = reqEnv.getData("FIND_SJHM");
		if(FIND_SJHM != null && !"".equals(FIND_SJHM.trim())) {
			sql.append(" AND SJHM LIKE '%"+FIND_SJHM+"%'");
		}
		String FIND_TSINFO = reqEnv.getData("FIND_TSINFO");
		if(FIND_TSINFO != null && !"".equals(FIND_TSINFO.trim())) {
			sql.append(" AND TSINFO LIKE '%"+FIND_TSINFO+"%'");
		}
		String FIND_TS_SJ = reqEnv.getData("FIND_TS_SJ");
		if(FIND_TS_SJ != null && !"".equals(FIND_TS_SJ.trim())) {
			sql.append(" AND TS_SJ >= TO_DATE('"+FIND_TS_SJ+"','YYYY-MM-DD')");
		}
		String FIND_TS_SJ2 = reqEnv.getData("FIND_TS_SJ2");
		if(FIND_TS_SJ2 != null && !"".equals(FIND_TS_SJ2.trim())) {
			sql.append(" AND TS_SJ < (TO_DATE('"+FIND_TS_SJ2+"','YYYY-MM-DD')+1)");
		}
		Object[] args = new Object[] {};
		List<Tsjb> tsjbs = null;
		if(pageBean!=null) {
			tsjbs = pm.queryPageList(sql.toString() , Tsjb.class, args , pageBean);
		} else {
			tsjbs = pm.queryForList(sql.toString() , Tsjb.class, args);
		}
		try {
			GwinSoftUtil.translate(tsjbs, "HF_BJ", "0:未回复;1:已回复");
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("tsjbs", tsjbs);
	}
}
