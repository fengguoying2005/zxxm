package com.gwinsoft.components2.yw.xydz;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import net.sf.json.JSONObject;

import com.gwinsoft.components.dmb.DMB;
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

public class XydzAction extends BaseAction<BaseIAO> {
		private Xydz xydz;
		private List<Xydz> xydzs;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_NSRBM;
		private String FIND_NSRMC;
		private String FIND_ORG_DM;
		private String FIND_CBTX;
		private String FIND_CJTX;
		private String FIND_JKTX;
		private String FIND_JKSBTX;
		private String FIND_GMFPTX;
		private String FIND_TYDQTX;
		private String FIND_KTRQQ;
		private String FIND_KTRQZ;
		private String FIND_BZ;
		private Map orgmap = null;
		public String list(){
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_NSRBM", FIND_NSRBM);
				putRequestData("FIND_NSRMC", FIND_NSRMC);
				putRequestData("FIND_ORG_DM", FIND_ORG_DM);
				putRequestData("FIND_CBTX", FIND_CBTX);
				putRequestData("FIND_CJTX", FIND_CJTX);
				putRequestData("FIND_JKTX", FIND_JKTX);
				putRequestData("FIND_JKSBTX", FIND_JKSBTX);
				putRequestData("FIND_GMFPTX", FIND_GMFPTX);
				putRequestData("FIND_TYDQTX", FIND_TYDQTX);
				putRequestData("FIND_KTRQQ", FIND_KTRQQ);
				putRequestData("FIND_KTRQZ", FIND_KTRQZ);
				putRequestData("FIND_BZ", FIND_BZ);
				this.invokeInteraction("list");
				xydzs = this.getResponseData("xydzs");
				return "list";
		}
		public String loadNsr() {
			JSONObject json = new JSONObject();
			String NSRBM = request.getParameter("NSRBM");

			this.putRequestData("NSRBM", NSRBM);
			this.invokeInteraction("loadNsr");
			json.put("NSRBM", getResponseData("NSRBM"));
			json.put("NSRMC", getResponseData("NSRMC"));
			json.put("ORG_DM", getResponseData("ORG_DM"));
			json.put("message", getResponseData("message"));
			try {
				response.setContentType("text/html; charset=utf-8");
				response.getWriter().print(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		public String toAdd() {
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
			xydz = this.getResponseData("xydz");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("xydz", xydz);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.LSH = this.getResponseData("uid");
			if(resEnv.getAPPException()!=null) {return "add";}
			see();
			return "mod";
		}
		public String mod() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("LSH", LSH);
			this.putRequestData("xydz", xydz);
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
		String[][] attrs = new String[12][2];
		attrs[0][0]=FIND_NSRBM;attrs[0][1]=FIND_NSRBM;
		attrs[1][0]=FIND_NSRMC;attrs[1][1]=FIND_NSRMC;
		attrs[2][0]=FIND_ORG_DM;attrs[2][1]=FIND_ORG_DM;
		attrs[3][0]=FIND_CBTX;attrs[3][1]=FIND_CBTX;
		attrs[4][0]=FIND_CJTX;attrs[4][1]=FIND_CJTX;
		attrs[5][0]=FIND_JKTX;attrs[5][1]=FIND_JKTX;
		attrs[6][0]=FIND_JKSBTX;attrs[6][1]=FIND_JKSBTX;
		attrs[7][0]=FIND_GMFPTX;attrs[7][1]=FIND_GMFPTX;
		attrs[8][0]=FIND_TYDQTX;attrs[8][1]=FIND_TYDQTX;
		attrs[9][0]=FIND_KTRQQ;attrs[9][1]=FIND_KTRQQ;
		attrs[10][0]=FIND_KTRQZ;attrs[10][1]=FIND_KTRQZ;
		attrs[11][0]=FIND_BZ;attrs[11][1]=FIND_BZ;
		for (Xydz _xydz: xydzs) {
			list.add(new CommonExcelPrintBean().setObject(_xydz.getLSH()).setObject(_xydz.getNSRBM()).setObject(_xydz.getNSRMC()).setObject(_xydz.getORG_DM()).setObject(_xydz.getCBTX()).setObject(_xydz.getCJTX()).setObject(_xydz.getJKTX()).setObject(_xydz.getJKSBTX()).setObject(_xydz.getGMFPTX()).setObject(_xydz.getTYDQTX()).setObject(_xydz.getKTRQ_Q()).setObject(_xydz.getKTRQQ()).setObject(_xydz.getKTRQ_Z()).setObject(_xydz.getKTRQZ()).setObject(_xydz.getBZ()).setObject(_xydz.getLRRY_DM()).setObject(_xydz.getXGRY_DM()).setObject(_xydz.getLR_SJ()).setObject(_xydz.getXG_SJ()));
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
		public void setXydz(Xydz xydz) {
				this.xydz=xydz;
		}
		public Xydz getXydz(){
				return this.xydz;
		}
		public void setXydzs(List<Xydz> xydzs) {
				this.xydzs=xydzs;
		}
		public List<Xydz> getXydzs(){
				return this.xydzs;
		}
		public void setLSH(String LSH) {
				this.LSH=LSH;
		}
		public String getLSH(){
				return this.LSH;
		}
		public void setFIND_NSRBM(String FIND_NSRBM) {
				this.FIND_NSRBM=FIND_NSRBM;
		}
		public String getFIND_NSRBM(){
				return this.FIND_NSRBM;
		}
		public void setFIND_NSRMC(String FIND_NSRMC) {
				this.FIND_NSRMC=FIND_NSRMC;
		}
		public String getFIND_NSRMC(){
				return this.FIND_NSRMC;
		}
		public void setFIND_ORG_DM(String FIND_ORG_DM) {
				this.FIND_ORG_DM=FIND_ORG_DM;
		}
		public String getFIND_ORG_DM(){
				return this.FIND_ORG_DM;
		}
		public void setFIND_CBTX(String FIND_CBTX) {
				this.FIND_CBTX=FIND_CBTX;
		}
		public String getFIND_CBTX(){
				return this.FIND_CBTX;
		}
		public void setFIND_CJTX(String FIND_CJTX) {
				this.FIND_CJTX=FIND_CJTX;
		}
		public String getFIND_CJTX(){
				return this.FIND_CJTX;
		}
		public void setFIND_JKTX(String FIND_JKTX) {
				this.FIND_JKTX=FIND_JKTX;
		}
		public String getFIND_JKTX(){
				return this.FIND_JKTX;
		}
		public void setFIND_JKSBTX(String FIND_JKSBTX) {
				this.FIND_JKSBTX=FIND_JKSBTX;
		}
		public String getFIND_JKSBTX(){
				return this.FIND_JKSBTX;
		}
		public void setFIND_GMFPTX(String FIND_GMFPTX) {
				this.FIND_GMFPTX=FIND_GMFPTX;
		}
		public String getFIND_GMFPTX(){
				return this.FIND_GMFPTX;
		}
		public void setFIND_TYDQTX(String FIND_TYDQTX) {
				this.FIND_TYDQTX=FIND_TYDQTX;
		}
		public String getFIND_TYDQTX(){
				return this.FIND_TYDQTX;
		}
		public void setFIND_KTRQQ(String FIND_KTRQQ) {
				this.FIND_KTRQQ=FIND_KTRQQ;
		}
		public String getFIND_KTRQQ(){
				return this.FIND_KTRQQ;
		}
		public void setFIND_KTRQZ(String FIND_KTRQZ) {
				this.FIND_KTRQZ=FIND_KTRQZ;
		}
		public String getFIND_KTRQZ(){
				return this.FIND_KTRQZ;
		}
		public void setFIND_BZ(String FIND_BZ) {
				this.FIND_BZ=FIND_BZ;
		}
		public String getFIND_BZ(){
				return this.FIND_BZ;
		}
		public Map getOrgmap() {
			return DMB.sortByKey(DMB.getDMB("ORG_CACHE", "ORG_MC"),true);
		}
		public void setOrgmap(Map orgmap) {
			this.orgmap = orgmap;
		}
}
