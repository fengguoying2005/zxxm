package com.gwinsoft.components.nsrsms.cbimport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import jxl.Workbook;

import org.apache.struts2.ServletActionContext;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.web.action.BaseAction;
import com.gwinsoft.util.GwinSoftUtil;

public class CbimportAction extends BaseAction<BaseIAO> {
	 
    private static final int BUFFER_SIZE = 16 * 1024;  
	private String ORG_MC;
	private String NOW_DATE;
	// 文件标题
	private String title;
	// 上传文件类型
	private String myfileContentType;
	// 保存文件的目录路径(通过依赖注入)
	private String savePath;
	private File myfile;
	private String myfileFileName;
	private String filename = "催报信息模板.xls";
	
	public String execute() throws Exception {
		User user = UserHelper.getLoginUserFromRequest(request);
		ORG_MC = user.getLRRY_DM();
		NOW_DATE = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
		title = "短信催报-"+user.getUSER_MC()+'-'+GwinSoftUtil.getSysFormatDate("yyyyMMddHHmmss");
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
        return "SBTEMPLATE.xls";  
    }  
    
	public InputStream getInputStream() {
		/*String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/conf/print/" + filename);
		try {
			FileInputStream in = new FileInputStream(path);
			return in;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		return ServletActionContext.getServletContext().getResourceAsStream("C:/" + filename);
	}
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
				this.putRequestData("title", title);
				BaseResponseEnvelope resEnv = this.invokeInteraction("importfile");
				if(this.getResponseData("message")!=null) {
					String message = this.getResponseData("message");
					request.setAttribute("message", message);
				}
				title = "短信催报-"+user.getUSER_MC()+'-'+GwinSoftUtil.getSysFormatDate("yyyyMMddHHmmss");
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
}
