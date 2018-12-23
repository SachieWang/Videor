package com.videor.dao.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;
import com.videor.dao.inf.vfileDao;

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
	public Boolean uploadVfile(task t) {
		ArrayList<vfile> fs = t.getFiles();
		vfile tmp = null;
		int counts = 0;
		for (int i = 0; i < fs.size(); i++) {
			tmp = fs.get(i);
			if (upload(tmp)) {
				counts = counts + 1;
			}
		}
		if (counts == fs.size()) {
			t.setSourceOk(true);
			return true;
		} else
			return false;
	}

	@Override
	public Boolean upload(vfile f) {

		// TODO 删除oss账号密码
		boolean flag = true;
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录
		// https://ram.console.aliyun.com 创建RAM账号。
		String accessKeyId = "accessKeyId";
		String accessKeySecret = "accessKeySecret";
		String bucketName = "bucketName";
		String objectName = f.getFileName();
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {
			// 带进度条的上传。
			ossClient.putObject(new PutObjectRequest(bucketName, objectName, new FileInputStream(f.getFilePath()))
					.<PutObjectRequest>withProgressListener(new PutObjectProgressListener()));
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		// 关闭OSSClient。
		ossClient.shutdown();
		return flag;
	}

}
