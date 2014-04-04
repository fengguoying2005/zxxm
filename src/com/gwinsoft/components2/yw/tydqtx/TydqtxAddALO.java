package com.gwinsoft.components2.yw.tydqtx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class TydqtxAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Tydqtx tydqtx = reqEnv.getData("tydqtx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
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
		tydqtx.setLR_SJ(time);
		String message = "";
			pm.executeUpdate("INSERT INTO DX_TYDQTX(LSH,SWJGBM,NSRSBM,NSRMC,SJLX,SJHM,SMSINFO,TY_RQ,TYDQ_RQ,BZ,FSZT_DM,LR_SJ,FS_SJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", tydqtx);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
