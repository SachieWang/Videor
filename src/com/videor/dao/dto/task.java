package com.videor.dao.dto;

public class task {
	private String Id;
	private info taskInfo;

	public String getTaskId() {
		return Id;
	}

	public void setTaskId(String taskId) {
		this.Id = taskId;
	}

	public info getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(info taskInfo) {
		this.taskInfo = taskInfo;
	}
}
