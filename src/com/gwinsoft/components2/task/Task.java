package com.gwinsoft.components2.task;

public class Task {

	private String TYPE_DM;// 主键
	private String TYPE_DM2;// 主键2
	private String CRON;// 时间表达式
	private String BZ;// 备注
	private String YX_BJ;// 有效标记
	private String SCFS_SJ;
	private String SCJS_SJ;
	
	public String getTYPE_DM() {
		return TYPE_DM;
	}
	public void setTYPE_DM(String tYPE_DM) {
		TYPE_DM = tYPE_DM;
	}
	public String getTYPE_DM2() {
		return TYPE_DM2;
	}
	public void setTYPE_DM2(String tYPE_DM2) {
		TYPE_DM2 = tYPE_DM2;
	}
	public String getCRON() {
		return CRON;
	}
	public void setCRON(String cRON) {
		CRON = cRON;
	}
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	public String getYX_BJ() {
		return YX_BJ;
	}
	public void setYX_BJ(String yX_BJ) {
		YX_BJ = yX_BJ;
	}
	public String getSCFS_SJ() {
		return SCFS_SJ;
	}
	public void setSCFS_SJ(String sCFS_SJ) {
		SCFS_SJ = sCFS_SJ;
	}
	public String getSCJS_SJ() {
		return SCJS_SJ;
	}
	public void setSCJS_SJ(String sCJS_SJ) {
		SCJS_SJ = sCJS_SJ;
	}
}