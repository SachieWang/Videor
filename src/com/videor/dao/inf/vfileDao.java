package com.videor.dao.inf;

import java.io.File;

import com.videor.dao.dto.vfile;

public interface vfileDao {
	/**
	 * GUI����Դ������ѡȡ�ļ�����Vfile����
	 * 
	 * @param f
	 * @return
	 */
	public vfile getVfile(File f);

	/**
	 * �ϴ�Ŀ���ļ���������OSS
	 * 
	 * @param f
	 */
	public void uploadVfile(vfile f);

}
