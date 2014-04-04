package com.gwinsoft.components.qxgl.login;

import com.gwinsoft.framework.core.envelope.BaseRequestEnvelope;
import com.gwinsoft.framework.core.envelope.BaseResponseEnvelope;
import com.gwinsoft.framework.iao.BaseIAO;

public class QxglLoginIAO extends BaseIAO {

	protected BaseResponseEnvelope performTask(BaseRequestEnvelope reqEnv) {
		BaseResponseEnvelope resEnv = null;
		String dealMethod = reqEnv.getDealMethod();
		if("login".equals(dealMethod)) {
			resEnv = new QxglLoginLoginALO().execute(reqEnv);
		} else if("logout".equals(dealMethod)) {
			resEnv = new QxglLoginLogoutALO().execute(reqEnv);
		} else if("moidfyPwd".equals(dealMethod)) {
			resEnv = new QxglMoidfyPwdALO().execute(reqEnv);
		}
		return resEnv;
	}
}