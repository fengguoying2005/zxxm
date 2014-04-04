package com.gwinsoft.components.czysms.group;

import java.util.Date;

public class GROUPMX {

	private String GROUPMX_LSH;// 流水号
	private String GROUP_LSH;// 主表流水号
	private String ORG_DM_JG;// 机构
	private String ORG_DM_BM;// 部门
	private String USER_DM;// 人员
	private String SJHM;// 手机号码

	private String ORG_MC_JG;// 机构
	private String ORG_MC_BM;// 部门
	private String USER_MC;// 人员
	
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间
	
	private String ZW_DM;
	private String ZW_MC;

	public void setGROUPMX_LSH(String GROUPMX_LSH) {
		this.GROUPMX_LSH = GROUPMX_LSH;
	}

	public String getGROUPMX_LSH() {
		return this.GROUPMX_LSH;
	}

	public void setGROUP_LSH(String GROUP_LSH) {
		this.GROUP_LSH = GROUP_LSH;
	}

	public String getGROUP_LSH() {
		return this.GROUP_LSH;
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

	public String getORG_MC_JG() {
		return ORG_MC_JG;
	}

	public void setORG_MC_JG(String oRG_MC_JG) {
		ORG_MC_JG = oRG_MC_JG;
	}

	public String getORG_MC_BM() {
		return ORG_MC_BM;
	}

	public void setORG_MC_BM(String oRG_MC_BM) {
		ORG_MC_BM = oRG_MC_BM;
	}

	public String getUSER_MC() {
		return USER_MC;
	}

	public void setUSER_MC(String uSER_MC) {
		USER_MC = uSER_MC;
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
	
}
