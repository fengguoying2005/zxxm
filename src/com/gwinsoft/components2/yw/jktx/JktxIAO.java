package com.gwinsoft.components2.yw.jktx;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class JktxIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new JktxListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new JktxDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new JktxSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new JktxAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new JktxModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
