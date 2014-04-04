package com.gwinsoft.components.qxgl.user;

import java.util.Date;
import java.util.List;

import com.gwinsoft.components.qxgl.role.Role;

public class User {

	private String USER_DM;// 用户编码
	private String USER_MC;// 用户名称
	private String PASSWORD;// 密码
	private String XB_DM;// 性别
	private String ORG_DM_JG;// 机关
	private String ORG_DM_BM;// 部门
	private String SFZHM;// 身份证号
	private String TEL;// 电话
	private String JG;// 籍贯
	private String YZBM;// 邮政编码
	private String EMAIL;// EMAIL
	private String ADDR;// 地址
	private String BZ;// 备注
	private String YX_BJ;// 有效标记
	private String LRRY_DM;// 录入人员
	private String XGRY_DM;// 修改人员
	private Date LR_SJ;// 录入时间
	private Date XG_SJ;// 修改时间
	private String ZW_DM;
	private String JGPX;
	private String CZY_BJ = "1";

	private List<Role> roles;

	public void setUSER_DM(String USER_DM) {
		this.USER_DM = USER_DM;
	}

	public String getUSER_DM() {
		return this.USER_DM;
	}

	public void setUSER_MC(String USER_MC) {
		this.USER_MC = USER_MC;
	}

	public String getUSER_MC() {
		return this.USER_MC;
	}

	public void setPASSWORD(String PASSWORD) {
		this.PASSWORD = PASSWORD;
	}

	public String getPASSWORD() {
		return this.PASSWORD;
	}

	public void setXB_DM(String XB_DM) {
		this.XB_DM = XB_DM;
	}

	public String getXB_DM() {
		return this.XB_DM;
	}

	public void setORG_DM_JG(String ORG_DM_JG) {
		this.ORG_DM_JG = ORG_DM_JG;
	}

	public String getORG_DM_JG() {
		return this.ORG_DM_JG;
	}

	public void setORG_DM_BM(String ORG_DM_BM) {
		this.ORG_DM_BM = ORG_DM_BM;
	}

	public String getORG_DM_BM() {
		return this.ORG_DM_BM;
	}

	public void setSFZHM(String SFZHM) {
		this.SFZHM = SFZHM;
	}

	public String getSFZHM() {
		return this.SFZHM;
	}

	public void setTEL(String TEL) {
		this.TEL = TEL;
	}

	public String getTEL() {
		return this.TEL;
	}

	public void setJG(String JG) {
		this.JG = JG;
	}

	public String getJG() {
		return this.JG;
	}

	public void setYZBM(String YZBM) {
		this.YZBM = YZBM;
	}

	public String getYZBM() {
		return this.YZBM;
	}

	public void setEMAIL(String EMAIL) {
		this.EMAIL = EMAIL;
	}

	public String getEMAIL() {
		return this.EMAIL;
	}

	public void setADDR(String ADDR) {
		this.ADDR = ADDR;
	}

	public String getADDR() {
		return this.ADDR;
	}

	public void setBZ(String BZ) {
		this.BZ = BZ;
	}

	public String getBZ() {
		return this.BZ;
	}

	public void setYX_BJ(String YX_BJ) {
		this.YX_BJ = YX_BJ;
	}

	public String getYX_BJ() {
		return this.YX_BJ;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getZW_DM() {
		return ZW_DM;
	}

	public void setZW_DM(String zW_DM) {
		ZW_DM = zW_DM;
	}

	public String getJGPX() {
		return JGPX;
	}

	public void setJGPX(String jGPX) {
		JGPX = jGPX;
	}

	public String getCZY_BJ() {
		return CZY_BJ;
	}

	public void setCZY_BJ(String cZY_BJ) {
		CZY_BJ = cZY_BJ;
	}

}
