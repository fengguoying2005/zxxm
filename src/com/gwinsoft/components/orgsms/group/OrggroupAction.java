package com.gwinsoft.components.orgsms.group;

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

public class OrggroupAction extends BaseAction<BaseIAO> {
		private Orggroup orggroup;
		private List<Orggroup> orggroups;
		private PageBean pageBean = new PageBean(10);
		private String GROUP_LSH;
		private String FIND_GROUP_NAME;
		private String FIND_GROUP_COUNT;
		private Map orgmap = null;
		private List<GROUPMX> tablemx;
		private String datagridstr;
		public String list(){
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("GROUP_LSH", GROUP_LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_GROUP_NAME", FIND_GROUP_NAME);
				putRequestData("FIND_GROUP_COUNT", FIND_GROUP_COUNT);
				this.invokeInteraction("list");
				orggroups = this.getResponseData("orggroups");
				return "list";
		}
		public String loadgroup() {

			String GROUP_LSH = request.getParameter("GROUP_LSH");
			this.putRequestData("GROUP_LSH", GROUP_LSH);

			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			int nowpage = Integer.parseInt((page==null || "".equals(page)) ? "1" : page);
			int nowrows = Integer.parseInt((rows==null || "".equals(rows)) ? "10" : rows);
			PageBean pagebean = new PageBean();
			pagebean.setPageno(nowpage);
			pagebean.setPageitems(nowrows);
			this.putRequestData("page", page);
			this.putRequestData("rows", rows);
			this.putRequestData("pagebean", pagebean);
			
			this.invokeInteraction("listmx");

			tablemx=this.getResponseData("tablemx");
			
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			for(GROUPMX data : tablemx) {
				jsonObject = new JSONObject();
				jsonObject.put("GROUPMX_LSH", data.getGROUPMX_LSH());
				jsonObject.put("ORG_DM_JG",data.getORG_DM_JG());
				jsonObject.put("ORG_DM_BM",data.getORG_DM_BM());
				jsonObject.put("ORG_MC_JG",data.getORG_MC_JG());
				jsonObject.put("ORG_MC_BM",data.getORG_MC_BM());
				jsonObject.put("SJHM",data.getSJHM());
				jsonObject.put("USER_DM",data.getUSER_DM());
				jsonObject.put("USER_MC",data.getUSER_MC());
				jsonObject.put("ZW_DM",data.getZW_DM()==null?"":data.getZW_DM());
				jsonObject.put("ZW_MC",data.getZW_MC()==null?"":data.getZW_MC());
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
			String GROUP_LSH = request.getParameter("GROUP_LSH");
			this.putRequestData("KEYIDS", KEYIDS);
			this.putRequestData("GROUP_LSH", GROUP_LSH);
			this.invokeInteraction("deletemx");
			return null;
		}
		public String toAdd() {
			if(orggroup==null) {
				orggroup = new Orggroup();
			}
			User user = UserHelper.getLoginUserFromRequest(request);
			orggroup.setORG_DM_JG(user.getORG_DM_JG());
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
			this.putRequestData("GROUP_LSH", GROUP_LSH);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String see() {
			this.putRequestData("GROUP_LSH", GROUP_LSH);
			this.invokeInteraction("see");
			tablemx=this.getResponseData("tablemx");
			orggroup = this.getResponseData("orggroup");
			return "see";
		}
		public String add() {
			tablemx=new ArrayList<GROUPMX>();
			String[] ja = datagridstr.split(",");
			for(int i = 0; i < ja.length; i ++) {
				String data = ja[i];
				if(data==null || data.length()<2) {
					continue;
				}
				
				GROUPMX mx = new GROUPMX();
				String[] datas = data.split("~");
				if(datas.length<2)
					continue;
//						(String) map.get("USER_DM")		//datas[0]
//				+ "~" + (String) map.get("USER_MC") 	//datas[1]
//				+ "~" + (String) map.get("TEL")			//datas[2]
//				+ "~" + (String) map.get("ORG_DM_JG")	//datas[3]
//				+ "~" + bm								//datas[4]
//				+ "~" + (String) jgorg.get("ORG_MC")	//datas[5]
//				+ "~" + (String) bmorg.get("ORG_MC")	//datas[6]
//				+ "~" + ZWDM							//datas[7]
//				+ "~" + ZWMC							//datas[8]
//				+"~" +JGPX								datas[9]
				mx.setORG_DM_JG(datas[3]);
				mx.setORG_DM_BM(datas[4]);
				mx.setORG_MC_JG(datas[5]);
				mx.setORG_MC_BM(datas[6]);
				mx.setSJHM(datas[2]);
				mx.setUSER_DM(datas[0]);
				mx.setUSER_MC(datas[1]);
				if(datas.length>7) {
					mx.setZW_DM(datas[7]);
				} else {
					mx.setZW_DM("");
				}
				if(datas.length>8) {
					mx.setZW_MC(datas[8]);
				} else {
					mx.setZW_MC("");
				}
				tablemx.add(mx);
			}
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("orggroup", orggroup);
			this.putRequestData("tablemx", tablemx);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.GROUP_LSH = this.getResponseData("uid");
			if(resEnv.getAPPException()!=null) {return "add";}			return "mod";
		}
		public String mod() {
			tablemx=new ArrayList<GROUPMX>();
			String[] ja = datagridstr.split(",");
			for(int i = 0; i < ja.length; i ++) {
				String data = ja[i];
				if(data==null || data.length()<2) {
					continue;
				}
				GROUPMX mx = new GROUPMX();
				String[] datas = data.split("~");
				if(datas.length<2)
					continue;
				mx.setORG_DM_JG(datas[3]);
				mx.setORG_DM_BM(datas[4]);
				mx.setORG_MC_JG(datas[5]);
				mx.setORG_MC_BM(datas[6]);
				mx.setSJHM(datas[2]);
				mx.setUSER_DM(datas[0]);
				mx.setUSER_MC(datas[1]);
				if(datas.length>7) {
					mx.setZW_DM(datas[7]);
				} else {
					mx.setZW_DM("");
				}
				if(datas.length>8) {
					mx.setZW_MC(datas[8]);
				} else {
					mx.setZW_MC("");
				}
				tablemx.add(mx);
			}
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("GROUP_LSH", GROUP_LSH);
			this.putRequestData("orggroup", orggroup);
			this.putRequestData("tablemx", tablemx);
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
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "OrgGroups");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}
	public IComplexPrintData getExcelPrintData() {
		pageBean=null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[2][2];
		attrs[0][0]=FIND_GROUP_NAME;attrs[0][1]=FIND_GROUP_NAME;
		attrs[1][0]=FIND_GROUP_COUNT;attrs[1][1]=FIND_GROUP_COUNT;
		int i = 0;
		for (Orggroup _orggroup: orggroups) {
			i ++;
			list.add(new CommonExcelPrintBean()
					.setObject(""+i)
					.setObject(_orggroup.getORG_DM_JG())
					.setObject(_orggroup.getGROUP_NAME())
					.setObject(_orggroup.getGROUP_COUNT())
					.setObject(_orggroup.getLRRY_DM())
					.setObject(_orggroup.getLR_SJ()));
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
		public void setOrggroup(Orggroup orggroup) {
				this.orggroup=orggroup;
		}
		public Orggroup getOrggroup(){
				return this.orggroup;
		}
		public void setOrggroups(List<Orggroup> orggroups) {
				this.orggroups=orggroups;
		}
		public List<Orggroup> getOrggroups(){
				return this.orggroups;
		}
		public void setGROUP_LSH(String GROUP_LSH) {
				this.GROUP_LSH=GROUP_LSH;
		}
		public String getGROUP_LSH(){
				return this.GROUP_LSH;
		}
		public void setFIND_GROUP_NAME(String FIND_GROUP_NAME) {
				this.FIND_GROUP_NAME=FIND_GROUP_NAME;
		}
		public String getFIND_GROUP_NAME(){
				return this.FIND_GROUP_NAME;
		}
		public void setFIND_GROUP_COUNT(String FIND_GROUP_COUNT) {
				this.FIND_GROUP_COUNT=FIND_GROUP_COUNT;
		}
		public String getFIND_GROUP_COUNT(){
				return this.FIND_GROUP_COUNT;
		}
		public Map getOrgmap() {
			return DMB.sortByKey(DMB.getDMB("ORG_CACHE", "ORG_MC"),true);
		}
		public void setOrgmap(Map orgmap) {
			this.orgmap = orgmap;
		}
		public List<GROUPMX> getTablemx(){
			return tablemx;
		}
		public void setTablemx(List<GROUPMX> tablemx){
			this.tablemx = tablemx;
		}
		public String getDatagridstr() {
			return datagridstr;
		}
		public void setDatagridstr(String datagridstr) {
			this.datagridstr = datagridstr;
		}
		
}
