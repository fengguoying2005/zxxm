package com.gwinsoft.components.nsrsms.cjimport;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class CjimportIAO extends BaseIAO {

	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("importfile".equals(dealMethod)) {
			resEnv = new CjimportALO().execute(reqEnv);
		}
		return resEnv;
	}

}
