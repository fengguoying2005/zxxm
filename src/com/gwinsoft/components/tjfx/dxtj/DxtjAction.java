package com.gwinsoft.components.tjfx.dxtj;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.print.CommonExcelPrintBean;
import com.gwinsoft.framework.print.CommonExcelPrintData;
import com.gwinsoft.framework.print.ICommonExcelPrintBean;
import com.gwinsoft.framework.print.PrintServiceConstants;
import com.gwinsoft.framework.print.interfaces.IComplexPrintData;
import com.gwinsoft.framework.web.action.BaseAction;
import com.gwinsoft.util.GwinSoftUtil;

public class DxtjAction extends BaseAction<BaseIAO> {
	private Dxtj dxtj;
	private List<Dxtj> dxtjs;
	private String FIND_ORG_DM;
	private String FIND_YYS_DM;
	private String FIND_SMSTYPE_DM;
	private String FIND_KS_RQ;
	private String FIND_JZ_RQ;
	private String FIND_YYS;
	private String FIND_ORG;
	private String FIND_SMSTYPE;
	
	private Map org_dmmap = null;
	private Map yys_dmmap = null;
	private Map smstype_dmmap = null;

	public String list() {
		if(FIND_KS_RQ==null || "".equals(FIND_KS_RQ)) {
			FIND_KS_RQ = GwinSoftUtil.getFirstDayOfMonth();
			FIND_JZ_RQ = GwinSoftUtil.getLastDayOfMonth();
		}
		this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
		putRequestData("FIND_KS_RQ", FIND_KS_RQ);
		putRequestData("FIND_JZ_RQ", FIND_JZ_RQ);
		putRequestData("FIND_YYS_DM", FIND_YYS_DM);
		putRequestData("FIND_ORG_DM", FIND_ORG_DM);
		putRequestData("FIND_SMSTYPE_DM", FIND_SMSTYPE_DM);
		putRequestData("FIND_YYS", FIND_YYS);
		putRequestData("FIND_ORG", FIND_ORG);
		putRequestData("FIND_SMSTYPE", FIND_SMSTYPE);
		this.invokeInteraction("list");
		dxtjs = this.getResponseData("dxtjs");
		return "list";
	}

	public String printExcel() {
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY,
				"ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY,
				getExcelPrintData());
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY,
				"SmsStatistics");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY,
				PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}

	public IComplexPrintData getExcelPrintData() {
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[5][2];
		attrs[0][0] = FIND_ORG_DM;
		attrs[0][1] = FIND_ORG_DM;
		attrs[1][0] = FIND_YYS_DM;
		attrs[1][1] = FIND_YYS_DM;
		attrs[2][0] = FIND_SMSTYPE_DM;
		attrs[2][1] = FIND_SMSTYPE_DM;
		attrs[3][0] = FIND_KS_RQ;
		attrs[3][1] = FIND_KS_RQ;
		attrs[4][0] = FIND_JZ_RQ;
		attrs[4][1] = FIND_JZ_RQ;
		int i = 1;
		for (Dxtj _dxtj : dxtjs) {
			list.add(new CommonExcelPrintBean()
					.setObject(""+i)
					.setObject(_dxtj.getORG_DM())
					.setObject(_dxtj.getYYS_DM())
					.setObject(_dxtj.getSMSTYPE_DM())
					.setObject(_dxtj.getDXSL())
					.setObject(_dxtj.getDXJE())
			);
			i ++;
		}
		CommonExcelPrintData data = new CommonExcelPrintData(attrs, list, 2);
		return data;
	}

	public void setDxtj(Dxtj dxtj) {
		this.dxtj = dxtj;
	}

	public Dxtj getDxtj() {
		return this.dxtj;
	}

	public void setDxtjs(List<Dxtj> dxtjs) {
		this.dxtjs = dxtjs;
	}

	public List<Dxtj> getDxtjs() {
		return this.dxtjs;
	}

	public void setFIND_ORG_DM(String FIND_ORG_DM) {
		this.FIND_ORG_DM = FIND_ORG_DM;
	}

	public String getFIND_ORG_DM() {
		return this.FIND_ORG_DM;
	}

	public void setFIND_YYS_DM(String FIND_YYS_DM) {
		this.FIND_YYS_DM = FIND_YYS_DM;
	}

	public String getFIND_YYS_DM() {
		return this.FIND_YYS_DM;
	}

	public void setFIND_SMSTYPE_DM(String FIND_SMSTYPE_DM) {
		this.FIND_SMSTYPE_DM = FIND_SMSTYPE_DM;
	}

	public String getFIND_SMSTYPE_DM() {
		return this.FIND_SMSTYPE_DM;
	}

	public void setFIND_KS_RQ(String FIND_KS_RQ) {
		this.FIND_KS_RQ = FIND_KS_RQ;
	}

	public String getFIND_KS_RQ() {
		return this.FIND_KS_RQ;
	}

	public void setFIND_JZ_RQ(String FIND_JZ_RQ) {
		this.FIND_JZ_RQ = FIND_JZ_RQ;
	}

	public String getFIND_JZ_RQ() {
		return this.FIND_JZ_RQ;
	}

	public Map getOrg_dmmap() {
		User user = UserHelper.getLoginUserFromRequest(request);
		Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
		return DMB.sortByKey(dmb,true);
	}

	public void setOrg_dmmap(Map org_dmmap) {
		this.org_dmmap = org_dmmap;
	}

	public Map getYys_dmmap() {
		return DMB.sortByKey(DMB.getDMB("YYS_CACHE", "YYS_MC"), true);
	}

	public void setYys_dmmap(Map yys_dmmap) {
		this.yys_dmmap = yys_dmmap;
	}

	public Map getSmstype_dmmap() {
		return DMB
				.sortByKey(DMB.getDMB("SMSTYPE_CACHE", "SMSTYPE_MC"), true);
	}

	public void setSmstype_dmmap(Map smstype_dmmap) {
		this.smstype_dmmap = smstype_dmmap;
	}

	public String getFIND_YYS() {
		return FIND_YYS;
	}

	public void setFIND_YYS(String fIND_YYS) {
		FIND_YYS = fIND_YYS;
	}

	public String getFIND_ORG() {
		return FIND_ORG;
	}

	public void setFIND_ORG(String fIND_ORG) {
		FIND_ORG = fIND_ORG;
	}

	public String getFIND_SMSTYPE() {
		return FIND_SMSTYPE;
	}

	public void setFIND_SMSTYPE(String fIND_SMSTYPE) {
		FIND_SMSTYPE = fIND_SMSTYPE;
	}
	
}
