package com.gwinsoft.components.xtgl.zw;

import com.gwinsoft.framework.alo.BaseALO;

public class ZwDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("ZW_DM");
		String sql = "DELETE FROM DM_ZW WHERE ZW_DM=?";
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
