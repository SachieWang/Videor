package com.videor.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class connectMysql {

	private static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
	// TODO …æIP°¢ø‚°¢’À∫≈°¢√‹¬Î
	private static String DBURL = "jdbc:mysql://ip:3306/db";
	private static final String DBUSER = "DBUSER";
	private static final String DBPASSWORD = "DBPASSWORD";
	private static Connection conn = null;

	public static Connection getNewConn() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
			connectMysql dbc = new connectMysql();
			conn = dbc.getConnection();
			return conn;
		} catch (Exception e) {
			return null;
		}
	}

	public connectMysql() throws Exception {
		try {
			// throw new Exception(DBURL);
			if (conn != null) {
				conn.close();
				conn = null;
			}
			// Class.forName(DBDRIVER);
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
		} catch (Exception e) {
			throw e;
		}
	}

	public Connection getConnection() {
		return conn;
	}

	public Connection getNewConnection() {
		try {
			// throw new Exception(DBURL);
			Class.forName(DBDRIVER);
			connectMysql.conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
		} catch (Exception e) {

		}

		return connectMysql.conn;
	}

	public void close() throws Exception {
		if (connectMysql.conn != null) {
			try {
				connectMysql.conn.close();
			} catch (Exception e) {
				throw e;
			}
		}
	}

}
