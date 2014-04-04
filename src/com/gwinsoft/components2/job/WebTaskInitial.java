package com.gwinsoft.components2.job;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.gwinsoft.components2.task.Task;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class WebTaskInitial extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static boolean isRunning = false;
	
	public void init(ServletConfig config) throws ServletException {
		String path = config.getServletContext().getRealPath("") + config.getInitParameter("config");
		try {
			start(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void start(String path) throws Exception {
		if(!isRunning) {
			isRunning = true;
			//String path = IPSeeker.class.getResource("").toString() + "quartz.properties";
			TaskSchedulerFactory.getInstance().setFactory(new StdSchedulerFactory(path));
			loadJobDetails();
			try {
				TaskSchedulerFactory.getInstance().getScheduler().addGlobalJobListener(new TCPJobListener());
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
			try {
				TaskSchedulerFactory.getInstance().getScheduler().start();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadJobDetails() {
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			String sql = "SELECT TYPE_DM,CRON,SCFS_SJ,NULL AS SCJS_SJ,YX_BJ,BZ,'FS' AS TYPE_DM2 FROM DX_FSRW WHERE YX_BJ='1' UNION SELECT TYPE_DM,CRON,NULL AS SCFS_SJ,SCJS_SJ,YX_BJ,BZ,'JS' AS TYPE_DM2 FROM DX_JSRW WHERE YX_BJ='1'";
			List<Task> tasks = pm.queryForList(sql.toString(), Task.class, new Object[] {});
			for(Task task:tasks) {
				JobHelper.start(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
	}
}