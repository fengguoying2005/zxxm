package com.gwinsoft.components2.job.js;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gwinsoft.components.smssh.sms.SMSDBTOOL;

public class HeartbeatJob implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SMSDBTOOL.query("SELECT NOW() FROM DUAL");
	}

}
