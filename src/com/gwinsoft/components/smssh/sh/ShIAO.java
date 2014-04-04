package com.gwinsoft.components.smssh.sh;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class ShIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new ShListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new ShDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new ShSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new ShAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new ShModALO().execute(reqEnv);
		} else if ("send".equals(dealMethod)) {
			resEnv = new ShSendALO().execute(reqEnv);
		} else if ("seepage".equals(dealMethod)) {
			resEnv = new ShMXSeeALO().execute(reqEnv);
		}
		return resEnv;
	}
}
