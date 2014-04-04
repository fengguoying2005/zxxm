package com.gwinsoft.components.qxgl.role;

import java.util.Date;

public class Role {

	private String ROLE_LSH;// 角色流水号
	private String ROLE_MC;// 角色名称
	private String ORG_DM_JG;// 税务机关
	private String ROLE_MS;// 角色描述
	private String YX_BJ;// 有效标记
	private String XJYX_BJ;// 下级有效标记
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间

	public void setROLE_LSH(String ROLE_LSH) {
		this.ROLE_LSH = ROLE_LSH;
	}

	public String getROLE_LSH() {
		return this.ROLE_LSH;
	}

	public void setROLE_MC(String ROLE_MC) {
		this.ROLE_MC = ROLE_MC;
	}

	public String getROLE_MC() {
		return this.ROLE_MC;
	}

	public void setORG_DM_JG(String ORG_DM_JG) {
		this.ORG_DM_JG = ORG_DM_JG;
	}

	public String getORG_DM_JG() {
		return this.ORG_DM_JG;
	}

	public void setROLE_MS(String ROLE_MS) {
		this.ROLE_MS = ROLE_MS;
	}

	public String getROLE_MS() {
		return this.ROLE_MS;
	}

	public void setYX_BJ(String YX_BJ) {
		this.YX_BJ = YX_BJ;
	}

	public String getYX_BJ() {
		return this.YX_BJ;
	}

	public void setXJYX_BJ(String XJYX_BJ) {
		this.XJYX_BJ = XJYX_BJ;
	}

	public String getXJYX_BJ() {
		return this.XJYX_BJ;
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
