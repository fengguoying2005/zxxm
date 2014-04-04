package com.gwinsoft.components.xtgl.dxqm;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class DxqmIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new DxqmListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new DxqmDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new DxqmSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new DxqmAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new DxqmModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
