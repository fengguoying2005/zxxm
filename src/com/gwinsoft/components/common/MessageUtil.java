package com.gwinsoft.components.common;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class MessageUtil {

	public static String getMsg(String orgdm, String userdm) {
		boolean isAuditor = false;
		String AUTH_DM = null;
		Map<String, Map<String, Object>> AUTH_CACHE = CacheManager.getCache("AUTH_CACHE");
		Iterator<String> it = AUTH_CACHE.keySet().iterator();
		while(it.hasNext()) {
			String authdm = it.next();
			Map<String, Object> auth = AUTH_CACHE.get(authdm);
			String lj = (String) auth.get("AUTH_LJ");
			if("smssh/sh!list.action".equals(lj)) {
				AUTH_DM = authdm;
				break;
			}
		}
		StringBuffer msg = new StringBuffer("");
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			//审核通过纪录、审核驳回纪录
			if(userdm!=null) {
				String sql = "SELECT SMSTYPE_DM,SMSZT_DM,COUNT(0) AS NUM FROM SMS_NSRDATA WHERE LRRY_DM='"+userdm+"' and SMSZT_DM IN ('04','05') GROUP BY SMSTYPE_DM,SMSZT_DM";
				List<Map<String, Object>> list = pm.queryForList(sql);
				if(list!=null) {
					for(Map<String, Object> map : list) {
						String KEY = (String) map.get("SMSTYPE_DM");
						String SMSZT_DM = (String) map.get("SMSZT_DM");
						if("01".equals(KEY)) {
							KEY = "催报短信";
						} else if("02".equals(KEY)) {
							KEY = "催缴短信";
						} else if("03".equals(KEY)) {
							KEY = "内部短信";
						} else if("04".equals(KEY)) {
							KEY = "税法宣传";
						}
						if("04".equals(SMSZT_DM)) {
							SMSZT_DM = "已审批通过";
						} else if("05".equals(SMSZT_DM)) {
							SMSZT_DM = "未审批通过";
						}
						msg.append("您录入的").append(KEY).append(SMSZT_DM).append("，共").append(map.get("NUM")).append("条。<BR>");
					}
				}
			}
			msg.append("<BR>");
			//待审核纪录。
			//审核员
			if(AUTH_DM!=null) {
				String sql = "SELECT SMSTYPE_DM,COUNT(0) AS NUM FROM SMS_NSRDATA WHERE SMSZT_DM='03' AND ORG_DM_JG='"+orgdm+"' AND EXISTS (" +
							"SELECT 'YES' AS INFO FROM QX_USER_AUTH WHERE USER_DM='"+userdm+"' AND AUTH_DM='"+AUTH_DM+"' union SELECT 'YES' AS INFO FROM QX_USER_ROLE A,QX_ROLE B,QX_ROLE_AUTH C WHERE A.ROLE_LSH=B.ROLE_LSH AND B.ROLE_LSH=C.ROLE_LSH AND C.AUTH_DM='"+AUTH_DM+"' AND A.USER_DM='"+userdm+"'" +
							") GROUP BY SMSTYPE_DM";
				List<Map<String, Object>> list = pm.queryForList(sql);
				if(list!=null) {
					for(Map<String, Object> map : list) {
						String KEY = (String) map.get("SMSTYPE_DM");
						if("01".equals(KEY)) {
							KEY = "催报短信";
						} else if("02".equals(KEY)) {
							KEY = "催缴短信";
						} else if("03".equals(KEY)) {
							KEY = "内部短信";
						} else if("04".equals(KEY)) {
							KEY = "税法宣传";
						}
						msg.append("需要您审核的").append(KEY).append("，共").append(map.get("NUM")).append("条。<BR>");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
		//msg.append("</TABLE>");
		if("<BR>".equals(msg.toString())) {
			msg.append("无消息。");
		}
		return msg.toString();
	}
	public static void main(String[] args) {
		System.out.println(getMsg("",""));
		
	}
}
