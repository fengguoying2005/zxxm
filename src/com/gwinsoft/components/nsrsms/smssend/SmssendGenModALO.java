package com.gwinsoft.components.nsrsms.smssend;

import java.util.Date;
import java.util.List;

import com.gwinsoft.components.common.CommonTool;
import com.gwinsoft.components.nsrsms.group.Group;
import com.gwinsoft.components.nsrsms.group.NSRDATAMX;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.components.smssh.sms.SmsUtil;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class SmssendGenModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Date time = GwinSoftUtil.getSysDate();
		String BH = reqEnv.getData("NSRDATA_LSH");
		Group group = reqEnv.getData("group");
		DBPersistenceManager pm = this.getPM();
		pm.executeUpdate("UPDATE SMS_NSRDATA SET SMSZT_DM='02',DXQM='"+group.getDXQM()+"',DXMB='"+group.getDXMB()+"',DXMBINFO='"+group.getDXMBINFO()+"',XG_SJ=TO_DATE('"+CommonTool.format20(time)+"','YYYY-MM-DD HH24:MI:SS'),XGRY_DM='"+user.getUSER_DM()+"' WHERE SMSZT_DM IN('01','02','05') AND NSRDATA_LSH='"+BH+"'");
		String sql = "SELECT NSRBM,NSRMC,SJHM,CBRQQ,CBRQZ,SUM(JE) AS JE FROM SMS_NSRDATAMX WHERE NSRDATA_LSH='"+BH+"' GROUP BY NSRBM,NSRMC,SJHM,CBRQQ,CBRQZ";
		List<Nsrmsg> list = pm.queryForList(sql, Nsrmsg.class);
		String sql2 = "INSERT INTO SMS_NSRMSG(NSRMSG_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,CBRQQ,CBRQZ,MSG,JE,SMSTYPE_DM,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql3 = "SELECT * FROM SMS_NSRDATAMX WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
		List<NSRDATAMX> datamx = pm.queryForList(sql3, NSRDATAMX.class, new String[]{BH});
		pm.executeUpdate("DELETE FROM SMS_NSRMSG WHERE NSRDATA_LSH='"+BH+"'");
		int i = 0;
		for(Nsrmsg msg : list) {
			i ++;
			Nsrmsg nsrmsg = new Nsrmsg();
//			nsrmsg.setNSRMSG_LSH(GwinSoftUtil.getLSH());
			nsrmsg.setNSRMSG_LSH(BH+""+i);
			nsrmsg.setNSRDATA_LSH(BH);
			nsrmsg.setNSRBM(msg.getNSRBM());
			nsrmsg.setNSRMC(msg.getNSRMC());
			nsrmsg.setSJHM(msg.getSJHM());
			nsrmsg.setCBRQQ(msg.getCBRQQ());
			nsrmsg.setCBRQZ(msg.getCBRQZ());
			nsrmsg.setMSG(group.getDXMBINFO());
			nsrmsg.setJE(msg.getJE());
			nsrmsg.setSMSTYPE_DM("02");
			nsrmsg.setLRRY_DM(user.getXGRY_DM());
			nsrmsg.setXGRY_DM(user.getXGRY_DM());
			nsrmsg.setLR_SJ(time);
			nsrmsg.setXG_SJ(time);
			SmsUtil.genSMS(nsrmsg, datamx, group.getDXMBINFO(), user.getORG_DM_JG(), user.getLRRY_DM());
			pm.executeUpdate(sql2, nsrmsg);
		}
		message="短信生成成功！";
		resEnv.putData("message", message);
	}
}
