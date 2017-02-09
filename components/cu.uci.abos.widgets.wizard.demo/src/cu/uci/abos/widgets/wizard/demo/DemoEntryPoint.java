package cu.uci.abos.widgets.wizard.demo;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import cu.uci.abos.ui.api.IContributor;

public class DemoEntryPoint implements EntryPoint {

	@Override
	public int createUI() {
		
		final Shell shell = new Shell(new Display());

		IContributor contributor = new WizardDemoContributor();
		shell.setText(contributor.contributorName().toUpperCase());
		contributor.createUIControl(shell);
		
		shell.pack();
		shell.open();
		
		return 0;
	}
}
