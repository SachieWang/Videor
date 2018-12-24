package com.videor.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.videor.dao.dto.task;
import com.videor.dao.impl.taskDaoImpl;
import com.videor.dao.impl.vfileDaoImpl;

public class childThread implements Runnable {
	private ProgressBar progressBar = null;
	private Composite parent = null;
	private task tk = null;
	private Table table = null;

	/**
	 * 传入进度条、父窗口、任务体、表格控件
	 * 
	 * @param progressBar
	 * @param parent
	 * @param tk
	 * @param table
	 */
	public childThread(ProgressBar progressBar, Composite parent, task tk, Table table) {
		this.progressBar = progressBar;
		this.parent = parent;
		this.tk = tk;
		this.table = table;
	}

	public void run() {
		Boolean okOrNot = vfileDaoImpl.getInstance().uploadVfile(tk, progressBar, parent);
		// 上传成功则注册任务，注册数据，打印出任务id
		if (okOrNot) {
			taskDaoImpl.getInstance().registeTask(tk);
			taskDaoImpl.getInstance().invokeTask(tk);
			// 异步调用UI主线程
			parent.getDisplay().asyncExec(new Runnable() {
				public void run() {
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(tk.getTaskId());
				}
			});
			System.out.println(tk.getTaskId());
		}
	}

}

// {
//
// public void run() {
//
//
// }
// }
