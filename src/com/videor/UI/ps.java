package com.videor.UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ps {
	private static String ps;

	public static String getPs() {
		return ps;
	}

	/**
	 * @return
	 * @wbp.parser.entryPoint
	 */
	public static Shell pstext(Shell parent) {
		Shell child = new Shell(parent, SWT.TITLE);

		child.setSize(350, 250);
		child.setText("SWT Application");
		child.setLayout(new GridLayout(2, false));

		Text text = new Text(child, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		new Label(child, SWT.NONE);

		Button btnNewButton = new Button(child, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButton.setText("\u5B8C\u6210");

		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ps = text.getText();
				System.out.println("child thread:" + ps);
				child.dispose();
			}
		});
		child.open();
		return child;

	}
}
