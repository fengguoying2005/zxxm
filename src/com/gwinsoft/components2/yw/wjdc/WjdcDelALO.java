package com.gwinsoft.components2.yw.wjdc;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class WjdcDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("LSH");
		String sql = "DELETE FROM DX_WJDC WHERE LSH=?";
		String sql2 = "SELECT SFJS AS FLAG FROM DX_WJDC WHERE LSH='"+BH+"'";
		DBPersistenceManager pm = this.getPM();
		SqlRowSet row = pm.quereyForRowSet(sql2);
		if(row.next()) {
			String FLAG = row.getString("FLAG");
			if(!"0".equals(FLAG)) {
				resEnv.putData("message", "当前状态不允许删除。");
			} else {
				pm.executeUpdate(sql, new Object[] { BH });
				String message = "";
				message="删除成功！";
				resEnv.putData("message", message);
			}
		}
	}
}
