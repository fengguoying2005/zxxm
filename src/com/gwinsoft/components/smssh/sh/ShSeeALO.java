package com.gwinsoft.components.smssh.sh;

import java.util.List;

import com.gwinsoft.components.nsrsms.group.Group;
import com.gwinsoft.components.nsrsms.group.NSRDATAMX;
import com.gwinsoft.components.nsrsms.smssend.Nsrmsg;
import com.gwinsoft.components.orgsms.orgsmssend.ORGMSG;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class ShSeeALO extends BaseALO {

	protected void doService() {
		String sql = "SELECT * FROM SMS_NSRDATA WHERE NSRDATA_LSH=?";
		String bh = reqEnv.getData("NSRDATA_LSH");
		DBPersistenceManager pm = this.getPM();
		Group group = pm.queryForObject(sql, Group.class, new Object[] { bh });
		this.putData("group", group);
		/*if("03".equals(group.getSMSTYPE_DM())) {
			String sql2 = "SELECT * FROM SMS_ORGMSG WHERE NSRDATA_LSH=? ORDER BY ORG_MC_JG ASC";
			List<ORGMSG> tablemx = pm.queryForList(sql2, ORGMSG.class, new String[]{bh});
			this.putData("tablemx", tablemx);
		} else {
			if(!"01".equals(group.getSMSZT_DM())) {
				String sql2 = "SELECT * FROM SMS_NSRMSG WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
				List<Nsrmsg> tablemx = pm.queryForList(sql2, Nsrmsg.class, new String[]{bh});
				this.putData("tablemx", tablemx);
			} else {
				String sql2 = "SELECT * FROM SMS_NSRDATAMX WHERE NSRDATA_LSH=? ORDER BY NSRBM ASC";
				List<NSRDATAMX> tablemx = pm.queryForList(sql2, NSRDATAMX.class, new String[]{bh});
				this.putData("tablemx", tablemx);
			}
		}*/
	}
}
