package com.gwinsoft.components.xtgl.zw;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class ZwIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new ZwListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new ZwDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new ZwSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new ZwAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new ZwModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
