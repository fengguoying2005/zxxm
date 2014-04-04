package com.gwinsoft.components.xtgl.hmd;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class HmdListALO extends BaseALO {

	protected void doService() {
		User user = this.getData("user");
		String NSRBM = this.getData("NSRBM");
		String NSRMC = this.getData("NSRMC");
		
		StringBuffer sql = new StringBuffer("SELECT * FROM T_HMD WHERE 1=1");
		Map jg = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
		if(jg!=null) {
			sql.append(" AND ORG_DM_J IN (''");
			Iterator it = jg.keySet().iterator();
			while(it.hasNext()) {
				String swjg = (String) it.next();
				sql.append(",'").append(swjg).append("'");
			}
			sql.append(")");
		}
		if(NSRBM!=null) {
			sql.append(" AND NSRBM LIKE '%"+NSRBM+"%'");
		}
		if(NSRMC!=null) {
			sql.append(" AND NSRMC LIKE '%"+NSRMC+"%'");
		}

		PageBean pagebean = this.getData("pagebean");
		DBPersistenceManager pm = this.getPM();
		List<HMD> tablemx = pm.queryPageList(sql.toString(), HMD.class, new String[] {}, pagebean);
		try {
			GwinSoftUtil.translate(tablemx, "ORG_DM_J", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(tablemx, "SJLX_DM", "01:办税人员;02:法人;03:财务经理;04:未知;");
			GwinSoftUtil.translate(tablemx, "RYLX_DM", "01:地税添加;02:纳税人回复;");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.putData("tablemx", tablemx);
	}

}