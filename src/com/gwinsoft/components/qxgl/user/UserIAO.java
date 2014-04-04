package com.gwinsoft.components.qxgl.user;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class UserIAO extends BaseIAO {
		protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
				resEnv = new UserListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
				resEnv = new UserDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
				resEnv = new UserSeeALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
				resEnv = new UserAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
				resEnv = new UserModALO().execute(reqEnv);
		} else if("saveAssginRole".equals(dealMethod)) {
			resEnv = new QxglUserAssginRoleALO().execute(reqEnv);
		} else if("saveAssginAuth".equals(dealMethod)) {
			resEnv = new QxglUserAssginAuthALO().execute(reqEnv);
		}
		return resEnv;
		}
}
