package com.gwinsoft.components.nsrgl.nsrgroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class NsrgroupModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Nsrgroup nsrgroup = reqEnv.getData("nsrgroup");
		Date time = GwinSoftUtil.getSysDate();
		String BH = reqEnv.getData("LSH");
		String datas = this.getData("datas");
		DBPersistenceManager pm = this.getPM();
		int SIZE = 0;
		//MX
		{
			List<String> listsql = new ArrayList<String>();
			if(datas!=null && !"".equals(datas)) {
				String[] strs = datas.split(",");
				Set<String> swjgset = new HashSet<String>();
				Map<String,Set<String>> maps = new HashMap<String,Set<String>>();
				StringBuffer orgin = new StringBuffer("INSERT INTO NSR_GROUPMX(GROUPMX_LSH,NSR_LSH,GROUP_LSH,NSRBM,NSRMC,HYDM,SSS,ORG_DM,SSZGY,SBFS,DJZT,DJLX,ZCDZ,ZCLX,FR,FRSJH,CWJL,CWJLSJH,BSRY,BSRYSJH,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) " +
						"SELECT SYS_GUID() AS GROUPMX_LSH,LSH AS NSR_LSH,'"+BH+"' AS GROUP_LSH,NSRBM,NSRMC,HYDM,SSS,ORG_DM,SSZGY,SBFS,DJZT,DJLX,ZCDZ,ZCLX,FR,FRSJH,CWJL,CWJLSJH,BSRY,BSRYSJH,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ FROM NSR_JBXX T WHERE ORG_DM in (");
				for(String str:strs) {
					if(str==null || "".equals(str.trim())) {
						continue;
					}
					if(str.startsWith("NSR")) {
						//ry
						String[] nsrorg = str.substring(3).split(":");
						if(maps.containsKey(nsrorg[1])) {
							Set<String> set = maps.get(nsrorg[1]);
							set.add(nsrorg[0]);
						} else {
							Set<String> set = new HashSet<String>();
							set.add(nsrorg[0]);
							maps.put(nsrorg[1], set);
						}
					} else {
						//jg
						//orgin.append("'").append(str).append("',");
						Map swjgmap = DMB.getOrgDMB(str, 1, "J");
						Iterator it = swjgmap.keySet().iterator();
			        	while(it.hasNext()) {
			        		String key = (String) it.next();
			        		swjgset.add(key);
			        	}
					}
				}
				//孙子机关的纳税人
				Iterator<String> jgit = swjgset.iterator();
				while(jgit.hasNext()) {
					String jg = jgit.next();
					orgin.append("'").append(jg).append("',");
				}
				orgin.append("'')");
				String FIND_HYDM = this.getData("FIND_HYDM");
				if(FIND_HYDM  != null && !"".equals(FIND_HYDM)) {
					orgin.append(" AND HYDM='"+FIND_HYDM+"'");
				}

				String FIND_NSRBM = this.getData("FIND_NSRBM");
				if(FIND_NSRBM  != null && !"".equals(FIND_NSRBM)) {
					orgin.append(" AND NSRBM LIKE '%"+FIND_NSRBM+"%'");
				}
				String FIND_NSRMC = this.getData("FIND_NSRMC");
				if(FIND_NSRMC  != null && !"".equals(FIND_NSRMC)) {
					orgin.append(" AND NSRMC LIKE '%"+FIND_NSRMC+"%'");
				}
				String FIND_SBFS = this.getData("FIND_SBFS");
				if(FIND_SBFS != null && !"".equals(FIND_SBFS)) {
					orgin.append(" AND SBFS='"+FIND_SBFS+"'");
				}
				String FIND_DJLX = this.getData("FIND_DJLX");
				if(FIND_DJLX != null && !"".equals(FIND_DJLX)) {
					orgin.append(" AND DJLX='"+FIND_DJLX+"'");
				}
				String FIND_ZCLX = this.getData("FIND_ZCLX");
				if(FIND_ZCLX != null && !"".equals(FIND_ZCLX)) {
					orgin.append(" AND ZCLX='"+FIND_ZCLX+"'");
				}
				//NSR
				Iterator<String> it = maps.keySet().iterator();
				while(it.hasNext()) {
					String key = it.next();
					StringBuffer nsrsql = new StringBuffer("INSERT INTO NSR_GROUPMX(GROUPMX_LSH,NSR_LSH,GROUP_LSH,NSRBM,NSRMC,HYDM,SSS,ORG_DM,SSZGY,SBFS,DJZT,DJLX,ZCDZ,ZCLX,FR,FRSJH,CWJL,CWJLSJH,BSRY,BSRYSJH,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) SELECT SYS_GUID() AS GROUPMX_LSH,LSH AS NSR_LSH,'"+BH+"' AS GROUP_LSH,NSRBM,NSRMC,HYDM,SSS,ORG_DM,SSZGY,SBFS,DJZT,DJLX,ZCDZ,ZCLX,FR,FRSJH,CWJL,CWJLSJH,BSRY,BSRYSJH,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ FROM NSR_JBXX T WHERE ");
					nsrsql.append("ORG_DM='"+key+"' AND NSRBM IN (");
					Set<String> nsrset = maps.get(key);
					Iterator<String> it2 = nsrset.iterator();
					while(it2.hasNext()) {
						String value = it2.next();
						nsrsql.append("'").append(value).append("',");
					}
					nsrsql.append("'') AND NOT EXISTS (SELECT 1 FROM NSR_GROUPMX B WHERE B.NSRBM=T.NSRBM AND B.GROUP_LSH='"+BH+"')");
					//最后执行。确保过滤。
					listsql.add(nsrsql.toString());
				}
				orgin.append(" AND NOT EXISTS (SELECT 1 FROM NSR_GROUPMX B WHERE B.NSRBM=T.NSRBM AND B.GROUP_LSH='"+BH+"')");
				int n = pm.executeUpdate(orgin.toString());
				SIZE=SIZE+n;
			}
			for(String sql: listsql) {
				int n = pm.executeUpdate(sql);
				SIZE=SIZE+n;
			}
		}
		//ZB
        nsrgroup.setLSH(BH);
		nsrgroup.setXG_SJ(time);
		nsrgroup.setXGRY_DM(user.getUSER_DM());
		nsrgroup.setORG_DM(user.getORG_DM_JG());
		pm.executeUpdate("UPDATE NSR_GROUP SET GROUP_COUNT=GROUP_COUNT+"+SIZE+",ORG_DM=?,GROUP_NAME=?,INFO=?,XG_SJ=?,XGRY_DM=? WHERE LSH=?", nsrgroup);
		message="修改成功！";
		resEnv.putData("message", message);
	}
}
