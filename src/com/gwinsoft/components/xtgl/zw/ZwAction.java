package com.gwinsoft.components.xtgl.zw;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.gwinsoft.components.dmb.DMB;
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

public class ZwAction extends BaseAction<BaseIAO> {
		private Zw zw;
		private List<Zw> zws;
		private PageBean pageBean = new PageBean(10);
		private String ZW_DM;
		public String list(){
			this.putRequestData("ZW_DM", ZW_DM);
				putRequestData("pageBean", pageBean);
				this.invokeInteraction("list");
				zws = this.getResponseData("zws");
				return "list";
		}
		public String toAdd() {
			return "add";
		}
		public String toMod() {
			see();
			return "mod";
		}
		public String del() {
			this.putRequestData("ZW_DM", ZW_DM);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String see() {
			this.putRequestData("ZW_DM", ZW_DM);
			this.invokeInteraction("see");
			zw = this.getResponseData("zw");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("zw", zw);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.ZW_DM = this.getResponseData("uid");
			if(resEnv.getAPPException()!=null) {return "add";}			return "mod";
		}
		public String mod() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("ZW_DM", ZW_DM);
			this.putRequestData("zw", zw);
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
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY, "ComplexExcelPrintTest");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY, PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}
	public IComplexPrintData getExcelPrintData() {
   pageBean=null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[0][2];
		for (Zw _zw: zws) {
			list.add(new CommonExcelPrintBean().setObject(_zw.getZW_DM()).setObject(_zw.getZW_MC()).setObject(_zw.getBZ()).setObject(_zw.getLRRY_DM()).setObject(_zw.getXGRY_DM()).setObject(_zw.getLR_SJ()).setObject(_zw.getXG_SJ()));
		}
		CommonExcelPrintData data = new CommonExcelPrintData(attrs, list, 5);
		return data;
	}
		public void setPageBean(PageBean pageBean) {
				this.pageBean=pageBean;
		}
		public PageBean getPageBean(){
				return this.pageBean;
		}
		public void setZw(Zw zw) {
				this.zw=zw;
		}
		public Zw getZw(){
				return this.zw;
		}
		public void setZws(List<Zw> zws) {
				this.zws=zws;
		}
		public List<Zw> getZws(){
				return this.zws;
		}
		public void setZW_DM(String ZW_DM) {
				this.ZW_DM=ZW_DM;
		}
		public String getZW_DM(){
				return this.ZW_DM;
		}
}
