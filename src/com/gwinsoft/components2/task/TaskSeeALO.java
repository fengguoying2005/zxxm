package com.gwinsoft.components2.task;

import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class TaskSeeALO extends BaseALO {

	protected void doService() {
		String sql = null;
		String bh = reqEnv.getData("LSH");
		String TYPE_DM2 = reqEnv.getData("TYPE_DM2");
		if("FS".equals(TYPE_DM2)) {
			sql = "SELECT TYPE_DM,CRON,TO_CHAR(SCFS_SJ,'YYYY-MM-DD HH24:MI:SS') AS SCFS_SJ,YX_BJ,BZ,'FS' AS TYPE_DM2 FROM DX_FSRW WHERE TYPE_DM=?";
		} else {
			sql = "SELECT TYPE_DM,CRON,TO_CHAR(SCJS_SJ,'YYYY-MM-DD HH24:MI:SS') AS SCFS_SJ,YX_BJ,BZ,'JS' AS TYPE_DM2 FROM DX_JSRW WHERE TYPE_DM=?";
		}
		DBPersistenceManager pm = this.getPM();
		Task task = pm.queryForObject(sql, Task.class, new Object[] { bh });
		this.putData("task", task);
	}
}
