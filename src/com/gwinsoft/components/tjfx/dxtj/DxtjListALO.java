package com.gwinsoft.components.tjfx.dxtj;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class DxtjListALO extends BaseALO {

	protected void doService() {
		String viewtable = "V_SMS_SFXCMSG";

		String FIND_KS_RQ = reqEnv.getData("FIND_KS_RQ");
		if(FIND_KS_RQ != null && !"".equals(FIND_KS_RQ.trim())) {
			String year = FIND_KS_RQ.substring(0, 4);
			String month = FIND_KS_RQ.substring(5, 7);
			viewtable = viewtable+year+month;
		}
		
		User user = reqEnv.getData("user");
		StringBuffer casewhen = new StringBuffer("");
		Map DXCS = DMB.getDMB("DXCS_CACHE", "DXJG");
		Iterator its = DXCS.keySet().iterator();
		boolean isHaveCase = false;
		while(its.hasNext()) {
			String key = (String) its.next();
			BigDecimal bd = (BigDecimal) DXCS.get(key);
			double value = bd.doubleValue();
			if(!isHaveCase) {
				isHaveCase = true;
				casewhen.append(" CASE WHEN c.YYS_DM='"+key+"' THEN "+value);
			} else {
				casewhen.append(" WHEN c.YYS_DM='"+key+"' THEN "+value);
			}
		}
		if(isHaveCase) {
			casewhen.append(" ELSE 0 END");
		} else {
			casewhen.append("0");
		}
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT COUNT(0) AS DXSL,SUM(DXJG) AS DXJE");
		StringBuffer tail = new StringBuffer("");
		StringBuffer orderby = new StringBuffer("");
		String sql1 = "SELECT a.ORG_DM_JG,a.SMSTYPE_DM,c.YYS_DM,"+casewhen.toString()+" AS DXJG FROM sms_nsrdata a, sms_nsrmsg b,dm_yyshd c where a.NSRDATA_LSH=b.NSRDATA_LSH and SUBSTR(b.SJHM,1,3)=c.YYSHD_DM AND B.SMSTYPE_DM='07'";
		String sql2 = "SELECT  a.ORG_DM_JG,a.SMSTYPE_DM,c.YYS_DM,"+casewhen.toString()+" AS DXJG FROM sms_nsrdata a, sms_orgmsg b,dm_yyshd c where a.NSRDATA_LSH=b.NSRDATA_LSH  and SUBSTR(b.SJHM,1,3)=c.YYSHD_DM AND B.SMSTYPE_DM='07'";
		String sql3 = "SELECT a.ORG_DM_JG,a.SMSTYPE_DM,c.YYS_DM,"+casewhen.toString()+" AS DXJG FROM sms_nsrdata a, "+viewtable+" b,dm_yyshd c where a.NSRDATA_LSH=b.NSRDATA_LSH and SUBSTR(b.SJHM,1,3)=c.YYSHD_DM AND B.SMSTYPE_DM='07'";
		String FIND_ORG_DM = reqEnv.getData("FIND_ORG_DM");
		if(FIND_ORG_DM != null && !"".equals(FIND_ORG_DM.trim())) {
			sql1+=(" AND a.ORG_DM_JG='"+FIND_ORG_DM+"'");
			sql2+=(" AND a.ORG_DM_JG='"+FIND_ORG_DM+"'");
			sql3+=(" AND a.ORG_DM_JG='"+FIND_ORG_DM+"'");
		} else {
			Map jg = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
			if(jg!=null) {
				sql1+=(" AND a.ORG_DM_JG IN (''");
				sql2+=(" AND a.ORG_DM_JG IN (''");
				sql3+=(" AND a.ORG_DM_JG IN (''");
				Iterator it = jg.keySet().iterator();
				while(it.hasNext()) {
					String swjg = (String) it.next();
					sql1+=(",'"+swjg+"'");
					sql2+=(",'"+swjg+"'");
					sql3+=(",'"+swjg+"'");
				}
				sql1+=(")");
				sql2+=(")");
				sql3+=(")");
			}
		}
		String FIND_YYS_DM = reqEnv.getData("FIND_YYS_DM");
		if(FIND_YYS_DM != null && !"".equals(FIND_YYS_DM.trim())) {
			sql1+=(" and c.YYS_DM='"+FIND_YYS_DM+"'");
			sql2+=(" and c.YYS_DM='"+FIND_YYS_DM+"'");
			sql3+=(" and c.YYS_DM='"+FIND_YYS_DM+"'");
		}
		String FIND_SMSTYPE_DM = reqEnv.getData("FIND_SMSTYPE_DM");
		if(FIND_SMSTYPE_DM != null && !"".equals(FIND_SMSTYPE_DM.trim())) {
			sql1+=(" and a.SMSTYPE_DM='"+FIND_SMSTYPE_DM+"'");
			sql2+=(" and a.SMSTYPE_DM='"+FIND_SMSTYPE_DM+"'");
			sql3+=(" and a.SMSTYPE_DM='"+FIND_SMSTYPE_DM+"'");
		}
		if(FIND_KS_RQ != null && !"".equals(FIND_KS_RQ.trim())) {
			sql1+=(" and b.FS_SJ>=TO_DATE('"+FIND_KS_RQ+"','YYYY-MM-DD')");//TO_DATE('"+FIND_KS_RQ+"','YYYY-MM-DD')
			sql2+=(" and b.FS_SJ>=TO_DATE('"+FIND_KS_RQ+"','YYYY-MM-DD')");
			sql3+=(" and b.FS_SJ>=TO_DATE('"+FIND_KS_RQ+"','YYYY-MM-DD')");
		}
		String FIND_JZ_RQ = reqEnv.getData("FIND_JZ_RQ");
		if(FIND_JZ_RQ != null && !"".equals(FIND_JZ_RQ.trim())) {
			sql1+=(" and b.FS_SJ<=TO_DATE('"+FIND_JZ_RQ+"','YYYY-MM-DD')");
			sql2+=(" and b.FS_SJ<=TO_DATE('"+FIND_JZ_RQ+"','YYYY-MM-DD')");
			sql3+=(" and b.FS_SJ<=TO_DATE('"+FIND_JZ_RQ+"','YYYY-MM-DD')");
		}
		boolean isHaveGroupBy = false;
		String FIND_SMSTYPE = reqEnv.getData("FIND_SMSTYPE");
		if(FIND_SMSTYPE != null && !"".equals(FIND_SMSTYPE.trim())) {
			sql.append(",").append("T.SMSTYPE_DM");
			if(!isHaveGroupBy) {
				isHaveGroupBy = true;
				tail.append(" GROUP BY ").append("T.SMSTYPE_DM");
				orderby.append(" ORDER BY ").append("T.SMSTYPE_DM");
			} else {
				
			}
		}
		String FIND_ORG = reqEnv.getData("FIND_ORG");
		if(FIND_ORG != null && !"".equals(FIND_ORG.trim())) {
			sql.append(",").append("T.ORG_DM_JG AS ORG_DM");
			if(!isHaveGroupBy) {
				isHaveGroupBy = true;
				tail.append(" GROUP BY ").append("T.ORG_DM_JG");
				orderby.append(" ORDER BY ").append("T.ORG_DM_JG");
			} else {
				tail.append(",T.ORG_DM_JG");
				orderby.append(",T.ORG_DM_JG");
			}
		}
		String FIND_YYS = reqEnv.getData("FIND_YYS");
		if(FIND_YYS != null && !"".equals(FIND_YYS.trim())) {
			sql.append(",").append("T.YYS_DM");
			if(!isHaveGroupBy) {
				isHaveGroupBy = true;
				tail.append(" GROUP BY ").append("T.YYS_DM");
				orderby.append(" ORDER BY ").append("T.YYS_DM");
			} else {
				tail.append(",T.YYS_DM");
				orderby.append(",T.YYS_DM");
			}
		}
		sql.append(" FROM (").append(sql1).append(" UNION ALL ").append(sql2).append(" UNION ALL ").append(sql3).append(") T ").append(tail.toString()).append(orderby.toString());
		Object[] args = new Object[] {};
		List<Dxtj> dxtjs = pm.queryForList(sql.toString() , Dxtj.class, args);
		try {
			GwinSoftUtil.translate(dxtjs, "ORG_DM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(dxtjs, "YYS_DM", DMB.getTranslateStr(DMB.getDMB("YYS_CACHE", "YYS_MC")));
			GwinSoftUtil.translate(dxtjs, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("dxtjs", dxtjs);
	}
}
