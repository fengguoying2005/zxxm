package com.gwinsoft.components2.yw.fpzj;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class FpzjcxIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new FpzjcxListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new FpzjcxDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new FpzjcxSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new FpzjcxAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new FpzjcxModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
