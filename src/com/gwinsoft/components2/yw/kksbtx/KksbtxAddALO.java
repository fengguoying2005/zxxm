package com.gwinsoft.components2.yw.kksbtx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class KksbtxAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Kksbtx kksbtx = reqEnv.getData("kksbtx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		//时间处理
		if("".equals(kksbtx.getSKSSQ())) {
			kksbtx.setSKSSQ(null);
		}
		//时间处理
		if("".equals(kksbtx.getSKSSZ())) {
			kksbtx.setSKSSZ(null);
		}
		//时间处理
		if("".equals(kksbtx.getJKQX_RQ())) {
			kksbtx.setJKQX_RQ(null);
		}
		//时间处理
		if("".equals(kksbtx.getFS_SJ())) {
			kksbtx.setFS_SJ(null);
		}
		kksbtx.setLR_SJ(time);
		String message = "";
			pm.executeUpdate("INSERT INTO DX_KKSBTX(LSH,SWJGBM,NSRSBM,NSRMC,SJLX,SJHM,SMSINFO,SKSSQ,SKSSZ,JKQX_RQ,BZ,FSZT_DM,LR_SJ,FS_SJ,YZPZXH,SF_JE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", kksbtx);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
