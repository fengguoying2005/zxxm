package com.gwinsoft.components.smssh.sms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.gwinsoft.components.jobs.ReceiveSmsJob;
import com.gwinsoft.components.jobs.Smsdata;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class ReceiveSMS {

	/*INSERT INTO sms_outbox(sismsid, extcode, destaddr, messagecontent,reqdeliveryreport,msgfmt,sendmethod,requesttime,applicationid) 
	VALUES('a','300', '13842099301','您好', 1, 15, 0, '2012-11-05', '300')

	select * from sms_sent*/
	public static void receivesms(List<String> keyid) throws SQLException {
		for(String key : keyid) {
			receivesms(key);
		}
	}
	public static void receivesms(String[] keyid) throws SQLException {
		for(String key : keyid) {
			receivesms(key);
		}
	}
	public static String receivesmszt(String keyid) throws SQLException {
		String sql = "SELECT smsstatus FROM sms_sent where sismsid = '"+keyid+"'";
		CachedRowSet row = SMSDBTOOL.query(sql);
		while(row.next()) {
			return row.getString("smsstatus");
		}
		return null;
	}
	public static List<Smsdata> receivesms(String keyid) throws SQLException {
		List<Smsdata> datas = new ArrayList<Smsdata>();
		String sql = "SELECT sismsid,sentresult,smsstatus,statustime FROM sms_sent where sismsid like '"+keyid+"%' and smsstatus='DeliveryToTerminal'";
		CachedRowSet row = SMSDBTOOL.query(sql);
		while(row.next()) {
			String sismsid = row.getString("sismsid");
			String statustime = row.getString("statustime");
			datas.add(new Smsdata(sismsid, statustime));
		}
		return datas;
		//new ReceiveSmsJob(datas).start();
	}
}
