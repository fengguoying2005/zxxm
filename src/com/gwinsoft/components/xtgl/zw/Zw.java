package com.gwinsoft.components.xtgl.zw;

import java.util.Date;

public class Zw {

	private String ZW_DM;// 职务代码
	private String ZW_MC;// 职务名称
	private String BZ;// 备注
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间

	public void setZW_DM(String ZW_DM) {
		this.ZW_DM = ZW_DM;
	}

	public String getZW_DM() {
		return this.ZW_DM;
	}

	public void setZW_MC(String ZW_MC) {
		this.ZW_MC = ZW_MC;
	}

	public String getZW_MC() {
		return this.ZW_MC;
	}

	public void setBZ(String BZ) {
		this.BZ = BZ;
	}

	public String getBZ() {
		return this.BZ;
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
