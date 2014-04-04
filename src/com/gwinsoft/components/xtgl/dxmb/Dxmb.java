package com.gwinsoft.components.xtgl.dxmb;

import java.util.Date;

public class Dxmb{

private String DXMB_LSH;//流水号
private String ORG_DM_JG;//税务机关
private String SMSTYPE_DM;//模板类型
private String DXMB_MC;//模板名称
private String INFO;//模板内容
private String BZ;//备注
private String LRRY_DM;//录入人员
private String XGRY_DM;//修改人员
private Date LR_SJ;//录入时间
private Date XG_SJ;//修改时间

public void setDXMB_LSH(String DXMB_LSH) {
		this.DXMB_LSH=DXMB_LSH;
}
public String getDXMB_LSH(){
		return this.DXMB_LSH;
}
public void setORG_DM_JG(String ORG_DM_JG) {
		this.ORG_DM_JG=ORG_DM_JG;
}
public String getORG_DM_JG(){
		return this.ORG_DM_JG;
}
public void setSMSTYPE_DM(String SMSTYPE_DM) {
		this.SMSTYPE_DM=SMSTYPE_DM;
}
public String getSMSTYPE_DM(){
		return this.SMSTYPE_DM;
}
public void setDXMB_MC(String DXMB_MC) {
		this.DXMB_MC=DXMB_MC;
}
public String getDXMB_MC(){
		return this.DXMB_MC;
}
public void setINFO(String INFO) {
		this.INFO=INFO;
}
public String getINFO(){
		return this.INFO;
}
public void setBZ(String BZ) {
		this.BZ=BZ;
}
public String getBZ(){
		return this.BZ;
}
public void setLRRY_DM(String LRRY_DM) {
		this.LRRY_DM=LRRY_DM;
}
public String getLRRY_DM(){
		return this.LRRY_DM;
}
public void setXGRY_DM(String XGRY_DM) {
		this.XGRY_DM=XGRY_DM;
}
public String getXGRY_DM(){
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
