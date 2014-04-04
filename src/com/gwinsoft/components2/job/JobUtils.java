package com.gwinsoft.components2.job;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.smssh.sms.SMSDBTOOL;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class JobUtils {

	private static final DecimalFormat dfmoney = new DecimalFormat("0.00");
	public static String formatMoney(double value) {
		return dfmoney.format(value);
	}
	public static String formatMoney(String value) {
		double dvalue = 0;
		try {
			dvalue = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return formatMoney(dvalue);
	}
	public static String getLSH() {
		UUID uid = UUID.randomUUID();
		return uid.toString().replaceAll("-", "");
	}
	public static String getFormatDate(Date date, String format) {
		if(date==null) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Date getFormatDate(String date, String format) {
		if(date==null || "".equals(date)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getJsJobStart(String TYPE_DM) {
		String DATE_FROM = null;
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			String sql1 = "SELECT TO_CHAR(SCJS_SJ,'yyyy-mm-dd hh24:mi:ss') AS SCJS_SJ FROM DX_JSRW WHERE TYPE_DM='"+TYPE_DM+"'";
			SqlRowSet row = pm.quereyForRowSet(sql1);
			if(row.next()) {
				DATE_FROM = row.getString("SCJS_SJ");
			} else {
				Date theday = GwinSoftUtil.getdateAdded(GwinSoftUtil.getSysDate(), Calendar.DAY_OF_MONTH, -1);
				DATE_FROM = GwinSoftUtil.getFormatDate(theday, "yyyy-MM-dd");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
		return DATE_FROM;
	}
	public static void saveJsJobEnd(String TYPE_DM, String time) {
		DBPersistenceManager pm2 = null;
		try {
			String sql2 = "UPDATE DX_JSRW SET SCJS_SJ=TO_DATE('"+time+"','yyyy-mm-dd hh24:mi:ss') WHERE TYPE_DM='"+TYPE_DM+"'";
			pm2 = DBHelper.getPm();
			pm2.executeUpdate(sql2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pm2 != null) {
				pm2.close();
			}
		}
	}
	public static String getFsJobStart(String TYPE_DM) {
		String DATE_FROM = null;
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			String sql1 = "SELECT TO_CHAR(SCFS_SJ,'yyyy-mm-dd hh24:mi:ss') AS SCFS_SJ FROM DX_FSRW WHERE TYPE_DM='"+TYPE_DM+"'";
			SqlRowSet row = pm.quereyForRowSet(sql1);
			if(row.next()) {
				DATE_FROM = row.getString("SCFS_SJ");
			} else {
				Date theday = GwinSoftUtil.getdateAdded(GwinSoftUtil.getSysDate(), Calendar.DAY_OF_MONTH, -1);
				DATE_FROM = GwinSoftUtil.getFormatDate(theday, "yyyy-MM-dd");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
		return DATE_FROM;
	}
	public static void saveFsJobEnd(String TYPE_DM, String time) {
		DBPersistenceManager pm2 = null;
		try {
			String sql2 = "UPDATE DX_FSRW SET SCFS_SJ=TO_DATE('"+time+"','yyyy-mm-dd hh24:mi:ss') WHERE TYPE_DM='"+TYPE_DM+"'";
			pm2 = DBHelper.getPm();
			pm2.executeUpdate(sql2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pm2 != null) {
				pm2.close();
			}
		}
	}
	public static String getMasDate() {
		String DATE_END = "";
		String sql1 = "select date_format(date_add(now(), interval -30 second),'%Y-%m-%d %H:%i:%S') as NOWDATE";
		CachedRowSet row1 = SMSDBTOOL.query(sql1);
		try {
			if(row1.next()) {
				DATE_END = row1.getString("NOWDATE");
			} else {
				Date theday = GwinSoftUtil.getdateAdded(GwinSoftUtil.getSysDate(), Calendar.MINUTE, -30);
				DATE_END = GwinSoftUtil.getFormatDate(theday, "yyyy-MM-dd HH:mm:ss");
			}
		} catch (SQLException e) {
			Date theday = GwinSoftUtil.getdateAdded(GwinSoftUtil.getSysDate(), Calendar.MINUTE, -30);
			DATE_END = GwinSoftUtil.getFormatDate(theday, "yyyy-MM-dd HH:mm:ss");
		}
		return DATE_END;
	}
	public static String getNowDate() {
		String DATE_END = "";
		DBPersistenceManager pm2 = null;
		try {
			String sql2 = "SELECT TO_CHAR(SYSDATE,'yyyy-mm-dd hh24:mi:ss') AS NOWDATE FROM DUAL";
			pm2 = DBHelper.getPm();
			SqlRowSet row = pm2.quereyForRowSet(sql2);
			if(row.next()) {
				DATE_END = row.getString("NOWDATE");
			} else {
				Date theday = GwinSoftUtil.getdateAdded(GwinSoftUtil.getSysDate(), Calendar.MINUTE, -10);
				DATE_END = GwinSoftUtil.getFormatDate(theday, "yyyy-MM-dd HH:mm:ss");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pm2 != null) {
				pm2.close();
			}
		}
		return DATE_END;
	}
	/**
	 */
	public static String getZjkDate() {
		String DATE_END = "";
		DBPersistenceManager pm2 = null;
		try {
			String sql2 = "SELECT SMNOW FROM NOWDATE";
			pm2 = DBHelper.getPm();
			SqlRowSet row = pm2.quereyForRowSet(sql2);
			if(row.next()) {
				DATE_END = row.getString("SMNOW");
			} else {
				Date theday = GwinSoftUtil.getdateAdded(GwinSoftUtil.getSysDate(), Calendar.MINUTE, -10);
				DATE_END = GwinSoftUtil.getFormatDate(theday, "yyyy-MM-dd HH:mm:ss");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pm2 != null) {
				pm2.close();
			}
		}
		return DATE_END;
	}
}
