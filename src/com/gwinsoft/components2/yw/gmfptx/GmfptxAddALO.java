package com.gwinsoft.components2.yw.gmfptx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class GmfptxAddALO extends BaseALO {

	protected void doService() {
		User user = reqEnv.getData("user");
		Gmfptx gmfptx = reqEnv.getData("gmfptx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		String uid = GwinSoftUtil.getLSH();
		DBPersistenceManager pm = this.getPM();
		//时间处理
		if("".equals(gmfptx.getTYDQ_RQ())) {
			gmfptx.setTYDQ_RQ(null);
		}
		//时间处理
		if("".equals(gmfptx.getTY_RQ())) {
			gmfptx.setTY_RQ(null);
		}
		//时间处理
		if("".equals(gmfptx.getFS_SJ())) {
			gmfptx.setFS_SJ(null);
		}
		gmfptx.setLR_SJ(time);
		String message = "";
			pm.executeUpdate("INSERT INTO DX_GMFPTX(LSH,SWJGBM,NSRSBM,NSRMC,SJLX,SJHM,SMSINFO,TYDQ_RQ,TY_RQ,BZ,FSZT_DM,LR_SJ,FS_SJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", gmfptx);
			message="保存成功！";
		resEnv.putData("message", message);
		resEnv.putData("uid", uid);
	}
}
