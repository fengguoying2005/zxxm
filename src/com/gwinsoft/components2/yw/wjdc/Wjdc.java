package com.gwinsoft.components2.yw.wjdc;

import java.util.Date;

public class Wjdc {

	private String LSH;// 流水号
	private String INFO;// 问卷内容
	private String BZ;// 特征码
	private String SFJS;// 是否结束
	private String DCJL;// 调查结论
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间
	private String LRSJ;// 录入时间
	private String XGSJ;// 修改时间

	public void setLSH(String LSH) {
		this.LSH = LSH;
	}

	public String getLSH() {
		return this.LSH;
	}

	public void setINFO(String INFO) {
		this.INFO = INFO;
	}

	public String getINFO() {
		return this.INFO;
	}

	public void setBZ(String BZ) {
		this.BZ = BZ;
	}

	public String getBZ() {
		return this.BZ;
	}

	public void setSFJS(String SFJS) {
		this.SFJS = SFJS;
	}

	public String getSFJS() {
		return this.SFJS;
	}

	public void setDCJL(String DCJL) {
		this.DCJL = DCJL;
	}

	public String getDCJL() {
		return this.DCJL;
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

	public String getLRSJ() {
		return LRSJ;
	}

	public void setLRSJ(String lRSJ) {
		LRSJ = lRSJ;
	}

	public String getXGSJ() {
		return XGSJ;
	}

	public void setXGSJ(String xGSJ) {
		XGSJ = xGSJ;
	}

}
