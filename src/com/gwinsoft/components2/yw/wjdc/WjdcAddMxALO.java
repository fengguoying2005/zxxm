package com.gwinsoft.components2.yw.wjdc;

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

public class WjdcAddMxALO extends BaseALO {

	protected void doService() {
		String BH = reqEnv.getData("LSH");
		User user = reqEnv.getData("user");
		String rylx = reqEnv.getData("rylx");
		String xzfs = reqEnv.getData("xzfs");
		String datas = reqEnv.getData("datas");
		Date time = GwinSoftUtil.getSysDate();
		String RYSJ = ("1".equals(rylx))?"BSRYSJH":(("2".equals(rylx))?"CWJLSJH":(("3".equals(rylx))?"FRSJH":"ALL"));
		String[] RYSJS = new String[]{"BSRYSJH","CWJLSJH","FRSJH"};
		int SIZE = 0;
		String message = "";
		DBPersistenceManager pm = this.getPM();
		if("2".equals(xzfs)) {//JG
			List<String> listsql = new ArrayList<String>();
			Set<String> swjgset = new HashSet<String>();
			if(datas!=null) {
				if(!"ALL".equals(RYSJ)) {
					String[] strs = datas.split(",");
					Map<String,Set<String>> maps = new HashMap<String,Set<String>>();
					StringBuffer orgin = new StringBuffer("INSERT INTO DX_WJDCMX(LSH,ZB_LSH,SJHM,NSRBM,NSRMC,SMSINFO,LR_SJ,DXZT_DM,PHONETYPE) " +
							"SELECT SYS_GUID() AS LSH,'"+BH+"' AS ZB_LSH,"+RYSJ+" AS SJHM,NSRBM,NSRMC,'' AS SMSINFO,SYSDATE AS LR_SJ,'06' AS DXZT_DM,'"+RYSJ+"' AS PHONETYPE FROM NSR_JBXX T WHERE ORG_DM in (");
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
					orgin.append(" AND NOT EXISTS (SELECT 1 FROM DX_WJDCMX B WHERE B.ZB_LSH='"+BH+"' AND B.NSRBM=T.NSRBM AND B.PHONETYPE='"+RYSJ+"')");
					int n = pm.executeUpdate(orgin.toString());
					SIZE=SIZE+n;
					//NSR
					Iterator<String> it = maps.keySet().iterator();
					while(it.hasNext()) {
						String key = it.next();
						StringBuffer nsrsql = new StringBuffer("INSERT INTO DX_WJDCMX(LSH,ZB_LSH,SJHM,NSRBM,NSRMC,SMSINFO,LR_SJ,DXZT_DM,PHONETYPE) " +
								"SELECT SYS_GUID() AS LSH,'"+BH+"' AS ZB_LSH,"+RYSJ+" AS SJHM,NSRBM,NSRMC,'' AS SMSINFO,SYSDATE AS LR_SJ,'06' AS DXZT_DM,'"+RYSJ+"' AS PHONETYPE FROM NSR_JBXX T WHERE ");
						nsrsql.append("ORG_DM='"+key+"' AND NSRBM IN (");
						Set<String> nsrset = maps.get(key);
						Iterator<String> it2 = nsrset.iterator();
						while(it2.hasNext()) {
							String value = it2.next();
							nsrsql.append("'").append(value).append("',");
						}
						nsrsql.append("'')");
						nsrsql.append(" AND LENGTH("+RYSJ+")>10");
						nsrsql.append(" AND NOT EXISTS (SELECT 1 FROM DX_WJDCMX B WHERE B.ZB_LSH='"+BH+"' AND B.NSRBM=T.NSRBM AND B.PHONETYPE='"+RYSJ+"')");
						//最后执行。确保过滤。
						n = pm.executeUpdate(nsrsql.toString());
						SIZE=SIZE+n;
					}
				} else {
					for(String SJTYPE : RYSJS) {
						//start for
						String[] strs = datas.split(",");
						Map<String,Set<String>> maps = new HashMap<String,Set<String>>();
						StringBuffer orgin = new StringBuffer("INSERT INTO DX_WJDCMX(LSH,ZB_LSH,SJHM,NSRBM,NSRMC,SMSINFO,LR_SJ,DXZT_DM,PHONETYPE) " +
								"SELECT SYS_GUID() AS LSH,'"+BH+"' AS ZB_LSH,"+SJTYPE+" AS SJHM,NSRBM,NSRMC,'' AS SMSINFO,SYSDATE AS LR_SJ,'06' AS DXZT_DM,'"+SJTYPE+"' AS PHONETYPE FROM NSR_JBXX T WHERE ORG_DM in (");
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
						orgin.append(" AND NOT EXISTS (SELECT 1 FROM DX_WJDCMX B WHERE B.ZB_LSH='"+BH+"' AND B.NSRBM=T.NSRBM AND B.PHONETYPE='"+SJTYPE+"')");
						int n = pm.executeUpdate(orgin.toString());
						SIZE=SIZE+n;
						//NSR
						Iterator<String> it = maps.keySet().iterator();
						while(it.hasNext()) {
							String key = it.next();
							StringBuffer nsrsql = new StringBuffer("INSERT INTO DX_WJDCMX(LSH,ZB_LSH,SJHM,NSRBM,NSRMC,SMSINFO,LR_SJ,DXZT_DM,PHONETYPE) " +
									"SELECT SYS_GUID() AS LSH,'"+BH+"' AS ZB_LSH,"+SJTYPE+" AS SJHM,NSRBM,NSRMC,'' AS SMSINFO,SYSDATE AS LR_SJ,'06' AS DXZT_DM,'"+SJTYPE+"' AS PHONETYPE FROM NSR_JBXX T WHERE ");
							nsrsql.append("ORG_DM='"+key+"' AND NSRBM IN (");
							Set<String> nsrset = maps.get(key);
							Iterator<String> it2 = nsrset.iterator();
							while(it2.hasNext()) {
								String value = it2.next();
								nsrsql.append("'").append(value).append("',");
							}
							nsrsql.append("'')");
							nsrsql.append(" AND LENGTH("+SJTYPE+")>10");
							nsrsql.append(" AND NOT EXISTS (SELECT 1 FROM DX_WJDCMX B WHERE B.ZB_LSH='"+BH+"' AND B.NSRBM=T.NSRBM AND B.PHONETYPE='"+SJTYPE+"')");
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
				String[] strs = datas.split(",");
				for(String str:strs) {
					StringBuffer groupsql = new StringBuffer("INSERT INTO DX_WJDCMX(LSH,ZB_LSH,SJHM,NSRBM,NSRMC,SMSINFO,LR_SJ,DXZT_DM,PHONETYPE) " +
							"SELECT SYS_GUID() AS LSH,'"+BH+"' AS ZB_LSH,"+RYSJ+" AS SJHM,NSRBM,NSRMC,'' AS SMSINFO,SYSDATE AS LR_SJ,'06' AS DXZT_DM,'"+RYSJ+"' AS PHONETYPE FROM NSR_GROUPMX T WHERE GROUP_LSH=");
					groupsql.append("'").append(str).append("'");
					groupsql.append(" AND LENGTH("+RYSJ+")>10");
					groupsql.append(" AND NOT EXISTS (SELECT 1 FROM DX_WJDCMX B WHERE B.ZB_LSH='"+BH+"' AND B.SJHM=T."+RYSJ+" AND B.NSRBM=T.NSRBM AND B.PHONETYPE='"+RYSJ+"'  )");
					int n = pm.executeUpdate(groupsql.toString());
				}
			} else {
				for(String SJTYPE : RYSJS) {
					String[] strs = datas.split(",");
					for(String str:strs) {
						StringBuffer groupsql = new StringBuffer("INSERT INTO DX_WJDCMX(LSH,ZB_LSH,SJHM,NSRBM,NSRMC,SMSINFO,LR_SJ,DXZT_DM,PHONETYPE) " +
								"SELECT SYS_GUID() AS LSH,'"+BH+"' AS ZB_LSH,"+SJTYPE+" AS SJHM,NSRBM,NSRMC,'' AS SMSINFO,SYSDATE AS LR_SJ,'06' AS DXZT_DM,'"+SJTYPE+"' AS PHONETYPE FROM NSR_GROUPMX T WHERE GROUP_LSH=");
						groupsql.append("'").append(str).append("'");
						groupsql.append(" AND LENGTH("+SJTYPE+")>10");
						groupsql.append(" AND NOT EXISTS (SELECT 1 FROM DX_WJDCMX B WHERE B.ZB_LSH='"+BH+"' AND B.SJHM=T."+SJTYPE+" AND B.NSRBM=T.NSRBM AND B.PHONETYPE='"+SJTYPE+"')");
						
						int n = pm.executeUpdate(groupsql.toString());
					}
				}
			}
		} else {
			//NSR
			String[] strs = datas.split(",");
			if(!"ALL".equals(RYSJ)) {
				for(String str:strs) {
					if(str==null || "".equals(str.trim())) {
						continue;
					}
					String[] nsrorg = str.substring(3).split(":");
					String jg = nsrorg[1];
					String nsr = nsrorg[0];
					StringBuffer nsrsql = new StringBuffer("INSERT INTO DX_WJDCMX(LSH,ZB_LSH,SJHM,NSRBM,NSRMC,SMSINFO,LR_SJ,DXZT_DM,PHONETYPE)"+
							" SELECT SYS_GUID() AS LSH,'"+BH+"' AS ZB_LSH,"+RYSJ+" AS SJHM,NSRBM,NSRMC,'' AS SMSINFO,SYSDATE AS LR_SJ,'06' AS DXZT_DM,'"+RYSJ+"' AS PHONETYPE FROM NSR_JBXX T WHERE NSRBM='"+nsr+"' AND ORG_DM='"+jg+"' ");
					nsrsql.append(" AND LENGTH("+RYSJ+")>10");
					nsrsql.append(" AND NOT EXISTS (SELECT 1 FROM DX_WJDCMX B WHERE B.ZB_LSH='"+BH+"' AND B.SJHM=T."+RYSJ+" AND B.NSRBM='"+nsr+"' AND B.PHONETYPE='"+RYSJ+"')");
					int m = pm.executeUpdate(nsrsql.toString());
				}
			} else {
				for(String str:strs) {
					if(str==null || "".equals(str.trim())) {
						continue;
					}
					String[] nsrorg = str.substring(3).split(":");
					for(String SJTYPE : RYSJS) {
						String jg = nsrorg[1];
						String nsr = nsrorg[0];
						StringBuffer nsrsql = new StringBuffer("INSERT INTO DX_WJDCMX(LSH,ZB_LSH,SJHM,NSRBM,NSRMC,SMSINFO,LR_SJ,DXZT_DM,PHONETYPE)"+
								" SELECT SYS_GUID() AS LSH,'"+BH+"' AS ZB_LSH,"+SJTYPE+" AS SJHM,NSRBM,NSRMC,'' AS SMSINFO,SYSDATE AS LR_SJ,'06' AS DXZT_DM,'"+SJTYPE+"' AS PHONETYPE FROM NSR_JBXX T WHERE NSRBM='"+nsr+"' AND ORG_DM='"+jg+"' ");
						nsrsql.append(" AND LENGTH("+SJTYPE+")>10");
						nsrsql.append(" AND NOT EXISTS (SELECT 1 FROM DX_WJDCMX B WHERE B.ZB_LSH='"+BH+"' AND B.SJHM=T."+SJTYPE+" AND B.NSRBM='"+nsr+"' AND B.PHONETYPE='"+SJTYPE+"')");
						int m = pm.executeUpdate(nsrsql.toString());
					}
				}
			}
		}
		message="添加参与问卷纳税人成功！";
		resEnv.putData("message", message);
	}
}