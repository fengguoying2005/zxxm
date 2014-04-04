package com.gwinsoft.components.nsrgl.nsrdxfs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class NsrdxModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		String rylx = reqEnv.getData("rylx");
		String xzfs = reqEnv.getData("xzfs");
		String datas = reqEnv.getData("datas");
		Nsrdx nsrdx = reqEnv.getData("nsrdx");
		Date time = GwinSoftUtil.getSysDate();
		String BH = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
        nsrdx.setNSRDATA_LSH(BH);
		nsrdx.setXG_SJ(time);
		nsrdx.setXGRY_DM(user.getUSER_DM());
		nsrdx.setORG_DM_JG(user.getORG_DM_JG());
		nsrdx.setSMSTYPE_DM("04");
		nsrdx.setSMSZT_DM("01");
		nsrdx.setDXQM(rylx);//借用
		String RYSJ = ("1".equals(rylx))?"BSRYSJH":(("2".equals(rylx))?"CWJLSJH":(("3".equals(rylx))?"FRSJH":"ALL"));
		String[] RYSJS = new String[]{"BSRYSJH","CWJLSJH","FRSJH"};
		int SIZE = 0;
		
		String TABLENAME = "SMS_SFXCMSG";
		String selectSQL = "SELECT YYYYMMDD FROM SMS_NSRDATA WHERE NSRDATA_LSH='"+BH+"'";
		SqlRowSet row = this.getPM().quereyForRowSet(selectSQL);
		if(row.next()) {
			String YYYYMMDD = row.getString("YYYYMMDD");
			TABLENAME = "SMS_SFXCMSG"+YYYYMMDD;
		}

		String qm = null;
		{
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
		}
		pm.executeUpdate("UPDATE "+TABLENAME+" SET MSG='"+nsrdx.getDXMBINFO()+qm+"' WHERE NSRDATA_LSH='"+BH+"'");
		/*if(!"ALL".equals(RYSJ)) {
			pm.executeUpdate("UPDATE "+TABLENAME+" LEFT JOIN NSR_JBXX B ON B.LSH=SMS_SFXCMSG.NSR_LSH SET MSG='"+nsrdx.getDXMBINFO()+qm+"',SJHM=B."+RYSJ+" WHERE NSRDATA_LSH='"+BH+"'");
		} else {
			for(String SJTYPE : RYSJS) {
				pm.executeUpdate("UPDATE "+TABLENAME+" LEFT JOIN NSR_JBXX B ON B.LSH=SMS_SFXCMSG.NSR_LSH SET MSG='"+nsrdx.getDXMBINFO()+qm+"',SJHM=B."+SJTYPE+" WHERE NSRDATA_LSH='"+BH+"'");
			}
		}*/
		if("2".equals(xzfs)) {//JG
			List<String> listsql = new ArrayList<String>();
			Set<String> swjgset = new HashSet<String>();
			if(datas!=null) {
				int nn = pm.queryForInt("SELECT COUNT(0) FROM "+TABLENAME+" WHERE NSRDATA_LSH='"+BH+"'");
				SIZE=SIZE+nn;

				if(!"ALL".equals(RYSJ)) {
					String[] strs = datas.split(",");
					Map<String,Set<String>> maps = new HashMap<String,Set<String>>();
					StringBuffer orgin = new StringBuffer("INSERT INTO "+TABLENAME+"(SFXCMSG_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,MSG,SMSTYPE_DM,FSCS,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,NSR_LSH, PHONETYPE) " +
							"SELECT ('"+BH+"'|| ("+SIZE+"+rownum)) AS SFXCMSG_LSH,'"+BH+"' AS NSRDATA_LSH,NSRBM,NSRMC,"+RYSJ+" AS SJHM,'"+nsrdx.getDXMBINFO()+qm+"' AS MSG,'01' AS SMSTYPE_DM,0 AS FSCS,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ,LSH AS NSR_LSH,'"+RYSJ+"' AS PHONETYPE FROM NSR_JBXX T WHERE ORG_DM in (");
					for(String str:strs) {
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
							if(str==null || "".equals(str)) {
								
							} else {
								Map swjgmap = DMB.getOrgDMB(str, 1, "J");
								Iterator it = swjgmap.keySet().iterator();
					        	while(it.hasNext()) {
					        		String key = (String) it.next();
					        		swjgset.add(key);
					        	}
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
					orgin.append(" AND LENGTH("+RYSJ+")>10");
					orgin.append(" AND NOT EXISTS (SELECT 1 FROM "+TABLENAME+" B WHERE B.NSRBM=T.NSRBM AND B.PHONETYPE='"+RYSJ+"' AND B.NSRDATA_LSH='"+BH+"')");
					int n = pm.executeUpdate(orgin.toString());
					SIZE=SIZE+n;
					//NSR
					Iterator<String> it = maps.keySet().iterator();
					while(it.hasNext()) {
						String key = it.next();
						StringBuffer nsrsql = new StringBuffer("INSERT INTO "+TABLENAME+"(SFXCMSG_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,MSG,SMSTYPE_DM,FSCS,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,NSR_LSH,PHONETYPE) " +
								"SELECT ('"+BH+"'||("+SIZE+"+rownum)) AS SFXCMSG_LSH,'"+BH+"' AS NSRDATA_LSH,NSRBM,NSRMC,"+RYSJ+" AS SJHM,'"+nsrdx.getDXMBINFO()+qm+"' AS MSG,'01' AS SMSTYPE_DM,0 AS FSCS,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ,LSH AS NSR_LSH,'"+RYSJ+"' AS PHONETYPE FROM NSR_JBXX T WHERE ");
						nsrsql.append("ORG_DM='"+key+"' AND NSRBM IN (");
						Set<String> nsrset = maps.get(key);
						Iterator<String> it2 = nsrset.iterator();
						while(it2.hasNext()) {
							String value = it2.next();
							nsrsql.append("'").append(value).append("',");
						}
						nsrsql.append("'')");
						nsrsql.append(" AND LENGTH("+RYSJ+")>10");
						nsrsql.append(" AND NOT EXISTS (SELECT 1 FROM "+TABLENAME+" B WHERE B.NSRBM=T.NSRBM AND B.PHONETYPE='"+RYSJ+"' AND B.NSRDATA_LSH='"+BH+"')");
						//最后执行。确保过滤。
						n = pm.executeUpdate(nsrsql.toString());
						SIZE=SIZE+n;
					}
				} else {
					for(String SJTYPE : RYSJS) {
						//start for
						String[] strs = datas.split(",");
						Map<String,Set<String>> maps = new HashMap<String,Set<String>>();
						StringBuffer orgin = new StringBuffer("INSERT INTO "+TABLENAME+"(SFXCMSG_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,MSG,SMSTYPE_DM,FSCS,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,NSR_LSH,PHONETYPE) " +
								"SELECT ('"+BH+"'||( "+SIZE+"+rownum)) AS SFXCMSG_LSH,'"+BH+"' AS NSRDATA_LSH,NSRBM,NSRMC,"+SJTYPE+" AS SJHM,'"+nsrdx.getDXMBINFO()+qm+"' AS MSG,'01' AS SMSTYPE_DM,0 AS FSCS,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ,LSH AS NSR_LSH,'"+SJTYPE+"' AS PHONETYPE FROM NSR_JBXX T WHERE ORG_DM in (");
						for(String str:strs) {
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
								if(str==null || "".equals(str)) {
									
								} else {
									Map swjgmap = DMB.getOrgDMB(str, 1, "J");
									Iterator it = swjgmap.keySet().iterator();
						        	while(it.hasNext()) {
						        		String key = (String) it.next();
						        		swjgset.add(key);
						        	}
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
						orgin.append(" AND LENGTH("+SJTYPE+")>10");
						orgin.append(" AND NOT EXISTS (SELECT 1 FROM "+TABLENAME+" B WHERE B.NSRBM=T.NSRBM AND B.PHONETYPE='"+SJTYPE+"' AND B.NSRDATA_LSH='"+BH+"')");
						int n = pm.executeUpdate(orgin.toString());
						SIZE=SIZE+n;
						//NSR
						Iterator<String> it = maps.keySet().iterator();
						while(it.hasNext()) {
							String key = it.next();
							StringBuffer nsrsql = new StringBuffer("INSERT INTO "+TABLENAME+"(SFXCMSG_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,MSG,SMSTYPE_DM,FSCS,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,NSR_LSH,PHONETYPE) " +
									"SELECT ('"+BH+"'||( "+SIZE+"+rownum)) AS SFXCMSG_LSH,'"+BH+"' AS NSRDATA_LSH,NSRBM,NSRMC,"+SJTYPE+" AS SJHM,'"+nsrdx.getDXMBINFO()+qm+"' AS MSG,'01' AS SMSTYPE_DM,0 AS FSCS,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ,LSH AS NSR_LSH,'"+SJTYPE+"' AS PHONETYPE FROM NSR_JBXX T WHERE ");
							nsrsql.append("ORG_DM='"+key+"' AND NSRBM IN (");
							Set<String> nsrset = maps.get(key);
							Iterator<String> it2 = nsrset.iterator();
							while(it2.hasNext()) {
								String value = it2.next();
								nsrsql.append("'").append(value).append("',");
							}
							nsrsql.append("'')");
							nsrsql.append(" AND LENGTH("+SJTYPE+")>10");
							nsrsql.append(" AND NOT EXISTS (SELECT 1 FROM "+TABLENAME+" B WHERE B.NSRBM=T.NSRBM AND B.PHONETYPE='"+SJTYPE+"' AND B.NSRDATA_LSH='"+BH+"')");
							//最后执行。确保过滤。
							n = pm.executeUpdate(nsrsql.toString());
							SIZE=SIZE+n;
						}
						//end for
					}
				}
			}
		} else if("1".equals(xzfs)) {//GROUP
			if(!"ALL".equals(RYSJ)) {
				int nn = pm.queryForInt("SELECT COUNT(0) FROM "+TABLENAME+" WHERE NSRDATA_LSH='"+BH+"'");
				SIZE=SIZE+nn;
				String[] strs = datas.split(",");
				for(String str:strs) {
					StringBuffer groupsql = new StringBuffer("INSERT INTO "+TABLENAME+"(SFXCMSG_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,MSG,SMSTYPE_DM,FSCS,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,NSR_LSH,PHONETYPE) " +
						"SELECT ('"+BH+"'|| ("+SIZE+"+rownum)) AS SFXCMSG_LSH,'"+BH+"' AS NSRDATA_LSH,NSRBM,NSRMC,"+RYSJ+" AS SJHM,'"+nsrdx.getDXMBINFO()+qm+"' AS MSG,'01' AS SMSTYPE_DM,0 AS FSCS,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ,NSR_LSH,'"+RYSJ+"' AS PHONETYPE FROM NSR_GROUPMX T WHERE GROUP_LSH=");
					groupsql.append("'").append(str).append("'");
					groupsql.append(" AND LENGTH("+RYSJ+")>10");
					groupsql.append(" AND NOT EXISTS (SELECT 1 FROM "+TABLENAME+" B WHERE B.NSRBM=T.NSRBM AND B.PHONETYPE='"+RYSJ+"' AND B.NSRDATA_LSH='"+BH+"')");
					int n = pm.executeUpdate(groupsql.toString());
					SIZE=SIZE+n;
				}
			} else {
				int nn = pm.queryForInt("SELECT COUNT(0) FROM "+TABLENAME+" WHERE NSRDATA_LSH='"+BH+"'");
				SIZE=SIZE+nn;
				for(String SJTYPE : RYSJS) {
					String[] strs = datas.split(",");
					for(String str:strs) {
						StringBuffer groupsql = new StringBuffer("INSERT INTO "+TABLENAME+"(SFXCMSG_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,MSG,SMSTYPE_DM,FSCS,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,NSR_LSH,PHONETYPE) " +
								"SELECT ('"+BH+"'||( "+SIZE+"+rownum)) AS SFXCMSG_LSH,'"+BH+"' AS NSRDATA_LSH,NSRBM,NSRMC,"+SJTYPE+" AS SJHM,'"+nsrdx.getDXMBINFO()+qm+"' AS MSG,'01' AS SMSTYPE_DM,0 AS FSCS,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ,NSR_LSH,'"+SJTYPE+"' AS PHONETYPE FROM NSR_GROUPMX T WHERE GROUP_LSH=");
						groupsql.append("'").append(str).append("'");
						groupsql.append(" AND LENGTH("+SJTYPE+")>10");
						groupsql.append(" AND NOT EXISTS (SELECT 1 FROM "+TABLENAME+" B WHERE B.NSRBM=T.NSRBM AND B.PHONETYPE='"+SJTYPE+"' AND B.NSRDATA_LSH='"+BH+"')");
						
						int n = pm.executeUpdate(groupsql.toString());
						SIZE=SIZE+n;
					}
				}
			}
		} else {
			//NSR
			int nn = pm.queryForInt("SELECT COUNT(0) FROM "+TABLENAME+" WHERE NSRDATA_LSH='"+BH+"'");
			int k = nn+1;
			String[] strs = datas.split(",");
			for(String str:strs) {
				if(str==null || "".equals(str.trim())) {
					continue;
				}
				String[] nsrorg = str.substring(3).split(":");
				for(String SJTYPE : RYSJS) {
					String jg = nsrorg[1];
					String nsr = nsrorg[0];
					StringBuffer nsrsql = new StringBuffer("INSERT INTO "+TABLENAME+"(SFXCMSG_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,MSG,SMSTYPE_DM,FSCS,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,NSR_LSH,PHONETYPE)"+
							" SELECT ('"+BH+"'|| "+k+") AS SFXCMSG_LSH,'"+BH+"' AS NSRDATA_LSH,NSRBM,NSRMC,"+SJTYPE+" AS SJHM,'"+nsrdx.getDXMBINFO()+qm+"' AS MSG,'01' AS SMSTYPE_DM,0 AS FSCS,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ,LSH AS NSR_LSH,'"+SJTYPE+"' AS PHONETYPE FROM NSR_JBXX T WHERE NSRBM='"+nsr+"' AND ORG_DM='"+jg+"' ");
					nsrsql.append(" AND LENGTH("+SJTYPE+")>10");
					nsrsql.append(" AND NOT EXISTS (SELECT 1 FROM "+TABLENAME+" B WHERE B.NSRDATA_LSH='"+BH+"' AND B.SJHM=T."+SJTYPE+" AND B.NSRBM='"+nsr+"' AND B.PHONETYPE='"+SJTYPE+"')");
					int m = pm.executeUpdate(nsrsql.toString());
					k = k + m;
				}
			}
		}
		pm.executeUpdate("UPDATE SMS_NSRDATA SET SMSTYPE_DM=?,GROUP_NAME=?,ORG_DM_JG=?,SMSZT_DM=?,XG_SJ=?,XGRY_DM=?,DXQM=?,DXMBINFO=? WHERE NSRDATA_LSH=?", nsrdx);
		message="修改成功！";
		resEnv.putData("message", message);
	}
	
	private void phonetypesave(String type) {
		
	}
}
