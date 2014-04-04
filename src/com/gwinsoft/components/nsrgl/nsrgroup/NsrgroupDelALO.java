package com.gwinsoft.components.nsrgl.nsrgroup;

import com.gwinsoft.framework.alo.BaseALO;

public class NsrgroupDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("LSH");
		String sql = "DELETE FROM NSR_GROUP WHERE LSH=?";
		String sql2 = "DELETE FROM NSR_GROUPMX WHERE GROUP_LSH=?";
		this.getPM().executeUpdate(sql2, new Object[] { BH });
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
