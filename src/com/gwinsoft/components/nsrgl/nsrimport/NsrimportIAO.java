package com.gwinsoft.components.nsrgl.nsrimport;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class NsrimportIAO extends BaseIAO {

	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("importfile".equals(dealMethod)) {
			resEnv = new NsrimportALO().execute(reqEnv);
		}
		return resEnv;
	}

}
