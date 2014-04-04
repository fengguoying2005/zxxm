package com.gwinsoft.components.tjfx.business;

import java.util.Date;

public class Business {

	private String NSRDATA_LSH;// 流水号
	private String ORG_DM_JG;// 税务机关
	private String SMSTYPE_DM;// 短信类型
	private String GROUP_NAME;// 群组名称
	private String SMSZT_DM;// 短信状态
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间

	public void setNSRDATA_LSH(String NSRDATA_LSH) {
		this.NSRDATA_LSH = NSRDATA_LSH;
	}

	public String getNSRDATA_LSH() {
		return this.NSRDATA_LSH;
	}

	public void setORG_DM_JG(String ORG_DM_JG) {
		this.ORG_DM_JG = ORG_DM_JG;
	}

	public String getORG_DM_JG() {
		return this.ORG_DM_JG;
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

	public void setSMSZT_DM(String SMSZT_DM) {
		this.SMSZT_DM = SMSZT_DM;
	}

	public String getSMSZT_DM() {
		return this.SMSZT_DM;
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

}
