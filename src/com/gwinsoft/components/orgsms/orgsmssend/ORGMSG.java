package com.gwinsoft.components.orgsms.orgsmssend;

import java.util.Date;

public class ORGMSG {

	private String NSRDATAMX_LSH;// 流水号
	private String NSRDATA_LSH;// 主表流水号
	private String ORG_DM_JG;// 机构
	private String ORG_DM_BM;// 部门
	private String USER_DM;// 人员
	private String SJHM;// 手机号码
	private String ORG_MC_JG;// 机构
	private String ORG_MC_BM;// 部门
	private String USER_MC;// 人员
	private String MSG;// 短信
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间
	private String SMSTYPE_DM;
	private Date FS_SJ;
	private String FSCS;
	
	private String ZW_DM;
	private String ZW_MC;
	
	private String MSG2;
	private String RECEIVETIME;

	public void setNSRDATAMX_LSH(String NSRDATAMX_LSH) {
		this.NSRDATAMX_LSH = NSRDATAMX_LSH;
	}

	public String getNSRDATAMX_LSH() {
		return this.NSRDATAMX_LSH;
	}

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

	public void setORG_DM_BM(String ORG_DM_BM) {
		this.ORG_DM_BM = ORG_DM_BM;
	}

	public String getORG_DM_BM() {
		return this.ORG_DM_BM;
	}

	public void setUSER_DM(String USER_DM) {
		this.USER_DM = USER_DM;
	}

	public String getUSER_DM() {
		return this.USER_DM;
	}

	public void setSJHM(String SJHM) {
		this.SJHM = SJHM;
	}

	public String getSJHM() {
		return this.SJHM;
	}

	public void setORG_MC_JG(String ORG_MC_JG) {
		this.ORG_MC_JG = ORG_MC_JG;
	}

	public String getORG_MC_JG() {
		return this.ORG_MC_JG;
	}

	public void setORG_MC_BM(String ORG_MC_BM) {
		this.ORG_MC_BM = ORG_MC_BM;
	}

	public String getORG_MC_BM() {
		return this.ORG_MC_BM;
	}

	public void setUSER_MC(String USER_MC) {
		this.USER_MC = USER_MC;
	}

	public String getUSER_MC() {
		return this.USER_MC;
	}

	public void setMSG(String MSG) {
		this.MSG = MSG;
	}

	public String getMSG() {
		return this.MSG;
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

	public String getSMSTYPE_DM() {
		if(SMSTYPE_DM==null) {
			SMSTYPE_DM = "06";
		}
		return SMSTYPE_DM;
	}

	public void setSMSTYPE_DM(String sMSTYPE_DM) {
		SMSTYPE_DM = sMSTYPE_DM;
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

	public Date getFS_SJ() {
		return FS_SJ;
	}

	public void setFS_SJ(Date fS_SJ) {
		FS_SJ = fS_SJ;
	}

	public String getFSCS() {
		return FSCS;
	}

	public void setFSCS(String fSCS) {
		FSCS = fSCS;
	}

	public String getZW_DM() {
		return ZW_DM;
	}

	public void setZW_DM(String zW_DM) {
		ZW_DM = zW_DM;
	}

	public String getZW_MC() {
		return ZW_MC;
	}

	public void setZW_MC(String zW_MC) {
		ZW_MC = zW_MC;
	}

	public String getMSG2() {
		return MSG2;
	}

	public void setMSG2(String mSG2) {
		MSG2 = mSG2;
	}

	public String getRECEIVETIME() {
		return RECEIVETIME;
	}

	public void setRECEIVETIME(String rECEIVETIME) {
		RECEIVETIME = rECEIVETIME;
	}

}
