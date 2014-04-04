package com.gwinsoft.components.smssh.sms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.gwinsoft.components.jobs.UserSmsData;
import com.gwinsoft.components2.job.JobUtils;

public class ReceiveUserSMS {

	public static List<UserSmsData> receiveusersms() throws SQLException {
		String DATE_FROM = JobUtils.getJsJobStart("INBOX");
		String DATE_END = JobUtils.getMasDate();
		List<UserSmsData> datas = new ArrayList<UserSmsData>();
		String sql = "SELECT EXTCODE,MASSMSID,SOURCEADDR,RECEIVETIME,MESSAGECONTENT FROM sms_inbox WHERE RECEIVETIME>=str_to_date('"+DATE_FROM +"','%Y-%m-%d %H:%i:%S') AND RECEIVETIME<str_to_date('"+DATE_END +"','%Y-%m-%d %H:%i:%S')";
		CachedRowSet row = SMSDBTOOL.query(sql);
		while(row.next()) {
			String MASSMSID = row.getString("MASSMSID");
			String SOURCEADDR = row.getString("SOURCEADDR");
			String RECEIVETIME = row.getString("RECEIVETIME");
			String MESSAGECONTENT = row.getString("MESSAGECONTENT");
			String EXTCODE = row.getString("EXTCODE");
			UserSmsData data = new UserSmsData();
			data.setKEYID(MASSMSID);
			data.setPHONE(SOURCEADDR);
			data.setRECEIVETIME(RECEIVETIME);
			data.setMSG(MESSAGECONTENT);
			data.setEXTCODE(EXTCODE);
			data.setKZM(EXTCODE);
			datas.add(data);
		}
		JobUtils.saveJsJobEnd("INBOX", DATE_END);
		return datas;
	}
}
