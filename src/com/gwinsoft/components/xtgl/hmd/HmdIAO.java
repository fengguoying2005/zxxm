package com.gwinsoft.components.xtgl.hmd;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class HmdIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("listmx".equals(dealMethod)) {
			resEnv = new HmdListALO().execute(reqEnv);
		} else if ("deletemx".equals(dealMethod)) {
			resEnv = new HmdDelALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new HmdAddALO().execute(reqEnv);
		} else if ("nsrsee".equals(dealMethod)) {
			resEnv = new HmdNsrSeeALO().execute(reqEnv);
		}
		return resEnv;
	}
}
