package com.videor.dao.inf;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
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
	 * �����ϴ�����
	 * 
	 * @param f
	 */
	public Boolean uploadVfile(task t, ProgressBar bar, Composite parent);

	/**
	 * �ϴ�Ŀ���ļ���������OSS
	 * 
	 * @param f
	 * @param ID
	 * @return
	 */
	// public Boolean upload(vfile f, String ID);

	/**
	 * �����ļ�
	 * 
	 * @param t
	 * @return
	 */
	public Boolean downloadVfile(String taskID, String[] filenames, String path);

}
