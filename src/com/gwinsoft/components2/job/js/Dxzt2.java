package com.gwinsoft.components2.job.js;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gwinsoft.components2.job.JobUtils;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class Dxzt2 implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("二期短信状态");
		String type_dm = "DXZT2";
		String from = JobUtils.getJsJobStart(type_dm);
		String to = JobUtils.getMasDate();
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			System.out.println(from+"->"+to);
			JobUtils.saveJsJobEnd(type_dm, to);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
	}
}