package com.gwinsoft.components2.job;

import java.util.Hashtable;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

public class TCPJobListener implements JobListener {
	private static final Hashtable<Object, Object> JOB_PROCESS_INFOS = new Hashtable<Object, Object>();

	public String getName() {
		return "ProcessJobListener";
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
		System.out.println();
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		try {
			context.getScheduler().pauseJob(context.getJobDetail().getName(),
					"default");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		Processor processor = null;
		if (ProcessJob.class.isAssignableFrom(context.getJobDetail()
				.getJobClass())) {
			processor = new Processor();
			context.getJobDetail().getJobDataMap()
					.put(Processor.PROCESSOR_STORAGE_KEY, processor);
		}
		if (processor != null) {
			JOB_PROCESS_INFOS.put(context.getJobDetail().getFullName(),
					processor);
		} else {
			JOB_PROCESS_INFOS.put(context.getJobDetail().getFullName(), "");
		}
	}

	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		try {
			context.getScheduler().resumeJob(context.getJobDetail().getName(),
					"default");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		JOB_PROCESS_INFOS.remove(context.getJobDetail().getFullName());
	}

	public static final String[] getRunningJobs() {
		Object[] keys = JOB_PROCESS_INFOS.keySet().toArray();
		String[] result = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			result[i] = keys[i].toString();
		}

		return result;
	}

	public static final Processor getJobProcessor(String jobFullName) {
		Object obj = JOB_PROCESS_INFOS.get(jobFullName);
		Processor processor = null;
		if (obj instanceof Processor) {
			processor = (Processor) obj;
			if (processor.isComplete() || processor.getPercentage() >= 1) {
				JOB_PROCESS_INFOS.remove(jobFullName);
			}
		}
		return processor;
	}

	public static final boolean isRunningJob(String jobFullName) {
		return JOB_PROCESS_INFOS.containsKey(jobFullName);
	}
}