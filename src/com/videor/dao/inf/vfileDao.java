package com.videor.dao.inf;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
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
	 * 生成上传队列
	 * 
	 * @param f
	 */
	public Boolean uploadVfile(task t, ProgressBar bar, Composite parent);

	/**
	 * 上传目标文件至阿里云OSS
	 * 
	 * @param f
	 * @param ID
	 * @return
	 */
	// public Boolean upload(vfile f, String ID);

	/**
	 * 下载文件
	 * 
	 * @param t
	 * @return
	 */
	public Boolean downloadVfile(String taskID, String[] filenames, String path);

}
