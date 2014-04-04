package com.gwinsoft.components.orgsms.orgsmssend;

import java.util.Date;

public class Orgsmssend {

	private String NSRDATA_LSH;// 流水号
	private String SMSTYPE_DM;// 短信类型
	private String GROUP_NAME;// 群组名称
	private String ORG_DM_JG;// 税务机关
	private String DXMBINFO;//
	private String DXQM;//
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间
	private String SMSZT_DM;// 短信状态
	private String THYY;
	
	private String CALLBACK;
	private String CALLBACKINFO;
	private String SHY;

	public void setNSRDATA_LSH(String NSRDATA_LSH) {
		this.NSRDATA_LSH = NSRDATA_LSH;
	}

	public String getNSRDATA_LSH() {
		return this.NSRDATA_LSH;
	}

	public void setSMSTYPE_DM(String SMSTYPE_DM) {
		this.SMSTYPE_DM = SMSTYPE_DM;
	}

	public String getSMSTYPE_DM() {
		return this.SMSTYPE_DM;
	}

	public void setGROUP_NAME(String GROUP_NAME) {
		this.GROUP_NAME = GROUP_NAME;
	}

	public String getGROUP_NAME() {
		return this.GROUP_NAME;
	}

	public void setORG_DM_JG(String ORG_DM_JG) {
		this.ORG_DM_JG = ORG_DM_JG;
	}

	public String getORG_DM_JG() {
		return this.ORG_DM_JG;
	}

	public void setLRRY_DM(String LRRY_DM) {
		this.LRRY_DM = LRRY_DM;
	}

	public String getLRRY_DM() {
		return this.LRRY_DM;
	}

	public void setXGRY_DM(String XGRY_DM) {
		this.XGRY_DM = XGRY_DM;
	}

	public String getXGRY_DM() {
		return this.XGRY_DM;
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

	public void setSMSZT_DM(String SMSZT_DM) {
		this.SMSZT_DM = SMSZT_DM;
	}

	public String getSMSZT_DM() {
		return this.SMSZT_DM;
	}

	public String getDXMBINFO() {
		return DXMBINFO;
	}

	public void setDXMBINFO(String dXMBINFO) {
		DXMBINFO = dXMBINFO;
	}

	public String getDXQM() {
		return DXQM;
	}

	public void setDXQM(String dXQM) {
		DXQM = dXQM;
	}

	public String getTHYY() {
		return THYY;
	}

	public void setTHYY(String tHYY) {
		THYY = tHYY;
	}

	public String getCALLBACK() {
		return CALLBACK;
	}

	public void setCALLBACK(String cALLBACK) {
		CALLBACK = cALLBACK;
	}

	public String getCALLBACKINFO() {
		return CALLBACKINFO;
	}

	public void setCALLBACKINFO(String cALLBACKINFO) {
		CALLBACKINFO = cALLBACKINFO;
	}

	public String getSHY() {
		return SHY;
	}

	public void setSHY(String sHY) {
		SHY = sHY;
	}
}
