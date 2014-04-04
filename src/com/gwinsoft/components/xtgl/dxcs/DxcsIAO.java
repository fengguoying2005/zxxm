package com.gwinsoft.components.xtgl.dxcs;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class DxcsIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new DxcsListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new DxcsDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new DxcsSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new DxcsAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new DxcsModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
