package com.gwinsoft.components.nsrgl.jbxx;

import com.gwinsoft.framework.alo.BaseALO;

public class NsrjbxxDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("LSH");
		String sql = "DELETE FROM NSR_JBXX WHERE LSH=?";
		this.getPM().executeUpdate(sql, new Object[] { BH });
		String message = "";
		message="删除成功！";
		resEnv.putData("message", message);
	}
}
