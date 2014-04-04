package com.gwinsoft.components.orgsms.orgsmssend;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.common.CommonTool;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class OrgsmssendModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Orgsmssend orgsmssend = reqEnv.getData("orgsmssend");
		List<ORGMSG> tablemx=reqEnv.getData("tablemx");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
        orgsmssend.setNSRDATA_LSH(BH);
		orgsmssend.setXG_SJ(time);
		orgsmssend.setORG_DM_JG(user.getORG_DM_JG());
		orgsmssend.setSMSTYPE_DM("03");
		orgsmssend.setSMSZT_DM("02");
		orgsmssend.setXGRY_DM(user.getUSER_DM());
		//pm.executeUpdate("DELETE FROM SMS_ORGMSG WHERE NSRDATA_LSH='"+BH+"'");
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
		for(ORGMSG mx:tablemx) {
			String uid2=GwinSoftUtil.getLSH();
			mx.setSMSTYPE_DM("02");
			mx.setNSRDATAMX_LSH(uid2);
			mx.setNSRDATA_LSH(BH);
			mx.setLR_SJ(time);
			mx.setXG_SJ(time);
			mx.setLRRY_DM(user.getUSER_DM());
			mx.setXGRY_DM(user.getUSER_DM());

			if("1".equals(orgsmssend.getCALLBACK())) {
				mx.setMSG(orgsmssend.getDXMBINFO()+qm);
			} else {
				mx.setMSG(orgsmssend.getDXMBINFO()+qm);
			}
			
			//mx.setMSG(orgsmssend.getDXMBINFO()+qm);
			pm.executeUpdate("INSERT INTO SMS_ORGMSG(NSRDATAMX_LSH,NSRDATA_LSH,ORG_DM_JG,ORG_DM_BM,USER_DM,SJHM,ORG_MC_JG,ORG_MC_BM,USER_MC,MSG,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,SMSTYPE_DM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", mx);
		}
		//
		String sql = "delete from SMS_ORGMSG where NSRDATAMX_LSH in ("+
		"    select temp.NSRDATAMX_LSH from ("+
		"        select a.NSRDATAMX_LSH  from SMS_ORGMSG a  where a.NSRDATA_LSH='"+BH+"'"+
		"        and exists ("+
		"            select 1 from SMS_ORGMSG b where a.NSRDATA_LSH=b.NSRDATA_LSH and a.USER_DM=b.USER_DM group by b.NSRDATA_LSH,b.USER_DM having count(b.USER_DM) > 1"+
		"        )"+
		"        and not exists ("+
		"            select * from (select min(c.NSRDATAMX_LSH) NSRDATAMX_LSH from SMS_ORGMSG c where c.NSRDATA_LSH='"+BH+"' group by c.NSRDATA_LSH,c.USER_DM having count(c.USER_DM)>1) d where d.NSRDATAMX_LSH=a.NSRDATAMX_LSH"+
		"        )"+
		"    ) temp"+
		")";
		pm.executeUpdate(sql);
		sql = "UPDATE SMS_ORGMSG SET MSG='"+orgsmssend.getDXMBINFO()+qm+"',XGRY_DM='"+user.getUSER_DM()+"',XG_SJ=TO_DATE('"+CommonTool.format20(time)+"','YYYY-MM-DD HH24:MI:SS') WHERE NSRDATA_LSH='"+BH+"'";
		pm.executeUpdate(sql);
		pm.executeUpdate("UPDATE SMS_NSRDATA SET SMSTYPE_DM=?,GROUP_NAME=?,ORG_DM_JG=?,SMSZT_DM=?,XG_SJ=?,XGRY_DM=?,DXMBINFO=?,CALLBACK=? WHERE NSRDATA_LSH=?", orgsmssend);
		message="修改成功！";
		resEnv.putData("message", message);
	}
}
