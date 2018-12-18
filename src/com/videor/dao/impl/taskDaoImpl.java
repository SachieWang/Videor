package com.videor.dao.impl;

import com.videor.dao.dto.info;
import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;
import com.videor.dao.inf.taskDao;

public class taskDaoImpl implements taskDao {
	private static taskDaoImpl tsk = new taskDaoImpl();

	public static taskDaoImpl getInstance() {
		return tsk;
	}

	@Override
	public task generateTask(vfile vf) {
		// TODO 自动生成的方法存根
		task tk = new task();
		// 关联ID
		tk.setTaskId(vf.getId());
		tk.setTaskInfo(null);
		return tk;
	}

	@Override
	public info queryTask(task tk) {
		// TODO 自动生成的方法存根
		return null;
	}

}
