package com.videor.dao.inf;

import com.videor.dao.dto.info;
import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;

public interface taskDao {
	/**
	 * 关联文件与任务
	 * 
	 * @param f
	 * @return
	 */
	public task generateTask(vfile f);

	/**
	 * 查询任务信息及状态
	 * 
	 * @param tk
	 * @return
	 */
	public info queryTask(task tk);

}
