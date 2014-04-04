package com.gwinsoft.components.nsrgl.nsrgroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.print.CommonExcelPrintBean;
import com.gwinsoft.framework.print.CommonExcelPrintData;
import com.gwinsoft.framework.print.ICommonExcelPrintBean;
import com.gwinsoft.framework.print.PrintServiceConstants;
import com.gwinsoft.framework.print.interfaces.IComplexPrintData;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.framework.web.action.BaseAction;

public class NsrgroupAction extends BaseAction<BaseIAO> {
		private Nsrgroup nsrgroup;
		private List<Nsrgroup> nsrgroups;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_GROUP_NAME;
		private String FIND_INFO;
		private String FIND_KS_RQ;
		private String FIND_JZ_RQ;
		private String datas;

		private String FIND_NSRBM;
		private String FIND_NSRMC;
		private String FIND_HYDM;
		private String FIND_SSZGY;
		private String FIND_SBFS;
		private String FIND_DJLX;
		private String FIND_ZCLX;
		private Map hymap = null;
		private Map sbfsmap = null;
		private Map djztmap = null;
		private Map djlxmap = null;
		private Map zclxmap = null;
		
		private List<GROUPMX> tablemx;
		private GROUPMX groupmx;
		public String list(){
			User user = UserHelper.getLoginUserFromRequest(request);
			this.putRequestData("user", user);
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_GROUP_NAME", FIND_GROUP_NAME);
				putRequestData("FIND_INFO", FIND_INFO);
				putRequestData("FIND_KS_RQ", FIND_KS_RQ);
				putRequestData("FIND_JZ_RQ", FIND_JZ_RQ);
				this.invokeInteraction("list");
				nsrgroups = this.getResponseData("nsrgroups");
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
			
			this.invokeInteraction("listmx");
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			List<GROUPMX> msgs = this.getResponseData("tablemx");
			for(GROUPMX msg : msgs) {
				jsonObject = new JSONObject();
				jsonObject.put("GROUPMX_LSH", msg.getGROUPMX_LSH());
				jsonObject.put("NSRBM", msg.getNSRBM());
				jsonObject.put("NSRMC", msg.getNSRMC());

				jsonObject.put("ORG_DM", msg.getORG_DM());
				jsonObject.put("FR", msg.getFR());
				jsonObject.put("FRSJH", msg.getFRSJH()==null?"":msg.getFRSJH());
				jsonObject.put("CWJL", msg.getCWJL());
				jsonObject.put("CWJLSJH", msg.getCWJLSJH()==null?"":msg.getCWJLSJH());
				jsonObject.put("BSRY", msg.getBSRY());
				jsonObject.put("BSRYSJH", msg.getBSRYSJH()==null?"":msg.getBSRYSJH());
				jsonObject.put("HYDM", msg.getHYDM());
				jsonObject.put("SSZGY", msg.getSSZGY());
				jsonObject.put("SBFS", msg.getSBFS());
				jsonObject.put("DJZT", msg.getDJZT());
				jsonObject.put("DJLX", msg.getDJLX());
				jsonObject.put("ZCLX", msg.getZCLX());
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
		public String deletemx() {
			String KEYIDS = request.getParameter("KEYIDS");
			String LSH = request.getParameter("LSH");
			this.putRequestData("KEYIDS", KEYIDS);
			this.putRequestData("LSH", LSH);
			this.invokeInteraction("deletemx");
			return null;
		}
		public String seemx() {
			String LSH = request.getParameter("LSH");
			this.putRequestData("LSH", LSH);
			this.invokeInteraction("seemx");
			groupmx = this.getResponseData("groupmx");
			return "seemx";
		}
		public String modmx() {
			
			return null;
		}
		public String toAdd() {
			User user = UserHelper.getLoginUserFromRequest(request);
			nsrgroup = new Nsrgroup();
			nsrgroup.setORG_DM(user.getLRRY_DM());
			tablemx=new ArrayList<GROUPMX>();
			GROUPMX mx = new GROUPMX();
			tablemx.add(mx);
			return "add";
		}
		public String toMod() {
			see();
			return "mod";
		}
		public String del() {
			this.putRequestData("LSH", LSH);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String see() {
			this.putRequestData("LSH", LSH);
			this.invokeInteraction("see");
			tablemx=this.getResponseData("tablemx");
			nsrgroup = this.getResponseData("nsrgroup");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("nsrgroup", nsrgroup);
			this.putRequestData("datas", datas);

			this.putRequestData("FIND_NSRBM", FIND_NSRBM);
			this.putRequestData("FIND_NSRMC", FIND_NSRMC);
			this.putRequestData("FIND_HYDM", FIND_HYDM);
			this.putRequestData("FIND_SBFS", FIND_SBFS);
			this.putRequestData("FIND_DJLX", FIND_DJLX);
			this.putRequestData("FIND_ZCLX", FIND_ZCLX);

			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.LSH = this.getResponseData("uid");
			if (resEnv.getAPPException() != null) {
				return "add";
			}
			return "mod";
		}
		public String mod() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("LSH", LSH);
			this.putRequestData("nsrgroup", nsrgroup);
			
			this.putRequestData("datas", datas);

			this.putRequestData("FIND_NSRBM", FIND_NSRBM);
			this.putRequestData("FIND_NSRMC", FIND_NSRMC);
			this.putRequestData("FIND_HYDM", FIND_HYDM);
			this.putRequestData("FIND_SBFS", FIND_SBFS);
			this.putRequestData("FIND_DJLX", FIND_DJLX);
			this.putRequestData("FIND_ZCLX", FIND_ZCLX);
			
			this.invokeInteraction("mod");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return "mod";
		}
	public String printExcel() {
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY, "ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY, getExcelPrintData());
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "NSRGroupList");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}
	
		public IComplexPrintData getExcelPrintData() {
			pageBean = null;
			list();
			List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
			String[][] attrs = new String[2][2];
			attrs[0][0] = FIND_GROUP_NAME;
			attrs[0][1] = FIND_GROUP_NAME;
			attrs[1][0] = FIND_INFO;
			attrs[1][1] = FIND_INFO;
			int n = 1;
			for (Nsrgroup _nsrgroup : nsrgroups) {
				list.add(new CommonExcelPrintBean()
						.setObject(n ++)
						.setObject(_nsrgroup.getORG_DM())
						.setObject(_nsrgroup.getGROUP_NAME())
						.setObject(_nsrgroup.getGROUP_COUNT())
						.setObject(_nsrgroup.getINFO())
						.setObject(_nsrgroup.getLRRY_DM())
						.setObject(_nsrgroup.getLR_SJ()));
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
		public void setNsrgroup(Nsrgroup nsrgroup) {
				this.nsrgroup=nsrgroup;
		}
		public Nsrgroup getNsrgroup(){
				return this.nsrgroup;
		}
		public void setNsrgroups(List<Nsrgroup> nsrgroups) {
				this.nsrgroups=nsrgroups;
		}
		public List<Nsrgroup> getNsrgroups(){
				return this.nsrgroups;
		}
		public void setLSH(String LSH) {
				this.LSH=LSH;
		}
		public String getLSH(){
				return this.LSH;
		}
		public void setFIND_GROUP_NAME(String FIND_GROUP_NAME) {
				this.FIND_GROUP_NAME=FIND_GROUP_NAME;
		}
		public String getFIND_GROUP_NAME(){
				return this.FIND_GROUP_NAME;
		}
		public void setFIND_INFO(String FIND_INFO) {
				this.FIND_INFO=FIND_INFO;
		}
		public String getFIND_INFO(){
				return this.FIND_INFO;
		}
		public List<GROUPMX> getTablemx(){
			return tablemx;
		}
		public void setTablemx(List<GROUPMX> tablemx){
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

		public void setFIND_HYDM(String FIND_HYDM) {
				this.FIND_HYDM=FIND_HYDM;
		}
		public String getFIND_HYDM(){
				return this.FIND_HYDM;
		}
		public void setFIND_SSZGY(String FIND_SSZGY) {
				this.FIND_SSZGY=FIND_SSZGY;
		}
		public String getFIND_SSZGY(){
				return this.FIND_SSZGY;
		}
		public void setFIND_SBFS(String FIND_SBFS) {
				this.FIND_SBFS=FIND_SBFS;
		}
		public String getFIND_SBFS(){
				return this.FIND_SBFS;
		}
		public void setFIND_DJLX(String FIND_DJLX) {
				this.FIND_DJLX=FIND_DJLX;
		}
		public String getFIND_DJLX(){
				return this.FIND_DJLX;
		}
		public void setFIND_ZCLX(String FIND_ZCLX) {
				this.FIND_ZCLX=FIND_ZCLX;
		}
		public String getFIND_ZCLX(){
				return this.FIND_ZCLX;
		}
		public Map getHymap() {
			return DMB.sortByKey(DMB.getDMB("HY_CACHE", "HY_MC"),true);
		}
		public void setHymap(Map hymap) {
			this.hymap = hymap;
		}
		public Map getSbfsmap() {
			return DMB.sortByKey(DMB.getDMB("SBFS_CACHE", "SBFS_MC"),true);
		}
		public void setSbfsmap(Map sbfsmap) {
			this.sbfsmap = sbfsmap;
		}
		public Map getDjztmap() {
			return DMB.sortByKey(DMB.getDMB("DJZT_CACHE", "DJZT_MC"),true);
		}
		public void setDjztmap(Map djztmap) {
			this.djztmap = djztmap;
		}
		public Map getDjlxmap() {
			return DMB.sortByKey(DMB.getDMB("DJLX_CACHE", "DJLX_MC"),true);
		}
		public void setDjlxmap(Map djlxmap) {
			this.djlxmap = djlxmap;
		}
		public Map getZclxmap() {
			return DMB.sortByKey(DMB.getDMB("ZCLX_CACHE", "ZCLX_MC"),true);
		}
		public void setZclxmap(Map zclxmap) {
			this.zclxmap = zclxmap;
		}
		public String getDatas() {
			return datas;
		}
		public void setDatas(String datas) {
			this.datas = datas;
		}
		public GROUPMX getGroupmx() {
			return groupmx;
		}
		public void setGroupmx(GROUPMX groupmx) {
			this.groupmx = groupmx;
		}
		public String getFIND_NSRBM() {
			return FIND_NSRBM;
		}
		public void setFIND_NSRBM(String fIND_NSRBM) {
			FIND_NSRBM = fIND_NSRBM;
		}
		public String getFIND_NSRMC() {
			return FIND_NSRMC;
		}
		public void setFIND_NSRMC(String fIND_NSRMC) {
			FIND_NSRMC = fIND_NSRMC;
		}
		
}
