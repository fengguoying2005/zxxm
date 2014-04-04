package com.gwinsoft.components2.yw.jktx;

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

public class JktxAction extends BaseAction<BaseIAO> {
		private Jktx jktx;
		private List<Jktx> jktxs;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_SWJGBM;
		private String FIND_NSRSBM;
		private String FIND_NSRMC;
		private String FIND_SJLX;
		private String FIND_SBQX;
		private String FIND_JKLX_DM;
		private Map orgmap = null;
		private Map jsrlxmap = null;
		private Map jklxmap = null;
		public String list(){
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_SWJGBM", FIND_SWJGBM);
				putRequestData("FIND_NSRSBM", FIND_NSRSBM);
				putRequestData("FIND_NSRMC", FIND_NSRMC);
				putRequestData("FIND_SJLX", FIND_SJLX);
				putRequestData("FIND_SBQX", FIND_SBQX);
				putRequestData("FIND_JKLX_DM", FIND_JKLX_DM);
				this.invokeInteraction("list");
				jktxs = this.getResponseData("jktxs");
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
			jktx = this.getResponseData("jktx");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("jktx", jktx);
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
			this.putRequestData("jktx", jktx);
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
		attrs[4][0]=FIND_SBQX;attrs[4][1]=FIND_SBQX;
		attrs[5][0]=FIND_JKLX_DM;attrs[5][1]=FIND_JKLX_DM;
		for (Jktx _jktx: jktxs) {
			list.add(new CommonExcelPrintBean().setObject(_jktx.getLSH()).setObject(_jktx.getSWJGBM()).setObject(_jktx.getNSRSBM()).setObject(_jktx.getNSRMC()).setObject(_jktx.getSJLX()).setObject(_jktx.getSJHM()).setObject(_jktx.getSMSINFO()).setObject(_jktx.getSKSSQ()).setObject(_jktx.getSKSSZ()).setObject(_jktx.getSBQX()).setObject(_jktx.getBZ()).setObject(_jktx.getFSZT_DM()).setObject(_jktx.getLR_SJ()).setObject(_jktx.getFS_SJ()).setObject(_jktx.getXTSPHM()).setObject(_jktx.getJKLX_DM()).setObject(_jktx.getSF_JE()));
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
		public void setJktx(Jktx jktx) {
				this.jktx=jktx;
		}
		public Jktx getJktx(){
				return this.jktx;
		}
		public void setJktxs(List<Jktx> jktxs) {
				this.jktxs=jktxs;
		}
		public List<Jktx> getJktxs(){
				return this.jktxs;
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
		public void setFIND_SBQX(String FIND_SBQX) {
				this.FIND_SBQX=FIND_SBQX;
		}
		public String getFIND_SBQX(){
				return this.FIND_SBQX;
		}
		public void setFIND_JKLX_DM(String FIND_JKLX_DM) {
				this.FIND_JKLX_DM=FIND_JKLX_DM;
		}
		public String getFIND_JKLX_DM(){
				return this.FIND_JKLX_DM;
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
		public Map getJklxmap() {
			return DMB.sortByKey(DMB.getDMB("JKLX_CACHE", "JKLX_MC"),true);
		}
		public void setJklxmap(Map jklxmap) {
			this.jklxmap = jklxmap;
		}
}
