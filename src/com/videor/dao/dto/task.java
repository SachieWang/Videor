package com.videor.dao.dto;

public class task {
	private String Id;
	private info taskInfo;

	public String getTaskId() {
		return Id;
	}

	public void setTaskId(String i) {
		this.Id = i;
	}

	public info getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(info taskInfo) {
		this.taskInfo = taskInfo;
	}
}
