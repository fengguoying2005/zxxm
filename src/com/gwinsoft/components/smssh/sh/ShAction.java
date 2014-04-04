package com.gwinsoft.components.smssh.sh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gwinsoft.components.common.CommonTool;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrgl.nsrdxfs.SFXCMSG;
import com.gwinsoft.components.nsrsms.group.Group;
import com.gwinsoft.components.nsrsms.group.NSRDATAMX;
import com.gwinsoft.components.nsrsms.smssend.Nsrmsg;
import com.gwinsoft.components.orgsms.orgsmssend.ORGMSG;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
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

public class ShAction extends BaseAction<BaseIAO> {
		private Group group;
		private List<Group> groups;
		private PageBean pageBean = new PageBean(10);
		private String NSRDATA_LSH;
		private String FIND_SMSTYPE_DM;
		private String FIND_GROUP_NAME;
		private String FIND_SMSZT_DM;
		private String FIND_SMSZT_DM2;
		private String FIND_KS_RQ;
		private String FIND_JZ_RQ;
		
		private Map smstypemap = null;
		private Map orgmap = null;
		private Map smsztmap = null;
		private List<NSRDATAMX> tablemx;
		
		private Map dxmbmap;
		
		public String list(){
			if(FIND_SMSZT_DM2==null) {
				FIND_SMSZT_DM2 = "03";
			}
			User user = UserHelper.getLoginUserFromRequest(request);
			putRequestData("user", user);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_SMSTYPE_DM", FIND_SMSTYPE_DM);
				putRequestData("FIND_GROUP_NAME", FIND_GROUP_NAME);
				putRequestData("FIND_SMSZT_DM", FIND_SMSZT_DM2);
				putRequestData("FIND_KS_RQ", FIND_KS_RQ);
				putRequestData("FIND_JZ_RQ", FIND_JZ_RQ);
				this.invokeInteraction("list");
				groups = this.getResponseData("groups");
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
					jsonObject.put("CBRQQ", CommonTool.format10(msg.getCBRQQ()));
					jsonObject.put("CBRQZ", CommonTool.format10(msg.getCBRQZ()));
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
			if(group.getDXQM() == null || group.getDXQM().equals("")) {
				User user = UserHelper.getLoginUserFromRequest(request);
				Map map = DMB.getDMB("DXQM_CACHE", "DXQM_MC");
				group.setDXQM((String) map.get(user.getORG_DM_JG()));
			}
			return "mod";
		}
		public String del() {
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
			this.putRequestData("THYY", FIND_SMSZT_DM);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			FIND_SMSZT_DM = "";
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
			this.putRequestData("DXMB", group.getDXMB());
			this.putRequestData("DXMBINFO", group.getDXMBINFO());
			this.putRequestData("DXQM", group.getDXQM());
			this.invokeInteraction("mod");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return toMod();
		}
		public String gensms() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
			this.putRequestData("group", group);
			this.invokeInteraction("gensms");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return toMod();
		}
		public String send() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
			this.invokeInteraction("send");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
	public String printExcel() {
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY, "ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY, getExcelPrintData());
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "VerifyList");
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
			i++;
			list.add(new CommonExcelPrintBean()
					.setObject(""+i)
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
			Map map = new HashMap();
			map.put("03", "等待审核");
			map.put("04", "审核通过");
			map.put("06", "发送中");
			map.put("07", "已发送");
			return map;
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
		public Map getDxmbmap() {
			dxmbmap = new HashMap();
			User user = UserHelper.getLoginUserFromRequest(request);
			Map<String, Map<String, Object>> cache = CacheManager.getCache("DXMB_CACHE");
			Iterator<String> it = cache.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				if(user.getORG_DM_JG().equals(cache.get(key).get("ORG_DM_JG"))) {
					dxmbmap.put(key, cache.get(key).get("DXMB_MC"));
				}
			}
			return dxmbmap;
		}
		public void setDxmbmap(Map dxmbmap) {
			this.dxmbmap = dxmbmap;
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
		public String getFIND_SMSZT_DM2() {
			return FIND_SMSZT_DM2;
		}
		public void setFIND_SMSZT_DM2(String fIND_SMSZT_DM2) {
			FIND_SMSZT_DM2 = fIND_SMSZT_DM2;
		}
		
}
