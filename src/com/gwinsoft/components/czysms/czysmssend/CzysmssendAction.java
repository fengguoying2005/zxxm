package com.gwinsoft.components.czysms.czysmssend;

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
import com.gwinsoft.util.GwinSoftUtil;

public class CzysmssendAction extends BaseAction<BaseIAO> {
	private Czysmssend orgsmssend;
	private List<Czysmssend> orgsmssends;
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
	private List<CZYMSG> tablemx;
	private String datagridstr;

	public String list() {
		this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
		this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
		putRequestData("pageBean", pageBean);
		putRequestData("FIND_SMSTYPE_DM", FIND_SMSTYPE_DM);
		putRequestData("FIND_GROUP_NAME", FIND_GROUP_NAME);
		putRequestData("FIND_SMSZT_DM", FIND_SMSZT_DM);
		putRequestData("FIND_KS_RQ", FIND_KS_RQ);
		putRequestData("FIND_JZ_RQ", FIND_JZ_RQ);
		this.invokeInteraction("list");
		orgsmssends = this.getResponseData("orgsmssends");
		return "list";
	}

	public String deletemx() {
		String KEYIDS = request.getParameter("KEYIDS");
		String NSRDATA_LSH = request.getParameter("NSRDATA_LSH");
		this.putRequestData("KEYIDS", KEYIDS);
		this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
		this.invokeInteraction("deletemx");
		return null;
	}
	public String loadgroup() {
		String NSRDATA_LSH = request.getParameter("NSRDATA_LSH");
		this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);

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

		tablemx = this.getResponseData("tablemx");

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		for (CZYMSG data : tablemx) {
			jsonObject = new JSONObject();
			jsonObject.put("NSRDATAMX_LSH", data.getNSRDATAMX_LSH());
			jsonObject.put("ORG_DM_JG", data.getORG_DM_JG());
			jsonObject.put("ORG_DM_BM", data.getORG_DM_BM());
			jsonObject.put("ORG_MC_JG", data.getORG_MC_JG());
			jsonObject.put("ORG_MC_BM", data.getORG_MC_BM());
			jsonObject.put("SJHM", data.getSJHM());
			jsonObject.put("USER_DM", data.getUSER_DM());
			jsonObject.put("USER_MC", data.getUSER_MC());
			jsonObject.put("ZW_DM",data.getZW_DM()==null?"":data.getZW_DM());
			jsonObject.put("ZW_MC",data.getZW_MC()==null?"":data.getZW_MC());
			jsonObject.put("MSG", data.getMSG());
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
		if (orgsmssend == null) {
			orgsmssend = new Czysmssend();
		}
		User user = UserHelper.getLoginUserFromRequest(request);
		orgsmssend.setORG_DM_JG(user.getORG_DM_JG());
		orgsmssend.setSMSTYPE_DM("05");
		orgsmssend.setSMSZT_DM("01");
		
		orgsmssend.setCALLBACK("1");
		String date = GwinSoftUtil.getSysFormatDate("yyyyMMdd");
		String CALLNO = date;
		String NO = GwinSoftUtil.getYWLSHXL(date);
		if(NO!=null) {
			CALLNO = "6"+CALLNO+NO;
		}
		orgsmssend.setDXQM(CALLNO);
		
		tablemx = new ArrayList<CZYMSG>();
		CZYMSG mx = new CZYMSG();
		tablemx.add(mx);
		return "add";
	}

	public String toMod() {
		see();
//		orgsmssend.setCALLBACK("1");
//		String date = GwinSoftUtil.getSysFormatDate("yyyyMMdd");
//		String CALLNO = date;
//		String NO = GwinSoftUtil.getYWLSHXL(date);
//		if(NO!=null) {
//			CALLNO = CALLNO+NO;
//		}
		return "mod";
	}

	public String del() {
		this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
		this.invokeInteraction("del");
		if (this.getResponseData("message") != null) {
			String message = this.getResponseData("message");
			request.setAttribute("message", message);
		}
		return list();
	}

	public String see() {
		this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
		this.invokeInteraction("see");
		tablemx = this.getResponseData("tablemx");
		orgsmssend = this.getResponseData("orgsmssend");
		return "see";
	}

