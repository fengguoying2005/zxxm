package com.gwinsoft.components2.yw.wjdc;

import java.util.List;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class WjdcListMxALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("LSH");
		PageBean pagebean = this.getData("pagebean");
		DBPersistenceManager pm = this.getPM();
		String selectSQL = "SELECT * FROM DX_WJDCMX WHERE ZB_LSH='"+LSH+"'";
		List<Wjdcmx> list = pm.queryPageList(selectSQL, Wjdcmx.class, new String[] {}, pagebean);
		if(list.size()>0) {
			try {
				GwinSoftUtil.translate(list, "PHONETYPE", "BSRYSJH:办税人员;CWJLSJH:财务经理;FRSJH:法人;");
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.putData("tablemx", list);
		}
	}

}
