package com.gwinsoft.components.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gwinsoft.components.smssh.sms.SMSDBTOOL;

//TODO 无用
@Deprecated
public class HeartbeatJob implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SMSDBTOOL.query("SELECT NOW() FROM DUAL");
	}

}
