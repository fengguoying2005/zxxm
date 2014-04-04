package com.gwinsoft.components.nsrgl.nsrgroup;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class NsrgroupIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new NsrgroupListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new NsrgroupDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new NsrgroupSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new NsrgroupAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new NsrgroupModALO().execute(reqEnv);
		} else if ("listmx".equals(dealMethod)) {
			resEnv = new NsrgroupListMXALO().execute(reqEnv);
		} else if ("deletemx".equals(dealMethod)) {
			resEnv = new NsrgroupDelMXALO().execute(reqEnv);
		} else if ("seemx".equals(dealMethod)) {
			resEnv = new NsrgroupSeeMXALO().execute(reqEnv);
		}
		return resEnv;
	}
}
