package com.gwinsoft.components2.yw.gmfptx;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class GmfptxIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new GmfptxListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new GmfptxDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new GmfptxSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new GmfptxAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new GmfptxModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
