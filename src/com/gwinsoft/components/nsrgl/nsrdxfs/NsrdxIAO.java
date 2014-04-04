package com.gwinsoft.components.nsrgl.nsrdxfs;

import com.gwinsoft.components.nsrgl.nsrgroup.NsrgroupSeeMXALO;
import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class NsrdxIAO extends BaseIAO {
	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if ("list".equals(dealMethod)) {
			resEnv = new NsrdxListALO().execute(reqEnv);
		} else if ("del".equals(dealMethod)) {
			resEnv = new NsrdxDelALO().execute(reqEnv);
		} else if ("see".equals(dealMethod)) {
			resEnv = new NsrdxSeeALO().execute(reqEnv);
		} else if ("add".equals(dealMethod)) {
			resEnv = new NsrdxAddALO().execute(reqEnv);
		} else if ("mod".equals(dealMethod)) {
			resEnv = new NsrdxModALO().execute(reqEnv);
		} else if ("listmx".equals(dealMethod)) {
			resEnv = new NsrdxListMXALO().execute(reqEnv);
		} else if ("seemx".equals(dealMethod)) {
			resEnv = new NsrdxSeeMXALO().execute(reqEnv);
		} else if ("deletemx".equals(dealMethod)) {
			resEnv = new NsrdxDelMXALO().execute(reqEnv);
		} else if ("SH".equals(dealMethod)) {
			resEnv = new NsrdxSHALO().execute(reqEnv);
		}
		return resEnv;
	}
}
