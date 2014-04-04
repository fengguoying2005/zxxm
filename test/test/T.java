package test;

import java.util.Calendar;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.gwinsoft.components2.job.JobUtils;


public class T {
	public static void main(String[] args) throws Exception {

		String SSRQ_Q = "20130401";
		Date datestart = JobUtils.getFormatDate(SSRQ_Q, "yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(datestart);
		int d = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(d);
	}
}
