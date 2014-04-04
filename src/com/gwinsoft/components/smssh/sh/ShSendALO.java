package com.gwinsoft.components.smssh.sh;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.nsrgl.nsrdxfs.SFXCMSG;
import com.gwinsoft.components.nsrsms.group.Group;
import com.gwinsoft.components.nsrsms.smssend.Nsrmsg;
import com.gwinsoft.components.orgsms.orgsmssend.ORGMSG;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.components.smssh.sms.SendSMS;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class ShSendALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String BH = reqEnv.getData("NSRDATA_LSH");
		BH = BH.replaceAll(",", "','");
		DBPersistenceManager pm = this.getPM();
		int n = pm.executeUpdate("UPDATE SMS_NSRDATA SET THYY='审核通过。',SMSZT_DM='06',XG_SJ=TO_DATE('"+time+"','YYYY-MM-DD HH24:MI:SS'),XGRY_DM='"+user.getUSER_DM()+"' WHERE SMSZT_DM='03' AND NSRDATA_LSH IN ('"+BH+"')");
		if(n>0) {
			String sql = "SELECT * FROM SMS_NSRDATA WHERE NSRDATA_LSH IN ('"+BH+"')";
			List<Group> groups = pm.queryForList(sql, Group.class);
			for(Group group:groups) {
				if("03".equals(group.getSMSTYPE_DM()) || "05".equals(group.getSMSTYPE_DM())) {//内部短信
					
					this.getPM().executeUpdate("UPDATE SMS_ORGMSG SET SMSTYPE_DM='06',FSCS=0 WHERE NSRDATA_LSH='"+group.getNSRDATA_LSH()+"'");
					String sql2 = "SELECT * FROM SMS_ORGMSG WHERE NSRDATA_LSH = '"+group.getNSRDATA_LSH()+"'";
					List<ORGMSG> tablemx = pm.queryForList(sql2, ORGMSG.class, new String[]{});
					String extcode = "";//300 to 
					if("1".equals(group.getCALLBACK())) {
						extcode = group.getDXQM();
					}
					SendSMS.sendOrgsms(tablemx,extcode);
				} else if("04".equals(group.getSMSTYPE_DM())) {//税法宣传
					String selectSQL = "SELECT NSRDATA_LSH,YYYYMMDD FROM SMS_NSRDATA WHERE NSRDATA_LSH='"+group.getNSRDATA_LSH()+"'";
					SqlRowSet row = this.getPM().quereyForRowSet(selectSQL);
					while(row.next()) {
						String NSRDATA_LSH = row.getString("NSRDATA_LSH");
						String YYYYMMDD = row.getString("YYYYMMDD");
						String TABLENAME = "SMS_SFXCMSG"+YYYYMMDD;
						this.getPM().executeUpdate("UPDATE "+TABLENAME+" SET SMSTYPE_DM='06',FSCS=0 WHERE NSRDATA_LSH = '"+NSRDATA_LSH+"' AND length(SJHM)=11");
						String sql2 = "SELECT MAX(SFXCMSG_LSH) AS SFXCMSG_LSH,SJHM,MAX(MSG) AS MSG FROM "+TABLENAME+" T WHERE NSRDATA_LSH = '"+NSRDATA_LSH+"' AND NOT EXISTS (SELECT 1 FROM T_HMD B WHERE B.NSRBM=T.NSRBM AND (B.SJLX_DM='04' OR (T.PHONETYPE='BSRYSJH' AND B.SJLX_DM='01') OR (T.PHONETYPE='FRSJH' AND B.SJLX_DM='02') OR (T.PHONETYPE='CWJLSJH' AND B.SJLX_DM='03')) AND B.SJHM=T.SJHM) GROUP BY SJHM HAVING length(SJHM)=11";
						List<SFXCMSG> tablemx = pm.queryForList(sql2, SFXCMSG.class, new String[]{});
						SendSMS.sendSfxcsms(tablemx);
					}
				} else {//催报催缴
					this.getPM().executeUpdate("UPDATE SMS_NSRMSG SET SMSTYPE_DM='06',FSCS=0 WHERE NSRDATA_LSH ='"+group.getNSRDATA_LSH()+"' AND length(SJHM)=11");
					String sql2 = "SELECT * FROM SMS_NSRMSG T WHERE NSRDATA_LSH='"+group.getNSRDATA_LSH()+"' AND length(SJHM)=11 AND NOT EXISTS (SELECT 1 FROM T_HMD B WHERE B.NSRBM=T.NSRBM AND B.SJHM=T.SJHM)";
					List<Nsrmsg> tablemx = pm.queryForList(sql2, Nsrmsg.class, new String[]{});
					SendSMS.sendsms(tablemx);
				}
			}
			message="短信审核通过！正在发送短信中。";
		} else {
			message = "状态不符。";
		}
		resEnv.putData("message", message);
	}
}
