package com.gwinsoft.components.nsrsms.group;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NSRDATAMX {

	private String NSRDATAMX_LSH;// 流水号
	private String NSRDATA_LSH;// 主表流水号
	private String NSRBM;// 纳税人编码
	private String NSRMC;// 纳税人名称
	private String SJHM;// 手机号码
	private Date CBRQQ;// 有效日期起
	private Date CBRQZ;// 有效日期止
	private String ZSXM;// 征收项目
	private String JE;// 金额
	private String ZSPM;// 征收品目
	private Date SKSSQ_Q;// 税款所属期起
	private Date SKSSQ_Z;// 税款所属期止
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间
	
	private String STR_CBRQQ;
	private String STR_CBRQZ;
	private String STR_SKSSQ_Q;
	private String STR_SKSSQ_Z;

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

	public void setNSRBM(String NSRBM) {
		this.NSRBM = NSRBM;
	}

	public String getNSRBM() {
		return this.NSRBM;
	}

	public void setNSRMC(String NSRMC) {
		this.NSRMC = NSRMC;
	}

	public String getNSRMC() {
		return this.NSRMC;
	}

	public void setSJHM(String SJHM) {
		this.SJHM = SJHM;
	}

	public String getSJHM() {
		return this.SJHM;
	}

	public void setZSXM(String ZSXM) {
		this.ZSXM = ZSXM;
	}

	public String getZSXM() {
		return this.ZSXM;
	}

	public void setJE(String JE) {
		this.JE = JE;
	}

	public String getJE() {
		return this.JE;
	}

	public void setZSPM(String ZSPM) {
		this.ZSPM = ZSPM;
	}

	public String getZSPM() {
		return this.ZSPM;
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

	public Date getSKSSQ_Q() {
		return SKSSQ_Q;
	}

	public void setSKSSQ_Q(Date sKSSQ_Q) {
		SKSSQ_Q = sKSSQ_Q;
	}

	public Date getSKSSQ_Z() {
		return SKSSQ_Z;
	}

	public void setSKSSQ_Z(Date sKSSQ_Z) {
		SKSSQ_Z = sKSSQ_Z;
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

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public String getSTR_CBRQQ() {
		if(CBRQQ==null) {
			return "";
		}
		return sdf.format(CBRQQ);
	}

	public String getSTR_CBRQZ() {
		if(CBRQQ==null) {
			return "";
		}
		return sdf.format(CBRQZ);
	}

	public String getSTR_SKSSQ_Q() {
		if(CBRQQ==null) {
			return "";
		}
		return sdf.format(SKSSQ_Q);
	}

	public String getSTR_SKSSQ_Z() {
		if(CBRQQ==null) {
			return "";
		}
		return sdf.format(SKSSQ_Z);
	}	
}
