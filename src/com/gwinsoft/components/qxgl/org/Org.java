package com.gwinsoft.components.qxgl.org;

import java.util.Date;

public class Org{

private String ORG_DM;//机构代码
private String ORG_MC;//机构名称
private String ORG_TYPE;//机构类型
private String ORG_DESC;//机构描述
private String SJ_ORG_DM;//上级机构
private String YX_BJ;//有效标记
private String LRRY_DM;//录入人员
private String XGRY_DM;//修改人员
private Date LR_SJ;//录入时间
private Date XG_SJ;//修改时间
private String JGPX;

public void setORG_DM(String ORG_DM) {
		this.ORG_DM=ORG_DM;
}
public String getORG_DM(){
		return this.ORG_DM;
}
public void setORG_MC(String ORG_MC) {
		this.ORG_MC=ORG_MC;
}
public String getORG_MC(){
		return this.ORG_MC;
}
public void setORG_TYPE(String ORG_TYPE) {
		this.ORG_TYPE=ORG_TYPE;
}
public String getORG_TYPE(){
		return this.ORG_TYPE;
}
public void setORG_DESC(String ORG_DESC) {
		this.ORG_DESC=ORG_DESC;
}
public String getORG_DESC(){
		return this.ORG_DESC;
}
public void setSJ_ORG_DM(String SJ_ORG_DM) {
		this.SJ_ORG_DM=SJ_ORG_DM;
}
public String getSJ_ORG_DM(){
		return this.SJ_ORG_DM;
}
public void setYX_BJ(String YX_BJ) {
		this.YX_BJ=YX_BJ;
}
public String getYX_BJ(){
		return this.YX_BJ;
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
public String getJGPX() {
	return JGPX;
}
public void setJGPX(String jGPX) {
	JGPX = jGPX;
}

}
