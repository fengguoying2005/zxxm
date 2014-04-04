package com.gwinsoft.components.xtgl.dxcs;

import java.util.Date;

public class Dxcs {

	private String YYS_DM;// 运营商
	private String YYS_MC;// 运营商
	private String DXJG;// 短信价格
	private String BZ;// 备注
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间

	public void setYYS_DM(String YYS_DM) {
		this.YYS_DM = YYS_DM;
	}

	public String getYYS_DM() {
		return this.YYS_DM;
	}

	public void setDXJG(String DXJG) {
		this.DXJG = DXJG;
	}

	public String getDXJG() {
		return this.DXJG;
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

	public String getYYS_MC() {
		return YYS_MC;
	}

	public void setYYS_MC(String yYS_MC) {
		YYS_MC = yYS_MC;
	}
	
}
