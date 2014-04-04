package com.gwinsoft.components2.yw.xydz;

import java.util.Date;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.components2.job.JobUtils;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class XydzModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Xydz xydz = reqEnv.getData("xydz");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        xydz.setLSH(BH);
		//时间处理
		if("".equals(xydz.getKTRQQ())) {
			xydz.setKTRQ_Q(null);
		} else {
			xydz.setKTRQ_Q(JobUtils.getFormatDate(xydz.getKTRQQ(), "yyyy-MM-dd"));
		}
		//时间处理
		if("".equals(xydz.getKTRQZ())) {
			xydz.setKTRQ_Z(null);
		} else {
			xydz.setKTRQ_Z(JobUtils.getFormatDate(xydz.getKTRQZ(), "yyyy-MM-dd"));
		}
		xydz.setXG_SJ(time);
		xydz.setXGRY_DM(user.getUSER_DM());
		pm.executeUpdate("UPDATE DX_XYDZ SET NSRMC=?,ORG_DM=?,CBTX=?,CJTX=?,JKTX=?,JKSBTX=?,GMFPTX=?,TYDQTX=?,BZ=?,XGRY_DM=?,XG_SJ=?,KTRQ_Q=?,KTRQ_Z=? WHERE LSH=?", xydz);
		message="修改成功！";
		resEnv.putData("message", message);
	}
}
