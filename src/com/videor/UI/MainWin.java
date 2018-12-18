package com.videor.UI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.videor.dao.dto.task;
import com.videor.dao.dto.vfile;
import com.videor.dao.impl.taskDaoImpl;
import com.videor.dao.impl.vfileDaoImpl;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

public class MainWin {

	protected Shell shell;
	private Text text;

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
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new BorderLayout(0, 0));

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_1 = new Menu(mntmFile);

		mntmFile.setMenu(menu_1);

		MenuItem mntmOpen = new MenuItem(menu_1, SWT.NONE);
		mntmOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
					final String platform = UIManager.getSystemLookAndFeelClassName();
					// If the current Look & Feel does not match the platform Look & Feel,
					// change it so it does.
					if (!UIManager.getLookAndFeel().getName().equals(platform)) {
						try {
							UIManager.setLookAndFeel(platform);
						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}
				}
				JFileChooser jfc = new JFileChooser();
				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					// Scanner input = null;
					// try {
					// input = new Scanner(file);
					// } catch (FileNotFoundException e1) { // TODO 自动生成的 catch 块
					// e1.printStackTrace();
					// }
					// while (input.hasNext()) {
					// System.out.println(input.nextLine());
					// }
					// input.close();
					vfile vf = vfileDaoImpl.getInstance().getVfile(file);
					task tk = taskDaoImpl.getInstance().generateTask(vf);
					System.out.println(vf.getId() + '\t' + vf.getFileName() + '\t' + vf.getFilePath());

				} else
					System.out.println("No file is selected!");
			}
		});

		mntmOpen.setText("Open");

		MenuItem mntmSave = new MenuItem(menu_1, SWT.NONE);
		mntmSave.setText("Save");

		List list = new List(shell, SWT.BORDER);
		list.setLayoutData(BorderLayout.WEST);

		text = new Text(shell, SWT.BORDER);
		text.setLayoutData(BorderLayout.CENTER);

	}

}
