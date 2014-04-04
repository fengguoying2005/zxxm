package com.gwinsoft.components2.yw.cbtx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CbtxAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Cbtx cbtx = reqEnv.getData("cbtx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		//时间处理
		if("".equals(cbtx.getSKSSQ())) {
			cbtx.setSKSSQ(null);
		}
		//时间处理
		if("".equals(cbtx.getSKSSZ())) {
			cbtx.setSKSSZ(null);
		}
		//时间处理
		if("".equals(cbtx.getSBQX())) {
			cbtx.setSBQX(null);
		}
		//时间处理
		if("".equals(cbtx.getFS_SJ())) {
			cbtx.setFS_SJ(null);
		}
		cbtx.setLR_SJ(time);
		String message = "";
			pm.executeUpdate("INSERT INTO DX_SFCBTX(LSH,SWJGBM,NSRSBM,NSRMC,SJLX,SJHM,SMSINFO,SKSSQ,SKSSZ,SBQX,BZ,FSZT_DM,LR_SJ,FS_SJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", cbtx);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
