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
		// TODO �Զ����ɵķ������
		task tk = new task();
		// ����ID
		tk.setTaskId(vf.getId());
		tk.setTaskInfo(null);
		return tk;
	}

	@Override
	public info queryTask(task tk) {
		// TODO �Զ����ɵķ������
		return null;
	}

}
