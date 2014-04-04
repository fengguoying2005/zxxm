package com.gwinsoft.components2.yw.tsjb;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.components2.job.JobUtils;
import com.gwinsoft.components2.job.SmsSender;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class TsjbModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Tsjb tsjb = reqEnv.getData("tsjb");
		List<TSJBHF> tablemx = reqEnv.getData("tablemx");
		Date time = GwinSoftUtil.getSysDate();
		// String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
		tsjb.setLSH(BH);
		// 时间处理
		if ("".equals(tsjb.getTS_SJ())) {
			tsjb.setTS_SJ(null);
		}
		// 时间处理
		if ("".equals(tsjb.getTS_SJ2())) {
			tsjb.setTS_SJ2(null);
		}
		for (TSJBHF mx : tablemx) {
			if(mx.getLSH()!=null && !"".equals(mx.getLSH())) {
				continue;
			}
			String uid2 = "TSHF"+JobUtils.getLSH();
			mx.setLSH(uid2);
			mx.setZBLSH(BH);
			mx.setHF_SJ(time);
			mx.setHFR_DM(user.getUSER_DM());
			pm.executeUpdate("INSERT INTO DX_TSJBHF(LSH,ZBLSH,HFINFO,HFR_DM,HF_SJ) VALUES(?,?,?,?,?)", mx);
			//启动短信发送队列
			mx.setSJHM(tsjb.getSJHM());
			SmsSender.schedule(mx);
		}
		pm.executeUpdate("UPDATE DX_TSJBCX SET HF_BJ='1' WHERE LSH=?", tsjb);
		message = "回复成功！";
		resEnv.putData("message", message);
	}
}
