package com.gwinsoft.components.nsrgl.jbxx;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class NsrjbxxListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		User user = this.getData("user");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.LSH AS HID_LSH,T.* FROM NSR_JBXX T WHERE 1=1 ");
		String FIND_NSRBM = reqEnv.getData("FIND_NSRBM");
		if(FIND_NSRBM != null && !"".equals(FIND_NSRBM.trim())) {
			sql.append(" AND NSRBM LIKE '%"+FIND_NSRBM+"%'");
		}
		String FIND_NSRMC = reqEnv.getData("FIND_NSRMC");
		if(FIND_NSRMC != null && !"".equals(FIND_NSRMC.trim())) {
			sql.append(" AND NSRMC LIKE '%"+FIND_NSRMC+"%'");
		}
		String FIND_HYDM = reqEnv.getData("FIND_HYDM");
		if(FIND_HYDM != null && !"".equals(FIND_HYDM.trim())) {
			sql.append(" AND HYDM LIKE '%"+FIND_HYDM+"%'");
		}
		String FIND_ORG_DM = reqEnv.getData("FIND_ORG_DM");
		if(FIND_ORG_DM != null && !"".equals(FIND_ORG_DM.trim())) {
			sql.append(" AND ORG_DM = '"+FIND_ORG_DM+"'");
		} else {
			Map jg = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
			if(jg!=null) {
				sql.append(" AND ORG_DM IN (''");
				Iterator it = jg.keySet().iterator();
				while(it.hasNext()) {
					String swjg = (String) it.next();
					sql.append(",'"+swjg+"'");
				}
				sql.append(")");
			}
		}
		String FIND_SSZGY = reqEnv.getData("FIND_SSZGY");
		if(FIND_SSZGY != null && !"".equals(FIND_SSZGY.trim())) {
			sql.append(" AND SSZGY LIKE '%"+FIND_SSZGY+"%'");
		}
		String FIND_SBFS = reqEnv.getData("FIND_SBFS");
		if(FIND_SBFS != null && !"".equals(FIND_SBFS.trim())) {
			sql.append(" AND SBFS LIKE '%"+FIND_SBFS+"%'");
		}
		String FIND_DJLX = reqEnv.getData("FIND_DJLX");
		if(FIND_DJLX != null && !"".equals(FIND_DJLX.trim())) {
			sql.append(" AND DJLX LIKE '%"+FIND_DJLX+"%'");
		}
		String FIND_ZCLX = reqEnv.getData("FIND_ZCLX");
		if(FIND_ZCLX != null && !"".equals(FIND_ZCLX.trim())) {
			sql.append(" AND ZCLX LIKE '%"+FIND_ZCLX+"%'");
		}
		Object[] args = new Object[] {};
		List<Nsrjbxx> nsrjbxxs = null;
		if(pageBean!=null) {
			sql.append(" ORDER BY ORG_DM,NSRBM");
			nsrjbxxs = pm.queryPageList(sql.toString() , Nsrjbxx.class, args , pageBean);
		} else {
			nsrjbxxs = pm.queryForList(sql.toString()+" AND ROWNUM<=5000 ORDER BY ORG_DM,NSRBM" , Nsrjbxx.class, args);
		}
		try {
			GwinSoftUtil.translate(nsrjbxxs, "HYDM", DMB.getTranslateStr(DMB.getDMB("HY_CACHE", "HY_MC")));
			GwinSoftUtil.translate(nsrjbxxs, "ORG_DM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(nsrjbxxs, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(nsrjbxxs, "SBFS", DMB.getTranslateStr(DMB.getDMB("SBFS_CACHE", "SBFS_MC")));
			GwinSoftUtil.translate(nsrjbxxs, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(nsrjbxxs, "DJZT", DMB.getTranslateStr(DMB.getDMB("DJZT_CACHE", "DJZT_MC")));
			GwinSoftUtil.translate(nsrjbxxs, "DJLX", DMB.getTranslateStr(DMB.getDMB("DJLX_CACHE", "DJLX_MC")));
			GwinSoftUtil.translate(nsrjbxxs, "ZCLX", DMB.getTranslateStr(DMB.getDMB("ZCLX_CACHE", "ZCLX_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("nsrjbxxs", nsrjbxxs);
	}
}
