package com.videor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.videor.dao.db.connectMysql;
import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;
import com.videor.dao.inf.taskDao;

import utils.md5Util;

public class taskDaoImpl implements taskDao {
	private static taskDaoImpl tsk = new taskDaoImpl();

	public static taskDaoImpl getInstance() {
		return tsk;
	}

	@Override
	public task generateTask(ArrayList<vfile> vf) {
		task tk = new task();
		// 容纳文件列表
		tk.setFiles(vf);
		return tk;
	}

	@Override
	public void confirmTask(task t) {
		// 生成任务Id
		ArrayList<vfile> tmp = t.getFiles();
		String sTaskId = tmp.get(0).getFileName();
		for (int i = 1; i < tmp.size(); i++) {
			sTaskId = sTaskId.concat(tmp.get(i).getFileName());
		}
		System.out.println(sTaskId + '\n');
		sTaskId = sTaskId.concat(Long.toString(System.currentTimeMillis()));
		System.out.println(sTaskId);
		sTaskId = md5Util.transformMD5(sTaskId);
		t.setTaskId(sTaskId.substring(0, 7));
		infoDaoImpl.getInstance().createTaskInfo(t);
		// 注册文件
		// config the DB conn
		Connection conn = connectMysql.getNewConn();
		PreparedStatement pst = null;
		StringBuffer buffer = new StringBuffer("INSERT INTO vfile(Id,vfileName,vfilePath) VALUES (?,?,?)");
		// regist the vfile infomation into DB
		try {
			for (vfile vfile : tmp) {
				pst = conn.prepareStatement(buffer.toString());
				pst.setString(1, vfile.getId());
				pst.setString(2, vfile.getFileName());
				pst.setString(3, vfile.getFilePath());
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 注册信息
		buffer = new StringBuffer("INSERT INTO info(Id,content) VALUES (?,?)");
		try {

			pst = conn.prepareStatement(buffer.toString());
			pst.setString(1, t.getTaskId());
			pst.setString(2, t.getTaskInfo().getTaskInfo());
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 注册任务
		buffer = new StringBuffer("INSERT INTO task(Id) VALUES (?)");
		try {
			pst = conn.prepareStatement(buffer.toString());
			pst.setString(1, t.getTaskId());
			pst.executeUpdate();
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
	}

	@Override
	public ArrayList<String> queryTask() {
		Connection conn = connectMysql.getNewConn();
		PreparedStatement pst = null;
		ResultSet result = null;
		ArrayList<String> tasks = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer("SELECT Id FROM task WHERE 1");
		// regist the vfile infomation into DB
		try {
			pst = conn.prepareStatement(buffer.toString());
			result = pst.executeQuery();
			while (result.next()) {
				tasks.add(result.getString(1));
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
		return tasks;
	}

}
