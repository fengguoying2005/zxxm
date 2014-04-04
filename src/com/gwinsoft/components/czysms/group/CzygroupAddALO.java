package com.gwinsoft.components.czysms.group;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheServlet;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CzygroupAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Czygroup orggroup = reqEnv.getData("orggroup");
		List<GROUPMX> tablemx=reqEnv.getData("tablemx");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		orggroup.setORG_DM_JG(user.getORG_DM_JG());
		orggroup.setGROUP_LSH(uid);
		orggroup.setLR_SJ(time);
		orggroup.setXG_SJ(time);
		orggroup.setLRRY_DM(user.getUSER_DM());
		orggroup.setXGRY_DM(user.getUSER_DM());
		orggroup.setGROUP_COUNT(tablemx.size()+"");
		String message = "";
		for(GROUPMX mx:tablemx) {
			String uid2=GwinSoftUtil.getLSH();
			mx.setGROUPMX_LSH(uid2);
			mx.setGROUP_LSH(uid);
			mx.setLR_SJ(time);
			mx.setXG_SJ(time);
			mx.setLRRY_DM(user.getUSER_DM());
			mx.setXGRY_DM(user.getUSER_DM());
			pm.executeUpdate("INSERT INTO CZY_GROUPMX(GROUPMX_LSH,GROUP_LSH,ORG_DM_JG,ORG_DM_BM,USER_DM,SJHM,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,ORG_MC_JG,ORG_MC_BM,USER_MC,ZW_DM,ZW_MC) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", mx);
		}
		pm.executeUpdate("INSERT INTO CZY_GROUP(GROUP_LSH,ORG_DM_JG,GROUP_NAME,GROUP_COUNT,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?,?)", orggroup);
		message="保存成功！";
		CacheServlet.freshCache("CZYGROUP_CACHE", pm);
		CacheServlet.freshCache("CZYGROUPMX_CACHE", pm);
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
