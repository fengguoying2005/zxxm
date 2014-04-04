package com.gwinsoft.components2.task;

import java.util.ArrayList;
import java.util.List;

import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.print.CommonExcelPrintBean;
import com.gwinsoft.framework.print.CommonExcelPrintData;
import com.gwinsoft.framework.print.ICommonExcelPrintBean;
import com.gwinsoft.framework.print.PrintServiceConstants;
import com.gwinsoft.framework.print.interfaces.IComplexPrintData;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.framework.web.action.BaseAction;

public class TaskAction extends BaseAction<BaseIAO> {
	private Task task;
	private List<Task> tasks;
	private PageBean pageBean = new PageBean(20);
	private String LSH;
	private String TYPE_DM2;

	public String list() {
		this.putRequestData("LSH", LSH);
		putRequestData("pageBean", pageBean);
		this.invokeInteraction("list");
		tasks = this.getResponseData("tasks");
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
		this.putRequestData("TYPE_DM2", TYPE_DM2);
		this.invokeInteraction("del");
		if (this.getResponseData("message") != null) {
			String message = this.getResponseData("message");
			request.setAttribute("message", message);
		}
		return list();
	}

	public String see() {
		this.putRequestData("LSH", LSH);
		this.putRequestData("TYPE_DM2", TYPE_DM2);
		this.invokeInteraction("see");
		task = this.getResponseData("task");
		return "see";
	}

	public String add() {
		this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
		this.putRequestData("task", task);
		BaseResponseEnvelope resEnv = this.invokeInteraction("add");
		if (this.getResponseData("message") != null) {
			String message = this.getResponseData("message");
			request.setAttribute("message", message);
		}
		this.LSH = this.getResponseData("uid");
		if (resEnv.getAPPException() != null) {
			return "add";
		}
		return "mod";
	}

	public String mod() {
		this.putRequestData("user", UserHelper.getLoginUserFromRequest(request));
		this.putRequestData("LSH", LSH);
		this.putRequestData("TYPE_DM2", TYPE_DM2);
		this.putRequestData("task", task);
		this.invokeInteraction("mod");
		if (this.getResponseData("message") != null) {
			String message = this.getResponseData("message");
			request.setAttribute("message", message);
		}
		return "mod";
	}

	public String printExcel() {
		request.setAttribute(PrintServiceConstants.PRINT_HELPER_NAME_KEY,
				"ComplexExcelPrint");
		request.setAttribute(PrintServiceConstants.PRINT_DATA_KEY,
				getExcelPrintData());
		request.setAttribute(PrintServiceConstants.PRINT_TEMPLATE_NAME_KEY,
				"task");
		request.setAttribute(PrintServiceConstants.PRINT_TYPE_KEY,
				PrintServiceConstants.PRINT_TYPE_EXCEL);
		return "printService";
	}

	public IComplexPrintData getExcelPrintData() {
		pageBean = null;
		list();
		List<ICommonExcelPrintBean> list = new ArrayList<ICommonExcelPrintBean>();
		String[][] attrs = new String[0][2];
		int i = 1;
		for (Task _task : tasks) {
			list.add(new CommonExcelPrintBean().setObject(""+i)
					.setObject(_task.getTYPE_DM())
					.setObject(_task.getCRON())
					.setObject(_task.getTYPE_DM2())
					.setObject(_task.getYX_BJ()));
			i++;
		}
		CommonExcelPrintData data = new CommonExcelPrintData(attrs, list, 2);
		return data;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public PageBean getPageBean() {
		return this.pageBean;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setLSH(String LSH) {
		this.LSH = LSH;
	}

	public String getLSH() {
		return this.LSH;
	}

	public String getTYPE_DM2() {
		return TYPE_DM2;
	}

	public void setTYPE_DM2(String tYPE_DM2) {
		TYPE_DM2 = tYPE_DM2;
	}
	
}
