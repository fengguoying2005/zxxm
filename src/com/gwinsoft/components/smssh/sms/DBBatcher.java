package com.gwinsoft.components.smssh.sms;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBBatcher {

	private static final int MAXBATCHSIZE = 10000;
	private Connection con;
	private Statement stmt;
	private int size = 0;
	
	public DBBatcher() throws SQLException {
		this.con = SMSDBTOOL.getCon();
		stmt = con.createStatement();
	}

	private void commit() throws SQLException {
		if(size>0) {
			stmt.executeBatch();
			stmt.clearBatch();
			size = 0;
		}
	}
	
	public void addSQL(String sql) throws SQLException {
		stmt.addBatch(sql);
		size ++;
		if(size>=MAXBATCHSIZE) {
			commit();
		}
	}
	public void finish() throws SQLException {
		if(size>0) {
			commit();
		}
		if(con!=null) {
			con.close();
		}
	}
}
