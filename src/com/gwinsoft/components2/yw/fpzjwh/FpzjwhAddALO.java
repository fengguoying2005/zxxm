package com.gwinsoft.components2.yw.fpzjwh;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class FpzjwhAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Fpzjwh fpzjwh = reqEnv.getData("fpzjwh");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		//时间处理
		if("".equals(fpzjwh.getLRRY_DM())) {
			fpzjwh.setLRRY_DM(null);
		}
		//时间处理
		if("".equals(fpzjwh.getXGRY_DM())) {
			fpzjwh.setXGRY_DM(null);
		}
		fpzjwh.setLR_SJ(time);
		fpzjwh.setXG_SJ(time);
		fpzjwh.setLRRY_DM(user.getUSER_DM());
		fpzjwh.setXGRY_DM(user.getUSER_DM());
		String message = "";
		SqlRowSet r = pm.quereyForRowSet("SELECT 1 FROM DX_FPZJXX WHERE SSX = '"+fpzjwh.getSSX()+"' AND KJNY = '"+fpzjwh.getKJNY()+"'");
		if(r.next()) {
			String sss = fpzjwh.getSSX();
			try {
				GwinSoftUtil.translate(fpzjwh, "SSX", DMB.getTranslateStr(DMB.getDMB("SJSX_CACHE", "SJSX_MC")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			message = "该市“"+fpzjwh.getSSX()+"”、开奖年月“"+fpzjwh.getKJNY()+"”已有中奖内容！请修改所属市或开奖年月。";
			this.resEnv.setAPPException(new APPException(message));
			fpzjwh.setSSX(sss);
		} else {
			fpzjwh.setLSH(GwinSoftUtil.getLSH());
			pm.executeUpdate("INSERT INTO DX_FPZJXX(LSH,SSX,KJNY,INFO,BZ,LR_SJ,XG_SJ,LRRY_DM,XGRY_DM) VALUES(?,?,?,?,?,?,?,?,?)", fpzjwh);
			message="保存成功！";
		}
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
