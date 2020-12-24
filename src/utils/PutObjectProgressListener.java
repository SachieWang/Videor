package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.PutObjectRequest;
import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;

public class PutObjectProgressListener implements ProgressListener {
	private long bytesWritten = 0;
	private long totalBytes = -1;
	private boolean succeed = false;
	ProgressBar bar;
	Composite parent;

	public PutObjectProgressListener(long totalBytes, ProgressBar bar, Composite parent) {
		this.totalBytes = totalBytes;
		this.bar = bar;
		this.parent = parent;
	}

	@Override
	public void progressChanged(ProgressEvent progressEvent) {
		long bytes = progressEvent.getBytes();
		ProgressEventType eventType = progressEvent.getEventType();
		switch (eventType) {
		case TRANSFER_STARTED_EVENT:
			System.out.println("Start to upload......");
			break;
		case REQUEST_CONTENT_LENGTH_EVENT:
			this.totalBytes = bytes;
			System.out.println(this.totalBytes + " bytes in total will be uploaded to OSS");
			break;
		case REQUEST_BYTE_TRANSFER_EVENT:
			this.bytesWritten += bytes;
			if (this.totalBytes != -1) {
				int percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
				System.out.println(bytes + " bytes have been written at this time, upload progress: " + percent + "%("
						+ this.bytesWritten + "/" + this.totalBytes + ")");
				parent.getDisplay().asyncExec(new Runnable() {
					public void run() {
						bar.setSelection(percent);
					}
				});

			} else {
				System.out.println(bytes + " bytes have been written at this time, upload ratio: unknown" + "("
						+ this.bytesWritten + "/...)");
			}
			break;
		case TRANSFER_COMPLETED_EVENT:
			this.succeed = true;
			System.out.println("Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
			break;
		case TRANSFER_FAILED_EVENT:
			System.out.println("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
			break;
		default:
			break;
		}
	}

	public boolean isSucceed() {
		return succeed;
	}

	public static Boolean uploadVfile(task t, ProgressBar bar, Composite parent2) {
		ArrayList<vfile> fs = t.getFiles();
		vfile tmp = null;
		int counts = 0;
		for (int i = 0; i < fs.size(); i++) {
			tmp = fs.get(i);
			if (upload(tmp, t.getTaskId(), bar, parent2)) {
				counts = counts + 1;
			}
		}
		if (counts == fs.size()) {
			t.setSourceOk(true);
			return true;
		} else
			return false;
	}

	public static Boolean upload(vfile f, String ID, ProgressBar bar, Composite parent2) {

		boolean flag = true;
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录
		// https://ram.console.aliyun.com 创建RAM账号。

		// TODO 删除oss账号密码
		String accessKeyId = "accessKeyId";
		String accessKeySecret = "accessKeySecret";
		String bucketName = "bucketName";

		String[] nameConstruct = f.getFileName().split("\\.").clone();
		String objectName = "in/" + nameConstruct[0] + "_" + ID + "." + nameConstruct[1];
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		File file = new File(f.getFilePath());
		long totalBytes = file.length();
		parent2.getDisplay().asyncExec(new Runnable() {
			public void run() {
				bar.setSelection(0);
			}
		});

		try {
			// 带进度条的上传。
			ossClient.putObject(new PutObjectRequest(bucketName, objectName, new FileInputStream(f.getFilePath()))
					.<PutObjectRequest>withProgressListener(new PutObjectProgressListener(totalBytes, bar, parent2)));
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		// 关闭OSSClient。
		ossClient.shutdown();
		return flag;
	}
}
