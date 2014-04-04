package com.gwinsoft.components.smssh.sms;

import java.sql.SQLException;
import java.util.List;

import com.gwinsoft.components.nsrgl.nsrdxfs.SFXCMSG;
import com.gwinsoft.components.nsrsms.smssend.Nsrmsg;
import com.gwinsoft.components.orgsms.orgsmssend.ORGMSG;
import com.gwinsoft.util.GwinSoftUtil;

public class SendSMS {
	
	public static void sendsms(List<Nsrmsg> tablemx) {
		new Sender(tablemx).start();
	}
	public static void sendOrgsms(List<ORGMSG> orgsms, String extcode) {
		new Sender2(orgsms, extcode).start();
	}
	public static void sendSfxcsms(List<SFXCMSG> sms) {
		new Sender3(sms).start();
	}
	public static void sendsms(String uid, String tel,String info, String extcode) {
		String sql = getSQL(uid, tel, info, extcode);
		SMSDBTOOL.update(sql);
	}
	public static String getSQL(String uid, String tel,String info, String extcode) {
		String time = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd HH:mm:ss.S");
		String sql = "INSERT INTO sms_outbox(sismsid, extcode, destaddr, messagecontent,reqdeliveryreport,msgfmt,sendmethod,requesttime,applicationid) VALUES('"+uid +"','"+extcode+"', '"+tel+"','"+info+"', 1, 15, 0, '"+time+"', '66')";
		return sql;
	}
}
class Sender extends Thread {
	List<Nsrmsg> tablemx;
	public Sender(List<Nsrmsg> tablemx) {
		this.tablemx = tablemx;
	}
	public void run() {
		for(Nsrmsg msg : tablemx) {
			SendSMS.sendsms(msg.getNSRMSG_LSH()+"#1",msg.getSJHM(), msg.getMSG(), "");//300 to 
		}
	}
}
class Sender2 extends Thread {
	List<ORGMSG> tablemx;
	String extcode;
	public Sender2(List<ORGMSG> tablemx, String extcode) {
		this.tablemx = tablemx;
		this.extcode = extcode;
	}
	public void run() {
		for(ORGMSG msg : tablemx) {
			SendSMS.sendsms(msg.getNSRDATAMX_LSH()+"#2", msg.getSJHM(), msg.getMSG(), extcode);
		}
	}
}
class Sender3 extends Thread {
	List<SFXCMSG> tablemx;
	public Sender3(List<SFXCMSG> tablemx) {
		this.tablemx = tablemx;
	}
	public void run() {
		DBBatcher batch = null;
		try {
			if(tablemx!=null && tablemx.size()>0) {
				batch = new DBBatcher();
			}
			for(SFXCMSG msg : tablemx) {
				String sql = SendSMS.getSQL(msg.getSFXCMSG_LSH()+"#3", msg.getSJHM(), msg.getMSG(),"");
				//SendSMS.sendsms(msg.getSFXCMSG_LSH()+"#3", msg.getSJHM(), msg.getMSG(),"");//300 to 
				batch.addSQL(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(batch!=null) {
				try {
					batch.finish();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}