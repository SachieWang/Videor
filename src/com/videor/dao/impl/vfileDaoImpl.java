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
	 * ��ȡvfimplȫ��ʵ��
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

		// TODO ɾ��oss�˺�����
		boolean flag = true;
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
			flag = false;
		}

		// �ر�OSSClient��
		ossClient.shutdown();
		return flag;
	}

}
