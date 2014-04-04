package com.gwinsoft.components.tjfx.dxtj;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class DxtjIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new DxtjListALO().execute(reqEnv);
		}
		return resEnv;
	}
}
