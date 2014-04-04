package com.gwinsoft.components.tjfx.dxtj;

import java.util.Date;

public class Dxtj {

	private String ORG_DM;// 税务机关
	private String YYS_DM;// 运营商
	private String SMSTYPE_DM;// 短信类型
	private String DXSL;// 短信数量
	private String DXJE;// 短信金额
	private Date KS_RQ;// 开始日期
	private Date JZ_RQ;// 截止日期

	public void setORG_DM(String ORG_DM) {
		this.ORG_DM = ORG_DM;
	}

	public String getORG_DM() {
		return this.ORG_DM;
	}

	public void setYYS_DM(String YYS_DM) {
		this.YYS_DM = YYS_DM;
	}

	public String getYYS_DM() {
		return this.YYS_DM;
	}

	public void setSMSTYPE_DM(String SMSTYPE_DM) {
		this.SMSTYPE_DM = SMSTYPE_DM;
	}

	public String getSMSTYPE_DM() {
		return this.SMSTYPE_DM;
	}

	public void setDXSL(String DXSL) {
		this.DXSL = DXSL;
	}

	public String getDXSL() {
		return this.DXSL;
	}

	public void setDXJE(String DXJE) {
		this.DXJE = DXJE;
	}

	public String getDXJE() {
		return this.DXJE;
	}

	public Date getKS_RQ() {
		return KS_RQ;
	}

	public void setKS_RQ(Date kS_RQ) {
		KS_RQ = kS_RQ;
	}

	public Date getJZ_RQ() {
		return JZ_RQ;
	}

	public void setJZ_RQ(Date jZ_RQ) {
		JZ_RQ = jZ_RQ;
	}

}
