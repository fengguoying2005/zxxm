package com.gwinsoft.components2.yw.gmfptx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class GmfptxModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Gmfptx gmfptx = reqEnv.getData("gmfptx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        gmfptx.setLSH(BH);
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
			pm.executeUpdate("UPDATE DX_GMFPTX SET SWJGBM=?,NSRSBM=?,NSRMC=?,SJLX=?,SJHM=?,SMSINFO=?,TYDQ_RQ=?,TY_RQ=?,BZ=?,FSZT_DM=?,LR_SJ=?,FS_SJ=? WHERE LSH=?", gmfptx);
			message="修改成功！";
		resEnv.putData("message", message);
	}
}
