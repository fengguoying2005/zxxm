package com.gwinsoft.components.nsrgl.nsrgroup;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.util.GwinSoftUtil;

public class NsrgroupSeeMXALO extends BaseALO {

	protected void doService() {
		String LSH = this.getData("LSH");
		String sql = "SELECT * FROM NSR_GROUPMX WHERE GROUPMX_LSH=?";
		GROUPMX groupmx = this.getPM().queryForObject(sql, GROUPMX.class, new String[]{LSH});
		this.putData("groupmx", groupmx);
		try {
			GwinSoftUtil.translate(groupmx, "ORG_DM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(groupmx, "HYDM", DMB.getTranslateStr(DMB.getDMB("HY_CACHE", "HY_MC")));
			GwinSoftUtil.translate(groupmx, "SBFS", DMB.getTranslateStr(DMB.getDMB("SBFS_CACHE", "SBFS_MC")));
			GwinSoftUtil.translate(groupmx, "DJZT", DMB.getTranslateStr(DMB.getDMB("DJZT_CACHE", "DJZT_MC")));
			GwinSoftUtil.translate(groupmx, "DJLX", DMB.getTranslateStr(DMB.getDMB("DJLX_CACHE", "DJLX_MC")));
			GwinSoftUtil.translate(groupmx, "ZCLX", DMB.getTranslateStr(DMB.getDMB("ZCLX_CACHE", "ZCLX_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
