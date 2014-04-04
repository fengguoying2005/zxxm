package com.gwinsoft.components.nsrgl.jbxx;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class NsrjbxxModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Nsrjbxx nsrjbxx = reqEnv.getData("nsrjbxx");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        nsrjbxx.setLSH(BH);
		nsrjbxx.setXG_SJ(time);
		nsrjbxx.setXGRY_DM(user.getUSER_DM());
		SqlRowSet r = pm.quereyForRowSet("SELECT '<纳税人编码>已经存在，请更换。' MSG FROM NSR_JBXX WHERE NSRBM = '"+nsrjbxx.getNSRBM()+"' and LSH<>'"+BH+"'");
		if(r.next()) {
			String msg = r.getString("MSG");
			this.resEnv.setAPPException(new APPException(msg));
		} else {
			pm.executeUpdate("UPDATE NSR_JBXX SET NSRBM=?,NSRMC=?,HYDM=?,SSS=?,ORG_DM=?,SSZGY=?,SBFS=?,DJZT=?,DJLX=?,ZCDZ=?,ZCLX=?,FR=?,FRSJH=?,CWJL=?,CWJLSJH=?,BSRY=?,BSRYSJH=?,XG_SJ=?,XGRY_DM=? WHERE LSH=?", nsrjbxx);
			message="修改成功！";
		}
		resEnv.putData("message", message);
	}
}
