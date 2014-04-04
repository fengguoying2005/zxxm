package com.gwinsoft.components.tjfx.business;

import com.gwinsoft.components.smssh.sh.ShMXSeeALO;
import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class BusinessIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new BusinessListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new BusinessDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new BusinessSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new BusinessAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new BusinessModALO().execute(reqEnv);
		} else if ("seepage".equals(dealMethod)) {
			resEnv = new ShMXSeeALO().execute(reqEnv);
		}
		return resEnv;
		}
}
