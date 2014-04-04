package com.gwinsoft.components.nsrsms.smssend;

import java.util.Date;

public class Nsrmsg {
	private String NSRMSG_LSH;//流水号
	private String NSRDATA_LSH;//主表流水号
	private String NSRBM;//纳税人编码
	private String NSRMC;//纳税人名称
	private String SJHM;//手机号码
	private Date CBRQQ;//有效日期起
	private Date CBRQZ;//有效日期止
	private String MSG;//
	private String JE;//金额
	private String SMSTYPE_DM;//
	private String FS_SJ;//
	private String LRRY_DM;//录入人员
	private String XGRY_DM;//修改人员
	private Date LR_SJ;//录入时间
	private Date XG_SJ;//修改时间
	private String FSCS;
	
	public String getNSRMSG_LSH() {
		return NSRMSG_LSH;
	}
	public void setNSRMSG_LSH(String nSRMSG_LSH) {
		NSRMSG_LSH = nSRMSG_LSH;
	}
	public String getNSRDATA_LSH() {
		return NSRDATA_LSH;
	}
	public void setNSRDATA_LSH(String nSRDATA_LSH) {
		NSRDATA_LSH = nSRDATA_LSH;
	}
	public String getNSRBM() {
		return NSRBM;
	}
	public void setNSRBM(String nSRBM) {
		NSRBM = nSRBM;
	}
	public String getNSRMC() {
		return NSRMC;
	}
	public void setNSRMC(String nSRMC) {
		NSRMC = nSRMC;
	}
	public String getSJHM() {
		return SJHM;
	}
	public void setSJHM(String sJHM) {
		SJHM = sJHM;
	}

	public String getMSG() {
		return MSG;
	}
	public void setMSG(String mSG) {
		MSG = mSG;
	}
	public String getJE() {
		return JE;
	}
	public void setJE(String jE) {
		JE = jE;
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
	public String getFS_SJ() {
		if(FS_SJ==null) {
			FS_SJ = "";
		}
		return FS_SJ;
	}
	public void setFS_SJ(String fS_SJ) {
		FS_SJ = fS_SJ;
	}
	public String getLRRY_DM() {
		return LRRY_DM;
	}
	public void setLRRY_DM(String lRRY_DM) {
		LRRY_DM = lRRY_DM;
	}
	public String getXGRY_DM() {
		return XGRY_DM;
	}
	public void setXGRY_DM(String xGRY_DM) {
		XGRY_DM = xGRY_DM;
	}
	
	public Date getCBRQQ() {
		return CBRQQ;
	}
	public void setCBRQQ(Date cBRQQ) {
		CBRQQ = cBRQQ;
	}
	public Date getCBRQZ() {
		return CBRQZ;
	}
	public void setCBRQZ(Date cBRQZ) {
		CBRQZ = cBRQZ;
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
	public String getFSCS() {
		return FSCS;
	}
	public void setFSCS(String fSCS) {
		FSCS = fSCS;
	}
	
}
