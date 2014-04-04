package com.gwinsoft.components.nsrgl.nsrdxfs;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.framework.alo.BaseALO;

public class NsrdxDelALO extends BaseALO {

	protected void doService() {
		String BH = this.getData("NSRDATA_LSH");
		String selectSQL = "SELECT YYYYMMDD FROM SMS_NSRDATA WHERE NSRDATA_LSH='"+BH+"'";
		String sql = "DELETE FROM SMS_NSRDATA WHERE NSRDATA_LSH=? AND SMSZT_DM IN ('01','02','05')";
		SqlRowSet row = this.getPM().quereyForRowSet(selectSQL);
		if(row.next()) {
			String YYYYMMDD = row.getString("YYYYMMDD");
			String TABLENAME = "SMS_SFXCMSG"+YYYYMMDD;
			String sql2 = "DELETE FROM "+TABLENAME+" WHERE NSRDATA_LSH=?";
			int n = this.getPM().executeUpdate(sql, new Object[] { BH });
			if(n>0) {
				this.getPM().executeUpdate(sql2, new Object[] { BH });
			}
			String message = "";
			message="删除成功！";
			resEnv.putData("message", message);
		}
	}
}
