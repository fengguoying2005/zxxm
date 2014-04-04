package com.gwinsoft.components.czysms.group;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class CzygroupIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new CzygroupListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new CzygroupDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new CzygroupSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new CzygroupAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new CzygroupModALO().execute(reqEnv);
		} else if ("listmx".equals(dealMethod)) {
			resEnv = new CzygroupListMXALO().execute(reqEnv);
		} else if ("deletemx".equals(dealMethod)) {
			resEnv = new CzygroupDeleteMXALO().execute(reqEnv);
		}
		return resEnv;
	}
}
