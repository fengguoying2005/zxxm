package com.gwinsoft.components2.yw.tsjb;

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

public class TsjbAction extends BaseAction<BaseIAO> {
		private Tsjb tsjb;
		private List<Tsjb> tsjbs;
		private PageBean pageBean = new PageBean(10);
		private String LSH;
		private String FIND_SJHM;
		private String FIND_TSINFO;
		private String FIND_TS_SJ;
		private String FIND_TS_SJ2;
		private List<TSJBHF> tablemx;
		public String list(){
			this.putRequestData("LSH", LSH);
				putRequestData("pageBean", pageBean);
				putRequestData("FIND_SJHM", FIND_SJHM);
				putRequestData("FIND_TSINFO", FIND_TSINFO);
				putRequestData("FIND_TS_SJ", FIND_TS_SJ);
				putRequestData("FIND_TS_SJ2", FIND_TS_SJ2);
				this.invokeInteraction("list");
				tsjbs = this.getResponseData("tsjbs");
				return "list";
		}
		public String toAdd() {
			tablemx=new ArrayList<TSJBHF>();
			TSJBHF mx = new TSJBHF();
			tablemx.add(mx);
			return "add";
		}
		public String toMod() {
			see();
			TSJBHF hf = new TSJBHF();
			hf.setHFINFO("");
			tablemx.add(hf);
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
			tablemx=this.getResponseData("tablemx");
			tsjb = this.getResponseData("tsjb");
			return "see";
		}
		public String add() {
			this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
			this.putRequestData("tsjb", tsjb);
			this.putRequestData("tablemx", tablemx);
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
			this.putRequestData("tsjb", tsjb);
			this.putRequestData("tablemx", tablemx);
			this.invokeInteraction("mod");
			if(this.getResponseData("message")!=null) {
				String message = this.getResponseData("message");
				request.setAttribute("message", message);
			}
			see();
			TSJBHF hf = new TSJBHF();
			hf.setHFINFO("");
			tablemx.add(hf);
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
		String[][] attrs = new String[4][2];
		attrs[0][0]=FIND_SJHM;attrs[0][1]=FIND_SJHM;
		attrs[1][0]=FIND_TSINFO;attrs[1][1]=FIND_TSINFO;
		attrs[2][0]=FIND_TS_SJ;attrs[2][1]=FIND_TS_SJ;
		attrs[3][0]=FIND_TS_SJ2;attrs[3][1]=FIND_TS_SJ2;
		for (Tsjb _tsjb: tsjbs) {
			list.add(new CommonExcelPrintBean().setObject(_tsjb.getLSH()).setObject(_tsjb.getSJHM()).setObject(_tsjb.getTSINFO()).setObject(_tsjb.getTS_SJ()).setObject(_tsjb.getTS_SJ2()).setObject(_tsjb.getHF_BJ()));
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
		public void setTsjb(Tsjb tsjb) {
				this.tsjb=tsjb;
		}
		public Tsjb getTsjb(){
				return this.tsjb;
		}
		public void setTsjbs(List<Tsjb> tsjbs) {
				this.tsjbs=tsjbs;
		}
		public List<Tsjb> getTsjbs(){
				return this.tsjbs;
		}
		public void setLSH(String LSH) {
				this.LSH=LSH;
		}
		public String getLSH(){
				return this.LSH;
		}
		public void setFIND_SJHM(String FIND_SJHM) {
				this.FIND_SJHM=FIND_SJHM;
		}
		public String getFIND_SJHM(){
				return this.FIND_SJHM;
		}
		public void setFIND_TSINFO(String FIND_TSINFO) {
				this.FIND_TSINFO=FIND_TSINFO;
		}
		public String getFIND_TSINFO(){
				return this.FIND_TSINFO;
		}
		public void setFIND_TS_SJ(String FIND_TS_SJ) {
				this.FIND_TS_SJ=FIND_TS_SJ;
		}
		public String getFIND_TS_SJ(){
				return this.FIND_TS_SJ;
		}
		public void setFIND_TS_SJ2(String FIND_TS_SJ2) {
				this.FIND_TS_SJ2=FIND_TS_SJ2;
		}
		public String getFIND_TS_SJ2(){
				return this.FIND_TS_SJ2;
		}
		public List<TSJBHF> getTablemx(){
			return tablemx;
		}
		public void setTablemx(List<TSJBHF> tablemx){
			this.tablemx = tablemx;
		}
}
