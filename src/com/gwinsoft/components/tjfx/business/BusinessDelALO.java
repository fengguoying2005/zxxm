package com.gwinsoft.components.tjfx.business;

import com.gwinsoft.framework.alo.BaseALO;

public class BusinessDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("NSRDATA_LSH");
		String sql = "DELETE FROM SMS_NSRDATA WHERE NSRDATA_LSH=?";
		String sql2 = "DELETE FROM SMS_NSRDATAMX WHERE NSRDATA_LSH=?";
		this.getPM().executeUpdate(sql2, new Object[] { BH });
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
