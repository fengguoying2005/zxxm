package com.gwinsoft.components2.dxmb;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.framework.web.action.BaseAction;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.print.PrintServiceConstants;
import com.gwinsoft.framework.print.interfaces.IComplexPrintData;
import com.gwinsoft.framework.print.ICommonExcelPrintBean;
import com.gwinsoft.framework.print.CommonExcelPrintBean;
import com.gwinsoft.framework.print.CommonExcelPrintData;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;

public class Dxmb2Action extends BaseAction<BaseIAO> {
		private Dxmb2 dxmb;
		private List<Dxmb2> dxmbs;
		private PageBean pageBean = new PageBean(10);
		private String DXMB_LSH;
		private Map orgjgmap = null;
		private Map smstypemap = null;
		private Map dxysmap = null;
		public String list(){
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
				putRequestData("pageBean", pageBean);
				this.invokeInteraction("list");
				dxmbs = this.getResponseData("dxmbs");
				return "list";
		}
		public String toAdd() {
			if(dxmb==null) {
				dxmb = new Dxmb2();
			}
			return "add";
		}
		public String toMod() {
			see();
			return "mod";
		}
		public String del() {
			this.putRequestData("DXMB_LSH", DXMB_LSH);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String see() {
			this.putRequestData("DXMB_LSH", DXMB_LSH);
			this.invokeInteraction("see");
			dxmb = this.getResponseData("dxmb");
			return "see";
		}
		public String add() {
			String msg = DxmbUtil.validateDx(dxmb);
			if(msg==null || "".equals(msg)) {
				
			} else {
				request.setAttribute("message", msg);
				return "add";
			}
			
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("dxmb", dxmb);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.DXMB_LSH = this.getResponseData("uid");
			if (resEnv.getAPPException() != null) {
				return "add";
			}
			return "mod";
		}
		public String validatedxmb() {
			dxmb.setDXLX_DM(DXMB_LSH);
			String msg = DxmbUtil.validateDx(dxmb);
			if(msg==null || "".equals(msg)) {
				msg = "验证通过。";
			}
			request.setAttribute("message", msg);
			if(DXMB_LSH==null) {
				return "add";
			} else {
				return "mod";
			}
		}
		public String mod() {
			dxmb.setDXLX_DM(DXMB_LSH);
			String msg = DxmbUtil.validateDx(dxmb);
			if(msg==null || "".equals(msg)) {
				
			} else {
				request.setAttribute("message", msg);
				return "mod";
			}
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("DXMB_LSH", DXMB_LSH);
			this.putRequestData("dxmb", dxmb);
			this.invokeInteraction("mod");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return "mod";
		}
	public String printExcel() {
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY, "ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY, getExcelPrintData());
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "SmsTemplates");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}
	public IComplexPrintData getExcelPrintData() {
		pageBean=null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[0][2];
		int i = 0;
		for (Dxmb2 _dxmb: dxmbs) {
			i ++;
			list.add(new CommonExcelPrintBean().setObject(""+i)
					.setObject(_dxmb.getINFO())
					.setObject(_dxmb.getBZ()));
		}
		CommonExcelPrintData data = new CommonExcelPrintData(attrs, list, 2);
		return data;
	}
		public void setPageBean(PageBean pageBean) {
				this.pageBean=pageBean;
		}
		public PageBean getPageBean(){
				return this.pageBean;
		}
		public void setDxmb(Dxmb2 dxmb) {
				this.dxmb=dxmb;
		}
		public Dxmb2 getDxmb(){
				return this.dxmb;
		}
		public void setDxmbs(List<Dxmb2> dxmbs) {
				this.dxmbs=dxmbs;
		}
		public List<Dxmb2> getDxmbs(){
				return this.dxmbs;
		}
		public void setDXMB_LSH(String DXMB_LSH) {
				this.DXMB_LSH=DXMB_LSH;
		}
		public String getDXMB_LSH(){
				return this.DXMB_LSH;
		}
		public Map getOrgjgmap() {
			User user = UserHelper.getLoginUserFromRequest(request);
			Map dmb = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
			return DMB.sortByKey(dmb,true);
		}
		public void setOrgjgmap(Map orgjgmap) {
			this.orgjgmap = orgjgmap;
		}
		public Map getSmstypemap() {
			return DMB.sortByKey(DMB.getDMB("DXLX_CACHE", "DXLX_MC"),true);
		}
		public void setSmstypemap(Map smstypemap) {
			this.smstypemap = smstypemap;
		}
		public Map getDxysmap() {
			return DMB.sortByKey(DMB.getDMB("DXYS2_CACHE", "DXYS_MC"),true);
		}
		public void setDxysmap(Map dxysmap) {
			this.dxysmap = dxysmap;
		}
		
}
