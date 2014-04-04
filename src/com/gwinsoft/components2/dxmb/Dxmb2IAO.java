package com.gwinsoft.components2.dxmb;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class Dxmb2IAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new Dxmb2ListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new Dxmb2DelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new Dxmb2SeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new Dxmb2AddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new Dxmb2ModALO().execute(reqEnv);
		}
		return resEnv;
		}
}
