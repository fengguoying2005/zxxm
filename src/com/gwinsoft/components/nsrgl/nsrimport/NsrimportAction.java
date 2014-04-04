package com.gwinsoft.components.nsrgl.nsrimport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import jxl.Workbook;

import org.apache.struts2.ServletActionContext;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.web.action.BaseAction;

public class NsrimportAction extends BaseAction<BaseIAO> {
	 
    private static final int BUFFER_SIZE = 16 * 1024;  
    
	private String ORG_DM;
	
	// 上传文件类型
	private String myfileContentType;
	private String savePath;
	private File myfile;
	private String myfileFileName;
	private String filename = "NSR.xls";

	private Map orgmap = null;
	
	public String execute() throws Exception {
		User user = UserHelper.getLoginUserFromRequest(request);
		return SUCCESS;
	}

	private InputStream excelStream;  //输出流变量  
    private String excelFileName; //下载文件名  
    private String templatePosition;  //模板路径  
    
    public String export() throws Exception {
    	this.excelFileName=filename;
    	String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/conf/print/" + filename);
    	this.excelStream = new FileInputStream(path);
        return SUCCESS;  
    }
    /** 
     * 提供中文转换的功能 
     * @return 
     */  
    public String getExcelFileName() {  
        return "NSR.xls";  
    }  
    
	/*public InputStream getInputStream() {
		return ServletActionContext.getServletContext().getResourceAsStream("C:/" + filename);
	}*/
	public String importfile() throws Exception {
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
				this.putRequestData("ORG_DM", ORG_DM);
				BaseResponseEnvelope resEnv = this.invokeInteraction("importfile");
				if(this.getResponseData("message")!=null) {
					String message = this.getResponseData("message");
					request.setAttribute("message", message);
				}
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
	
	public String getORG_DM() {
		return ORG_DM;
	}
	public void setORG_DM(String oRG_DM) {
		ORG_DM = oRG_DM;
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

	public void setFilename(String fileName) {
		this.filename = fileName;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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
	public static int getBufferSize() {
		return BUFFER_SIZE;
	}
	public String getFilename() {
		return filename;
	}

	public Map getOrgmap() {
		User user = UserHelper.getLoginUserFromRequest(request);
		Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
		return DMB.sortByKey(dmb,true);
	}
	public void setOrgmap(Map orgmap) {
		this.orgmap = orgmap;
	}
}
