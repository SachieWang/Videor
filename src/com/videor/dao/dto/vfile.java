package com.videor.dao.dto;

public class vfile {

	private String hashId;
	private String vfileName;
	private String vfilePath;

	public String getId() {
		return hashId;
	}

	public void setId(String id) {
		this.hashId = id;
	}

	public String getFileName() {
		return vfileName;
	}

	public void setFileName(String fileName) {
		this.vfileName = fileName;
	}

	public String getFilePath() {
		return vfilePath;
	}

	public void setFilePath(String filePath) {
		this.vfilePath = filePath;
	}

}
