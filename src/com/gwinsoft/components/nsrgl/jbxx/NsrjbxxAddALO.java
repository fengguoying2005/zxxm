package com.gwinsoft.components.nsrgl.jbxx;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class NsrjbxxAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Nsrjbxx nsrjbxx = reqEnv.getData("nsrjbxx");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		nsrjbxx.setLSH(uid);
		nsrjbxx.setLR_SJ(time);
		nsrjbxx.setXG_SJ(time);
		nsrjbxx.setLRRY_DM(user.getUSER_DM());
		nsrjbxx.setXGRY_DM(user.getUSER_DM());
		String message = "";
		SqlRowSet r = pm.quereyForRowSet("SELECT '<纳税人编码>已经存在，请更换。' MSG FROM NSR_JBXX WHERE NSRBM = '"+nsrjbxx.getNSRBM()+"'");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("INSERT INTO NSR_JBXX(LSH,NSRBM,NSRMC,HYDM,SSS,ORG_DM,SSZGY,LRRY_DM,SBFS,XGRY_DM,DJZT,LR_SJ,DJLX,XG_SJ,ZCDZ,ZCLX,FR,FRSJH,CWJL,CWJLSJH,BSRY,BSRYSJH) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", nsrjbxx);
			message="保存成功！";
		}
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
