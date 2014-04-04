package com.gwinsoft.components.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.common.msg.MSGINFO;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrgl.nsrdxfs.SFXCMSG;
import com.gwinsoft.components.nsrsms.group.Group;
import com.gwinsoft.components.nsrsms.smssend.Nsrmsg;
import com.gwinsoft.components.orgsms.orgsmssend.ORGMSG;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.print.CommonExcelPrintBean;
import com.gwinsoft.framework.print.CommonExcelPrintData;
import com.gwinsoft.framework.print.ICommonExcelPrintBean;
import com.gwinsoft.framework.print.PrintServiceConstants;
import com.gwinsoft.framework.web.action.BaseAction;
import com.gwinsoft.util.GwinSoftUtil;

/**
 * @author fenggy
 */
public class MessageAction extends BaseAction<BaseIAO> {

	private static final long serialVersionUID = 1L;

	public String sendermsg() {
		String YYYYMMDD = GwinSoftUtil.getFormatDate(GwinSoftUtil.getSysDate(), "yyyyMMdd");
		String TABLENAME = "SMS_SFXCMSG"+YYYYMMDD;
		User user = UserHelper.getLoginUserFromRequest(request);
		String orgdm = user.getORG_DM_JG();
		String userdm = user.getUSER_DM();
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			String date = GwinSoftUtil.getFormatDate(GwinSoftUtil.getSysDate(), "yyyy-MM-dd");
			String sql = "SELECT T.NSRDATA_LSH,T.SMSTYPE_DM,T.GROUP_NAME,T.SMSZT_DM,T.LRRY_DM,T.LR_SJ,T.ORG_DM_JG,SUM(CASE WHEN M.SMSTYPE_DM='07' THEN 1 ELSE 0 END) AS PASS_NUM,SUM(CASE WHEN M.SMSTYPE_DM='07' THEN 0 ELSE 1 END) AS NOPASS_NUM FROM SMS_NSRDATA T,sms_nsrmsg M WHERE T.NSRDATA_LSH=M.NSRDATA_LSH AND T.SMSTYPE_DM IN ('01','02') AND T.SMSZT_DM IN ('04','06','07') AND T.LRRY_DM='"+userdm+"' AND TO_CHAR(T.LR_SJ,'YYYY-MM-DD')='"+date+"' GROUP BY T.NSRDATA_LSH,T.SMSTYPE_DM,T.GROUP_NAME,T.SMSZT_DM,T.LRRY_DM,T.LR_SJ,T.ORG_DM_JG"+
						" UNION ALL"+
						" SELECT T.NSRDATA_LSH,T.SMSTYPE_DM,T.GROUP_NAME,T.SMSZT_DM,T.LRRY_DM,T.LR_SJ,T.ORG_DM_JG,SUM(CASE WHEN M.SMSTYPE_DM='07' THEN 1 ELSE 0 END) AS PASS_NUM,SUM(CASE WHEN M.SMSTYPE_DM='07' THEN 0 ELSE 1 END) AS NOPASS_NUM FROM SMS_NSRDATA T,sms_orgmsg M WHERE T.NSRDATA_LSH=M.NSRDATA_LSH AND T.SMSTYPE_DM='03' AND T.SMSZT_DM IN ('04','06','07') AND T.LRRY_DM='"+userdm+"' AND TO_CHAR(T.LR_SJ,'YYYY-MM-DD')='"+date+"' GROUP BY T.NSRDATA_LSH,T.SMSTYPE_DM,T.GROUP_NAME,T.SMSZT_DM,T.LRRY_DM,T.LR_SJ,T.ORG_DM_JG"+
						" UNION ALL"+
						" SELECT T.NSRDATA_LSH,T.SMSTYPE_DM,T.GROUP_NAME,T.SMSZT_DM,T.LRRY_DM,T.LR_SJ,T.ORG_DM_JG,SUM(CASE WHEN M.SMSTYPE_DM='07' THEN 1 ELSE 0 END) AS PASS_NUM,SUM(CASE WHEN M.SMSTYPE_DM='07' THEN 0 ELSE 1 END) AS NOPASS_NUM FROM SMS_NSRDATA T,"+TABLENAME+" M WHERE T.NSRDATA_LSH=M.NSRDATA_LSH AND T.SMSTYPE_DM='04' AND T.SMSZT_DM IN ('04','06','07') AND T.LRRY_DM='"+userdm+"' AND TO_CHAR(T.LR_SJ,'YYYY-MM-DD')='"+date+"' GROUP BY T.NSRDATA_LSH,T.SMSTYPE_DM,T.GROUP_NAME,T.SMSZT_DM,T.LRRY_DM,T.LR_SJ,T.ORG_DM_JG";
			List<MSGINFO> list = pm.queryForList(sql, MSGINFO.class);
			if(list!=null) {
				try {
					GwinSoftUtil.translate(list, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC")));
					GwinSoftUtil.translate(list, "ORG_DM_JG", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
					GwinSoftUtil.translate(list, "SMSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonObject = null;
				for(MSGINFO data : list) {
					jsonObject = new JSONObject();
					// jsonObject.put("GROUPMX_LSH", data.getGROUPMX_LSH());
					jsonObject.put("NSRDATA_LSH", data.getNSRDATA_LSH());
					jsonObject.put("SMSTYPE_DM", data.getSMSTYPE_DM());
					jsonObject.put("GROUP_NAME", data.getGROUP_NAME());
					jsonObject.put("SMSZT_DM", data.getSMSZT_DM());
					jsonObject.put("LRRY_DM", data.getLRRY_DM());
					jsonObject.put("LR_SJ", data.getLR_SJ());
					jsonObject.put("ORG_DM_JG", data.getORG_DM_JG());
					jsonObject.put("PASS_NUM",data.getPASS_NUM());
					jsonObject.put("NOPASS_NUM",data.getNOPASS_NUM());
					jsonArray.add(jsonObject);
				}
				JSONObject json = new JSONObject();
				// 设置返回的数据总记录条数
				json.put("total", list.size());
				// 设置查询的数据结果
				json.put("rows", jsonArray);
				try {
					response.setContentType("text/html; charset=utf-8");
					response.getWriter().print(json.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
		return null;
	}
	public String exportnopass() {
		String YYYYMMDD = GwinSoftUtil.getFormatDate(GwinSoftUtil.getSysDate(), "yyyyMMdd");
		String TABLENAME = "SMS_SFXCMSG"+YYYYMMDD;
		String NSRDATA_LSH = request.getParameter("NSRDATA_LSH");
		String SMSTYPE_DM = request.getParameter("SMSTYPE_DM");
		String[][] attrs = new String[2][2];
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			Group group = pm.queryForObject("SELECT * FROM SMS_NSRDATA WHERE NSRDATA_LSH=?", Group.class, new Object[] { NSRDATA_LSH });
			try {
				GwinSoftUtil.translate(group, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			attrs[0][0] = "SMSTYPE_DM";
			attrs[0][1] = group.getSMSTYPE_DM();
			attrs[1][0] = "GROUP_NAME";
			attrs[1][1] = group.getGROUP_NAME();
			
			if ("03".equals(SMSTYPE_DM)) {
				String sql2 = "SELECT * FROM SMS_ORGMSG WHERE NSRDATA_LSH=? AND SMSTYPE_DM<>'07' AND ROWNUM<=5000";
				List<ORGMSG> tablemx = pm.queryForList(sql2, ORGMSG.class, new String[] { NSRDATA_LSH });
				int i = 0;
				for(ORGMSG msg : tablemx) {
					i++;
					list.add(new CommonExcelPrintBean()
							.setObject(""+i)
							.setObject(msg.getUSER_MC())
							.setObject(msg.getSJHM())
							.setObject(msg.getMSG()));
				}
			} else if ("04".equals(SMSTYPE_DM)) {
				String sql2 = "SELECT * FROM "+TABLENAME+" WHERE NSRDATA_LSH=? AND SMSTYPE_DM<>'07' AND ROWNUM<=5000";
				List<SFXCMSG> tablemx = pm.queryForList(sql2, SFXCMSG.class, new String[] { NSRDATA_LSH });
				int i = 0;
				for(SFXCMSG msg : tablemx) {
					i++;
					list.add(new CommonExcelPrintBean()
							.setObject(""+i)
							.setObject(msg.getNSRMC())
							.setObject(msg.getSJHM())
							.setObject(msg.getMSG()));
				}
			} else {
				String sql2 = "SELECT * FROM SMS_NSRMSG WHERE NSRDATA_LSH=? AND SMSTYPE_DM<>'07' AND ROWNUM<=5000";
				List<Nsrmsg> tablemx = pm.queryForList(sql2, Nsrmsg.class, new String[] { NSRDATA_LSH });
				int i = 0;
				for(Nsrmsg msg : tablemx) {
					i++;
					list.add(new CommonExcelPrintBean()
							.setObject(""+i)
							.setObject(msg.getNSRMC())
							.setObject(msg.getSJHM())
							.setObject(msg.getMSG()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
		CommonExcelPrintData data = new CommonExcelPrintData(attrs, list, 3);
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY, "ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY, data);
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "NotSendedList");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
//		try {
//			this.getResponse().sendRedirect("PrintServiceAction2.action");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return "print";
	}
	public String auditormsg() {
		User user = UserHelper.getLoginUserFromRequest(request);
		String orgdm = user.getORG_DM_JG();
		String userdm = user.getUSER_DM();
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
		if(AUTH_DM!=null) {
			DBPersistenceManager pm = null;
			try {
				pm = DBHelper.getPm();
				String sql1 = "SELECT 'YES' AS INFO FROM QX_USER_AUTH WHERE USER_DM='"+userdm+"' AND AUTH_DM='"+AUTH_DM+"' union SELECT 'YES' AS INFO FROM QX_USER_ROLE A,QX_ROLE B,QX_ROLE_AUTH C WHERE A.ROLE_LSH=B.ROLE_LSH AND B.ROLE_LSH=C.ROLE_LSH AND C.AUTH_DM='"+AUTH_DM+"' AND A.USER_DM='"+userdm+"'";
				SqlRowSet rows = pm.quereyForRowSet(sql1);
				if(rows.next()) {
					String sql = "SELECT T.NSRDATA_LSH,T.SMSTYPE_DM,T.GROUP_NAME,T.SMSZT_DM,T.LRRY_DM,T.XG_SJ AS LR_SJ,T.ORG_DM_JG,'0' AS PASS_NUM,'0' AS NOPASS_NUM FROM SMS_NSRDATA T WHERE T.SMSZT_DM='03' AND SHY='"+userdm+"' ";
					List<MSGINFO> list = pm.queryForList(sql, MSGINFO.class);
					if(list!=null) {
						try {
							GwinSoftUtil.translate(list, "SMSTYPE_DM", DMB.getTranslateStr(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC")));
							GwinSoftUtil.translate(list, "ORG_DM_JG", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
							GwinSoftUtil.translate(list, "SMSZT_DM", DMB.getTranslateStr(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC")));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						JSONArray jsonArray = new JSONArray();
						JSONObject jsonObject = null;
						for(MSGINFO data : list) {
							jsonObject = new JSONObject();
							// jsonObject.put("GROUPMX_LSH", data.getGROUPMX_LSH());
							jsonObject.put("NSRDATA_LSH", data.getNSRDATA_LSH());
							jsonObject.put("SMSTYPE_DM", data.getSMSTYPE_DM());
							jsonObject.put("GROUP_NAME", data.getGROUP_NAME());
							jsonObject.put("SMSZT_DM", data.getSMSZT_DM());
							jsonObject.put("LRRY_DM", data.getLRRY_DM());
							jsonObject.put("LR_SJ", data.getLR_SJ());
							jsonObject.put("ORG_DM_JG", data.getORG_DM_JG());
							jsonObject.put("PASS_NUM",data.getPASS_NUM());
							jsonObject.put("NOPASS_NUM",data.getNOPASS_NUM());
							jsonArray.add(jsonObject);
						}
						JSONObject json = new JSONObject();
						// 设置返回的数据总记录条数
						json.put("total", list.size());
						// 设置查询的数据结果
						json.put("rows", jsonArray);
						try {
							response.setContentType("text/html; charset=utf-8");
							response.getWriter().print(json.toString());
						} catch (IOException e) {
							e.printStackTrace();
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
		}
		return null;
	}
}
