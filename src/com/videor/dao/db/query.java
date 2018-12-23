package com.videor.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
