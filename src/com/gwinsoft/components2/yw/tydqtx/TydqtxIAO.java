package com.gwinsoft.components2.yw.tydqtx;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class TydqtxIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new TydqtxListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new TydqtxDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new TydqtxSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new TydqtxAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new TydqtxModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
