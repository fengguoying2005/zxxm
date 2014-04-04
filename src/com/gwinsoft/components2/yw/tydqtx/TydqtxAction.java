package com.gwinsoft.components2.yw.tydqtx;

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

public class TydqtxAction extends BaseAction<BaseIAO> {
		private Tydqtx tydqtx;
		private List<Tydqtx> tydqtxs;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_SWJGBM;
		private String FIND_NSRSBM;
		private String FIND_NSRMC;
		private String FIND_SJLX;
		private String FIND_TY_RQ;
		private String FIND_TYDQ_RQ;
		private Map orgmap = null;
		private Map jsrlxmap = null;
		public String list(){
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_SWJGBM", FIND_SWJGBM);
				putRequestData("FIND_NSRSBM", FIND_NSRSBM);
				putRequestData("FIND_NSRMC", FIND_NSRMC);
				putRequestData("FIND_SJLX", FIND_SJLX);
				putRequestData("FIND_TY_RQ", FIND_TY_RQ);
				putRequestData("FIND_TYDQ_RQ", FIND_TYDQ_RQ);
				this.invokeInteraction("list");
				tydqtxs = this.getResponseData("tydqtxs");
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
			tydqtx = this.getResponseData("tydqtx");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("tydqtx", tydqtx);
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
			this.putRequestData("tydqtx", tydqtx);
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
		attrs[3][0]=FIND_SJLX;attrs[3][1]=FIND_SJLX;
		attrs[4][0]=FIND_TY_RQ;attrs[4][1]=FIND_TY_RQ;
		attrs[5][0]=FIND_TYDQ_RQ;attrs[5][1]=FIND_TYDQ_RQ;
		for (Tydqtx _tydqtx: tydqtxs) {
			list.add(new CommonExcelPrintBean().setObject(_tydqtx.getLSH()).setObject(_tydqtx.getSWJGBM()).setObject(_tydqtx.getNSRSBM()).setObject(_tydqtx.getNSRMC()).setObject(_tydqtx.getSJLX()).setObject(_tydqtx.getSJHM()).setObject(_tydqtx.getSMSINFO()).setObject(_tydqtx.getTY_RQ()).setObject(_tydqtx.getTYDQ_RQ()).setObject(_tydqtx.getBZ()).setObject(_tydqtx.getFSZT_DM()).setObject(_tydqtx.getLR_SJ()).setObject(_tydqtx.getFS_SJ()));
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
		public void setTydqtx(Tydqtx tydqtx) {
				this.tydqtx=tydqtx;
		}
		public Tydqtx getTydqtx(){
				return this.tydqtx;
		}
		public void setTydqtxs(List<Tydqtx> tydqtxs) {
				this.tydqtxs=tydqtxs;
		}
		public List<Tydqtx> getTydqtxs(){
				return this.tydqtxs;
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
		public void setFIND_TY_RQ(String FIND_TY_RQ) {
				this.FIND_TY_RQ=FIND_TY_RQ;
		}
		public String getFIND_TY_RQ(){
				return this.FIND_TY_RQ;
		}
		public void setFIND_TYDQ_RQ(String FIND_TYDQ_RQ) {
				this.FIND_TYDQ_RQ=FIND_TYDQ_RQ;
		}
		public String getFIND_TYDQ_RQ(){
				return this.FIND_TYDQ_RQ;
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
