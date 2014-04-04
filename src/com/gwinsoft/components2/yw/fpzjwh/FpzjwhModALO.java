package com.gwinsoft.components2.yw.fpzjwh;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class FpzjwhModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Fpzjwh fpzjwh = reqEnv.getData("fpzjwh");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        fpzjwh.setLSH(BH);
		//时间处理
		if("".equals(fpzjwh.getLRRY_DM())) {
			fpzjwh.setLRRY_DM(null);
		}
		//时间处理
		if("".equals(fpzjwh.getXGRY_DM())) {
			fpzjwh.setXGRY_DM(null);
		}
		fpzjwh.setXG_SJ(time);
		fpzjwh.setXGRY_DM(user.getUSER_DM());SqlRowSet r = pm.quereyForRowSet("SELECT 1 FROM DX_FPZJXX WHERE LSH!='"+fpzjwh.getLSH()+"' AND SSX = '"+fpzjwh.getSSX()+"' AND KJNY = '"+fpzjwh.getKJNY()+"'");
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
			pm.executeUpdate("UPDATE DX_FPZJXX SET SSX=?,KJNY=?,INFO=?,BZ=?,XG_SJ=?,XGRY_DM=? WHERE LSH=?", fpzjwh);
			message="修改成功！";
		}
		resEnv.putData("message", message);
	}
}
