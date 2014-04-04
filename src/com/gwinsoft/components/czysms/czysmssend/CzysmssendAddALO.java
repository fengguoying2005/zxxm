package com.gwinsoft.components.czysms.czysmssend;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CzysmssendAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Czysmssend orgsmssend = reqEnv.getData("orgsmssend");
		List<CZYMSG> tablemx=reqEnv.getData("tablemx");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		orgsmssend.setLR_SJ(time);
		orgsmssend.setXG_SJ(time);
		orgsmssend.setLRRY_DM(user.getUSER_DM());
		orgsmssend.setXGRY_DM(user.getUSER_DM());
		orgsmssend.setORG_DM_JG(user.getORG_DM_JG());
		orgsmssend.setSMSTYPE_DM("05");
		orgsmssend.setSMSZT_DM("02");
		orgsmssend.setNSRDATA_LSH(uid);
		String message = "";
		int i = 0;
		
		String qm = null;
		Map<String, Map<String, Object>> cache = CacheManager.getCache("DXQM_CACHE");
		Iterator<String> it = cache.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if(key.equals(user.getORG_DM_JG())) {
				qm = (String) cache.get(key).get("DXQM_MC");
			}
		}
		if(qm == null) {
			qm = ("【"+user.getLRRY_DM()+"】");
		}
		
		for(CZYMSG mx:tablemx) {
			i ++;
			//String uid2=GwinSoftUtil.getLSH();
			mx.setSMSTYPE_DM("02");
			mx.setNSRDATAMX_LSH(uid+i);
			mx.setNSRDATA_LSH(uid);
			mx.setLR_SJ(time);
			mx.setXG_SJ(time);
			mx.setLRRY_DM(user.getUSER_DM());
			mx.setXGRY_DM(user.getUSER_DM());
			if("1".equals(orgsmssend.getCALLBACK())) {
				mx.setMSG(orgsmssend.getDXMBINFO()+qm);
			} else {
				mx.setMSG(orgsmssend.getDXMBINFO()+qm);
			}
			pm.executeUpdate("INSERT INTO SMS_ORGMSG(NSRDATAMX_LSH,NSRDATA_LSH,ORG_DM_JG,ORG_DM_BM,USER_DM,SJHM,ORG_MC_JG,ORG_MC_BM,USER_MC,MSG,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,SMSTYPE_DM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", mx);
		}
		pm.executeUpdate("INSERT INTO SMS_NSRDATA(NSRDATA_LSH,SMSTYPE_DM,GROUP_NAME,ORG_DM_JG,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,SMSZT_DM,DXMBINFO,CALLBACK,DXQM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", orgsmssend);
		message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
