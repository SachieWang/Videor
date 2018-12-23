package com.videor.dao.dto;

public class info {
	private String birth;
	private String taskId;
	private String taskPS = "Default Messeges\n\n";
	private String taskFiles;
	private String taskInfo;

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskPS() {
		return taskPS;
	}

	public void setTaskPS(String taskPS) {
		this.taskPS = taskPS;
	}

	public String getTaskFiles() {
		return taskFiles;
	}

	public void setTaskFiles(String taskFiles) {
		this.taskFiles = taskFiles;
	}

	public String getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}
}
