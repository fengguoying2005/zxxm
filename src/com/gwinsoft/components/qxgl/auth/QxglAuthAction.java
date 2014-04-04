package com.gwinsoft.components.qxgl.auth;

import java.util.ArrayList;
import java.util.List;

import com.gwinsoft.framework.web.action.BaseAction;

public class QxglAuthAction extends BaseAction<QxglAuthIAO> {

	private static final long serialVersionUID = 1L;
	//list页面,add页面,modify页面
	private List<Auth> auths;
	//add页面,modify页面
	private Auth auth = new Auth();
	//see页面
	private String authdm;
	
	//默认功能
	public String execute() {
		return listAuth();
	}
	//列表
	public String listAuth() {
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
		String dealMethod = "delAuth";
		putRequestData("authdm", authdm);
		this.invokeInteraction(dealMethod);
		return listAuth();
	}
	//list页面：修改按钮
	public String modAuth() {
		seeAuth();
		//add by fenggy 2011-08-31 防止没有明细数据是字表没有显示行
		if(auths == null) {
			auths = new ArrayList<Auth>();
		}
		if(auths.size()==0) {
			auths.add(new Auth());
		}
		return "modAuth";
	}
	//list页面：查看按钮
	public String seeAuth() {
		String dealMethod = "seeAuth";
		putRequestData("authdm", authdm);
		this.invokeInteraction(dealMethod);
		auths = getResponseData("auths");
		auth = getResponseData("auth");
		return "seeAuth";
	}
	//add页面:saveAuth
	public String saveAddAuth() {
		String dealMethod = "saveAddAuth";
		putRequestData("auth", auth);
		putRequestData("auths", auths);
		this.invokeInteraction(dealMethod);
		//add by fenggy 2011-08-31 防止没有明细数据是字表没有显示行
		if(auths == null) {
			auths = new ArrayList<Auth>();
		}
		if(auths.size()==0) {
			auths.add(new Auth());
		}
		return "saveAddAuth";
	}
	//add页面,modify页面:backAuth
	public String backAuth() {
		return listAuth();
	}
	//modify页面:saveModAuth
	public String saveModAuth() {
		String dealMethod = "saveModAuth";
		putRequestData("auth", auth);
		putRequestData("auths", auths);
		this.invokeInteraction(dealMethod);
		//add by fenggy 2011-08-31 防止没有明细数据是字表没有显示行
		if(auths == null) {
			auths = new ArrayList<Auth>();
		}
		if(auths.size()==0) {
			auths.add(new Auth());
		}
		return "saveModAuth";
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
	public String getAuthdm() {
		return authdm;
	}
	public void setAuthdm(String authdm) {
		this.authdm = authdm;
	}
}