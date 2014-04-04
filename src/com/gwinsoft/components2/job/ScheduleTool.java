package com.gwinsoft.components2.job;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleTool {

	private static int produceTaskMinNumber = 10;
	private static int produceTaskMaxNumber = 500;
	private static int MaxBlockNumber = 1000000;
	private static ExecutorService exec;
	
	private static synchronized void init() {
		if(exec==null) {
			exec = new ThreadPoolExecutor(produceTaskMinNumber, produceTaskMaxNumber, 0,  
	                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MaxBlockNumber),  
	                new ThreadPoolExecutor.DiscardOldestPolicy());
		}
	}
	private static void execute(Runnable runner) {
		if(exec==null) {
			init();
		}
		exec.execute(runner);
	}
	public static void schedule(Runnable runner) {
		execute(runner);
	}
}
