package com.gwinsoft.components.xtgl.dxqm;

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

public class DxqmAction extends BaseAction<BaseIAO> {
		private Dxqm dxqm;
		private List<Dxqm> dxqms;
		private PageBean pageBean = new PageBean(10);
		private String DXQM_DM;
		public String list(){
				putRequestData("pageBean", pageBean);
				this.invokeInteraction("list");
				dxqms = this.getResponseData("dxqms");
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
			this.putRequestData("DXQM_DM", DXQM_DM);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String see() {
			User user = UserHelper.getLoginUserFromRequest(request);
			this.putRequestData("DXQM_DM", user.getORG_DM_JG());
			this.invokeInteraction("see");
			dxqm = this.getResponseData("dxqm");
			if(dxqm == null) {
				dxqm = new Dxqm();
			} else {
				DXQM_DM = dxqm.getDXQM_DM();
			}
			if(dxqm.getDXQM_DM()==null || "".equals(dxqm.getDXQM_DM())) {
				dxqm.setDXQM_DM(user.getORG_DM_JG());
			}
			if(dxqm.getDXQM_MC()==null) {
				dxqm.setDXQM_MC("【"+user.getLRRY_DM()+"】");
			}
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("dxqm", dxqm);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.DXQM_DM = this.getResponseData("uid");
			if(resEnv.getAPPException()!=null) {return "add";}			return "mod";
		}
		public String mod() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("DXQM_DM", DXQM_DM);
			this.putRequestData("dxqm", dxqm);
			this.invokeInteraction("mod");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			DXQM_DM = dxqm.getDXQM_DM();
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
		for (Dxqm _dxqm: dxqms) {
			list.add(new CommonExcelPrintBean().setObject(_dxqm.getDXQM_DM()).setObject(_dxqm.getDXQM_MC()));
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
		public void setDxqm(Dxqm dxqm) {
				this.dxqm=dxqm;
		}
		public Dxqm getDxqm(){
				return this.dxqm;
		}
		public void setDxqms(List<Dxqm> dxqms) {
				this.dxqms=dxqms;
		}
		public List<Dxqm> getDxqms(){
				return this.dxqms;
		}
		public void setDXQM_DM(String DXQM_DM) {
				this.DXQM_DM=DXQM_DM;
		}
		public String getDXQM_DM(){
				return this.DXQM_DM;
		}
}
