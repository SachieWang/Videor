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
	 * ��ȡvfimplȫ��ʵ��
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
		// Endpoint�Ժ���Ϊ��������Region�밴ʵ�������д��
		String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
		// ���������˺�AccessKeyӵ������API�ķ���Ȩ�ޣ����պܸߡ�ǿ�ҽ�����������ʹ��RAM�˺Ž���API���ʻ��ճ���ά�����¼
		// https://ram.console.aliyun.com ����RAM�˺š�
		String accessKeyId = "accessKeyId";
		String accessKeySecret = "accessKeySecret";
		String bucketName = "bucketName";
		String objectName = f.getFileName();

		// ����OSSClientʵ����
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {
			// �����������ϴ���
			ossClient.putObject(new PutObjectRequest(bucketName, objectName, new FileInputStream(f.getFilePath()))
					.<PutObjectRequest>withProgressListener(new PutObjectProgressListener()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		// �ر�OSSClient��
		ossClient.shutdown();
	}
}
