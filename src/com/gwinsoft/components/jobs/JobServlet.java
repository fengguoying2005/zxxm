package com.gwinsoft.components.jobs;

import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

@Deprecated
public class JobServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3611996629515724724L;
	
	public void init() throws ServletException {
		String classes = this.getInitParameter("classes");
		String crons = this.getInitParameter("crons");
		String[] clazzs = classes.split("#");
		String[] cronss = crons.split("#");
		for (int i = 0; i < clazzs.length; i ++) {
			try {
				Class<?> c = Class.forName(clazzs[i]);
				String cronExpression = cronss[i];
				HolidayCalendar calendar = new HolidayCalendar();
				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
				scheduler.start();
				scheduler.addCalendar("calendar", calendar, true, false);
				JobDetail jobDetail = new JobDetail("messageJob"+i, Scheduler.DEFAULT_GROUP, c);
				Trigger trigger = new CronTrigger("cronTrigger"+i, Scheduler.DEFAULT_GROUP, cronExpression);
				scheduler.scheduleJob(jobDetail, trigger);
			} catch (SchedulerException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		System.out.println("启动自动任务完毕。");
		super.init();
	}
	public static void main(String[] args) {
	}
}