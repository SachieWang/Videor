package com.videor.dao.inf;

import java.util.ArrayList;

import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;

public interface vfileDao {
	/**
	 * 
	 * 创建包含文件路径及hashId的相关文件列表。（旧）GUI从资源管理器选取文件生成Vfile对象，将文件信息注册进mysql
	 * 
	 * @param f
	 * @return
	 */
	public ArrayList<vfile> getVfile(String[] name, String[] path);

	/**
	 * 上传目标文件至阿里云OSS
	 * 
	 * @param f
	 */
	public Boolean uploadVfile(task t);

	Boolean upload(vfile f);

}
