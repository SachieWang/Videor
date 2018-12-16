package com.videor.dao.inf;

import java.io.File;

import com.videor.dao.dto.vfile;

public interface vfileDao {
	/**
	 * GUI从资源管理器选取文件生成Vfile对象
	 * 
	 * @param f
	 * @return
	 */
	public vfile getVfile(File f);

	/**
	 * 上传目标文件至阿里云OSS
	 * 
	 * @param f
	 */
	public void uploadVfile(vfile f);

}
