package com.gwinsoft.components2.task;

import org.quartz.SchedulerException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.components2.job.JobHelper;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class TaskModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Task task = reqEnv.getData("task");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String BH = reqEnv.getData("LSH");
		String sql_select = null;
		String sql_check = null;
		String sql_pdate = null;
		if("FS".equals(task.getTYPE_DM2())) {
			sql_select = "SELECT T.*,'FS' AS TYPE_DM2 FROM DX_FSRW T WHERE TYPE_DM=?";
			sql_pdate = "UPDATE DX_FSRW SET TYPE_DM=?,CRON=?,YX_BJ=?,BZ=? WHERE TYPE_DM='"+BH+"'";
			sql_check = "SELECT CASE WHEN TYPE_DM = '" + task.getTYPE_DM() + "' THEN '<任务>已经存在。' END MSG FROM DX_FSRW WHERE TYPE_DM!='"+BH+"' AND TYPE_DM = '" + task.getTYPE_DM() + "'";
		} else {
			sql_select = "SELECT T.*,'JS' AS TYPE_DM2 FROM DX_JSRW T WHERE TYPE_DM=?";
			sql_pdate = "UPDATE DX_JSRW SET TYPE_DM=?,CRON=?,YX_BJ=?,BZ=? WHERE TYPE_DM='"+BH+"'";
			sql_check = "SELECT CASE WHEN TYPE_DM = '" + task.getTYPE_DM() + "' THEN '<任务>已经存在。' END MSG FROM DX_JSRW WHERE TYPE_DM!='"+BH+"' AND TYPE_DM = '" + task.getTYPE_DM() + "'";
		}
		DBPersistenceManager pm = this.getPM();
		SqlRowSet r = pm.quereyForRowSet(sql_check);
		if (r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			try {
				Task task22 = pm.queryForObject(sql_select, Task.class, new Object[] { BH });
				JobHelper.stop(task22);
			} catch (SchedulerException e) {
				message = "删除失败（停止任务失败）！";
				resEnv.putData("message", message);
			}
			pm.executeUpdate(sql_pdate, task);
			message = "修改成功！";
			if("1".equals(task.getYX_BJ())) {
				try {
					JobHelper.start(task);
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		}
		resEnv.putData("message", message);
	}
}
