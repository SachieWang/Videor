package com.videor.dao.dto;

import java.util.ArrayList;

public class task {
	private String Id;
	private info taskInfo;
	private ArrayList<vfile> vfiles;
	private Boolean sourceOk = false;

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

	public ArrayList<vfile> getFiles() {
		return vfiles;
	}

	public void setFiles(ArrayList<vfile> files) {
		this.vfiles = files;
	}

	public Boolean getSourceOk() {
		return sourceOk;
	}

	public void setSourceOk(Boolean sourceOk) {
		this.sourceOk = sourceOk;
	}
}
