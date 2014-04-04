package com.gwinsoft.components.nsrsms.smssend;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class SmssendIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new SmssendListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new SmssendDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new SmssendSeeALO().execute(reqEnv);
		} else if ("seepage".equals(dealMethod)) {
			resEnv = new SmssendMXSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new SmssendAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new SmssendModALO().execute(reqEnv);
		} else if ("gensms".equals(dealMethod)) {
			resEnv = new SmssendGenModALO().execute(reqEnv);
		} else if ("send".equals(dealMethod)) {
			resEnv = new SmssendSendALO().execute(reqEnv);
		}
		return resEnv;
	}
}
