package com.gwinsoft.components.xtgl.dxcs;

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

public class DxcsAction extends BaseAction<BaseIAO> {
		private Dxcs dxcs;
		private List<Dxcs> dxcss;
		private PageBean pageBean = new PageBean(10);
		private String YYS_DM;
		private Map yys_dmmap = null;
		public String list(){
			this.putRequestData("YYS_DM", YYS_DM);
				putRequestData("pageBean", pageBean);
				this.invokeInteraction("list");
				dxcss = this.getResponseData("dxcss");
				return "list";
		}
		public String toAdd() {
			return "add";
		}
		public String toMod() {
			this.putRequestData("YYS_DM", YYS_DM);
			putRequestData("pageBean", null);
			this.invokeInteraction("list");
			dxcss = this.getResponseData("dxcss");
			return "mod";
		}
		public String del() {
			this.putRequestData("YYS_DM", YYS_DM);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String see() {
			this.putRequestData("YYS_DM", YYS_DM);
			this.invokeInteraction("see");
			dxcs = this.getResponseData("dxcs");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("dxcs", dxcs);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.YYS_DM = this.getResponseData("uid");
			if(resEnv.getAPPException()!=null) {return "add";}			return "mod";
		}
		public String mod() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("dxcss", dxcss);
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
		for (Dxcs _dxcs: dxcss) {
			list.add(new CommonExcelPrintBean().setObject(_dxcs.getYYS_DM()).setObject(_dxcs.getDXJG()).setObject(_dxcs.getBZ()).setObject(_dxcs.getLRRY_DM()).setObject(_dxcs.getXGRY_DM()).setObject(_dxcs.getLR_SJ()).setObject(_dxcs.getXG_SJ()));
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
		public void setDxcs(Dxcs dxcs) {
				this.dxcs=dxcs;
		}
		public Dxcs getDxcs(){
				return this.dxcs;
		}
		public void setDxcss(List<Dxcs> dxcss) {
				this.dxcss=dxcss;
		}
		public List<Dxcs> getDxcss(){
				return this.dxcss;
		}
		public void setYYS_DM(String YYS_DM) {
				this.YYS_DM=YYS_DM;
		}
		public String getYYS_DM(){
				return this.YYS_DM;
		}
		public Map getYys_dmmap() {
			return DMB.getDMB("YYS_CACHE", "YYS_MC");
		}
		public void setYys_dmmap(Map yys_dmmap) {
			this.yys_dmmap = yys_dmmap;
		}
}
