package com.videor.dao.inf;

import com.videor.dao.dto.info;
import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;

public interface taskDao {
	/**
	 * �����ļ�������
	 * 
	 * @param f
	 * @return
	 */
	public task generateTask(vfile f);

	/**
	 * ��ѯ������Ϣ��״̬
	 * 
	 * @param tk
	 * @return
	 */
	public info queryTask(task tk);

}
