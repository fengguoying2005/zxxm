package com.gwinsoft.components.orgsms.group;

import java.util.Date;

public class Orggroup {

	private String GROUP_LSH;// 流水号
	private String ORG_DM_JG;// 税务机关
	private String GROUP_NAME;// 群组名称
	private String GROUP_COUNT;// 群组数量
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间

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

	public void setGROUP_NAME(String GROUP_NAME) {
		this.GROUP_NAME = GROUP_NAME;
	}

	public String getGROUP_NAME() {
		return this.GROUP_NAME;
	}

	public void setGROUP_COUNT(String GROUP_COUNT) {
		this.GROUP_COUNT = GROUP_COUNT;
	}

	public String getGROUP_COUNT() {
		return this.GROUP_COUNT;
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
