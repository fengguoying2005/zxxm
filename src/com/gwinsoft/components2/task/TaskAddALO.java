package com.gwinsoft.components2.task;

import org.quartz.SchedulerException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components2.job.JobHelper;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class TaskAddALO extends BaseALO {

	protected void doService() {
		Task task = reqEnv.getData("task");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		DBPersistenceManager pm = this.getPM();
		String message = "";
		String sql_check = null;
		String sql = null;
		if("FS".equals(task.getTYPE_DM2())) {
			task.setSCFS_SJ(time);
			sql = "INSERT INTO DX_FSRW(TYPE_DM,CRON,SCFS_SJ,YX_BJ,BZ) VALUES(?,?,?,?,?)";
			sql_check = "SELECT CASE WHEN TYPE_DM = '" + task.getTYPE_DM() + "' THEN '<任务>已经存在。' END MSG FROM DX_FSRW WHERE TYPE_DM = '" + task.getTYPE_DM() + "'";
		} else {
			task.setSCJS_SJ(time);
			sql = "INSERT INTO DX_JSRW(TYPE_DM,CRON,SCJS_SJ,YX_BJ,BZ) VALUES(?,?,?,?,?)";
			sql_check = "SELECT CASE WHEN TYPE_DM = '" + task.getTYPE_DM() + "' THEN '<任务>已经存在。' END MSG FROM DX_JSRW WHERE TYPE_DM = '" + task.getTYPE_DM() + "'";
		}
		SqlRowSet r = pm.quereyForRowSet(sql_check );
		if (r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate(sql, task);
			message = "保存成功！";
			try {
				JobHelper.start(task);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		resEnv.putData("message", message);
		resEnv.putData("uid", task.getTYPE_DM());
	}
}
