package com.gwinsoft.components2.yw.cbtx;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class CbtxIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new CbtxListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new CbtxDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new CbtxSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new CbtxAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new CbtxModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
