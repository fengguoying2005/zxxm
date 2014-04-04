package com.gwinsoft.components2.yw.fpzj;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class FpzjcxModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Fpzjcx fpzjcx = reqEnv.getData("fpzjcx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        fpzjcx.setLSH(BH);
		//时间处理
		if("".equals(fpzjcx.getLR_SJ2())) {
			fpzjcx.setLR_SJ2(null);
		}
		//时间处理
		if("".equals(fpzjcx.getFS_SJ())) {
			fpzjcx.setFS_SJ(null);
		}
			pm.executeUpdate("UPDATE DX_FPZJCX SET SSX=?,KJNY=?,SJHM=?,LR_SJ=?,FS_SJ=?,FSZT_DM=?,SMSINFO=?,SMSINFO2=? WHERE LSH=?", fpzjcx);
			message="修改成功！";
		resEnv.putData("message", message);
	}
}
