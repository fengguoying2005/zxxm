package com.gwinsoft.components2.yw.fpzw;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class FpzwcxAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Fpzwcx fpzwcx = reqEnv.getData("fpzwcx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		//时间处理
		if("".equals(fpzwcx.getFP_RQ())) {
			fpzwcx.setFP_RQ(null);
		}
		//时间处理
		if("".equals(fpzwcx.getLR_SJ2())) {
			fpzwcx.setLR_SJ2(null);
		}
		//时间处理
		if("".equals(fpzwcx.getFS_SJ())) {
			fpzwcx.setFS_SJ(null);
		}
		fpzwcx.setLR_SJ(time);
		String message = "";
			pm.executeUpdate("INSERT INTO DX_FPZWCX(LSH,SWJGBM,NSRSBM,NSRMC,SJHM,FPZL,FPDM,FPHM,FP_RQ,FP_JE,SMSINFO,SMSINFO2,LR_SJ,FS_SJ,FSZT_DM,BZ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", fpzwcx);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
