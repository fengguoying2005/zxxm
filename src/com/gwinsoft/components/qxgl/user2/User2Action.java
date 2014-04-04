package com.gwinsoft.components.qxgl.user2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Workbook;

import org.apache.struts2.ServletActionContext;

import com.gwinsoft.components.common.CommonTool;
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
import com.gwinsoft.util.GwinSoftUtil;

public class User2Action extends BaseAction<BaseIAO> {
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

		private String ORG_MC;
		private String NOW_DATE;
		
	    private static final int BUFFER_SIZE = 16 * 1024;  
		// 上传文件类型
		private String myfileContentType;
		// 保存文件的目录路径(通过依赖注入)
		private String savePath;
		private File myfile;
		private String myfileFileName;
		private String filename = "内部人员模板.xls";

		private InputStream excelStream;  //输出流变量  
	    private String excelFileName; //下载文件名  
	    private String templatePosition;  //模板路径  
		public String list(){
			com.gwinsoft.components.qxgl.user.User loginuser = UserHelper.getLoginUserFromRequest(request);
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
			String LSH = CommonTool.getLSH();
			user = new User();
			user.setUSER_DM(LSH);
			user.setYX_BJ("1");
			return "add";
		}
		public String toMod() {
			see();
			return "mod";
		}
		public String importExcel() {
			User user = UserHelper.getLoginUserFromRequest(request);
			ORG_MC = user.getLRRY_DM();
			NOW_DATE = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
			return "importExcel";
		}
		public String importfile() {
			if(this.getMyfileFileName()==null || !this.getMyfileFileName().endsWith(".xls")) {
				this.addActionError("上传文件的类型须为excel文件，并且以xls结尾。");
			} else {
				String dstPath = ServletActionContext.getServletContext().getRealPath(this.getSavePath()) + "\\" + this.getMyfileFileName();
				//System.out.println("上传的文件的类型:" + this.getMyfileContentType()+",文件:"+dstPath);
				File dstFile = new File(dstPath);
				copy(this.myfile, dstFile);
				Workbook book;
				try {
					book = Workbook.getWorkbook(dstFile);
					User user = UserHelper.getLoginUserFromRequest(request);
					this.putRequestData("user", user);
					this.putRequestData("book", book);
					//this.putRequestData("title", title);
					BaseResponseEnvelope resEnv = this.invokeInteraction("importfile");
					if(this.getResponseData("message")!=null) {
						String message = this.getResponseData("message");
						request.setAttribute("message", message);
					}
					//title = "短信催报-"+user.getUSER_MC()+'-'+GwinSoftUtil.getSysFormatDate("yyyyMMddHHmmss");
				} catch (Exception e) {
					e.printStackTrace();
					this.addActionError("上传文件出错。");
				}
			}
			return SUCCESS;
		}

		//封装的一个把源文件对象复制成目标文件对象  
		private static void copy(File src, File dst) {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				int len = 0;
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != out) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		public String export() throws IOException {
			this.excelFileName=filename;
	    	String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/conf/print/" + filename);
	    	this.excelStream = new FileInputStream(path);
	        return SUCCESS;  
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
					//.setObject(""+i)
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
			com.gwinsoft.components.qxgl.user.User user = UserHelper.getLoginUserFromRequest(request);
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
		public String getMyfileContentType() {
			return myfileContentType;
		}
		public void setMyfileContentType(String myfileContentType) {
			this.myfileContentType = myfileContentType;
		}
		public String getSavePath() {
			return savePath;
		}
		public void setSavePath(String savePath) {
			this.savePath = savePath;
		}
		public File getMyfile() {
			return myfile;
		}
		public void setMyfile(File myfile) {
			this.myfile = myfile;
		}
		public String getMyfileFileName() {
			return myfileFileName;
		}
		public void setMyfileFileName(String myfileFileName) {
			this.myfileFileName = myfileFileName;
		}
		public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		public InputStream getExcelStream() {
			return excelStream;
		}
		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		}
		public String getExcelFileName() {
			return "USERTEMPLATE.xls";
		}
		public void setExcelFileName(String excelFileName) {
			this.excelFileName = excelFileName;
		}
		public String getTemplatePosition() {
			return templatePosition;
		}
		public void setTemplatePosition(String templatePosition) {
			this.templatePosition = templatePosition;
		}
		public String getORG_MC() {
			return ORG_MC;
		}
		public void setORG_MC(String oRG_MC) {
			ORG_MC = oRG_MC;
		}
		public String getNOW_DATE() {
			return NOW_DATE;
		}
		public void setNOW_DATE(String nOW_DATE) {
			NOW_DATE = nOW_DATE;
		}
		public static int getBufferSize() {
			return BUFFER_SIZE;
		}
		
}
