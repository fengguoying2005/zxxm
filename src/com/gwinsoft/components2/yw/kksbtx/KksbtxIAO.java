package com.gwinsoft.components2.yw.kksbtx;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class KksbtxIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new KksbtxListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new KksbtxDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new KksbtxSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new KksbtxAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new KksbtxModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
