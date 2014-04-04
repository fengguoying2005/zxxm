package com.gwinsoft.components2.yw.tsjb;

import java.util.Date;

import com.gwinsoft.components2.job.SMS;

public class TSJBHF extends SMS {

	private String LSH;// 流水号
	private String ZBLSH;// 主表流水号
	private String HFINFO;// 回复内容
	private String HFR_DM;// 回复人员
	private Date HF_SJ;// 回复时间
	private String HFSJ;// 回复时间
	private String SJHM;

	public void setLSH(String LSH) {
		this.LSH = LSH;
	}

	public String getHFSJ() {
		return HFSJ;
	}

	public void setHFSJ(String hFSJ) {
		HFSJ = hFSJ;
	}

	public void setHF_SJ(Date hF_SJ) {
		HF_SJ = hF_SJ;
	}

	public String getLSH() {
		return this.LSH;
	}

	public void setZBLSH(String ZBLSH) {
		this.ZBLSH = ZBLSH;
	}

	public String getZBLSH() {
		return this.ZBLSH;
	}

	public void setHFINFO(String HFINFO) {
		this.HFINFO = HFINFO;
	}

	public String getHFINFO() {
		return this.HFINFO;
	}

	public void setHFR_DM(String HFR_DM) {
		this.HFR_DM = HFR_DM;
	}

	public String getHFR_DM() {
		return this.HFR_DM;
	}

	public Date getHF_SJ() {
		return HF_SJ;
	}
	public void setSJHM(String SJHM) {
		this.SJHM = SJHM;
	}
	public String getSJHM() {
		return SJHM;
	}

	public String getSMSINFO() {
		return HFINFO;
	}
}