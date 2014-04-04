package com.gwinsoft.components.xtgl.dxcs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class DxcsListALO extends BaseALO {

	protected void doService() {
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.YYS_DM AS YYS_MC,T.* FROM DM_DXCS T WHERE 1=1 ");
		Object[] args = new Object[] {};
		List<Dxcs> dxcss = null;
		dxcss = pm.queryForList(sql.toString() , Dxcs.class, args);
		if(dxcss == null) {
			dxcss = new ArrayList<Dxcs>();
		}
		Map yysmap = DMB.getDMB("YYS_CACHE", "YYS_MC");
		Iterator it = yysmap.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			boolean isHave = false;
			for(Dxcs cs : dxcss) {
				if(cs.getYYS_DM().equals(key)) {
					isHave = true;
					break;
				}
			}
			if(!isHave) {
				Dxcs cs = new Dxcs();
				cs.setYYS_DM(key);
				cs.setYYS_MC(key);
				cs.setBZ("");
				dxcss.add(cs );
			}
		}
		try {
			GwinSoftUtil.translate(dxcss, "YYS_MC", DMB.getTranslateStr(DMB.getDMB("YYS_CACHE", "YYS_MC")));
			GwinSoftUtil.translate(dxcss, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
			GwinSoftUtil.translate(dxcss, "XGRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("dxcss", dxcss);
	}
}
