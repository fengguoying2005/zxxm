package com.gwinsoft.components.qxgl.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class QxglRoleAction extends BaseAction<BaseIAO> {

	private Role role;
	private List<Role> roles;
	private PageBean pageBean = new PageBean(10);
	private String ROLE_LSH;
	private String FIND_ROLE_MC;
	private String FIND_ORG_DM_JG;
	private String FIND_ROLE_MS;
	private String FIND_YX_BJ;
	private Map orgmap = null;
	private String assignAuth;
	
	public String assignAuth() {
		return "assignAuth";
	}
	public String saveAssginAuth() {
		this.putRequestData("roledm", ROLE_LSH);
		this.putRequestData("assignAuth", assignAuth);
		this.invokeInteraction("saveAssginAuth");
		return list();
	}
	public String list(){
		User user = UserHelper.getLoginUserFromRequest(request);
		this.putRequestData("user", user);
			putRequestData("pageBean", pageBean);
			putRequestData("FIND_ROLE_MC", FIND_ROLE_MC);
			putRequestData("FIND_ORG_DM_JG", FIND_ORG_DM_JG);
			putRequestData("FIND_ROLE_MS", FIND_ROLE_MS);
			putRequestData("FIND_YX_BJ", FIND_YX_BJ);
			this.invokeInteraction("list");
			roles = this.getResponseData("roles");
			return "list";
	}
	public String toAdd() {
		User user = UserHelper.getLoginUserFromRequest(request);
		role = new Role();
		role.setORG_DM_JG(user.getORG_DM_JG());
		return "add";
	}
	public String toMod() {
		see();
		return "mod";
	}
	public String del() {
		this.putRequestData("ROLE_LSH", ROLE_LSH);
		this.invokeInteraction("del");
		if(this.getResponseData("message")!=null) {
			String message = this.getResponseData("message");
			request.setAttribute("message", message);
		}
		return list();
	}
	public String see() {
		this.putRequestData("ROLE_LSH", ROLE_LSH);
		this.invokeInteraction("see");
		role = this.getResponseData("role");
		return "see";
	}
	public String add() {
		User user = UserHelper.getLoginUserFromRequest(request);
		role.setORG_DM_JG(user.getORG_DM_JG());
		this.putRequestData("user", user);
		this.putRequestData("role", role);
		BaseResponseEnvelope resEnv = this.invokeInteraction("add");
		if(this.getResponseData("message")!=null) {
			String message = this.getResponseData("message");
			request.setAttribute("message", message);
		}
		this.ROLE_LSH = this.getResponseData("uid");
		if(resEnv.getAPPException()!=null) {return "add";}			return "mod";
	}
	public String mod() {
		User user = UserHelper.getLoginUserFromRequest(request);
		role.setORG_DM_JG(user.getORG_DM_JG());
		this.putRequestData("user", user);
		this.putRequestData("ROLE_LSH", ROLE_LSH);
		this.putRequestData("role", role);
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
	request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "RoleList");
	request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
	return "printService";
}
public IComplexPrintData getExcelPrintData() {
	pageBean=null;
	list();
	List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
	String[][] attrs = new String[4][2];
	attrs[0][0]=FIND_ROLE_MC;attrs[0][1]=FIND_ROLE_MC;
	attrs[1][0]=FIND_ORG_DM_JG;attrs[1][1]=FIND_ORG_DM_JG;
	attrs[2][0]=FIND_ROLE_MS;attrs[2][1]=FIND_ROLE_MS;
	attrs[3][0]=FIND_YX_BJ;attrs[3][1]=FIND_YX_BJ;
	int i = 1;
	for (Role _role: roles) {
			list.add(new CommonExcelPrintBean().setObject(""+i)
					.setObject(_role.getORG_DM_JG())
					.setObject(_role.getROLE_MC())
					.setObject(_role.getROLE_MS())
					.setObject(_role.getYX_BJ())
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
	public void setRole(Role role) {
			this.role=role;
	}
	public Role getRole(){
			return this.role;
	}
	public void setRoles(List<Role> roles) {
			this.roles=roles;
	}
	public List<Role> getRoles(){
			return this.roles;
	}
	public void setROLE_LSH(String ROLE_LSH) {
			this.ROLE_LSH=ROLE_LSH;
	}
	public String getROLE_LSH(){
			return this.ROLE_LSH;
	}
	public void setFIND_ROLE_MC(String FIND_ROLE_MC) {
			this.FIND_ROLE_MC=FIND_ROLE_MC;
	}
	public String getFIND_ROLE_MC(){
			return this.FIND_ROLE_MC;
	}
	public void setFIND_ORG_DM_JG(String FIND_ORG_DM_JG) {
			this.FIND_ORG_DM_JG=FIND_ORG_DM_JG;
	}
	public String getFIND_ORG_DM_JG(){
			return this.FIND_ORG_DM_JG;
	}
	public void setFIND_ROLE_MS(String FIND_ROLE_MS) {
			this.FIND_ROLE_MS=FIND_ROLE_MS;
	}
	public String getFIND_ROLE_MS(){
			return this.FIND_ROLE_MS;
	}
	public void setFIND_YX_BJ(String FIND_YX_BJ) {
			this.FIND_YX_BJ=FIND_YX_BJ;
	}
	public String getFIND_YX_BJ(){
			return this.FIND_YX_BJ;
	}
	public Map getOrgmap() {
		User user = UserHelper.getLoginUserFromRequest(request);
		Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
		return DMB.sortByKey(dmb,true);
	}
	public void setOrgmap(Map orgmap) {
		this.orgmap = orgmap;
	}
	public String getAssignAuth() {
		return assignAuth;
	}
	public void setAssignAuth(String assignAuth) {
		this.assignAuth = assignAuth;
	}
	
}