package com.gwinsoft.components.qxgl.user;

import java.util.HashMap;
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

public class UserAction extends BaseAction<BaseIAO> {
		private User user;
		private List<User> users;
		private PageBean pageBean = new PageBean(10);
		private String USER_DM;
		private String FIND_USER_DM;
		private String FIND_USER_MC;
		private String FIND_ORG_DM_JG;
		private String FIND_ORG_DM_BM;
		private Map xbmap = null;
		private Map zwmap = null;
		private Map orgjgmap = null;
		private Map orgbmmap = null;
		private String assignRole;
		private String assignAuth;
		private String account;
		private String orgdm;
		public String list(){
			User loginuser = UserHelper.getLoginUserFromRequest(request);
			orgdm = loginuser.getORG_DM_JG();
			this.putRequestData("loginuser", loginuser );
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_USER_DM", FIND_USER_DM);
				putRequestData("FIND_USER_MC", FIND_USER_MC);
				putRequestData("FIND_ORG_DM_JG", FIND_ORG_DM_JG);
				putRequestData("FIND_ORG_DM_BM", FIND_ORG_DM_BM);
				this.invokeInteraction("list");
				users = this.getResponseData("users");
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
			this.putRequestData("USER_DM", USER_DM);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String see() {
			this.putRequestData("USER_DM", USER_DM);
			this.invokeInteraction("see");
			user = this.getResponseData("user");
			return "see";
		}
		public String add() {
			this.putRequestData("loginuser", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("user", user);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.USER_DM = this.getResponseData("uid");
			if(resEnv.getAPPException()!=null) {return "add";}			return "mod";
		}
		public String mod() {
			this.putRequestData("loginuser", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("USER_DM", USER_DM);
			this.putRequestData("user", user);
			this.invokeInteraction("mod");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return "mod";
		}

		public String saveAssginRole() {
			putRequestData("account", account);
			putRequestData("assignRole", assignRole);
			this.invokeInteraction("saveAssginRole");
			return list();
		}
		public String saveAssginAuth() {
			this.putRequestData("account", account);
			this.putRequestData("assignAuth", assignAuth);
			this.invokeInteraction("saveAssginAuth");
			return list();
		}
	public String printExcel() {
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY, "ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY, getExcelPrintData());
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "UserList");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}
	public IComplexPrintData getExcelPrintData() {
		pageBean=null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[4][2];
		attrs[0][0]=FIND_USER_DM;attrs[0][1]=FIND_USER_DM;
		attrs[1][0]=FIND_USER_MC;attrs[1][1]=FIND_USER_MC;
		attrs[2][0]=FIND_ORG_DM_JG;attrs[2][1]=FIND_ORG_DM_JG;
		attrs[3][0]=FIND_ORG_DM_BM;attrs[3][1]=FIND_ORG_DM_BM;
		int i = 1;
		for (User _user: users) {
			list.add(new CommonExcelPrintBean()
				.setObject(_user.getUSER_DM())
				.setObject(_user.getUSER_MC())
				.setObject(_user.getORG_DM_JG())
				.setObject(_user.getORG_DM_BM())
				.setObject(_user.getZW_DM())
				.setObject(_user.getXB_DM())
				.setObject(_user.getTEL())
			);
			i ++;
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
		public void setUser(User user) {
				this.user=user;
		}
		public User getUser(){
				return this.user;
		}
		public void setUsers(List<User> users) {
				this.users=users;
		}
		public List<User> getUsers(){
				return this.users;
		}
		public void setUSER_DM(String USER_DM) {
				this.USER_DM=USER_DM;
		}
		public String getUSER_DM(){
				return this.USER_DM;
		}
		public void setFIND_USER_DM(String FIND_USER_DM) {
				this.FIND_USER_DM=FIND_USER_DM;
		}
		public String getFIND_USER_DM(){
				return this.FIND_USER_DM;
		}
		public void setFIND_USER_MC(String FIND_USER_MC) {
				this.FIND_USER_MC=FIND_USER_MC;
		}
		public String getFIND_USER_MC(){
				return this.FIND_USER_MC;
		}
		public void setFIND_ORG_DM_JG(String FIND_ORG_DM_JG) {
				this.FIND_ORG_DM_JG=FIND_ORG_DM_JG;
		}
		public String getFIND_ORG_DM_JG(){
				return this.FIND_ORG_DM_JG;
		}
		public void setFIND_ORG_DM_BM(String FIND_ORG_DM_BM) {
				this.FIND_ORG_DM_BM=FIND_ORG_DM_BM;
		}
		public String getFIND_ORG_DM_BM(){
				return this.FIND_ORG_DM_BM;
		}
		public Map getXbmap() {
			return DMB.sortByKey(DMB.getDMB("XB_CACHE", "XB_MC"),true);
		}
		public void setXbmap(Map xbmap) {
			this.xbmap = xbmap;
		}
		public Map getZwmap() {
			return DMB.sortByKey(DMB.getDMB("ZW_CACHE", "ZW_MC"),true);
		}
		public void setZwmap(Map zwmap) {
			this.zwmap = zwmap;
		}
		public Map getOrgjgmap() {
			User user = UserHelper.getLoginUserFromRequest(request);
			Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
			return DMB.sortByKey(dmb,true);
		}
		public void setOrgjgmap(Map orgjgmap) {
			this.orgjgmap = orgjgmap;
		}
		public Map getOrgbmmap() {
			Map dmb = DMB.getOrgDMB("", 0, "ALL");
			return DMB.sortByKey(dmb,true);
		}
		public void setOrgbmmap(Map orgbmmap) {
			this.orgbmmap = orgbmmap;
		}
		public String getAssignRole() {
			return assignRole;
		}
		public void setAssignRole(String assignRole) {
			this.assignRole = assignRole;
		}
		public String getAssignAuth() {
			return assignAuth;
		}
		public void setAssignAuth(String assignAuth) {
			this.assignAuth = assignAuth;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getOrgdm() {
			return orgdm;
		}
		public void setOrgdm(String orgdm) {
			this.orgdm = orgdm;
		}
		
}
