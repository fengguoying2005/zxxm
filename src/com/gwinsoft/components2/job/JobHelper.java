package com.gwinsoft.components2.job;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

import com.gwinsoft.components2.task.Task;
import com.gwinsoft.util.GwinSoftUtil;

public class JobHelper {
	public static final String[] getRunningJobs() {
		return TCPJobListener.getRunningJobs();
	}

	public static final Processor getJobProcessor(String jobFullName) {
		return TCPJobListener.getJobProcessor(jobFullName);
	}

	public static final boolean isRunningJob(String jobFullName) {
		return TCPJobListener.isRunningJob(jobFullName);
	}

	public static final String getJobProcessorText(String jobFullName) {
		Processor processor = TCPJobListener.getJobProcessor(jobFullName);
		return processor != null ? processor.getPercentageText() : "没有进度显示";
	}
	public static void start(Task task) throws SchedulerException {
		String tasktype = task.getTYPE_DM();
		String jobClass = null;
		if("FS".equals(task.getTYPE_DM2())) {
			jobClass = "com.gwinsoft.components2.job.fs."+tasktype;
		} else {
			jobClass = "com.gwinsoft.components2.job.js."+tasktype;
		}
		String cron = task.getCRON();
		
		JobDetail jobDetail = new JobDetail();
		CronTrigger trigger = new CronTrigger();
		String name = tasktype + task.getTYPE_DM2();
		jobDetail.setName(name);
		jobDetail.setGroup("default");
		try {
			jobDetail.setJobClass(Class.forName(jobClass));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		jobDetail.setRequestsRecovery(false);
		jobDetail.setDescription("BZ");
		trigger.setName(name);
		trigger.setGroup("default");
		trigger.setJobName(name);
		trigger.setJobGroup("default");
		trigger.setDescription("LGN");
		Date startTime = GwinSoftUtil.getSysDate();
		Date endTime = GwinSoftUtil.getdateAdded(startTime, Calendar.YEAR, 1);
		trigger.setStartTime(startTime);
		trigger.setEndTime(endTime);
		trigger.setCalendarName(name);
		try {
			trigger.setCronExpression(cron);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
		TaskSchedulerFactory.getInstance().getScheduler().addJob(jobDetail, true);
		TaskSchedulerFactory.getInstance().getScheduler().scheduleJob(trigger);
	}
	//修改时调用stop，再调用start
	//删除时调用stop
	//增加时调用start
	public static boolean stop(Task task) throws SchedulerException {
		if (!JobHelper.isRunningJob("default." + task.getTYPE_DM() + task.getTYPE_DM2())) {
			try {
				TaskSchedulerFactory.getInstance().getScheduler().interrupt(task.getTYPE_DM() + task.getTYPE_DM2(), "default");
				TaskSchedulerFactory.getInstance().getScheduler().deleteJob(task.getTYPE_DM() + task.getTYPE_DM2(), "default");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}
