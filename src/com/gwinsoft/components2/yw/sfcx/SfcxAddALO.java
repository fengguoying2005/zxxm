package com.gwinsoft.components2.yw.sfcx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class SfcxAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Sfcx sfcx = reqEnv.getData("sfcx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		//时间处理
		if("".equals(sfcx.getLR_SJ2())) {
			sfcx.setLR_SJ2(null);
		}
		//时间处理
		if("".equals(sfcx.getFS_SJ())) {
			sfcx.setFS_SJ(null);
		}
		sfcx.setLR_SJ(time);
		String message = "";
			pm.executeUpdate("INSERT INTO DX_SFCX(LSH,SWJGBM,SSNY,NSRSBM,NSRMC,SJHM,SF_JE,LR_SJ,FS_SJ,FSZT_DM,SMSINFO,SMSINFO2) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", sfcx);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
