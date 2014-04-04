package com.gwinsoft.components.nsrgl.jbxx;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class NsrjbxxIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new NsrjbxxListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new NsrjbxxDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new NsrjbxxSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new NsrjbxxAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new NsrjbxxModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
