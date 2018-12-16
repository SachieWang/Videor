package com.videor.dao.impl;

import java.io.File;
import java.io.FileInputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.videor.dao.dto.vfile;
import com.videor.dao.inf.vfileDao;

import utils.PutObjectProgressListener;

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
	public vfile getVfile(File f) {
		vfile tmp = new vfile();
		tmp.setFileName(f.getName());
		tmp.setFilePath(f.getAbsolutePath());
		uploadVfile(tmp);
		return tmp;
	}

	public void uploadVfile(vfile f) {
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
		}
		// 关闭OSSClient。
		ossClient.shutdown();
	}
}
