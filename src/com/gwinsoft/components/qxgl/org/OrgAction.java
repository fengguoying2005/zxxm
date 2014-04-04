package com.gwinsoft.components.qxgl.org;

import java.util.Iterator;
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

public class OrgAction extends BaseAction<BaseIAO> {
		private Org org;
		private List<Org> orgs;
		private PageBean pageBean = new PageBean(10);
		private String ORG_DM;
		private String FIND_ORG_DM;
		private String FIND_ORG_MC;
		private String FIND_ORG_TYPE;
		private String FIND_ORG_DESC;
		private String FIND_SJ_ORG_DM;
		private String SJJGDM = null;
		private String SJJGMC = null;
		private String BJJGDM = null;
		
		private Map orgtypemap = null;
		private Map orgmap = null;
		private Map sjorgmap = null;
		public String list(){
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_ORG_DM", FIND_ORG_DM);
				putRequestData("FIND_ORG_MC", FIND_ORG_MC);
				putRequestData("FIND_ORG_TYPE", FIND_ORG_TYPE);
				putRequestData("FIND_ORG_DESC", FIND_ORG_DESC);
				putRequestData("FIND_SJ_ORG_DM", FIND_SJ_ORG_DM);
				this.invokeInteraction("list");
				orgs = this.getResponseData("orgs");
				return "list";
		}
		public String toAdd() {
			User user = UserHelper.getLoginUserFromRequest(request);
			//ORG_DM = user.getORG_DM_JG();
			Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 5, "J");
			Iterator<String> it = dmb.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				SJJGDM = key;
				SJJGMC = (String) dmb.get(key);
			}
			return "add";
		}
		public String toMod() {
			User user = UserHelper.getLoginUserFromRequest(request);
			BJJGDM = user.getORG_DM_JG();
			Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 5, "J");
			Iterator<String> it = dmb.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				SJJGDM = key;
				SJJGMC = (String) dmb.get(key);
			}
			see();
			return "mod";
		}
		public String del() {
			this.putRequestData("ORG_DM", ORG_DM);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String see() {
			this.putRequestData("ORG_DM", ORG_DM);
			this.invokeInteraction("see");
			org = this.getResponseData("org");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("org", org);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.ORG_DM = this.getResponseData("uid");
			if(resEnv.getAPPException()!=null) {return "add";}			return "mod";
		}
		public String mod() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("ORG_DM", ORG_DM);
			this.putRequestData("org", org);
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
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "OrgList");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}
	public IComplexPrintData getExcelPrintData() {
		pageBean=null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[5][2];
		attrs[0][0]=FIND_ORG_DM;attrs[0][1]=FIND_ORG_DM;
		attrs[1][0]=FIND_ORG_MC;attrs[1][1]=FIND_ORG_MC;
		attrs[2][0]=FIND_ORG_TYPE;attrs[2][1]=FIND_ORG_TYPE;
		attrs[3][0]=FIND_ORG_DESC;attrs[3][1]=FIND_ORG_DESC;
		attrs[4][0]=FIND_SJ_ORG_DM;attrs[4][1]=FIND_SJ_ORG_DM;
		int i = 1;
		for (Org _org: orgs) {
			list.add(new CommonExcelPrintBean().setObject(""+i)
					.setObject(_org.getORG_DM())
					.setObject(_org.getORG_MC())
					.setObject(_org.getORG_TYPE())
					.setObject(_org.getORG_DESC())
					.setObject(_org.getSJ_ORG_DM())
					.setObject(_org.getYX_BJ())
					);
			i++;
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
		public void setOrg(Org org) {
				this.org=org;
		}
		public Org getOrg(){
				return this.org;
		}
		public void setOrgs(List<Org> orgs) {
				this.orgs=orgs;
		}
		public List<Org> getOrgs(){
				return this.orgs;
		}
		public void setORG_DM(String ORG_DM) {
				this.ORG_DM=ORG_DM;
		}
		public String getORG_DM(){
				return this.ORG_DM;
		}
		public void setFIND_ORG_DM(String FIND_ORG_DM) {
				this.FIND_ORG_DM=FIND_ORG_DM;
		}
		public String getFIND_ORG_DM(){
				return this.FIND_ORG_DM;
		}
		public void setFIND_ORG_MC(String FIND_ORG_MC) {
				this.FIND_ORG_MC=FIND_ORG_MC;
		}
		public String getFIND_ORG_MC(){
				return this.FIND_ORG_MC;
		}
		public void setFIND_ORG_TYPE(String FIND_ORG_TYPE) {
				this.FIND_ORG_TYPE=FIND_ORG_TYPE;
		}
		public String getFIND_ORG_TYPE(){
				return this.FIND_ORG_TYPE;
		}
		public void setFIND_ORG_DESC(String FIND_ORG_DESC) {
				this.FIND_ORG_DESC=FIND_ORG_DESC;
		}
		public String getFIND_ORG_DESC(){
				return this.FIND_ORG_DESC;
		}
		public void setFIND_SJ_ORG_DM(String FIND_SJ_ORG_DM) {
				this.FIND_SJ_ORG_DM=FIND_SJ_ORG_DM;
		}
		public String getFIND_SJ_ORG_DM(){
				return this.FIND_SJ_ORG_DM;
		}
		public Map getOrgtypemap() {
			return DMB.sortByKey(DMB.getDMB("ORGTYPE_CACHE", "ORGTYPE_MC"),true);
		}
		public void setOrgtypemap(Map orgtypemap) {
			this.orgtypemap = orgtypemap;
		}
		public Map getOrgmap() {
			return DMB.sortByKey(DMB.getDMB("ORG_CACHE", "ORG_MC"),true);
//			User user = UserHelper.getLoginUserFromRequest(request);
//			Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 2, "J");
//			return DMB.sortByKey(dmb,true);
		}
		public void setOrgmap(Map orgmap) {
			this.orgmap = orgmap;
		}
		public Map getSjorgmap() {
			User user = UserHelper.getLoginUserFromRequest(request);
			Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 5, "J");
			Iterator<String> it = dmb.keySet().iterator();
			String SJ = null,SJMC = null;
			while(it.hasNext()) {
				String key = it.next();
				SJ = key;
				SJMC = (String) dmb.get(key);
			}
			dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
			if(SJ!=null)
			dmb.put(SJ, SJMC);
			return DMB.sortByKey(dmb,true);
		}
		public void setSjorgmap(Map sjorgmap) {
			this.sjorgmap = sjorgmap;
		}
		public String getSJJGDM() {
			return SJJGDM;
		}
		public void setSJJGDM(String sJJGDM) {
			SJJGDM = sJJGDM;
		}
		public String getSJJGMC() {
			return SJJGMC;
		}
		public void setSJJGMC(String sJJGMC) {
			SJJGMC = sJJGMC;
		}
		public String getBJJGDM() {
			return BJJGDM;
		}
		public void setBJJGDM(String bJJGDM) {
			BJJGDM = bJJGDM;
		}
		
}
