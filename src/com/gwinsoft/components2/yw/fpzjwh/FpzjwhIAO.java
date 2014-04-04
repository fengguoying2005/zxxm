package com.gwinsoft.components2.yw.fpzjwh;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class FpzjwhIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new FpzjwhListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new FpzjwhDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new FpzjwhSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new FpzjwhAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new FpzjwhModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
