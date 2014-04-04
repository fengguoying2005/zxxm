package com.gwinsoft.components2.task;

import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class TaskListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT TYPE_DM,CRON,TO_CHAR(SCFS_SJ, 'YYYY-MM-DD HH24:MI:SS') AS SCFS_SJ,NULL AS SCJS_SJ,YX_BJ,BZ,'FS' AS TYPE_DM2 FROM DX_FSRW UNION SELECT TYPE_DM,CRON,TO_CHAR(SCJS_SJ,'YYYY-MM-DD HH24:MI:SS') AS SCFS_SJ,NULL AS SCJS_SJ,YX_BJ,BZ,'JS' AS TYPE_DM2 FROM DX_JSRW) T ORDER BY BZ, TYPE_DM2, TYPE_DM");
		Object[] args = new Object[] {};
		List<Task> tasks = null;
		if (pageBean != null) {
			tasks = pm.queryPageList(sql.toString(), Task.class, args, pageBean);
		} else {
			tasks = pm.queryForList(sql.toString(), Task.class, args);
		}
		try {
			GwinSoftUtil.translate(tasks, "YX_BJ", "0:无效;1:有效;");
//			GwinSoftUtil.translate(tasks, "TYPE_DM2", "FS:发送任务;JS:接收任务;");
//			GwinSoftUtil.translate(tasks, "LRRY_DM", DMB.getTranslateStr(DMB.getDMB("USER_CACHE", "USER_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("tasks", tasks);
	}
}
