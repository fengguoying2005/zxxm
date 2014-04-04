package com.gwinsoft.components.nsrsms.group;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrsms.smssend.Nsrmsg;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.framework.web.action.BaseAction;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.print.PrintServiceConstants;
import com.gwinsoft.framework.print.interfaces.IComplexPrintData;
import com.gwinsoft.framework.print.ICommonExcelPrintBean;
import com.gwinsoft.framework.print.CommonExcelPrintBean;
import com.gwinsoft.framework.print.CommonExcelPrintData;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;

public class GroupAction extends BaseAction<BaseIAO> {
		private Group group;
		private List<Group> groups;
		private PageBean pageBean = new PageBean(10);
		private String NSRDATA_LSH;
		private String FIND_SMSTYPE_DM;
		private String FIND_GROUP_NAME;
		private String FIND_SMSZT_DM;
		private String FIND_KS_RQ;
		private String FIND_JZ_RQ;
		private Map smstypemap = null;
		private Map orgmap = null;
		private Map smsztmap = null;
		private List<NSRDATAMX> tablemx;
		public String list(){
			User user = UserHelper.getLoginUserFromRequest(request);
			putRequestData("user", user);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_SMSTYPE_DM", FIND_SMSTYPE_DM);
				putRequestData("FIND_GROUP_NAME", FIND_GROUP_NAME);
				putRequestData("FIND_SMSZT_DM", FIND_SMSZT_DM);
				putRequestData("FIND_KS_RQ", FIND_KS_RQ);
				putRequestData("FIND_JZ_RQ", FIND_JZ_RQ);
				this.invokeInteraction("list");
				groups = this.getResponseData("groups");
				return "list";
		}
		public String listmx() {
			String LSH = request.getParameter("LSH");
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			int nowpage = Integer.parseInt((page==null || "".equals(page)) ? "1" : page);
			int nowrows = Integer.parseInt((rows==null || "".equals(rows)) ? "10" : rows);
			PageBean pagebean = new PageBean();
			pagebean.setPageno(nowpage);
			pagebean.setPageitems(nowrows);
			this.putRequestData("LSH", LSH);
			this.putRequestData("page", page);
			this.putRequestData("rows", rows);
			this.putRequestData("pagebean", pagebean);
			this.invokeInteraction("seepage");
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			List<NSRDATAMX> msgs = this.getResponseData("tablemx");
			for(NSRDATAMX msg : msgs) {
				jsonObject = new JSONObject();
				jsonObject.put("NSRBM", msg.getNSRBM());
				jsonObject.put("NSRMC", msg.getNSRMC());
				jsonObject.put("SJHM", msg.getSJHM());
				jsonObject.put("CBRQQ", msg.getCBRQQ());
				jsonObject.put("CBRQZ", msg.getCBRQZ());
				
				jsonObject.put("ZSXM", msg.getZSXM());
				jsonObject.put("ZSPM", msg.getZSPM());
				jsonObject.put("SKSSQ_Q", msg.getSKSSQ_Q());
				jsonObject.put("SKSSQ_Z", msg.getSKSSQ_Z());
				

				jsonObject.put("STR_CBRQQ", msg.getSTR_CBRQQ());
				jsonObject.put("STR_CBRQZ", msg.getSTR_CBRQZ());
				jsonObject.put("STR_SKSSQ_Q", msg.getSTR_SKSSQ_Q());
				jsonObject.put("STR_SKSSQ_Z", msg.getSTR_SKSSQ_Z());
				
				jsonObject.put("JE", msg.getJE());
				jsonArray.add(jsonObject);
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
			group = this.getResponseData("group");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("group", group);
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
			this.putRequestData("group", group);
			this.invokeInteraction("mod");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return toMod();
		}
	public String printExcel() {
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY, "ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY, getExcelPrintData());
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "TaxpayerGroups");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}
	public IComplexPrintData getExcelPrintData() {
		pageBean=null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[3][2];
		attrs[0][0]=FIND_SMSTYPE_DM;attrs[0][1]=FIND_SMSTYPE_DM;
		attrs[1][0]=FIND_GROUP_NAME;attrs[1][1]=FIND_GROUP_NAME;
		attrs[2][0]=FIND_SMSZT_DM;attrs[2][1]=FIND_SMSZT_DM;
		int i = 0;
		for (Group _group: groups) {
			i ++;
			list.add(new CommonExcelPrintBean().setObject("" + i)
					.setObject(_group.getSMSTYPE_DM())
					.setObject(_group.getGROUP_NAME())
					.setObject(_group.getORG_DM_JG())
					.setObject(_group.getSMSZT_DM())
					.setObject(_group.getLRRY_DM())
					.setObject(_group.getLR_SJ()));
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
		public void setGroup(Group group) {
				this.group=group;
		}
		public Group getGroup(){
				return this.group;
		}
		public void setGroups(List<Group> groups) {
				this.groups=groups;
		}
		public List<Group> getGroups(){
				return this.groups;
		}
		public void setNSRDATA_LSH(String NSRDATA_LSH) {
				this.NSRDATA_LSH=NSRDATA_LSH;
		}
		public String getNSRDATA_LSH(){
				return this.NSRDATA_LSH;
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
		public Map getSmstypemap() {
			return DMB.sortByKey(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC"),true);
		}
		public void setSmstypemap(Map smstypemap) {
			this.smstypemap = smstypemap;
		}
		public Map getOrgmap() {
			User user = UserHelper.getLoginUserFromRequest(request);
			Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 2, "J");
			return DMB.sortByKey(dmb,true);
		}
		public void setOrgmap(Map orgmap) {
			this.orgmap = orgmap;
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
