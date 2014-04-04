package com.gwinsoft.components.orgsms.orgsmssend;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class OrgsmssendIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new OrgsmssendListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new OrgsmssendDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new OrgsmssendSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new OrgsmssendAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new OrgsmssendModALO().execute(reqEnv);
		} else if ("send".equals(dealMethod)) {
			resEnv = new OrgsmssendSendALO().execute(reqEnv);
		} else if ("seecallback".equals(dealMethod)) {
			resEnv = new OrgsmssendSeeCallbackALO().execute(reqEnv);
		} else if ("listmx".equals(dealMethod)) {
			resEnv = new OrgsmssendListMXALO().execute(reqEnv);
		} else if ("deletemx".equals(dealMethod)) {
			resEnv = new OrgsmssendDelMXALO().execute(reqEnv);
		}
		return resEnv;
	}
}
