package com.gwinsoft.components.nsrsms.cbimport;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class CbimportIAO extends BaseIAO {

	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("importfile".equals(dealMethod)) {
			resEnv = new CbimportALO().execute(reqEnv);
		}
		return resEnv;
	}

}
