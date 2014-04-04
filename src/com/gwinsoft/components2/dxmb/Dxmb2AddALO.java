package com.gwinsoft.components2.dxmb;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class Dxmb2AddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Dxmb2 dxmb = reqEnv.getData("dxmb");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		String message = "";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
