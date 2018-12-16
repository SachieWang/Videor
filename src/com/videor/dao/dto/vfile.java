package com.videor.dao.dto;

public class vfile {

	private String Id;
	private String vfileName;
	private String vfilePath;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		this.Id = id;
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
