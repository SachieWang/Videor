package com.videor.dao.inf;

import java.util.ArrayList;

import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;

public interface vfileDao {
	/**
	 * 
	 * ���������ļ�·����hashId������ļ��б����ɣ�GUI����Դ������ѡȡ�ļ�����Vfile���󣬽��ļ���Ϣע���mysql
	 * 
	 * @param f
	 * @return
	 */
	public ArrayList<vfile> getVfile(String[] name, String[] path);

	/**
	 * �ϴ�Ŀ���ļ���������OSS
	 * 
	 * @param f
	 */
	public Boolean uploadVfile(task t);

	Boolean upload(vfile f);

}
