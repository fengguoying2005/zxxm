package com.gwinsoft.components2.yw.wjdc;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gwinsoft.components.common.CommonTool;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrgl.nsrdxfs.SFXCMSG;
import com.gwinsoft.components2.job.JobUtils;
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

public class WjdcAction extends BaseAction<BaseIAO> {
		private Wjdc wjdc;
		private List<Wjdc> wjdcs;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_INFO;
		private String FIND_BZ;
		private String FIND_SFJS;
		private String FIND_DCJL;
		public String list(){
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_INFO", FIND_INFO);
				putRequestData("FIND_BZ", FIND_BZ);
				putRequestData("FIND_SFJS", FIND_SFJS);
				putRequestData("FIND_DCJL", FIND_DCJL);
				this.invokeInteraction("list");
				wjdcs = this.getResponseData("wjdcs");
				return "list";
		}
		public String toAdd() {
			return "add";
		}
		public String toMod() {
			see();
			return "mod";
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
			List<Wjdcmx> msgs = this.getResponseData("tablemx");
			if(msgs!=null)
			for(Wjdcmx msg : msgs) {
				jsonObject = new JSONObject();
				jsonObject.put("LSH", msg.getLSH());
				jsonObject.put("NSRBM", msg.getNSRBM());
				jsonObject.put("NSRMC", msg.getNSRMC());
				jsonObject.put("SJHM", msg.getSJHM()==null?"":msg.getSJHM());
				jsonObject.put("PHONETYPE", msg.getPHONETYPE());
				jsonObject.put("SMSINFO", msg.getSMSINFO()==null?"":msg.getSMSINFO());
				jsonObject.put("FK_SJ", msg.getFK_SJ()==null?"":(JobUtils.getFormatDate(msg.getFK_SJ(), "yyyy-MM-dd HH:mm:ss")));
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
		public String addmx() {
			String rylx = request.getParameter("rylx");
			String xzfs = request.getParameter("xzfs");
			this.putRequestData("LSH", LSH);
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("datas", datas);
			this.putRequestData("rylx", rylx);
			this.putRequestData("xzfs", xzfs);
			this.invokeInteraction("addmx");
			String message = null;
			if(this.getResponseData("message")!=null) {
				message = this.getResponseData("message");
			}
			see();
			if(message!=null) {
				request.setAttribute("message", message);
			}
			return "send";
		}
		public String deletemx() {
			String KEYIDS = request.getParameter("KEYIDS");
			String LSH = request.getParameter("LSH");
			this.putRequestData("KEYIDS", KEYIDS);
			this.putRequestData("LSH", LSH);
			this.invokeInteraction("deletemx");
			String message = null;
			if(this.getResponseData("message")!=null) {
				message = this.getResponseData("message");
			}
			see();
			if(message!=null) {
				request.setAttribute("message", message);
			}
			return "send";
		}
		public String send() {
			see();
			String sfjs = wjdc.getSFJS();
			if("1".equals(sfjs)) {
				request.setAttribute("message", "问卷调查已结束不能再次发送。");
				return list();
			} else if("2".equals(sfjs)) {
				request.setAttribute("message", "问卷调查已发送不能再次发送。");
				return list();
			} else {
				return "send";
			}
		}
		public String doSend() {
			String rylx = request.getParameter("rylx");
			String xzfs = request.getParameter("xzfs");
			this.putRequestData("LSH", LSH);
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("datas", datas);
			this.putRequestData("rylx", rylx);
			this.putRequestData("xzfs", xzfs);
			this.invokeInteraction("send");
			String message = null;
			if(this.getResponseData("message")!=null) {
				message = this.getResponseData("message");
			}
			see();
			if(message!=null) {
				request.setAttribute("message", message);
			}
			return "send";
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
			wjdc = this.getResponseData("wjdc");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("wjdc", wjdc);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.LSH = this.getResponseData("uid");
			if(resEnv.getAPPException()!=null) {return "add";}			return "mod";
		}
		public String mod() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("LSH", LSH);
			this.putRequestData("wjdc", wjdc);
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
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "ComplexExcelPrintTest");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}
	public IComplexPrintData getExcelPrintData() {
   pageBean=null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[4][2];
		attrs[0][0]=FIND_INFO;attrs[0][1]=FIND_INFO;
		attrs[1][0]=FIND_BZ;attrs[1][1]=FIND_BZ;
		attrs[2][0]=FIND_SFJS;attrs[2][1]=FIND_SFJS;
		attrs[3][0]=FIND_DCJL;attrs[3][1]=FIND_DCJL;
		for (Wjdc _wjdc: wjdcs) {
			list.add(new CommonExcelPrintBean().setObject(_wjdc.getLSH()).setObject(_wjdc.getINFO()).setObject(_wjdc.getBZ()).setObject(_wjdc.getSFJS()).setObject(_wjdc.getDCJL()).setObject(_wjdc.getLRRY_DM()).setObject(_wjdc.getXGRY_DM()).setObject(_wjdc.getLR_SJ()).setObject(_wjdc.getXG_SJ()));
		}
		CommonExcelPrintData data = new CommonExcelPrintData(attrs, list, 5);
		return data;
	}
		public void setPageBean(PageBean pageBean) {
				this.pageBean=pageBean;
		}
		public PageBean getPageBean(){
				return this.pageBean;
		}
		public void setWjdc(Wjdc wjdc) {
				this.wjdc=wjdc;
		}
		public Wjdc getWjdc(){
				return this.wjdc;
		}
		public void setWjdcs(List<Wjdc> wjdcs) {
				this.wjdcs=wjdcs;
		}
		public List<Wjdc> getWjdcs(){
				return this.wjdcs;
		}
		public void setLSH(String LSH) {
				this.LSH=LSH;
		}
		public String getLSH(){
				return this.LSH;
		}
		public void setFIND_INFO(String FIND_INFO) {
				this.FIND_INFO=FIND_INFO;
		}
		public String getFIND_INFO(){
				return this.FIND_INFO;
		}
		public void setFIND_BZ(String FIND_BZ) {
				this.FIND_BZ=FIND_BZ;
		}
		public String getFIND_BZ(){
				return this.FIND_BZ;
		}
		public void setFIND_SFJS(String FIND_SFJS) {
				this.FIND_SFJS=FIND_SFJS;
		}
		public String getFIND_SFJS(){
				return this.FIND_SFJS;
		}
		public void setFIND_DCJL(String FIND_DCJL) {
				this.FIND_DCJL=FIND_DCJL;
		}
		public String getFIND_DCJL(){
				return this.FIND_DCJL;
		}

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
}
