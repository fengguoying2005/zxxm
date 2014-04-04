package com.gwinsoft.components2.yw.tsjb;

import java.util.Date;

public class Tsjb {

	private String LSH;// 流水号
	private String SJHM;// 手机号码
	private String TSINFO;// 投诉内容
	private Date TS_SJ;// 投诉时间
	private Date TS_SJ2;// 投诉时间止
	private String HF_BJ;// 回复标记
	private String TSSJ;
	
	public Date getTS_SJ() {
		return TS_SJ;
	}

	public void setTS_SJ(Date tS_SJ) {
		TS_SJ = tS_SJ;
	}

	public Date getTS_SJ2() {
		return TS_SJ2;
	}

	public void setTS_SJ2(Date tS_SJ2) {
		TS_SJ2 = tS_SJ2;
	}

	public String getTSSJ() {
		return TSSJ;
	}

	public void setTSSJ(String tSSJ) {
		TSSJ = tSSJ;
	}

	public void setLSH(String LSH) {
		this.LSH = LSH;
	}

	public String getLSH() {
		return this.LSH;
	}

	public void setSJHM(String SJHM) {
		this.SJHM = SJHM;
	}

	public String getSJHM() {
		return this.SJHM;
	}

	public void setTSINFO(String TSINFO) {
		this.TSINFO = TSINFO;
	}

	public String getTSINFO() {
		return this.TSINFO;
	}

	public void setHF_BJ(String HF_BJ) {
		this.HF_BJ = HF_BJ;
	}

	public String getHF_BJ() {
		return this.HF_BJ;
	}
}
