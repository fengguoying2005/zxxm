package com.gwinsoft.components2.yw.fpzw;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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

public class FpzwcxAction extends BaseAction<BaseIAO> {
		private Fpzwcx fpzwcx;
		private List<Fpzwcx> fpzwcxs;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_SWJGBM;
		private String FIND_NSRSBM;
		private String FIND_NSRMC;
		private String FIND_SJHM;
		private String FIND_LR_SJ;
		private String FIND_LR_SJ2;
		private Map orgmap = null;
		private Map fsztmap = null;
		public String list(){
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_SWJGBM", FIND_SWJGBM);
				putRequestData("FIND_NSRSBM", FIND_NSRSBM);
				putRequestData("FIND_NSRMC", FIND_NSRMC);
				putRequestData("FIND_SJHM", FIND_SJHM);
				putRequestData("FIND_LR_SJ", FIND_LR_SJ);
				putRequestData("FIND_LR_SJ2", FIND_LR_SJ2);
				this.invokeInteraction("list");
				fpzwcxs = this.getResponseData("fpzwcxs");
				return "list";
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
			fpzwcx = this.getResponseData("fpzwcx");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("fpzwcx", fpzwcx);
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
			this.putRequestData("fpzwcx", fpzwcx);
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
		String[][] attrs = new String[6][2];
		attrs[0][0]=FIND_SWJGBM;attrs[0][1]=FIND_SWJGBM;
		attrs[1][0]=FIND_NSRSBM;attrs[1][1]=FIND_NSRSBM;
		attrs[2][0]=FIND_NSRMC;attrs[2][1]=FIND_NSRMC;
		attrs[3][0]=FIND_SJHM;attrs[3][1]=FIND_SJHM;
		attrs[4][0]=FIND_LR_SJ;attrs[4][1]=FIND_LR_SJ;
		attrs[5][0]=FIND_LR_SJ2;attrs[5][1]=FIND_LR_SJ2;
		for (Fpzwcx _fpzwcx: fpzwcxs) {
			list.add(new CommonExcelPrintBean().setObject(_fpzwcx.getLSH()).setObject(_fpzwcx.getSWJGBM()).setObject(_fpzwcx.getNSRSBM()).setObject(_fpzwcx.getNSRMC()).setObject(_fpzwcx.getSJHM()).setObject(_fpzwcx.getFPZL()).setObject(_fpzwcx.getFPDM()).setObject(_fpzwcx.getFPHM()).setObject(_fpzwcx.getFP_RQ()).setObject(_fpzwcx.getFP_JE()).setObject(_fpzwcx.getSMSINFO()).setObject(_fpzwcx.getSMSINFO2()).setObject(_fpzwcx.getLR_SJ()).setObject(_fpzwcx.getLR_SJ2()).setObject(_fpzwcx.getFS_SJ()).setObject(_fpzwcx.getFSZT_DM()).setObject(_fpzwcx.getBZ()));
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
		public void setFpzwcx(Fpzwcx fpzwcx) {
				this.fpzwcx=fpzwcx;
		}
		public Fpzwcx getFpzwcx(){
				return this.fpzwcx;
		}
		public void setFpzwcxs(List<Fpzwcx> fpzwcxs) {
				this.fpzwcxs=fpzwcxs;
		}
		public List<Fpzwcx> getFpzwcxs(){
				return this.fpzwcxs;
		}
		public void setLSH(String LSH) {
				this.LSH=LSH;
		}
		public String getLSH(){
				return this.LSH;
		}
		public void setFIND_SWJGBM(String FIND_SWJGBM) {
				this.FIND_SWJGBM=FIND_SWJGBM;
		}
		public String getFIND_SWJGBM(){
				return this.FIND_SWJGBM;
		}
		public void setFIND_NSRSBM(String FIND_NSRSBM) {
				this.FIND_NSRSBM=FIND_NSRSBM;
		}
		public String getFIND_NSRSBM(){
				return this.FIND_NSRSBM;
		}
		public void setFIND_NSRMC(String FIND_NSRMC) {
				this.FIND_NSRMC=FIND_NSRMC;
		}
		public String getFIND_NSRMC(){
				return this.FIND_NSRMC;
		}
		public void setFIND_SJHM(String FIND_SJHM) {
				this.FIND_SJHM=FIND_SJHM;
		}
		public String getFIND_SJHM(){
				return this.FIND_SJHM;
		}
		public void setFIND_LR_SJ(String FIND_LR_SJ) {
				this.FIND_LR_SJ=FIND_LR_SJ;
		}
		public String getFIND_LR_SJ(){
				return this.FIND_LR_SJ;
		}
		public void setFIND_LR_SJ2(String FIND_LR_SJ2) {
				this.FIND_LR_SJ2=FIND_LR_SJ2;
		}
		public String getFIND_LR_SJ2(){
				return this.FIND_LR_SJ2;
		}
		public Map getOrgmap() {
			return DMB.sortByKey(DMB.getDMB("ORG_CACHE", "ORG_MC"),true);
		}
		public void setOrgmap(Map orgmap) {
			this.orgmap = orgmap;
		}
		public Map getFsztmap() {
			return DMB.sortByKey(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC"),true);
		}
		public void setFsztmap(Map fsztmap) {
			this.fsztmap = fsztmap;
		}
}
