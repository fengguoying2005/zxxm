package com.gwinsoft.components.nsrgl.nsrdxfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gwinsoft.components.common.CommonTool;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrgl.jbxx.Nsrjbxx;
import com.gwinsoft.components.nsrgl.nsrgroup.GROUPMX;
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

public class NsrdxAction extends BaseAction<BaseIAO> {
		private Nsrdx nsrdx;
		private List<Nsrdx> nsrdxs;
		private PageBean pageBean = new PageBean(10);
		private String NSRDATA_LSH;
		private String SHY;
		private String FIND_SMSTYPE_DM;
		private String FIND_GROUP_NAME;
		private String FIND_SMSZT_DM;
		private String FIND_KS_RQ;
		private String FIND_JZ_RQ;
		private Map smstypemap = null;
		private Map orgmap = null;
		private Map smsztmap = null;
		private List<GROUPMX> tablemx;
		private List<SFXCMSG> tablemx2;
		private Nsrjbxx groupmx;
		private SFXCMSG sfxcmsg;

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
		
		public String list(){
			User user = UserHelper.getLoginUserFromRequest(request);
			this.putRequestData("user", user);
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_SMSTYPE_DM", FIND_SMSTYPE_DM);
				putRequestData("FIND_GROUP_NAME", FIND_GROUP_NAME);
				putRequestData("FIND_SMSZT_DM", FIND_SMSZT_DM);
				putRequestData("FIND_KS_RQ", FIND_KS_RQ);
				putRequestData("FIND_JZ_RQ", FIND_JZ_RQ);
				this.invokeInteraction("list");
				nsrdxs = this.getResponseData("nsrdxs");
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
			List<SFXCMSG> msgs = this.getResponseData("tablemx");
			for(SFXCMSG msg : msgs) {
				jsonObject = new JSONObject();
				jsonObject.put("SFXCMSG_LSH", msg.getSFXCMSG_LSH()+","+msg.getNSR_LSH());
				jsonObject.put("NSRBM", msg.getNSRBM());
				jsonObject.put("NSRMC", msg.getNSRMC());
				jsonObject.put("SJHM", msg.getSJHM()==null?"":msg.getSJHM());
				jsonObject.put("MSG", msg.getMSG());
				
				jsonObject.put("SMSTYPE_DM", msg.getSMSTYPE_DM());
				jsonObject.put("FS_SJ", CommonTool.format20(msg.getFS_SJ()));
				jsonObject.put("FSCS", msg.getFSCS());
				jsonObject.put("PHONETYPE", msg.getPHONETYPE());
//				jsonObject.put("NSR_LSH", msg.getSFXCMSG_LSH());

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
		public String toAdd() {
			User user = UserHelper.getLoginUserFromRequest(request);
			nsrdx = new Nsrdx();
			nsrdx.setORG_DM_JG(user.getLRRY_DM());
			nsrdx.setSMSTYPE_DM("04");
			nsrdx.setSMSZT_DM("01");
			tablemx=new ArrayList<GROUPMX>();
			GROUPMX mx = new GROUPMX();
			tablemx.add(mx);
			return "add";
		}
		public String toMod() {
			see();
			return "mod";
		}
		public String SH() {
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
			this.putRequestData("SHY", SHY);
			this.invokeInteraction("SH");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String SH2() {
			mod();
			SH();
			return see();
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
			User user = UserHelper.getLoginUserFromRequest(request);
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
			this.invokeInteraction("see");
			nsrdx = this.getResponseData("nsrdx");
			nsrdx.setORG_DM_JG(user.getLRRY_DM());
			return "see";
		}
		public String add() {
			User user = UserHelper.getLoginUserFromRequest(request);
			nsrdx.setORG_DM_JG(user.getLRRY_DM());
			nsrdx.setSMSTYPE_DM("04");
			nsrdx.setSMSZT_DM("01");
			this.putRequestData("user", user);
			String rylx = request.getParameter("rylx");
			String xzfs = request.getParameter("xzfs");
			this.putRequestData("rylx", rylx);
			this.putRequestData("xzfs", xzfs);
			this.putRequestData("nsrdx", nsrdx);
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
			this.NSRDATA_LSH = this.getResponseData("uid");
			if (resEnv.getAPPException() != null) {
				return "add";
			}
			return "mod";
		}
		public String mod() {

			User user = UserHelper.getLoginUserFromRequest(request);
			nsrdx.setORG_DM_JG(user.getLRRY_DM());
			nsrdx.setSMSTYPE_DM("04");
			nsrdx.setSMSZT_DM("01");
			this.putRequestData("user", user);
			String rylx = request.getParameter("rylx");
			String xzfs = request.getParameter("xzfs");
			this.putRequestData("rylx", rylx);
			this.putRequestData("xzfs", xzfs);
			this.putRequestData("nsrdx", nsrdx);
			this.putRequestData("datas", datas);
			this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
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
			nsrdx.setORG_DM_JG(user.getLRRY_DM());
			return "mod";
		}
	public String printExcel() {
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY, "ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY, getExcelPrintData());
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "TaxpayerGroups");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}

	public IComplexPrintData getExcelPrintData() {
		pageBean = null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[3][2];
		attrs[0][0] = FIND_SMSTYPE_DM;
		attrs[0][1] = FIND_SMSTYPE_DM;
		attrs[1][0] = FIND_GROUP_NAME;
		attrs[1][1] = FIND_GROUP_NAME;
		attrs[2][0] = FIND_SMSZT_DM;
		attrs[2][1] = FIND_SMSZT_DM;
		int i = 1;
		for (Nsrdx _nsrdx : nsrdxs) {
			list.add(new CommonExcelPrintBean()
					.setObject(i ++)
					.setObject(_nsrdx.getSMSTYPE_DM())
					.setObject(_nsrdx.getGROUP_NAME())
					.setObject(_nsrdx.getORG_DM_JG())
					.setObject(_nsrdx.getSMSZT_DM())
					.setObject(_nsrdx.getLRRY_DM())
					.setObject(_nsrdx.getLR_SJ())
					);
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
		public void setNsrdx(Nsrdx nsrdx) {
				this.nsrdx=nsrdx;
		}
		public Nsrdx getNsrdx(){
				return this.nsrdx;
		}
		public void setNsrdxs(List<Nsrdx> nsrdxs) {
				this.nsrdxs=nsrdxs;
		}
		public List<Nsrdx> getNsrdxs(){
				return this.nsrdxs;
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
			return DMB.sortByKey(DMB.getDMB("ORG_CACHE", "ORG_MC"),true);
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
		public List<GROUPMX> getTablemx(){
			return tablemx;
		}
		public void setTablemx(List<GROUPMX> tablemx){
			this.tablemx = tablemx;
		}
		public String getFIND_JZ_RQ() {
			return FIND_JZ_RQ;
		}
		public void setFIND_JZ_RQ(String fIND_JZ_RQ) {
			FIND_JZ_RQ = fIND_JZ_RQ;
		}
		public Nsrjbxx getGroupmx() {
			return groupmx;
		}
		public void setGroupmx(Nsrjbxx groupmx) {
			this.groupmx = groupmx;
		}
		public String getFIND_KS_RQ() {
			return FIND_KS_RQ;
		}
		public void setFIND_KS_RQ(String fIND_KS_RQ) {
			FIND_KS_RQ = fIND_KS_RQ;
		}
		public List<SFXCMSG> getTablemx2() {
			return tablemx2;
		}
		public void setTablemx2(List<SFXCMSG> tablemx2) {
			this.tablemx2 = tablemx2;
		}
		public SFXCMSG getSfxcmsg() {
			return sfxcmsg;
		}
		public void setSfxcmsg(SFXCMSG sfxcmsg) {
			this.sfxcmsg = sfxcmsg;
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
		public String getSHY() {
			return SHY;
		}
		public void setSHY(String sHY) {
			SHY = sHY;
		}
		
}
