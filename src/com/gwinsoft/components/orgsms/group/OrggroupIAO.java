package com.gwinsoft.components.orgsms.group;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class OrggroupIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new OrggroupListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new OrggroupDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new OrggroupSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new OrggroupAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new OrggroupModALO().execute(reqEnv);
		} else if ("listmx".equals(dealMethod)) {
			resEnv = new OrggroupListMXALO().execute(reqEnv);
		} else if ("deletemx".equals(dealMethod)) {
			resEnv = new OrggroupDeleteMXALO().execute(reqEnv);
		}
		return resEnv;
	}
}
