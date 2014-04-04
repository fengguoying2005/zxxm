package com.gwinsoft.components2.yw.tsjb;

import com.gwinsoft.framework.alo.BaseALO;

public class TsjbDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("LSH");
		String sql = "DELETE FROM DX_TSJBCX WHERE LSH=?";
		String sql2 = "DELETE FROM DX_TSJBHF WHERE ZBLSH=?";
		this.getPM().executeUpdate(sql2, new Object[] { BH });
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
