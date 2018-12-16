package com.videor.dao.dto;

import java.io.File;

public class info {
	private String Id;
	private File logfile;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public File getLogfile() {
		return logfile;
	}

	public void setLogfile(File logfile) {
		this.logfile = logfile;
	}
}
