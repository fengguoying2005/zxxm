package com.gwinsoft.components.nsrgl.jbxx;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
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

public class NsrjbxxAction extends BaseAction<BaseIAO> {
		private Nsrjbxx nsrjbxx;
		private List<Nsrjbxx> nsrjbxxs;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_NSRBM;
		private String FIND_NSRMC;
		private String FIND_HYDM;
		private String FIND_ORG_DM;
		private String FIND_SSZGY;
		private String FIND_SBFS;
		private String FIND_DJLX;
		private String FIND_ZCLX;
		private Map hymap = null;
		private Map orgmap = null;
		private Map sbfsmap = null;
		private Map djztmap = null;
		private Map djlxmap = null;
		private Map zclxmap = null;
		public String list(){
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_NSRBM", FIND_NSRBM);
				putRequestData("FIND_NSRMC", FIND_NSRMC);
				putRequestData("FIND_HYDM", FIND_HYDM);
				putRequestData("FIND_ORG_DM", FIND_ORG_DM);
				putRequestData("FIND_SSZGY", FIND_SSZGY);
				putRequestData("FIND_SBFS", FIND_SBFS);
				putRequestData("FIND_DJLX", FIND_DJLX);
				putRequestData("FIND_ZCLX", FIND_ZCLX);
				this.invokeInteraction("list");
				nsrjbxxs = this.getResponseData("nsrjbxxs");
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
			nsrjbxx = this.getResponseData("nsrjbxx");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("nsrjbxx", nsrjbxx);
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
			this.putRequestData("nsrjbxx", nsrjbxx);
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
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "NsrList");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}

	public IComplexPrintData getExcelPrintData() {
		pageBean = null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[8][2];
		attrs[0][0] = FIND_NSRBM;
		attrs[0][1] = FIND_NSRBM;
		attrs[1][0] = FIND_NSRMC;
		attrs[1][1] = FIND_NSRMC;
		attrs[2][0] = FIND_HYDM;
		attrs[2][1] = FIND_HYDM;
		attrs[3][0] = FIND_ORG_DM;
		attrs[3][1] = FIND_ORG_DM;
		attrs[4][0] = FIND_SSZGY;
		attrs[4][1] = FIND_SSZGY;
		attrs[5][0] = FIND_SBFS;
		attrs[5][1] = FIND_SBFS;
		attrs[6][0] = FIND_DJLX;
		attrs[6][1] = FIND_DJLX;
		attrs[7][0] = FIND_ZCLX;
		attrs[7][1] = FIND_ZCLX;
		for (Nsrjbxx _nsrjbxx : nsrjbxxs) {
			list.add(new CommonExcelPrintBean()
					.setObject(_nsrjbxx.getNSRBM())
					.setObject(_nsrjbxx.getNSRMC())
					.setObject(_nsrjbxx.getHYDM())
					.setObject(_nsrjbxx.getSSS())
					.setObject(_nsrjbxx.getORG_DM())
					.setObject(_nsrjbxx.getFRSJH())
					.setObject(_nsrjbxx.getCWJLSJH())
					.setObject(_nsrjbxx.getBSRYSJH()));
		}
		CommonExcelPrintData data = new CommonExcelPrintData(attrs, list, 1);
		return data;
	}
		public void setPageBean(PageBean pageBean) {
				this.pageBean=pageBean;
		}
		public PageBean getPageBean(){
				return this.pageBean;
		}
		public void setNsrjbxx(Nsrjbxx nsrjbxx) {
				this.nsrjbxx=nsrjbxx;
		}
		public Nsrjbxx getNsrjbxx(){
				return this.nsrjbxx;
		}
		public void setNsrjbxxs(List<Nsrjbxx> nsrjbxxs) {
				this.nsrjbxxs=nsrjbxxs;
		}
		public List<Nsrjbxx> getNsrjbxxs(){
				return this.nsrjbxxs;
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
		public void setFIND_HYDM(String FIND_HYDM) {
				this.FIND_HYDM=FIND_HYDM;
		}
		public String getFIND_HYDM(){
				return this.FIND_HYDM;
		}
		public void setFIND_ORG_DM(String FIND_ORG_DM) {
				this.FIND_ORG_DM=FIND_ORG_DM;
		}
		public String getFIND_ORG_DM(){
				return this.FIND_ORG_DM;
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
		public Map getOrgmap() {
			User user = UserHelper.getLoginUserFromRequest(request);
			Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
			return DMB.sortByKey(dmb,true);
		}
		public void setOrgmap(Map orgmap) {
			this.orgmap = orgmap;
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
}
