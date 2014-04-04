package com.gwinsoft.components2.task;

import org.quartz.SchedulerException;

import com.gwinsoft.components2.job.JobHelper;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class TaskDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("LSH");
		String sql_delete = null;
		try {
			String sql = null;
			String bh = reqEnv.getData("LSH");
			String TYPE_DM2 = reqEnv.getData("TYPE_DM2");
			if("FS".equals(TYPE_DM2)) {
				sql = "SELECT TYPE_DM,CRON,SCFS_SJ,NULL AS SCJS_SJ,YX_BJ,BZ,'FS' AS TYPE_DM2 FROM DX_FSRW WHERE TYPE_DM=?";
				sql_delete = "DELETE FROM DX_FSRW WHERE TYPE_DM=?";
			} else {
				sql = "SELECT TYPE_DM,CRON,NULL AS SCFS_SJ,SCJS_SJ,YX_BJ,BZ,'JS' AS TYPE_DM2 FROM DX_JSRW WHERE TYPE_DM=?";
				sql_delete = "DELETE FROM DX_JSRW WHERE TYPE_DM=?";
			}
			DBPersistenceManager pm = this.getPM();
			Task task = pm.queryForObject(sql, Task.class, new Object[] { bh });
			JobHelper.stop(task);
		} catch (SchedulerException e) {
			String message = "删除失败（停止任务失败）！";
			resEnv.putData("message", message);
		}
		this.getPM().executeUpdate(sql_delete, new Object[] { BH });
		String message = "";
		message = "删除成功！";
		resEnv.putData("message", message);
	}
}
