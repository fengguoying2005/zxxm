package com.gwinsoft.components2.yw.cjtx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CjtxAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Cjtx cjtx = reqEnv.getData("cjtx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
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
		cjtx.setLR_SJ(time);
		String message = "";
			pm.executeUpdate("INSERT INTO DX_SFCJTX(LSH,SWJGBM,NSRSBM,NSRMC,SJLX,SJHM,SMSINFO,SKSSQ,SKSSZ,JKQX_RQ,SF_JE,BZ,FSZT_DM,LR_SJ,FS_SJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", cjtx);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
