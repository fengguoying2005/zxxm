package com.gwinsoft.components2.job;

import java.io.Serializable;

import org.quartz.JobDetail;
import org.quartz.Trigger;

public class JobData implements Serializable {
	private static final long serialVersionUID = 1L;
	private JobDetail jobDetail;
	private Trigger trigger;

	public JobData(JobDetail jobDetail, Trigger trigger2) {
		this.jobDetail = jobDetail;
		this.trigger = trigger2;
	}

	public JobDetail getJobDetail() {
		return jobDetail;
	}

	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
}