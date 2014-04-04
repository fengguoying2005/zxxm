package com.gwinsoft.components.xtgl.hmd;

import java.util.ArrayList;
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

public class HmdAddALO extends BaseALO {

	int SIZE = 0;
	protected void doService() {
		PhoneType phonetype = this.getData("phonetype");
		if("true".equals(phonetype.getBSR_BJ())) {
			addHmd("BSRYSJH","01", phonetype.getINFO());
		}
		if("true".equals(phonetype.getCWJL_BJ())) {
			addHmd("CWJLSJH","03", phonetype.getINFO());
		}
		if("true".equals(phonetype.getFR_BJ())) {
			addHmd("FRSJH","02", phonetype.getINFO());
		}
	}
	private void addHmd(String SJLX_KEY, String SJLX_DM, String INFO) {
		User user = reqEnv.getData("user");
		String datas = this.getData("datas");
		//String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		List<String> listsql = new ArrayList<String>();
		Set<String> swjgset = new HashSet<String>();
		String message = "";
		if(datas!=null) {
			String[] strs = datas.split(",");
			Map<String,Set<String>> maps = new HashMap<String,Set<String>>();
			StringBuffer orgin = new StringBuffer("INSERT INTO T_HMD(LSH,NSRBM,NSRMC,ORG_DM_J,SJHM,SJLX_DM,RYLX_DM,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,INFO) " +
					"SELECT SYS_GUID() AS LSH,NSRBM,NSRMC,ORG_DM AS ORG_DM_J,"+SJLX_KEY+" AS SJHM,'"+SJLX_DM+"' AS SJLX_DM,'01' AS RYLX_DM,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ,'"+INFO+"' AS INFO FROM NSR_JBXX T WHERE LENGTH("+SJLX_KEY+")>0 AND ORG_DM in (");
			for(String str:strs) {
				if("".equals(str)) {
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
				StringBuffer nsrsql = new StringBuffer(
						"INSERT INTO T_HMD(LSH,NSRBM,NSRMC,ORG_DM_J,SJHM,SJLX_DM,RYLX_DM,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,INFO) " +
						"SELECT SYS_GUID() AS LSH,NSRBM,NSRMC,ORG_DM AS ORG_DM_J,"+SJLX_KEY+" AS SJHM,'"+SJLX_DM+"' AS SJLX_DM,'01' AS RYLX_DM,'"+user.getUSER_DM()+"' AS LRRY_DM,'"+user.getUSER_DM()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ,'"+INFO+"' AS INFO FROM NSR_JBXX T WHERE ");
				nsrsql.append("ORG_DM='"+key+"' AND NSRBM IN (");
				Set<String> nsrset = maps.get(key);
				Iterator<String> it2 = nsrset.iterator();
				while(it2.hasNext()) {
					String value = it2.next();
					nsrsql.append("'").append(value).append("',");
				}
				nsrsql.append("'') AND LENGTH(T."+SJLX_KEY+")>0 AND NOT EXISTS (SELECT 1 FROM T_HMD B WHERE B.NSRBM=T.NSRBM AND B.ORG_DM_J='"+key+"' AND B.SJLX_DM='"+SJLX_DM+"' AND B.SJHM=T."+SJLX_KEY+")");// AND NOT EXISTS (SELECT 1 FROM NSR_GROUPMX B WHERE B.NSRBM=T.NSRBM AND B.ORG_DM='"+key+"')
				//最后执行。确保过滤。
				listsql.add(nsrsql.toString());
			}
			int n = 0;
			
			orgin.append(" AND NOT EXISTS (SELECT 1 FROM T_HMD B WHERE B.NSRBM=T.NSRBM AND B.ORG_DM_J=T.ORG_DM AND B.SJLX_DM='"+SJLX_DM+"' AND B.SJHM=T."+SJLX_KEY+")");
			//System.out.println(orgin.toString());
			n = pm.executeUpdate(orgin.toString());
			SIZE=SIZE+n;
		}
		for(String sql: listsql) {
			int n = 0;
			n = pm.executeUpdate(sql);
			//System.out.println(sql);
			SIZE=SIZE+n;
		}
		message="保存成功！成功条数："+SIZE+".";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
