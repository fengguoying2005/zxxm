package com.gwinsoft.components.xtgl.dxcs;

import com.gwinsoft.framework.alo.BaseALO;

public class DxcsDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("YYS_DM");
		String sql = "DELETE FROM DM_DXCS WHERE YYS_DM=?";
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
