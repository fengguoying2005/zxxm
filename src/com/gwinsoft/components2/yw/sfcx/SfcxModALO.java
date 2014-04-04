package com.gwinsoft.components2.yw.sfcx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class SfcxModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Sfcx sfcx = reqEnv.getData("sfcx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        sfcx.setLSH(BH);
		//时间处理
		if("".equals(sfcx.getLR_SJ2())) {
			sfcx.setLR_SJ2(null);
		}
		//时间处理
		if("".equals(sfcx.getFS_SJ())) {
			sfcx.setFS_SJ(null);
		}
			pm.executeUpdate("UPDATE DX_SFCX SET SWJGBM=?,SSNY=?,NSRSBM=?,NSRMC=?,SJHM=?,SF_JE=?,LR_SJ=?,FS_SJ=?,FSZT_DM=?,SMSINFO=?,SMSINFO2=? WHERE LSH=?", sfcx);
			message="修改成功！";
		resEnv.putData("message", message);
	}
}
