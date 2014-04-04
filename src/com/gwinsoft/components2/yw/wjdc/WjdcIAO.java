package com.gwinsoft.components2.yw.wjdc;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class WjdcIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new WjdcListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new WjdcDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new WjdcSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new WjdcAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new WjdcModALO().execute(reqEnv);
		} else if ("listmx".equals(dealMethod)) {
			resEnv = new WjdcListMxALO().execute(reqEnv);
		} else if ("addmx".equals(dealMethod)) {
			resEnv = new WjdcAddMxALO().execute(reqEnv);
		} else if ("deletemx".equals(dealMethod)) {
			resEnv = new WjdcDelMxALO().execute(reqEnv);
		} else if ("send".equals(dealMethod)) {
			resEnv = new WjdcSendMxALO().execute(reqEnv);
		}
		return resEnv;
	}
}
