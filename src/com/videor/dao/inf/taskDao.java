package com.videor.dao.inf;

import java.util.ArrayList;

import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;

public interface taskDao {
	/**
	 * �����������ļ��б�ĳ�ʼ�������
	 * 
	 * @param f
	 * @return
	 */
	public task generateTask(ArrayList<vfile> f);

	/**
	 * ȷ�����񣬲���������Id�������ݿ�ע������������Ϣ
	 * 
	 * @param t
	 */
	public void confirmTask(task t);

	/**
	 * ��ѯ����ȫ���б�����
	 * 
	 * @param tk
	 * @return
	 */
	public ArrayList<String> queryTask();

}
