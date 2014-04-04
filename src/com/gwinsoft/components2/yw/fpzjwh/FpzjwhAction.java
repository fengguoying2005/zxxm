package com.gwinsoft.components2.yw.fpzjwh;

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

public class FpzjwhAction extends BaseAction<BaseIAO> {
		private Fpzjwh fpzjwh;
		private List<Fpzjwh> fpzjwhs;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_SSX;
		private String FIND_KJNY;
		private Map sjsxmap = null;
		public String list(){
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_SSX", FIND_SSX);
				putRequestData("FIND_KJNY", FIND_KJNY);
				this.invokeInteraction("list");
				fpzjwhs = this.getResponseData("fpzjwhs");
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
			this.putRequestData("LSH", LSH);
			this.invokeInteraction("del");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			return list();
		}
		public String see() {
			this.putRequestData("LSH", LSH);
			this.invokeInteraction("see");
			fpzjwh = this.getResponseData("fpzjwh");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("fpzjwh", fpzjwh);
			BaseResponseEnvelope resEnv = this.invokeInteraction("add");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			this.LSH = this.getResponseData("uid");
			if(resEnv.getAPPException()!=null) {return "add";}			return "mod";
		}
		public String mod() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("LSH", LSH);
			this.putRequestData("fpzjwh", fpzjwh);
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
		String[][] attrs = new String[2][2];
		attrs[0][0]=FIND_SSX;attrs[0][1]=FIND_SSX;
		attrs[1][0]=FIND_KJNY;attrs[1][1]=FIND_KJNY;
		for (Fpzjwh _fpzjwh: fpzjwhs) {
			list.add(new CommonExcelPrintBean().setObject(_fpzjwh.getLSH()).setObject(_fpzjwh.getSSX()).setObject(_fpzjwh.getKJNY()).setObject(_fpzjwh.getINFO()).setObject(_fpzjwh.getBZ()).setObject(_fpzjwh.getLR_SJ()).setObject(_fpzjwh.getXG_SJ()).setObject(_fpzjwh.getLRRY_DM()).setObject(_fpzjwh.getXGRY_DM()));
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
		public void setFpzjwh(Fpzjwh fpzjwh) {
				this.fpzjwh=fpzjwh;
		}
		public Fpzjwh getFpzjwh(){
				return this.fpzjwh;
		}
		public void setFpzjwhs(List<Fpzjwh> fpzjwhs) {
				this.fpzjwhs=fpzjwhs;
		}
		public List<Fpzjwh> getFpzjwhs(){
				return this.fpzjwhs;
		}
		public void setLSH(String LSH) {
				this.LSH=LSH;
		}
		public String getLSH(){
				return this.LSH;
		}
		public void setFIND_SSX(String FIND_SSX) {
				this.FIND_SSX=FIND_SSX;
		}
		public String getFIND_SSX(){
				return this.FIND_SSX;
		}
		public void setFIND_KJNY(String FIND_KJNY) {
				this.FIND_KJNY=FIND_KJNY;
		}
		public String getFIND_KJNY(){
				return this.FIND_KJNY;
		}
		public Map getSjsxmap() {
			return DMB.sortByKey(DMB.getDMB("SJSX_CACHE", "SJSX_MC"),true);
		}
		public void setSjsxmap(Map sjsxmap) {
			this.sjsxmap = sjsxmap;
		}
}
