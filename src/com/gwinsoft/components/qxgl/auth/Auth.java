package com.gwinsoft.components.qxgl.auth;

import java.util.Date;

import com.gwinsoft.framework.core.bean.ValueBean;

public class Auth implements ValueBean {

	private static final long serialVersionUID = 1L;
	private String AUTH_DM;
	private String AUTH_MC;
	private String AUTH_CC;
	private String SJ_AUTH_DM;
	private String AUTH_MS;
	private String AUTH_LJ;
	private String YX_BJ = "0";
	private String TARGET;
	private String XS_SX = "0";
	private String LRRY_DM;
	private String XGRY_DM;
	private Date LR_SJ;
	private Date XG_SJ;
	private String SFDJ_BJ = "0";
	
	public String getAUTH_DM() {
		return AUTH_DM;
	}
	public void setAUTH_DM(String aUTHDM) {
		AUTH_DM = aUTHDM;
	}
	public String getAUTH_MC() {
		return AUTH_MC;
	}
	public void setAUTH_MC(String aUTHMC) {
		AUTH_MC = aUTHMC;
	}
	public String getAUTH_CC() {
		return AUTH_CC;
	}
	public void setAUTH_CC(String aUTHCC) {
		AUTH_CC = aUTHCC;
	}
	public String getSJ_AUTH_DM() {
		return SJ_AUTH_DM;
	}
	public void setSJ_AUTH_DM(String sJAUTHDM) {
		SJ_AUTH_DM = sJAUTHDM;
	}
	public String getAUTH_MS() {
		return AUTH_MS;
	}
	public void setAUTH_MS(String aUTHMS) {
		AUTH_MS = aUTHMS;
	}
	public String getAUTH_LJ() {
		return AUTH_LJ;
	}
	public void setAUTH_LJ(String aUTHLJ) {
		AUTH_LJ = aUTHLJ;
	}
	public String getYX_BJ() {
		return YX_BJ;
	}
	public void setYX_BJ(String yXBJ) {
		YX_BJ = yXBJ;
	}
	public String getTARGET() {
		return TARGET;
	}
	public void setTARGET(String tARGET) {
		TARGET = tARGET;
	}
	public String getXS_SX() {
		return XS_SX;
	}
	public void setXS_SX(String xSSX) {
		XS_SX = xSSX;
	}
	public String getLRRY_DM() {
		return LRRY_DM;
	}
	public void setLRRY_DM(String lRRYDM) {
		LRRY_DM = lRRYDM;
	}
	public String getXGRY_DM() {
		return XGRY_DM;
	}
	public void setXGRY_DM(String xGRYDM) {
		XGRY_DM = xGRYDM;
	}
	public Date getLR_SJ() {
		return LR_SJ;
	}
	public void setLR_SJ(Date lR_SJ) {
		LR_SJ = lR_SJ;
	}
	public Date getXG_SJ() {
		return XG_SJ;
	}
	public void setXG_SJ(Date xG_SJ) {
		XG_SJ = xG_SJ;
	}
	public String getSFDJ_BJ() {
		return SFDJ_BJ;
	}
	public void setSFDJ_BJ(String sFDJ_BJ) {
		SFDJ_BJ = sFDJ_BJ;
	}
	
}
