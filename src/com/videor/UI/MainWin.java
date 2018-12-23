package com.videor.UI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.videor.dao.db.query;
import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;
import com.videor.dao.impl.taskDaoImpl;
import com.videor.dao.impl.vfileDaoImpl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import java.util.ArrayList;

import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class MainWin {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWin window = new MainWin();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(620, 420);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(5, false));

		Button btnNewButton = new Button(shell, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_btnNewButton.widthHint = 50;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("start");

		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		GridData gd_btnNewButton_1 = new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1);
		gd_btnNewButton_1.widthHint = 50;
		btnNewButton_1.setLayoutData(gd_btnNewButton_1);
		btnNewButton_1.setText("abort");

		Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 5));

		Label lblNewLabel = new Label(shell, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 92;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("information");

		Button btnNewButton_2 = new Button(shell, SWT.NONE);
		GridData gd_btnNewButton_2 = new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1);
		gd_btnNewButton_2.widthHint = 70;
		btnNewButton_2.setLayoutData(gd_btnNewButton_2);
		btnNewButton_2.setText("refresh");

		Table table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 4));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("TaskID");

		TableColumn tblclmnStatus = new TableColumn(table, SWT.NONE);
		tblclmnStatus.setWidth(100);
		tblclmnStatus.setText("status");
		ArrayList<String> tasks = taskDaoImpl.getInstance().queryTask();
		for (String string : tasks) {
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(string);
		}

		Label label_2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		Text text = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL);
		GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_text.widthHint = 373;
		text.setLayoutData(gd_text);

		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);

		FileDialog filedialog = new FileDialog(shell, SWT.MULTI);
		filedialog.setText("选择文件");

		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("确认文件");
		messageBox.setMessage("您已选择如下文件,确认无误？：\n");

		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (filedialog.open() != null) {
					String[] paths = filedialog.getFileNames();
					String[] names = paths.clone();
					String path = filedialog.getFilterPath();
					for (int i = 0; i < paths.length; i++) {
						paths[i] = path.concat("\\").concat(paths[i]);
						System.out.println(names[i] + '\t' + paths[i]);
					}

					for (String string : names) {
						messageBox.setMessage(messageBox.getMessage().concat(string + "\n"));
					}
					int choice = messageBox.open();
					if (choice == SWT.YES) {
						// TODO 打开ps文本框
						// System.out.println(ps.pstext(shell));
						// String result = ps.pstext(shell);
						// System.out.println(result);

						// TODO PS文本框与守护线程
						// Thread th = Thread.currentThread();

						ArrayList<vfile> vf = vfileDaoImpl.getInstance().getVfile(names, paths);
						task tk = taskDaoImpl.getInstance().generateTask(vf);

						Boolean okOrNot = vfileDaoImpl.getInstance().uploadVfile(tk);
						// 上传成功则确认任务，注册数据，打印出任务id
						if (okOrNot) {
							taskDaoImpl.getInstance().confirmTask(tk);
							TableItem tableItem = new TableItem(table, SWT.NONE);
							tableItem.setText(tk.getTaskId());
							System.out.println(tk.getTaskId());
						}
					} else {
						System.out.println("放弃任务");
						return;
					}
				} else {
					System.out.println("放弃选择文件");
				}
			}
		});

		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem t = table.getItem(table.getSelectionIndex());
				System.out.println(t.getText(0));
				text.setText(query.queryInfo(t.getText(0)));
			}
		});

		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
			}
		});
	}
}
