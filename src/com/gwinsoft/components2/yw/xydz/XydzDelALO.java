package com.gwinsoft.components2.yw.xydz;

import com.gwinsoft.framework.alo.BaseALO;

public class XydzDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("LSH");
		String sql = "DELETE FROM DX_XYDZ WHERE LSH=?";
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