	public String seecallback() {
		this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
		this.invokeInteraction("seecallback");
		tablemx = this.getResponseData("tablemx");
		orgsmssend = this.getResponseData("orgsmssend");
		return "seecallback";
	}
	public String add() {

		tablemx = new ArrayList<CZYMSG>();
		String[] ja = datagridstr.split(",");
		for(int i = 0; i < ja.length; i ++) {
			String data = ja[i];
			if(data==null || data.length()<2) {
				continue;
			}
			
			CZYMSG mx = new CZYMSG();
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
		this.putRequestData("orgsmssend", orgsmssend);
		this.putRequestData("tablemx", tablemx);
		BaseResponseEnvelope resEnv = this.invokeInteraction("add");
		if (this.getResponseData("message") != null) {
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

		tablemx = new ArrayList<CZYMSG>();
		String[] ja = datagridstr.split(",");
		for(int i = 0; i < ja.length; i ++) {
			String data = ja[i];
			if(data==null || data.length()<2) {
				continue;
			}
			
			CZYMSG mx = new CZYMSG();
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
		this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
		this.putRequestData("orgsmssend", orgsmssend);
		this.putRequestData("tablemx", tablemx);
		this.invokeInteraction("mod");
		if (this.getResponseData("message") != null) {
			String message = this.getResponseData("message");
			request.setAttribute("message", message);
		}
		return "mod";
	}

	public String send() {
		this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
		this.putRequestData("NSRDATA_LSH", NSRDATA_LSH);
		this.putRequestData("SHY", SHY);
		this.invokeInteraction("send");
		if (this.getResponseData("message") != null) {
			String message = this.getResponseData("message");
			request.setAttribute("message", message);
		}
		return list();
	}
	public String SH2() {
		mod();
		send();
		return see();
	}

	public String printExcel() {
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY,
				"ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY,
				getExcelPrintData());
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY,
				"SendGroupList");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY,
				PrintServiceConstants.PRINT_TYPE_EXCEL);
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
		int i = 0;
		for (Czysmssend _orgsmssend : orgsmssends) {
			i++;
			list.add(new CommonExcelPrintBean().setObject("" + i)
					.setObject(_orgsmssend.getSMSTYPE_DM())
					.setObject(_orgsmssend.getGROUP_NAME())
					.setObject(_orgsmssend.getORG_DM_JG())
					.setObject(_orgsmssend.getSMSZT_DM())
					.setObject(_orgsmssend.getLRRY_DM())
					.setObject(_orgsmssend.getLR_SJ()));
		}
		CommonExcelPrintData data = new CommonExcelPrintData(attrs, list, 2);
		return data;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public PageBean getPageBean() {
		return this.pageBean;
	}

	public void setCzysmssend(Czysmssend orgsmssend) {
		this.orgsmssend = orgsmssend;
	}

	public Czysmssend getCzysmssend() {
		return this.orgsmssend;
	}

	public void setCzysmssends(List<Czysmssend> orgsmssends) {
		this.orgsmssends = orgsmssends;
	}

	public List<Czysmssend> getCzysmssends() {
		return this.orgsmssends;
	}

	public void setNSRDATA_LSH(String NSRDATA_LSH) {
		this.NSRDATA_LSH = NSRDATA_LSH;
	}

	public String getNSRDATA_LSH() {
		return this.NSRDATA_LSH;
	}

	public void setFIND_SMSTYPE_DM(String FIND_SMSTYPE_DM) {
		this.FIND_SMSTYPE_DM = FIND_SMSTYPE_DM;
	}

	public String getFIND_SMSTYPE_DM() {
		return this.FIND_SMSTYPE_DM;
	}

	public void setFIND_GROUP_NAME(String FIND_GROUP_NAME) {
		this.FIND_GROUP_NAME = FIND_GROUP_NAME;
	}

	public String getFIND_GROUP_NAME() {
		return this.FIND_GROUP_NAME;
	}

	public void setFIND_SMSZT_DM(String FIND_SMSZT_DM) {
		this.FIND_SMSZT_DM = FIND_SMSZT_DM;
	}

	public String getFIND_SMSZT_DM() {
		return this.FIND_SMSZT_DM;
	}

	public Map getSmstypemap() {
		return DMB.sortByKey(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC"), true);
	}

	public void setSmstypemap(Map smstypemap) {
		this.smstypemap = smstypemap;
	}

	public Map getOrgmap() {
		return DMB.sortByKey(DMB.getDMB("ORG_CACHE", "ORG_MC"), true);
	}

	public void setOrgmap(Map orgmap) {
		this.orgmap = orgmap;
	}

	public Map getSmsztmap() {
		return DMB.sortByKey(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC"), true);
	}

	public void setSmsztmap(Map smsztmap) {
		this.smsztmap = smsztmap;
	}

	public List<CZYMSG> getTablemx() {
		return tablemx;
	}

	public void setTablemx(List<CZYMSG> tablemx) {
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

	public String getDatagridstr() {
		return datagridstr;
	}

	public void setDatagridstr(String datagridstr) {
		this.datagridstr = datagridstr;
	}

	public String getSHY() {
		return SHY;
	}

	public void setSHY(String sHY) {
		SHY = sHY;
	}

	public Czysmssend getOrgsmssend() {
		return orgsmssend;
	}

	public void setOrgsmssend(Czysmssend orgsmssend) {
		this.orgsmssend = orgsmssend;
	}

	public List<Czysmssend> getOrgsmssends() {
		return orgsmssends;
	}

	public void setOrgsmssends(List<Czysmssend> orgsmssends) {
		this.orgsmssends = orgsmssends;
	}

}
