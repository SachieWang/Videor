package com.videor.dao.inf;

import java.util.ArrayList;

import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;

public interface taskDao {
	/**
	 * 生成容纳了文件列表的初始任务对象
	 * 
	 * @param f
	 * @return
	 */
	public task generateTask(ArrayList<vfile> f);

	/**
	 * 确认任务，并生成任务Id。向数据库注册完整任务信息
	 * 
	 * @param t
	 */
	public void confirmTask(task t);

	/**
	 * 查询任务全部列表及进度
	 * 
	 * @param tk
	 * @return
	 */
	public ArrayList<String> queryTask();

}
