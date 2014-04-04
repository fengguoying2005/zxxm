package com.gwinsoft.components2.yw.tydqtx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class TydqtxModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Tydqtx tydqtx = reqEnv.getData("tydqtx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        tydqtx.setLSH(BH);
		//时间处理
		if("".equals(tydqtx.getTY_RQ())) {
			tydqtx.setTY_RQ(null);
		}
		//时间处理
		if("".equals(tydqtx.getTYDQ_RQ())) {
			tydqtx.setTYDQ_RQ(null);
		}
		//时间处理
		if("".equals(tydqtx.getFS_SJ())) {
			tydqtx.setFS_SJ(null);
		}
			pm.executeUpdate("UPDATE DX_TYDQTX SET SWJGBM=?,NSRSBM=?,NSRMC=?,SJLX=?,SJHM=?,SMSINFO=?,TY_RQ=?,TYDQ_RQ=?,BZ=?,FSZT_DM=?,LR_SJ=?,FS_SJ=? WHERE LSH=?", tydqtx);
			message="修改成功！";
		resEnv.putData("message", message);
	}
}
