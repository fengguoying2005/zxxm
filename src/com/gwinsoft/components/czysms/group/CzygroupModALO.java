package com.gwinsoft.components.czysms.group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gwinsoft.components.common.CommonTool;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CzygroupModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Czygroup orggroup = reqEnv.getData("orggroup");
		List<GROUPMX> tablemx=reqEnv.getData("tablemx");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("GROUP_LSH");
		DBPersistenceManager pm = this.getPM();
		orggroup.setORG_DM_JG(user.getORG_DM_JG());
        orggroup.setGROUP_LSH(BH);
		orggroup.setXG_SJ(time);
		orggroup.setXGRY_DM(user.getUSER_DM());
		//pm.executeUpdate("DELETE FROM CZY_GROUPMX WHERE GROUP_LSH='"+BH+"'");
		String sql = "";
		List<String> sqls = new ArrayList<String>();
		int i = 0;
		long a = System.currentTimeMillis();
		for(GROUPMX mx:tablemx) {
			//String uid2=GwinSoftUtil.getLSH();
			//mx.setGROUPMX_LSH(uid2);
			mx.setGROUP_LSH(BH);
			mx.setLR_SJ(time);
			mx.setXG_SJ(time);
			mx.setLRRY_DM(user.getUSER_DM());
			mx.setXGRY_DM(user.getUSER_DM());
			sql = "INSERT INTO CZY_GROUPMX(GROUPMX_LSH,GROUP_LSH,ORG_DM_JG,ORG_DM_BM,USER_DM,SJHM,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,ORG_MC_JG,ORG_MC_BM,USER_MC,ZW_DM,ZW_MC) " +
					"SELECT SYS_GUID() AS GROUPMX_LSH,'"+BH+"' AS GROUP_LSH,'"+mx.getORG_DM_JG()+"' AS ORG_DM_JG,'"+mx.getORG_DM_BM()+"' AS ORG_DM_BM,'"+mx.getUSER_DM()+"' AS USER_DM,'"+mx.getSJHM()+"' AS SJHM,'"+mx.getLRRY_DM()+"' AS LRRY_DM,'"+mx.getXGRY_DM()+"' AS XGRY_DM,TO_DATE('"+CommonTool.format20(mx.getLR_SJ())+"','YYYY-MM-DD HH24:MI:SS') AS LR_SJ,TO_DATE('"+CommonTool.format20(mx.getXG_SJ())+"','YYYY-MM-DD HH24:MI:SS') AS XG_SJ,'"+mx.getORG_MC_JG()+"' AS ORG_MC_JG,'"+mx.getORG_MC_BM()+"' AS ORG_MC_BM,'"+mx.getUSER_MC()+"' AS USER_MC,'"+mx.getZW_DM()+"' AS ZW_DM,'"+mx.getZW_MC()+"' AS ZW_MC FROM DUAL";
			//sqls.add(sql);
			pm.executeUpdate(sql);
		}
//		if (sqls.size() > 0) {
//			String[] ss = new String[sqls.size()];
//			for (String s : sqls) {
//				ss[i++] = s;
//			}
//			pm.executeUpdateBatch(ss);
//		}
		sql = "delete from CZY_GROUPMX where GROUPMX_LSH in ("+
		"    select temp.GROUPMX_LSH from ("+
		"        select a.GROUPMX_LSH  from CZY_GROUPMX a  where a.GROUP_LSH='"+BH+"'"+
		"        and exists ("+
		"            select 1 from CZY_GROUPMX b where a.GROUP_LSH=b.GROUP_LSH and a.USER_DM=b.USER_DM group by b.GROUP_LSH,b.USER_DM having count(b.USER_DM) > 1"+
		"        )"+
		"        and not exists ("+
		"            select * from (select min(c.GROUPMX_LSH) GROUPMX_LSH from CZY_GROUPMX c where c.GROUP_LSH='"+BH+"' group by c.GROUP_LSH,c.USER_DM having count(c.USER_DM)>1) d where d.GROUPMX_LSH=a.GROUPMX_LSH"+
		"        )"+
		"    ) temp"+
		")";
		pm.executeUpdate(sql);
		//System.out.println("共执行："+(System.currentTimeMillis()-a)+" S");
		//select count
		sql = "SELECT COUNT(0) FROM CZY_GROUPMX WHERE GROUP_LSH='"+BH+"'";
		int nn = pm.queryForInt(sql);
		orggroup.setGROUP_COUNT(nn+"");
		pm.executeUpdate("UPDATE CZY_GROUP SET ORG_DM_JG=?,GROUP_NAME=?,GROUP_COUNT=?,XG_SJ=?,XGRY_DM=? WHERE GROUP_LSH=?", orggroup);
		message="修改成功！";
		CacheServlet.freshCache("CZYGROUP_CACHE", pm);
		CacheServlet.freshCache("CZYGROUPMX_CACHE", pm);
		resEnv.putData("message", message);
	}
}
