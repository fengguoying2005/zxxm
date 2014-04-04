package com.gwinsoft.components.xtgl.dxmb;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class DxmbListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.DXMB_LSH AS HID_LSH,T.* FROM SMS_DXMB T WHERE 1=1 ");
		User user = this.getData("user");
		String FIND_ORG_DM_JG = user.getORG_DM_JG();
		if(FIND_ORG_DM_JG != null && !"".equals(FIND_ORG_DM_JG.trim())) {
			sql.append(" AND ORG_DM_JG = '"+FIND_ORG_DM_JG+"'");
		}
		Object[] args = new Object[] {};
		List<Dxmb> dxmbs = null;
		if(pageBean!=null) {
			dxmbs = pm.queryPageList(sql.toString() , Dxmb.class, args , pageBean);
		} else {
			dxmbs = pm.queryForList(sql.toString() , Dxmb.class, args);
		}
		try {
			GwinSoftUtil.translate(dxmbs, "ORG_DM_JG", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(dxmbs, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC")));
			GwinSoftUtil.translate(dxmbs, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("dxmbs", dxmbs);
	}
}
