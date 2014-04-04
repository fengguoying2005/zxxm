package com.gwinsoft.components2.yw.tsjb;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class TsjbAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Tsjb tsjb = reqEnv.getData("tsjb");
		List<TSJBHF> tablemx=reqEnv.getData("tablemx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		//时间处理
		if("".equals(tsjb.getTS_SJ())) {
			tsjb.setTS_SJ(null);
		}
		//时间处理
		if("".equals(tsjb.getTS_SJ2())) {
			tsjb.setTS_SJ2(null);
		}
		String message = "";
		for(TSJBHF mx:tablemx) {
			String uid2=GwinSoftUtil.getLSH();
			mx.setLSH(uid2);
			mx.setZBLSH(uid);
			pm.executeUpdate("INSERT INTO DX_TSJBHF(LSH,ZBLSH,HFINFO,HFR_DM,HF_SJ) VALUES(?,?,?,?,?)", mx);
		}
			pm.executeUpdate("INSERT INTO DX_TSJBCX(LSH,SJHM,TSINFO,TS_SJ,HF_BJ) VALUES(?,?,?,?,?)", tsjb);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
