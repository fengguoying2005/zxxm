package com.gwinsoft.components2.yw.kksbtx;

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

public class KksbtxAction extends BaseAction<BaseIAO> {
		private Kksbtx kksbtx;
		private List<Kksbtx> kksbtxs;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_SWJGBM;
		private String FIND_NSRSBM;
		private String FIND_NSRMC;
		private String FIND_SJLX;
		private String FIND_KS_RQ;
		private String FIND_JZ_RQ;
		private Map orgmap = null;
		private Map jsrlxmap = null;
		public String list(){
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_SWJGBM", FIND_SWJGBM);
				putRequestData("FIND_NSRSBM", FIND_NSRSBM);
				putRequestData("FIND_NSRMC", FIND_NSRMC);
				putRequestData("FIND_SJLX", FIND_SJLX);
				putRequestData("FIND_KS_RQ", FIND_KS_RQ);
				putRequestData("FIND_JZ_RQ", FIND_JZ_RQ);
				this.invokeInteraction("list");
				kksbtxs = this.getResponseData("kksbtxs");
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
			kksbtx = this.getResponseData("kksbtx");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("kksbtx", kksbtx);
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
			this.putRequestData("kksbtx", kksbtx);
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
		attrs[0][0]=FIND_SWJGBM;attrs[0][1]=FIND_SWJGBM;
		attrs[1][0]=FIND_NSRSBM;attrs[1][1]=FIND_NSRSBM;
		attrs[2][0]=FIND_NSRMC;attrs[2][1]=FIND_NSRMC;
		attrs[3][0]=FIND_SJLX;attrs[3][1]=FIND_SJLX;
		for (Kksbtx _kksbtx: kksbtxs) {
			list.add(new CommonExcelPrintBean().setObject(_kksbtx.getLSH()).setObject(_kksbtx.getSWJGBM()).setObject(_kksbtx.getNSRSBM()).setObject(_kksbtx.getNSRMC()).setObject(_kksbtx.getSJLX()).setObject(_kksbtx.getSJHM()).setObject(_kksbtx.getSMSINFO()).setObject(_kksbtx.getSKSSQ()).setObject(_kksbtx.getSKSSZ()).setObject(_kksbtx.getJKQX_RQ()).setObject(_kksbtx.getBZ()).setObject(_kksbtx.getFSZT_DM()).setObject(_kksbtx.getLR_SJ()).setObject(_kksbtx.getFS_SJ()).setObject(_kksbtx.getYZPZXH()).setObject(_kksbtx.getSF_JE()));
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
		public void setKksbtx(Kksbtx kksbtx) {
				this.kksbtx=kksbtx;
		}
		public Kksbtx getKksbtx(){
				return this.kksbtx;
		}
		public void setKksbtxs(List<Kksbtx> kksbtxs) {
				this.kksbtxs=kksbtxs;
		}
		public List<Kksbtx> getKksbtxs(){
				return this.kksbtxs;
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
		public void setFIND_SJLX(String FIND_SJLX) {
				this.FIND_SJLX=FIND_SJLX;
		}
		public String getFIND_SJLX(){
				return this.FIND_SJLX;
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
		public Map getOrgmap() {
			return DMB.sortByKey(DMB.getDMB("ORG_CACHE", "ORG_MC"),true);
		}
		public void setOrgmap(Map orgmap) {
			this.orgmap = orgmap;
		}
		public Map getJsrlxmap() {
			return DMB.sortByKey(DMB.getDMB("JSRLX_CACHE", "JSRLX_MC"),true);
		}
		public void setJsrlxmap(Map jsrlxmap) {
			this.jsrlxmap = jsrlxmap;
		}
}
