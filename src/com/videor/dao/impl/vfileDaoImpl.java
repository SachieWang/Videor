package com.videor.dao.impl;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;
import com.videor.dao.inf.vfileDao;

import utils.GetObjectProgressListener;
import utils.PutObjectProgressListener;
import utils.md5Util;

public class vfileDaoImpl implements vfileDao {

	private static vfileDaoImpl vf = new vfileDaoImpl();

	/**
	 * 获取vfimpl全局实例
	 * 
	 * @return
	 */
	public static vfileDaoImpl getInstance() {
		return vf;
	}

	@Override
	public ArrayList<vfile> getVfile(String[] name, String[] path) {
		ArrayList<vfile> vfs = new ArrayList<vfile>();
		for (int i = 0; i < path.length; i++) {
			vfile tmp = new vfile();
			tmp.setFileName(name[i]);
			tmp.setFilePath(path[i]);
			// generate the file's md5 and get the firsr 8 charactors
			try {
				String md5Hashcode32 = md5Util.md5HashCode32(tmp.getFilePath());
				String timebase = md5Util.transformMD5(Long.toString(System.currentTimeMillis()));
				String sourceId = md5Hashcode32.concat(timebase);
				String hasedId = md5Util.transformMD5(sourceId);
				tmp.setId(hasedId.substring(0, 7));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			vfs.add(tmp);
		}
		return vfs;
	}

	@Override
	public Boolean uploadVfile(task t, ProgressBar bar, Composite parent) {
		return PutObjectProgressListener.uploadVfile(t, bar, parent);
		// ArrayList<vfile> fs = t.getFiles();
		// vfile tmp = null;
		// int counts = 0;
		// for (int i = 0; i < fs.size(); i++) {
		// tmp = fs.get(i);
		// if (upload(tmp, t.getTaskId())) {
		// counts = counts + 1;
		// }
		// }
		// if (counts == fs.size()) {
		// t.setSourceOk(true);
		// return true;
		// } else
		// return false;
	}

	@Override
	public Boolean downloadVfile(String taskID, String[] filenames, String path) {
		return GetObjectProgressListener.downloadVfiles(taskID, filenames, path);

	}

}
