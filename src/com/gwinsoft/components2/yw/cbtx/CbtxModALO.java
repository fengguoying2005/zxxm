package com.gwinsoft.components2.yw.cbtx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CbtxModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Cbtx cbtx = reqEnv.getData("cbtx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        cbtx.setLSH(BH);
		//时间处理
		if("".equals(cbtx.getSKSSQ())) {
			cbtx.setSKSSQ(null);
		}
		//时间处理
		if("".equals(cbtx.getSKSSZ())) {
			cbtx.setSKSSZ(null);
		}
		//时间处理
		if("".equals(cbtx.getSBQX())) {
			cbtx.setSBQX(null);
		}
		//时间处理
		if("".equals(cbtx.getFS_SJ())) {
			cbtx.setFS_SJ(null);
		}
			pm.executeUpdate("UPDATE DX_SFCBTX SET SWJGBM=?,NSRSBM=?,NSRMC=?,SJLX=?,SJHM=?,SMSINFO=?,SKSSQ=?,SKSSZ=?,SBQX=?,BZ=?,FSZT_DM=?,LR_SJ=?,FS_SJ=? WHERE LSH=?", cbtx);
			message="修改成功！";
		resEnv.putData("message", message);
	}
}
