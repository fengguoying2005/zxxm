package com.gwinsoft.components.qxgl.user2;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class User2IAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new User2ListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new User2DelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new User2SeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new User2AddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new User2ModALO().execute(reqEnv);
		} else if("saveAssginRole".equals(dealMethod)) {
			resEnv = new QxglUserAssginRoleALO().execute(reqEnv);
		} else if("saveAssginAuth".equals(dealMethod)) {
			resEnv = new QxglUserAssginAuthALO().execute(reqEnv);
		} else if("importfile".equals(dealMethod)) {
			resEnv = new User2ImportALO().execute(reqEnv);
		}
		return resEnv;
		}
}
