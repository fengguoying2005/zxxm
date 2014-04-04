package com.gwinsoft.components2.yw.fpzj;

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

public class FpzjcxAction extends BaseAction<BaseIAO> {
		private Fpzjcx fpzjcx;
		private List<Fpzjcx> fpzjcxs;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_SSX;
		private String FIND_KJNY;
		private String FIND_SJHM;
		private String FIND_LR_SJ;
		private String FIND_LR_SJ2;
		private Map sjsxmap = null;
		private Map fsztmap = null;
		public String list(){
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_SSX", FIND_SSX);
				putRequestData("FIND_KJNY", FIND_KJNY);
				putRequestData("FIND_SJHM", FIND_SJHM);
				putRequestData("FIND_LR_SJ", FIND_LR_SJ);
				putRequestData("FIND_LR_SJ2", FIND_LR_SJ2);
				this.invokeInteraction("list");
				fpzjcxs = this.getResponseData("fpzjcxs");
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
			fpzjcx = this.getResponseData("fpzjcx");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("fpzjcx", fpzjcx);
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
			this.putRequestData("fpzjcx", fpzjcx);
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
		String[][] attrs = new String[5][2];
		attrs[0][0]=FIND_SSX;attrs[0][1]=FIND_SSX;
		attrs[1][0]=FIND_KJNY;attrs[1][1]=FIND_KJNY;
		attrs[2][0]=FIND_SJHM;attrs[2][1]=FIND_SJHM;
		attrs[3][0]=FIND_LR_SJ;attrs[3][1]=FIND_LR_SJ;
		attrs[4][0]=FIND_LR_SJ2;attrs[4][1]=FIND_LR_SJ2;
		for (Fpzjcx _fpzjcx: fpzjcxs) {
			list.add(new CommonExcelPrintBean().setObject(_fpzjcx.getLSH()).setObject(_fpzjcx.getSSX()).setObject(_fpzjcx.getKJNY()).setObject(_fpzjcx.getSJHM()).setObject(_fpzjcx.getLR_SJ()).setObject(_fpzjcx.getLR_SJ2()).setObject(_fpzjcx.getFS_SJ()).setObject(_fpzjcx.getFSZT_DM()).setObject(_fpzjcx.getSMSINFO()).setObject(_fpzjcx.getSMSINFO2()));
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
		public void setFpzjcx(Fpzjcx fpzjcx) {
				this.fpzjcx=fpzjcx;
		}
		public Fpzjcx getFpzjcx(){
				return this.fpzjcx;
		}
		public void setFpzjcxs(List<Fpzjcx> fpzjcxs) {
				this.fpzjcxs=fpzjcxs;
		}
		public List<Fpzjcx> getFpzjcxs(){
				return this.fpzjcxs;
		}
		public void setLSH(String LSH) {
				this.LSH=LSH;
		}
		public String getLSH(){
				return this.LSH;
		}
		public void setFIND_SSX(String FIND_SSX) {
				this.FIND_SSX=FIND_SSX;
		}
		public String getFIND_SSX(){
				return this.FIND_SSX;
		}
		public void setFIND_KJNY(String FIND_KJNY) {
				this.FIND_KJNY=FIND_KJNY;
		}
		public String getFIND_KJNY(){
				return this.FIND_KJNY;
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
		public Map getSjsxmap() {
			return DMB.sortByKey(DMB.getDMB("SJSX_CACHE", "SJSX_MC"),true);
		}
		public void setSjsxmap(Map sjsxmap) {
			this.sjsxmap = sjsxmap;
		}
		public Map getFsztmap() {
			return DMB.sortByKey(DMB.getDMB("SMSZT_CACHE", "SMSZT_MC"),true);
		}
		public void setFsztmap(Map fsztmap) {
			this.fsztmap = fsztmap;
		}
}
