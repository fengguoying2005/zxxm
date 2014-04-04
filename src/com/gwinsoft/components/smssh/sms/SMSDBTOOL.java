package com.gwinsoft.components.smssh.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.rowset.CachedRowSet;

import com.gwinsoft.framework.otherdbpool.DataSourceFactory;
import com.gwinsoft.framework.otherdbpool.MiniConnectionPoolManager;
import com.gwinsoft.framework.otherdbpool.MiniConnectionPoolManager.TimeoutException;
import com.sun.rowset.CachedRowSetImpl;

/**
 * @author fenggy
 * 数据库操作工具
 */
public class SMSDBTOOL {

	private static MiniConnectionPoolManager dbpool;
	public static void setDbpool(MiniConnectionPoolManager dbpool) {
		SMSDBTOOL.dbpool = dbpool;
	}
	private SMSDBTOOL() {
	}
	public static Connection getCon() throws SQLException {
		Connection conn = null;
		try {
			conn = dbpool.getConnection();
		} catch (Exception e) {
			if(e instanceof TimeoutException) {
				e.printStackTrace();
			} else {
				dbpool.dispose();
				ConnectionPoolDataSource ds = DataSourceFactory.createMySQLDataSource(InitSmsServlet.getDBURL(), InitSmsServlet.getDBUSER(), InitSmsServlet.getDBPASSWORD());
				MiniConnectionPoolManager dbpool = new MiniConnectionPoolManager(ds, Integer.parseInt(InitSmsServlet.getMAXCONS()), Integer.parseInt(InitSmsServlet.getTIMEOUT()));
				SMSDBTOOL.setDbpool(dbpool);
				System.out.println("启动sms服务完毕。");
				return dbpool.getConnection();
			}
		}
		return conn;
	}
	/**
	 * 执行SQL语句
	 */
	public static boolean update(String sql) {
		boolean result = false;
		Connection con = null;
		try {
			con = getCon();
			con .createStatement().execute(sql);
			result =  true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	public static boolean update(String sql, Object ...values) {
		boolean result = false;
		Connection con = null;
		try {
			con = getCon();
			PreparedStatement stmt = con.prepareStatement(sql);
			for(int i = 0; i < values.length; i ++) {
				stmt.setObject(i+1, values[i]);
			}
			result = stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	public static CachedRowSet query(String sql) {
		CachedRowSet result = null;
		Connection con = null;
		try {
			con = getCon();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			result = new CachedRowSetImpl();
			result.populate(rs);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	public static CachedRowSet query(String sql, Object ...values) {
		CachedRowSet result = null;
		Connection con = null;
		try {
			con = getCon();
			PreparedStatement stmt = con.prepareStatement(sql);
			for(int i = 0; i < values.length; i ++) {
				stmt.setString(i+1, (String)values[i]);
			}
			ResultSet rs = stmt.executeQuery();
			result = new CachedRowSetImpl();
			result.populate(rs);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
