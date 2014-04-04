package com.gwinsoft.components.qxgl.auth;

import java.util.ArrayList;
import java.util.List;

import com.gwinsoft.framework.web.action.BaseAction;

public class QxglAuthListAction extends BaseAction<QxglAuthIAO> {

	private static final long serialVersionUID = 1L;
	//list页面,add页面
	private List<Auth> auths;
	//add页面
	private Auth auth = new Auth();
	
	
	//列表
	public String execute() {
		String dealMethod = "listAuth";
		this.invokeInteraction(dealMethod);
		auths = getResponseData("auths");
		return "listAuth";
	}
	//list页面：增加按钮
	public String addAuth() {
		auths = new ArrayList<Auth>();
		Auth _auth = new Auth();
		auths.add(_auth );
		return "addAuth";
	}
	//list页面：删除按钮
	public String delAuth() {
		
		return execute();
	}
	//list页面：修改按钮
	public String modAuth() {
		
		return "modAuth";
	}
	//list页面：查看按钮
	public String seeAuth() {
		
		return "seeAuth";
	}
	//add页面
	public String saveAuth() {
		
		return "saveAuth";
	}
	
	public List<Auth> getAuths() {
		return auths;
	}

	public void setAuths(List<Auth> auths) {
		this.auths = auths;
	}

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	
}