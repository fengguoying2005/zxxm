package com.gwinsoft.components2.yw.fpzjwh;

import java.util.Date;

public class Fpzjwh {

	private String LSH;// 流水号
	private String SSX;// 所属市
	private String KJNY;// 开奖年月
	private String INFO;// 中奖内容
	private String BZ;// 备注
	private Date LR_SJ;// 录入人员
	private Date XG_SJ;// 修改人员
	private String LRRY_DM;// 录入时间
	private String XGRY_DM;// 修改时间
	private String LRSJ;
	private String XGSJ;

	public void setLSH(String LSH) {
		this.LSH = LSH;
	}

	public String getLSH() {
		return this.LSH;
	}

	public void setSSX(String SSX) {
		this.SSX = SSX;
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

	public String getSSX() {
		return this.SSX;
	}

	public void setKJNY(String KJNY) {
		this.KJNY = KJNY;
	}

	public String getKJNY() {
		return this.KJNY;
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
}
