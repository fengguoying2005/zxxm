package com.gwinsoft.components.qxgl.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.components.smssh.sms.SendSMS;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.web.action.BaseAction;
import com.gwinsoft.framework.web.action.ImageAction;

public class QxglLoginAction extends BaseAction<QxglLoginIAO> {

	private static final long serialVersionUID = 1L;
	private User user;
	private String imageCode;
    private String result;
	
	public String toLogin() {
		//进入login页面
		//System.out.println("toLogin");
		return "toLogin";
	}
	public String sendsms() {
		String str = "";
		Random rdm = new Random();
		for(int i = 0 ; i < 4; i ++){
			int n = rdm.nextInt(10);
			str+=n;
		}
		ServletActionContext.getRequest().getSession().setAttribute(ImageAction.CheckCodeImageName, str);
		String strinfo = ("您登陆辽宁省地方税务局短信服务平台的验证码为："+str);
		Map<String, Map<String, Object>> cache = CacheManager.getCache("USER_CACHE");
		Iterator<String> it = cache.keySet().iterator();
		String USER_DM = (request.getParameter("USER_DM"));
		result = "未找到该登录用户："+USER_DM;
		while(it.hasNext()) {
			String key = it.next();
			if(key.equals(USER_DM)) {
				Object telO = cache.get(key).get("TEL");
				if(telO!=null&&telO.toString().length()>10) {
					String tel = (String) telO;
					SendSMS.sendsms(UUID.randomUUID().toString(), tel, strinfo, "");//300 to 
//					System.out.println(strinfo);
					result = "已发送验证码到您手机。";
					
				} else {
					result = "手机号码不存在！";
				}
				break;
			}
			//map.put(key, cache.get(key).get(valueName));//USER_DM
		}
		//System.out.println(strinfo);
		/*DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			System.out.println(pm);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}*/
    	return "success";
	}
	public String login() {
		Object o = session.get(ImageAction.CheckCodeImageName);
//		if(o == null || imageCode==null || !o.toString().equals(imageCode)) {
//			//addFieldError("imageCode", "校验码错误！");
//			request.setAttribute("message", "校验码错误！");
//			return "toLogin";
//		}
		this.putRequestData("user", user);
		BaseResponseEnvelope res = this.invokeInteraction("login");
		User _user = resEnv.getData("user");
		//保存用户到session
		UserHelper.setLoginUser(request, _user);
		if(_user != null) {
			return "login";
		} else {
			String msg = res.getAPPException().getMessage();
			if(msg==null || "".equals(msg)) {
				request.setAttribute("message", "用户不存在或密码错误！");
			} else {
				request.setAttribute("message", msg);
			}
			return toLogin();
		}
	}
	
	public String logout() {
		//从session取
		User _user = UserHelper.getLoginUserFromRequest(request);
		this.putRequestData("user", _user);
		this.invokeInteraction("logout");
		//从session移除
		UserHelper.removeLoginUser(request);
		return "logout";
	}

	public String toMoidfyPwd() {
		user = UserHelper.getLoginUserFromRequest(request);
		return "moidfypwd";
	}
	public String moidfyPwd() {
		this.putRequestData("user", user);
		this.invokeInteraction("moidfyPwd");
		return "moidfypwd";
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}