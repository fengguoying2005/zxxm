package com.gwinsoft.components2.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

public class TaskSchedulerFactory {
	private static final TaskSchedulerFactory instance = new TaskSchedulerFactory();
	private SchedulerFactory factory = null;
	private Scheduler scheduler = null;

	private TaskSchedulerFactory() {
	}

	public static final TaskSchedulerFactory getInstance() {
		return instance;
	}

	public synchronized Scheduler getScheduler() throws SchedulerException {
		if (scheduler == null) {
			scheduler = getFactory().getScheduler();
		}
		return scheduler;
	}

	SchedulerFactory getFactory() {
		assert factory != null : "没有设置SchedulerFactory！";
		return factory;
	}

	void setFactory(SchedulerFactory factory) {
		assert factory == null : "SchedulerFactory已经设置！";
		this.factory = factory;
	}
}