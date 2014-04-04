package com.gwinsoft.components2.yw.xydz;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class XydzListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,LSH,NSRBM,NSRMC,ORG_DM,CBTX,CJTX,JKTX,JKSBTX,GMFPTX,TYDQTX,LRRY_DM,XGRY_DM,BZ,TO_CHAR(KTRQ_Q,'YYYY-MM-DD') AS KTRQQ,TO_CHAR(KTRQ_Z,'YYYY-MM-DD') AS KTRQZ FROM DX_XYDZ T WHERE 1=1 ");
		String FIND_NSRBM = reqEnv.getData("FIND_NSRBM");
		if(FIND_NSRBM != null && !"".equals(FIND_NSRBM.trim())) {
			sql.append(" AND NSRBM LIKE '%"+FIND_NSRBM+"%'");
		}
		String FIND_NSRMC = reqEnv.getData("FIND_NSRMC");
		if(FIND_NSRMC != null && !"".equals(FIND_NSRMC.trim())) {
			sql.append(" AND NSRMC LIKE '%"+FIND_NSRMC+"%'");
		}
		String FIND_ORG_DM = reqEnv.getData("FIND_ORG_DM");
		if(FIND_ORG_DM != null && !"".equals(FIND_ORG_DM.trim())) {
			sql.append(" AND ORG_DM LIKE '%"+FIND_ORG_DM+"%'");
		}
		String FIND_CBTX = reqEnv.getData("FIND_CBTX");
		if(FIND_CBTX != null && !"".equals(FIND_CBTX.trim())) {
			sql.append(" AND CBTX = '"+FIND_CBTX+"'");
		}
		String FIND_CJTX = reqEnv.getData("FIND_CJTX");
		if(FIND_CJTX != null && !"".equals(FIND_CJTX.trim())) {
			sql.append(" AND CJTX = '"+FIND_CJTX+"'");
		}
		String FIND_JKTX = reqEnv.getData("FIND_JKTX");
		if(FIND_JKTX != null && !"".equals(FIND_JKTX.trim())) {
			sql.append(" AND JKTX = '"+FIND_JKTX+"'");
		}
		String FIND_JKSBTX = reqEnv.getData("FIND_JKSBTX");
		if(FIND_JKSBTX != null && !"".equals(FIND_JKSBTX.trim())) {
			sql.append(" AND JKSBTX = '"+FIND_JKSBTX+"'");
		}
		String FIND_GMFPTX = reqEnv.getData("FIND_GMFPTX");
		if(FIND_GMFPTX != null && !"".equals(FIND_GMFPTX.trim())) {
			sql.append(" AND GMFPTX = '"+FIND_GMFPTX+"'");
		}
		String FIND_TYDQTX = reqEnv.getData("FIND_TYDQTX");
		if(FIND_TYDQTX != null && !"".equals(FIND_TYDQTX.trim())) {
			sql.append(" AND TYDQTX = '"+FIND_TYDQTX+"'");
		}
		String FIND_KTRQQ = reqEnv.getData("FIND_KTRQQ");
		if(FIND_KTRQQ != null && !"".equals(FIND_KTRQQ.trim())) {
			sql.append(" AND KTRQ_Q <= TO_DATE('"+FIND_KTRQQ+"','YYYY-MM-DD')");
			sql.append(" AND KTRQ_Z >= TO_DATE('"+FIND_KTRQQ+"','YYYY-MM-DD')");
		}
		String FIND_KTRQZ = reqEnv.getData("FIND_KTRQZ");
		if(FIND_KTRQZ != null && !"".equals(FIND_KTRQZ.trim())) {
			sql.append(" AND KTRQ_Q <= TO_DATE('"+FIND_KTRQZ+"','YYYY-MM-DD')");
			sql.append(" AND KTRQ_Z >= TO_DATE('"+FIND_KTRQZ+"','YYYY-MM-DD')");
		}
		String FIND_BZ = reqEnv.getData("FIND_BZ");
		if(FIND_BZ != null && !"".equals(FIND_BZ.trim())) {
			sql.append(" AND BZ LIKE '%"+FIND_BZ+"%'");
		}
		Object[] args = new Object[] {};
		List<Xydz> xydzs = null;
		if(pageBean!=null) {
			xydzs = pm.queryPageList(sql.toString() , Xydz.class, args , pageBean);
		} else {
			xydzs = pm.queryForList(sql.toString() , Xydz.class, args);
		}
		try {
			GwinSoftUtil.translate(xydzs, "ORG_DM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(xydzs, "CBTX", "0:未开通;1:已开通;");
			GwinSoftUtil.translate(xydzs, "CJTX", "0:未开通;1:已开通;");
			GwinSoftUtil.translate(xydzs, "JKTX", "0:未开通;1:已开通;");
			GwinSoftUtil.translate(xydzs, "JKSBTX", "0:未开通;1:已开通;");
			GwinSoftUtil.translate(xydzs, "GMFPTX", "0:未开通;1:已开通;");
			GwinSoftUtil.translate(xydzs, "TYDQTX", "0:未开通;1:已开通;");
			GwinSoftUtil.translate(xydzs, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(xydzs, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("xydzs", xydzs);
	}
}
