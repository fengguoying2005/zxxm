package com.gwinsoft.components2.job;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.gwinsoft.components.smssh.sms.SendSMS;

public class SmsSender {

	private static int produceTaskMinNumber = 10;
	private static int produceTaskMaxNumber = 100;
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
	public static void schedule(SMS sms) {
		if (sms != null && sms.getSMSINFO() != null && !"".equals(sms.getSMSINFO().trim())
				 && sms.getLSH() != null && !"".equals(sms.getLSH().trim())
				 && sms.getSJHM() != null && !"".equals(sms.getSJHM().trim())) {
			execute(new RunSms(sms));
		}
	}
}
class RunSms  implements Runnable {
	private SMS sms;
	public RunSms(SMS sms) {
		this.sms = sms;
	}
	public void run() {
		SendSMS.sendsms(sms.getLSH(), sms.getSJHM(), sms.getSMSINFO(), sms.getExtcode());
	}
}