package com.gwinsoft.components2.yw.wjdc;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class WjdcAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Wjdc wjdc = reqEnv.getData("wjdc");
		Date time = GwinSoftUtil.getSysDate();
		String uid = GwinSoftUtil.getLSH().replaceAll("-", "");
		DBPersistenceManager pm = this.getPM();
		wjdc.setLSH(uid);
		wjdc.setSFJS("0");
		wjdc.setLR_SJ(time);
		wjdc.setXG_SJ(time);
		wjdc.setLRRY_DM(user.getUSER_DM());
		wjdc.setXGRY_DM(user.getUSER_DM());
		String message = "";
		String sql = "SELECT BZ FROM DX_WJDC WHERE SUBSTR('"+wjdc.getBZ()+"',0,LENGTH(BZ))=BZ UNION SELECT BZ FROM DX_WJDC WHERE BZ LIKE '"+wjdc.getBZ()+"%'";
		SqlRowSet row = pm.quereyForRowSet(sql);
		if(row.next()) {
			String tzm = row.getString("BZ");
			this.resEnv.setAPPException(new APPException("特征码与已存在的调查‘"+tzm+"’相似，请更换。"));
			message="特征码与已存在的调查‘"+tzm+"’相似，请更换。";
			resEnv.putData("message", message);
		} else {
			pm.executeUpdate("INSERT INTO DX_WJDC(LSH,INFO,BZ,SFJS,DCJL,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?,?,?)", wjdc);
			message="保存成功！";
			resEnv.putData("message", message);
		}
		resEnv.putData("uid", uid);
	}
}
/*
ABCDEF
ABCDEFG(NO)

12345678
123456(NO)

*/