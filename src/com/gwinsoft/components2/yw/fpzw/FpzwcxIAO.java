package com.gwinsoft.components2.yw.fpzw;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class FpzwcxIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new FpzwcxListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new FpzwcxDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new FpzwcxSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new FpzwcxAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new FpzwcxModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
