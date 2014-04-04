package com.gwinsoft.components.czysms.czysmssend;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class CzysmssendIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new CzysmssendListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new CzysmssendDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new CzysmssendSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new CzysmssendAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new CzysmssendModALO().execute(reqEnv);
		} else if ("send".equals(dealMethod)) {
			resEnv = new CzysmssendSendALO().execute(reqEnv);
		} else if ("seecallback".equals(dealMethod)) {
			resEnv = new CzysmssendSeeCallbackALO().execute(reqEnv);
		} else if ("listmx".equals(dealMethod)) {
			resEnv = new CzysmssendListMXALO().execute(reqEnv);
		} else if ("deletemx".equals(dealMethod)) {
			resEnv = new CzysmssendDelMXALO().execute(reqEnv);
		}
		return resEnv;
	}
}
