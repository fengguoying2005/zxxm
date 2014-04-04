package com.gwinsoft.components2.job.js;

import java.util.List;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import com.gwinsoft.components2.job.JobUtils;
import com.gwinsoft.components2.job.SmsSender;
import com.gwinsoft.components2.job.beans_js.FPZJCX_BEAN;
import com.gwinsoft.components2.job.beans_js.InboxUtil;
import com.gwinsoft.components2.job.beans_js.SMS_INBOX_BEAN;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class FPZJCX implements InterruptableJob {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//		System.out.println("发票中奖查询");
		String type_dm = "FPZJCX";
		String from = JobUtils.getJsJobStart(type_dm);
		String to = JobUtils.getNowDate();
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			String sql = "SELECT * FROM SMS_INBOX WHERE JS_SJ>='"+from+"' and JS_SJ<'"+to+"' AND TYPE_DM='"+type_dm+"'";
			List<SMS_INBOX_BEAN> inboxs = pm.queryForList(sql, SMS_INBOX_BEAN.class);
			for(SMS_INBOX_BEAN bean:inboxs) {
				try {
					FPZJCX_BEAN sms = InboxUtil.SAVE_FPZJCX_BEAN(bean, pm);
					//启动短信发送队列
					SmsSender.schedule(sms);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			JobUtils.saveJsJobEnd(type_dm, to);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
	}

	public void interrupt() throws UnableToInterruptJobException {
		
	}
}