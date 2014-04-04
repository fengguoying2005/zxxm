package com.gwinsoft.components2.yw.gmfptx;

import com.gwinsoft.framework.alo.BaseALO;

public class GmfptxDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("LSH");
		String sql = "DELETE FROM DX_GMFPTX WHERE LSH=?";
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
