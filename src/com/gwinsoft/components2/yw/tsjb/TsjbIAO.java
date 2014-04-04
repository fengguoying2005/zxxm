package com.gwinsoft.components2.yw.tsjb;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class TsjbIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new TsjbListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new TsjbDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new TsjbSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new TsjbAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new TsjbModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
