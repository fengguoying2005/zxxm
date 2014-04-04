package com.gwinsoft.components2.yw.xydz;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class XydzAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Xydz xydz = reqEnv.getData("xydz");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH();
		xydz.setLSH(uid);
		DBPersistenceManager pm = this.getPM();
		//时间处理
		if("".equals(xydz.getKTRQ_Q())) {
			xydz.setKTRQ_Q(null);
		}
		//时间处理
		if("".equals(xydz.getKTRQ_Z())) {
			xydz.setKTRQ_Z(null);
		}
		xydz.setLR_SJ(time);
		xydz.setXG_SJ(time);
		xydz.setLRRY_DM(user.getUSER_DM());
		xydz.setXGRY_DM(user.getUSER_DM());
		String message = "";
		String sql = "SELECT LSH FROM DX_XYDZ WHERE NSRBM=?";
		SqlRowSet row = pm.quereyForRowSet(sql, new String[]{xydz.getNSRBM()});
		if(row.next()) {
			uid = row.getString("LSH");
			pm.executeUpdate("UPDATE DX_XYDZ SET NSRMC=?,ORG_DM=?,CBTX=?,CJTX=?,JKTX=?,JKSBTX=?,GMFPTX=?,TYDQTX=?,KTRQ_Q=?,KTRQ_Z=?,BZ=?,XGRY_DM=?,XG_SJ=? WHERE NSRBM=?", xydz);
		} else {
			pm.executeUpdate("INSERT INTO DX_XYDZ(LSH,NSRBM,NSRMC,ORG_DM,CBTX,CJTX,JKTX,JKSBTX,GMFPTX,TYDQTX,KTRQ_Q,KTRQ_Z,BZ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", xydz);
		}
		message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
