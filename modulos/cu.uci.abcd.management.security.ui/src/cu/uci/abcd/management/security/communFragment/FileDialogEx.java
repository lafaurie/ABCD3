package cu.uci.abcd.management.security.communFragment;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.filedialog.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import cu.uci.abcd.management.security.l10n.AbosMessages;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class FileDialogEx {
	private Shell shell;
	private Label label;

	public FileDialogEx(Shell shell) {
		initUI(shell);
	}

	private void initUI(Shell shell) {
		final FileDialog fileDialog = new FileDialog(shell, SWT.NONE);
		fileDialog.setText(AbosMessages.get().LABEL_OPEN_FILE);
		DialogUtil.open(fileDialog, new DialogCallback() {
			private static final long serialVersionUID = 1080789552039834679L;
			public void dialogClosed(int returnCode) {
				if (returnCode == 32) {
				}
			}
		});

	}

	@SuppressWarnings("unused")
	private void onMouseDown() {
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		String[] filterNames = new String[] { "Java sources", "All Files (*)" };
		String[] filterExtensions = new String[] { "*.java", "*" };
		String path = dialog.open();
		if (path != null) {
			label.setText(path);
			label.pack();
			shell.pack();
		}
	}
}
