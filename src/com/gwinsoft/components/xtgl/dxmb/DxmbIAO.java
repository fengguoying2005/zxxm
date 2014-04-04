package com.gwinsoft.components.xtgl.dxmb;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class DxmbIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new DxmbListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new DxmbDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new DxmbSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new DxmbAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new DxmbModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
