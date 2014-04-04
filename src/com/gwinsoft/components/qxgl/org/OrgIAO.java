package com.gwinsoft.components.qxgl.org;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class OrgIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new OrgListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new OrgDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new OrgSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new OrgAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new OrgModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
