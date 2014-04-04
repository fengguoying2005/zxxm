package com.gwinsoft.components2.yw.xydz;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class XydzIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new XydzListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new XydzDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new XydzSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new XydzAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new XydzModALO().execute(reqEnv);
		} else if ("loadNsr".equals(dealMethod)) {
			resEnv = new XydzLoadNsrALO().execute(reqEnv);
		}
		return resEnv;
	}
}
