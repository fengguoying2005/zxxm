package com.gwinsoft.components.nsrsms.group;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class GroupIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new GroupListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new GroupDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new GroupSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new GroupAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new GroupModALO().execute(reqEnv);
		} else if ("seepage".equals(dealMethod)) {
			resEnv = new GroupMXSeeALO().execute(reqEnv);
		}
		return resEnv;
	}
}
