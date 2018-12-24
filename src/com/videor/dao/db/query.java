package com.videor.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class query {

	public static String queryInfo(String ID) {
		// config the DB conn
		Connection conn = connectMysql.getNewConn();
		PreparedStatement pst = null;
		ResultSet result = null;
		String info = null;
		StringBuffer buffer = new StringBuffer("SELECT content FROM info WHERE Id LIKE ?");
		// regist the vfile infomation into DB
		// buffer = new StringBuffer("INSERT INTO task(Id) VALUES (?)");
		try {
			pst = conn.prepareStatement(buffer.toString());
			pst.setString(1, ID);
			result = pst.executeQuery();
			while (result.next()) {
				info = result.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	public static String[] queryFiles(String ID) {
		Connection conn = connectMysql.getNewConn();
		PreparedStatement pst = null;
		ResultSet result = null;
		ArrayList<String> files = new ArrayList<>();
		StringBuffer buffer = new StringBuffer("SELECT vfileName FROM vfile WHERE Id LIKE ?");
		// regist the vfile infomation into DB
		// buffer = new StringBuffer("INSERT INTO task(Id) VALUES (?)");
		try {
			pst = conn.prepareStatement(buffer.toString());
			pst.setString(1, ID);
			result = pst.executeQuery();
			while (result.next()) {
				files.add(result.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		int size = files.size();
		String[] file = files.toArray(new String[size]);
		return file;
	}

	public static String queryProgress(String ID) {
		// config the DB conn
		Connection conn = connectMysql.getNewConn();
		PreparedStatement pst = null;
		ResultSet result = null;
		String progress = null;
		StringBuffer buffer = new StringBuffer("SELECT Progress FROM task WHERE Id LIKE ?");
		// regist the vfile infomation into DB
		// buffer = new StringBuffer("INSERT INTO task(Id) VALUES (?)");
		try {
			pst = conn.prepareStatement(buffer.toString());
			pst.setString(1, ID);
			result = pst.executeQuery();
			while (result.next()) {
				progress = Float.toString(result.getFloat(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return progress;
	}

}
