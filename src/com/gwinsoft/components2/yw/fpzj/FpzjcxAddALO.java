package com.gwinsoft.components2.yw.fpzj;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class FpzjcxAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Fpzjcx fpzjcx = reqEnv.getData("fpzjcx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		//时间处理
		if("".equals(fpzjcx.getLR_SJ2())) {
			fpzjcx.setLR_SJ2(null);
		}
		//时间处理
		if("".equals(fpzjcx.getFS_SJ())) {
			fpzjcx.setFS_SJ(null);
		}
		fpzjcx.setLR_SJ(time);
		String message = "";
			pm.executeUpdate("INSERT INTO DX_FPZJCX(LSH,SSX,KJNY,SJHM,LR_SJ,FS_SJ,FSZT_DM,SMSINFO,SMSINFO2) VALUES(?,?,?,?,?,?,?,?,?)", fpzjcx);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
