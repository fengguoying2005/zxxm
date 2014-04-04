package com.gwinsoft.components2.yw.cjtx;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class CjtxIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new CjtxListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new CjtxDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new CjtxSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new CjtxAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new CjtxModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
