package com.gwinsoft.components2.task;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class TaskIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new TaskListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new TaskDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new TaskSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new TaskAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new TaskModALO().execute(reqEnv);
		}
		return resEnv;
	}
}
