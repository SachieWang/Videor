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

		// 生成日志时间
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu/LL/dd--HH:mm:ss");
		String thetime = dt.format(f);
		info.setBirth("--------日志时间--------\n" + thetime + "\n\n");

		// 记录任务Id
		info.setTaskId("--------任务号--------\n" + tk.getTaskId() + "\n\n");

		// 文件列表
		ArrayList<vfile> tmp = tk.getFiles();
		String files = "--------文件列表--------\n";
		for (vfile vfile : tmp) {
			files = files.concat(vfile.getFileName()).concat("\n");
		}
		info.setTaskFiles(files + "\n");

		// 任务备注
		info.setTaskPS("--------任务备注--------\n" + "test\n\n");

		// 设置最终结果
		info.setTaskInfo(info.getBirth().concat(info.getTaskId()).concat(info.getTaskPS()).concat(info.getTaskFiles()));

		tk.setTaskInfo(info);
	}

}
