package com.gwinsoft.components.xtgl.dxqm;

import java.util.List;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class DxqmListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.DXQM_DM AS HID_LSH,T.* FROM DM_DXQM T WHERE 1=1 ");
		Object[] args = new Object[] {};
		List<Dxqm> dxqms = null;
		if(pageBean!=null) {
			dxqms = pm.queryPageList(sql.toString() , Dxqm.class, args , pageBean);
		} else {
			dxqms = pm.queryForList(sql.toString() , Dxqm.class, args);
		}
		resEnv.putData("dxqms", dxqms);
	}
}
