package com.videor.dao.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.videor.dao.dto.info;
import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;
import com.videor.dao.inf.infoDao;

public class infoDaoImpl implements infoDao {
	private static infoDaoImpl taskinfo = new infoDaoImpl();

	public static infoDaoImpl getInstance() {
		return taskinfo;
	}

	@Override
	public void createTaskInfo(task tk) {
		info info = new info();

		// ������־ʱ��
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu/LL/dd--HH:mm:ss");
		String thetime = dt.format(f);
		info.setBirth("--------��־ʱ��--------\n" + thetime + "\n\n");

		// ��¼����Id
		info.setTaskId("--------�����--------\n" + tk.getTaskId() + "\n\n");

		// �ļ��б�
		ArrayList<vfile> tmp = tk.getFiles();
		String files = "--------�ļ��б�--------\n";
		for (vfile vfile : tmp) {
			files = files.concat(vfile.getFileName()).concat("\n");
		}
		info.setTaskFiles(files + "\n");

		// ����ע
		info.setTaskPS("--------����ע--------\n" + "test\n\n");

		// �������ս��
		info.setTaskInfo(info.getBirth().concat(info.getTaskId()).concat(info.getTaskPS()).concat(info.getTaskFiles()));

		tk.setTaskInfo(info);
	}

}
