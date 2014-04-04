package com.gwinsoft.components.qxgl.role;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class QxglRoleIAO extends BaseIAO {

	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		String dealMethod = reqEnv.getDealMethod();
		BaseResponseEnvelope resEnv = null;
		if("list".equals(dealMethod)) {
			resEnv = new QxglRoleListALO().execute(reqEnv);
		} else if("del".equals(dealMethod)) {
			resEnv = new QxglRoleDelALO().execute(reqEnv);
		} else if("see".equals(dealMethod)) {
			resEnv = new QxglRoleLoadALO().execute(reqEnv);
		} else if("add".equals(dealMethod)) {
			resEnv = new QxglRoleSaveAddALO().execute(reqEnv);
		} else if("mod".equals(dealMethod)) {
			resEnv = new QxglRoleSaveModALO().execute(reqEnv);
		} else if("saveAssginAuth".equals(dealMethod)) {
			resEnv = new QxglRoleAssginAuthALO().execute(reqEnv);
		}
		return resEnv;
	}

}
