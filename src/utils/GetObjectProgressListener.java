package utils;

import java.io.File;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.GetObjectRequest;

public class GetObjectProgressListener implements ProgressListener {
	private long bytesRead = 0;
	private long totalBytes = -1;
	private boolean succeed = false;

	@Override
	public void progressChanged(ProgressEvent progressEvent) {
		long bytes = progressEvent.getBytes();
		ProgressEventType eventType = progressEvent.getEventType();
		switch (eventType) {
		case TRANSFER_STARTED_EVENT:
			System.out.println("Start to download......");
			break;
		case RESPONSE_CONTENT_LENGTH_EVENT:
			this.totalBytes = bytes;
			System.out.println(this.totalBytes + " bytes in total will be downloaded to a local file");
			break;
		case RESPONSE_BYTE_TRANSFER_EVENT:
			this.bytesRead += bytes;
			if (this.totalBytes != -1) {
				int percent = (int) (this.bytesRead * 100.0 / this.totalBytes);
				System.out.println(bytes + " bytes have been read at this time, download progress: " + percent + "%("
						+ this.bytesRead + "/" + this.totalBytes + ")");
			} else {
				System.out.println(bytes + " bytes have been read at this time, download ratio: unknown" + "("
						+ this.bytesRead + "/...)");
			}
			break;
		case TRANSFER_COMPLETED_EVENT:
			this.succeed = true;
			System.out.println("Succeed to download, " + this.bytesRead + " bytes have been transferred in total");
			break;
		case TRANSFER_FAILED_EVENT:
			System.out.println("Failed to download, " + this.bytesRead + " bytes have been transferred");
			break;
		default:
			break;
		}
	}

	public boolean isSucceed() {
		return succeed;
	}

	public static Boolean downloadVfiles(String taskID, String[] filenames, String path) {
		String pathbk = path;
		int counts = 0;
		for (String string : filenames) {
			String[] nameConstruct = string.split("\\.").clone();
			path = pathbk + nameConstruct[0] + "_" + taskID + "_out" + "." + nameConstruct[1];
			String objectName = "out/" + nameConstruct[0] + "_" + taskID + "_out" + "." + nameConstruct[1];
			if (download(objectName, path)) {
				counts = counts + 1;
			}
		}

		if (counts == filenames.length) {
			return true;
		} else
			return false;
	}

	public static Boolean download(String name, String path) {
		boolean flag = true;
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录
		// https://ram.console.aliyun.com 创建RAM账号。

		// TODO 删除oss账号密码
		String accessKeyId = "accessKeyId";
		String accessKeySecret = "accessKeySecret";
		String bucketName = "bucketName";

		String objectName = name;
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try {
			// 带进度条的下载。
			ossClient.getObject(new GetObjectRequest(bucketName, objectName)
					.<GetObjectRequest>withProgressListener(new GetObjectProgressListener()), new File(path));
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		// 关闭OSSClient。
		ossClient.shutdown();
		return flag;
	}
}