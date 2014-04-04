package com.gwinsoft.components2.yw.jktx;

import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class JktxModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Jktx jktx = reqEnv.getData("jktx");
		String time = GwinSoftUtil.getSysDate(GwinSoftUtil.getSysDate());
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        jktx.setLSH(BH);
		//时间处理
		if("".equals(jktx.getSKSSQ())) {
			jktx.setSKSSQ(null);
		}
		//时间处理
		if("".equals(jktx.getSKSSZ())) {
			jktx.setSKSSZ(null);
		}
		//时间处理
		if("".equals(jktx.getSBQX())) {
			jktx.setSBQX(null);
		}
		//时间处理
		if("".equals(jktx.getFS_SJ())) {
			jktx.setFS_SJ(null);
		}
			pm.executeUpdate("UPDATE DX_JKTX SET SWJGBM=?,NSRSBM=?,NSRMC=?,SJLX=?,SJHM=?,SMSINFO=?,SKSSQ=?,SKSSZ=?,SBQX=?,BZ=?,FSZT_DM=?,LR_SJ=?,FS_SJ=?,XTSPHM=?,JKLX_DM=?,SF_JE=? WHERE LSH=?", jktx);
			message="修改成功！";
		resEnv.putData("message", message);
	}
}
