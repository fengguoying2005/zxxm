package com.gwinsoft.components2.yw.sfcx;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class SfcxIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new SfcxListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new SfcxDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new SfcxSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new SfcxAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new SfcxModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
