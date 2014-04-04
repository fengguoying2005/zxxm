package com.gwinsoft.components.smssh.sms;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.ConnectionPoolDataSource;

import com.gwinsoft.framework.otherdbpool.DataSourceFactory;
import com.gwinsoft.framework.otherdbpool.MiniConnectionPoolManager;

public class InitSmsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static String DBURL;
	private static String DBUSER;
	private static String DBPASSWORD;
	private static String MAXCONS;
	private static String TIMEOUT;
	
	public void init() throws ServletException {
		DBURL = this.getInitParameter("DBURL");
		DBUSER = this.getInitParameter("DBUSER");
		DBPASSWORD = this.getInitParameter("DBPASSWORD");
		MAXCONS = this.getInitParameter("MAXCONS");
		TIMEOUT = this.getInitParameter("TIMEOUT");
		ConnectionPoolDataSource ds = DataSourceFactory.createMySQLDataSource(DBURL, DBUSER, DBPASSWORD);
		MiniConnectionPoolManager dbpool = new MiniConnectionPoolManager(ds, Integer.parseInt(MAXCONS), Integer.parseInt(TIMEOUT));
		SMSDBTOOL.setDbpool(dbpool );
		System.out.println("启动sms服务完毕。");
		super.init();
	}

	public static String getDBURL() {
		return DBURL;
	}

	public static String getDBUSER() {
		return DBUSER;
	}

	public static String getDBPASSWORD() {
		return DBPASSWORD;
	}

	public static String getMAXCONS() {
		return MAXCONS;
	}

	public static String getTIMEOUT() {
		return TIMEOUT;
	}
	
}
