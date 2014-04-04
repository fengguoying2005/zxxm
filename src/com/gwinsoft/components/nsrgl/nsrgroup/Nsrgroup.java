package com.gwinsoft.components.nsrgl.nsrgroup;

import java.util.Date;

public class Nsrgroup {

	private String LSH;// 主键
	private String ORG_DM;// 税务机关
	private String GROUP_NAME;// 群组名称
	private String INFO;// 情况说明
	private String LRRY_DM;// 录入人员
	private String GROUP_COUNT;// 群组数量
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间

	public void setLSH(String LSH) {
		this.LSH = LSH;
	}

	public String getLSH() {
		return this.LSH;
	}

	public void setORG_DM(String ORG_DM) {
		this.ORG_DM = ORG_DM;
	}

	public String getORG_DM() {
		return this.ORG_DM;
	}

	public void setGROUP_NAME(String GROUP_NAME) {
		this.GROUP_NAME = GROUP_NAME;
	}

	public String getGROUP_NAME() {
		return this.GROUP_NAME;
	}

	public void setINFO(String INFO) {
		this.INFO = INFO;
	}

	public String getINFO() {
		return this.INFO;
	}

	public void setLRRY_DM(String LRRY_DM) {
		this.LRRY_DM = LRRY_DM;
	}

	public String getLRRY_DM() {
		return this.LRRY_DM;
	}

	public void setGROUP_COUNT(String GROUP_COUNT) {
		this.GROUP_COUNT = GROUP_COUNT;
	}

	public String getGROUP_COUNT() {
		return this.GROUP_COUNT;
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
