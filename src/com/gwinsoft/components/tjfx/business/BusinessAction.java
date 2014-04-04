package com.gwinsoft.components.tjfx.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gwinsoft.components.common.CommonTool;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrgl.nsrdxfs.SFXCMSG;
import com.gwinsoft.components.nsrsms.group.Group;
import com.gwinsoft.components.nsrsms.smssend.Nsrmsg;
import com.gwinsoft.components.orgsms.orgsmssend.ORGMSG;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.print.CommonExcelPrintBean;
import com.gwinsoft.framework.print.CommonExcelPrintData;
import com.gwinsoft.framework.print.ICommonExcelPrintBean;
import com.gwinsoft.framework.print.PrintServiceConstants;
import com.gwinsoft.framework.print.interfaces.IComplexPrintData;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.framework.web.action.BaseAction;
import com.gwinsoft.util.GwinSoftUtil;

public class BusinessAction extends BaseAction<BaseIAO> {
		private Business business;
		private List<Business> businesss;
		private PageBean pageBean = new PageBean(10);
		private String NSRDATA_LSH;
		private String FIND_ORG_DM_JG;
		private String FIND_SMSTYPE_DM;
		private String FIND_GROUP_NAME;
		private String FIND_SMSZT_DM;
		private String FIND_KS_RQ;
		private String FIND_JZ_RQ;
		private Map orgmap = null;
		private Map smstypemap = null;
		private Map smsztmap = null;
		private List<NSRDATAMX> tablemx;
		public String list(){
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
	
			putRequestData("FIND_KS_RQ", FIND_KS_RQ);
			putRequestData("FIND_JZ_RQ", FIND_JZ_RQ);
			putRequestData("pageBean", pageBean);
			putRequestData("FIND_ORG_DM_JG", FIND_ORG_DM_JG);
			putRequestData("FIND_SMSTYPE_DM", FIND_SMSTYPE_DM);
			putRequestData("FIND_GROUP_NAME", FIND_GROUP_NAME);
			putRequestData("FIND_SMSZT_DM", FIND_SMSZT_DM);
			this.invokeInteraction("list");
			businesss = this.getResponseData("businesss");
			return "list";
		}
		public String listmx() {
			String LSH = request.getParameter("LSH");
			String SMSTYPE_DM = request.getParameter("SMSTYPE_DM");
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			int nowpage = Integer.parseInt((page==null || "".equals(page)) ? "1" : page);
			int nowrows = Integer.parseInt((rows==null || "".equals(rows)) ? "10" : rows);
			PageBean pagebean = new PageBean();
			pagebean.setPageno(nowpage);
			pagebean.setPageitems(nowrows);
			this.putRequestData("LSH", LSH);
			this.putRequestData("SMSTYPE_DM", SMSTYPE_DM);
			this.putRequestData("page", page);
			this.putRequestData("rows", rows);
			this.putRequestData("pagebean", pagebean);
			this.invokeInteraction("seepage");
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			if ("03".equals(SMSTYPE_DM) || "05".equals(SMSTYPE_DM)) {
				List<ORGMSG> msgs = this.getResponseData("tablemx");
				for(ORGMSG msg : msgs) {
					jsonObject = new JSONObject();
					jsonObject.put("ORG_MC_JG", msg.getORG_MC_JG());
					jsonObject.put("ORG_MC_BM", msg.getORG_MC_BM());
					jsonObject.put("USER_MC", msg.getUSER_MC());
					jsonObject.put("SJHM", msg.getSJHM()==null?"":msg.getSJHM());
					jsonObject.put("MSG", msg.getMSG());
					
					jsonObject.put("SMSTYPE_DM", msg.getSMSTYPE_DM());
					jsonObject.put("FS_SJ", CommonTool.format20(msg.getFS_SJ()));
					jsonObject.put("FSCS", msg.getFSCS());
					jsonArray.add(jsonObject);
				}
			} else if ("04".equals(SMSTYPE_DM)) {
				List<SFXCMSG> msgs = this.getResponseData("tablemx");
				for(SFXCMSG msg : msgs) {
					jsonObject = new JSONObject();
					jsonObject.put("NSRBM", msg.getNSRBM());
					jsonObject.put("NSRMC", msg.getNSRMC());
					jsonObject.put("SJHM", msg.getSJHM()==null?"":msg.getSJHM());
					jsonObject.put("MSG", msg.getMSG());
					
					jsonObject.put("SMSTYPE_DM", msg.getSMSTYPE_DM());
					jsonObject.put("FS_SJ", CommonTool.format20(msg.getFS_SJ()));
					jsonObject.put("FSCS", msg.getFSCS());
					jsonArray.add(jsonObject);
				}
			} else {
				List<Nsrmsg> msgs = this.getResponseData("tablemx");
				for(Nsrmsg msg : msgs) {
					jsonObject = new JSONObject();
					jsonObject.put("NSRBM", msg.getNSRBM());
					jsonObject.put("NSRMC", msg.getNSRMC());
					jsonObject.put("SJHM", msg.getSJHM()==null?"":msg.getSJHM());
					jsonObject.put("CBRQQ", msg.getCBRQQ());
					jsonObject.put("CBRQZ", msg.getCBRQZ());
					jsonObject.put("MSG", msg.getMSG());
					jsonObject.put("JE", msg.getJE());

					jsonObject.put("SMSTYPE_DM", msg.getSMSTYPE_DM());
					jsonObject.put("FS_SJ", msg.getFS_SJ()==null?"":msg.getFS_SJ());
					jsonObject.put("FSCS", msg.getFSCS());
					jsonArray.add(jsonObject);
				}
			}
			
			JSONObject json = new JSONObject();
			// 设置返回的数据总记录条数
			json.put("total", pagebean.getTotalitems());
			// 设置查询的数据结果
			json.put("rows", jsonArray);
			try {
				response.setContentType("text/html; charset=utf-8");
				response.getWriter().print(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		public String toAdd() {
			tablemx=new ArrayList<NSRDATAMX>();
			NSRDATAMX mx = new NSRDATAMX();
			tablemx.add(mx);
			return "add";
		}
		public String toMod() {
			see();
			return "mod";
		}
		public String del() {
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String see() {
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
			this.invokeInteraction("see");
			tablemx=this.getResponseData("tablemx");
			business = this.getResponseData("business");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("business", business);
			this.putRequestData("tablemx", tablemx);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.NSRDATA_LSH = this.getResponseData("uid");
			if(resEnv.getAPPException()!=null) {return "add";}			return "mod";
		}
		public String mod() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
			this.putRequestData("business", business);
			this.putRequestData("tablemx", tablemx);
			this.invokeInteraction("mod");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return "mod";
		}
		public String exportfilenosend() {
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
					String sql2 = "SELECT * FROM SMS_ORGMSG WHERE NSRDATA_LSH=? AND SMSTYPE_DM<>'07' AND ROWNUM<=2000";
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
					String sql2 = "SELECT * FROM SMS_SFXCMSG WHERE NSRDATA_LSH=? AND SMSTYPE_DM<>'07' AND ROWNUM<=2000";
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
					String sql2 = "SELECT * FROM SMS_NSRMSG WHERE NSRDATA_LSH=? AND SMSTYPE_DM<>'07' AND ROWNUM<=2000";
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
			return "printService";
		}
	public String printExcel() {
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY, "ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY, getExcelPrintData());
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "BusinessList");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}
	public IComplexPrintData getExcelPrintData() {
		pageBean=null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[4][2];
		attrs[0][0]=FIND_ORG_DM_JG;attrs[0][1]=FIND_ORG_DM_JG;
		attrs[1][0]=FIND_SMSTYPE_DM;attrs[1][1]=FIND_SMSTYPE_DM;
		attrs[2][0]=FIND_GROUP_NAME;attrs[2][1]=FIND_GROUP_NAME;
		attrs[3][0]=FIND_SMSZT_DM;attrs[3][1]=FIND_SMSZT_DM;
		int n = 1;
		for (Business _business: businesss) {
			list.add(new CommonExcelPrintBean()
					.setObject(n ++)
					.setObject(_business.getORG_DM_JG())
					.setObject(_business.getSMSTYPE_DM())
					.setObject(_business.getGROUP_NAME())
					.setObject(_business.getSMSZT_DM())
					.setObject(_business.getLRRY_DM())
					.setObject(_business.getLR_SJ()));
		}
		CommonExcelPrintData data = new CommonExcelPrintData(attrs, list, 2);
		return data;
	}
		public void setPageBean(PageBean pageBean) {
				this.pageBean=pageBean;
		}
		public PageBean getPageBean(){
				return this.pageBean;
		}
		public void setBusiness(Business business) {
				this.business=business;
		}
		public Business getBusiness(){
				return this.business;
		}
		public void setBusinesss(List<Business> businesss) {
				this.businesss=businesss;
		}
		public List<Business> getBusinesss(){
				return this.businesss;
		}
		public void setNSRDATA_LSH(String NSRDATA_LSH) {
				this.NSRDATA_LSH=NSRDATA_LSH;
		}
		public String getNSRDATA_LSH(){
				return this.NSRDATA_LSH;
		}
		public void setFIND_ORG_DM_JG(String FIND_ORG_DM_JG) {
				this.FIND_ORG_DM_JG=FIND_ORG_DM_JG;
		}
		public String getFIND_ORG_DM_JG(){
				return this.FIND_ORG_DM_JG;
		}
		public void setFIND_SMSTYPE_DM(String FIND_SMSTYPE_DM) {
				this.FIND_SMSTYPE_DM=FIND_SMSTYPE_DM;
		}
		public String getFIND_SMSTYPE_DM(){
				return this.FIND_SMSTYPE_DM;
		}
		public void setFIND_GROUP_NAME(String FIND_GROUP_NAME) {
				this.FIND_GROUP_NAME=FIND_GROUP_NAME;
		}
		public String getFIND_GROUP_NAME(){
				return this.FIND_GROUP_NAME;
		}
		public void setFIND_SMSZT_DM(String FIND_SMSZT_DM) {
				this.FIND_SMSZT_DM=FIND_SMSZT_DM;
		}
		public String getFIND_SMSZT_DM(){
				return this.FIND_SMSZT_DM;
		}
		public Map getOrgmap() {
			User user = UserHelper.getLoginUserFromRequest(request);
			Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
			return DMB.sortByKey(dmb,true);
		}
		public void setOrgmap(Map orgmap) {
			this.orgmap = orgmap;
		}
		public Map getSmstypemap() {
			return DMB.sortByKey(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC"),true);
		}
		public void setSmstypemap(Map smstypemap) {
			this.smstypemap = smstypemap;
		}
		public Map getSmsztmap() {
			return DMB.sortByKey(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC"),true);
		}
		public void setSmsztmap(Map smsztmap) {
			this.smsztmap = smsztmap;
		}
		public List<NSRDATAMX> getTablemx(){
			return tablemx;
		}
		public void setTablemx(List<NSRDATAMX> tablemx){
			this.tablemx = tablemx;
		}
		public String getFIND_KS_RQ() {
			return FIND_KS_RQ;
		}
		public void setFIND_KS_RQ(String fIND_KS_RQ) {
			FIND_KS_RQ = fIND_KS_RQ;
		}
		public String getFIND_JZ_RQ() {
			return FIND_JZ_RQ;
		}
		public void setFIND_JZ_RQ(String fIND_JZ_RQ) {
			FIND_JZ_RQ = fIND_JZ_RQ;
		}
		
}
