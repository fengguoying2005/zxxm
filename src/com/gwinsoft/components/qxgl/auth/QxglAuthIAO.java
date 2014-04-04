package com.gwinsoft.components.qxgl.auth;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class QxglAuthIAO extends BaseIAO {

	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String method = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("listAuth".equals(method)) {
			resEnv = new QxglAuthListALO().execute(reqEnv);
		} else if ("seeAuth".equals(method)) {
			resEnv = new QxglAuthLoadALO().execute(reqEnv);
		} else if ("delAuth".equals(method)) {
			resEnv = new QxglAuthDeleteALO().execute(reqEnv);
		} else if ("saveAddAuth".equals(method)) {
			resEnv = new QxglAuthSaveAddALO().execute(reqEnv);
		} else if ("saveModAuth".equals(method)) {
			resEnv = new QxglAuthSaveModALO().execute(reqEnv);
		}
		return resEnv;
	}
}