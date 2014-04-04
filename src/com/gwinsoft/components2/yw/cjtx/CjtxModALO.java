package com.gwinsoft.components2.yw.cjtx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CjtxModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Cjtx cjtx = reqEnv.getData("cjtx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        cjtx.setLSH(BH);
		//时间处理
		if("".equals(cjtx.getSKSSQ())) {
			cjtx.setSKSSQ(null);
		}
		//时间处理
		if("".equals(cjtx.getSKSSZ())) {
			cjtx.setSKSSZ(null);
		}
		//时间处理
		if("".equals(cjtx.getJKQX_RQ())) {
			cjtx.setJKQX_RQ(null);
		}
		//时间处理
		if("".equals(cjtx.getFS_SJ())) {
			cjtx.setFS_SJ(null);
		}
			pm.executeUpdate("UPDATE DX_SFCJTX SET SWJGBM=?,NSRSBM=?,NSRMC=?,SJLX=?,SJHM=?,SMSINFO=?,SKSSQ=?,SKSSZ=?,JKQX_RQ=?,SF_JE=?,BZ=?,FSZT_DM=?,LR_SJ=?,FS_SJ=? WHERE LSH=?", cjtx);
			message="修改成功！";
		resEnv.putData("message", message);
	}
}
