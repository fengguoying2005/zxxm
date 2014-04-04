package com.gwinsoft.components2.yw.gmfptx;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class GmfptxListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,LSH,NSRSBM,NSRMC,SJHM,SMSINFO,LR_SJ,TO_CHAR(FP_RQ,'YYYY-MM-DD') AS TY_RQ,BZ,FP_JE,FPDM,FPHM,FPZL,SWJGBM,FSZT_DM,FS_SJ,FPTX_LSH,SJLX FROM DX_GMFPTX T WHERE 1=1 ");
		String FIND_SWJGBM = reqEnv.getData("FIND_SWJGBM");
		if(FIND_SWJGBM != null && !"".equals(FIND_SWJGBM.trim())) {
			sql.append(" AND SWJGBM = '"+FIND_SWJGBM+"'");
		}
		String FIND_NSRSBM = reqEnv.getData("FIND_NSRSBM");
		if(FIND_NSRSBM != null && !"".equals(FIND_NSRSBM.trim())) {
			sql.append(" AND NSRSBM LIKE '%"+FIND_NSRSBM+"%'");
		}
		String FIND_NSRMC = reqEnv.getData("FIND_NSRMC");
		if(FIND_NSRMC != null && !"".equals(FIND_NSRMC.trim())) {
			sql.append(" AND NSRMC LIKE '%"+FIND_NSRMC+"%'");
		}
		String FIND_SJLX = reqEnv.getData("FIND_SJLX");
		if(FIND_SJLX != null && !"".equals(FIND_SJLX.trim()) && !"ALL".equals(FIND_SJLX.trim())) {
			sql.append(" AND SJLX = '"+FIND_SJLX+"'");
		}
		String FIND_TYDQ_RQ = reqEnv.getData("FIND_TYDQ_RQ");
		String FIND_TY_RQ = reqEnv.getData("FIND_TY_RQ");
		
		if(FIND_TYDQ_RQ != null && !"".equals(FIND_TYDQ_RQ.trim())) {
			sql.append(" AND FP_RQ >= TO_DATE('"+FIND_TYDQ_RQ+"','YYYY-MM-DD')");
		}
		if(FIND_TY_RQ != null && !"".equals(FIND_TY_RQ.trim())) {
			sql.append(" AND FP_RQ < (TO_DATE('"+FIND_TY_RQ+"','YYYY-MM-DD')+1)");
		}
		Object[] args = new Object[] {};
		List<Gmfptx> gmfptxs = null;
		if(pageBean!=null) {
			gmfptxs = pm.queryPageList(sql.toString() , Gmfptx.class, args , pageBean);
		} else {
			gmfptxs = pm.queryForList(sql.toString() , Gmfptx.class, args);
		}
		try {
			GwinSoftUtil.translate(gmfptxs, "SWJGBM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(gmfptxs, "SJLX", DMB.getTranslateStr(DMB.getDMB("JSRLX_CACHE", "JSRLX_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("gmfptxs", gmfptxs);
	}
}
