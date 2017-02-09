package cu.uci.abos.platform.contribution;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;

public abstract class GridLayoutPageContributor implements Contributor {

	protected Composite content;

	@Override
	public Control createUIControl(Composite parent) {
		content = new Composite(parent, SWT.NONE);
		Layout contentLayout = null;
		if (parent.getDisplay().getBounds().width > 1024) {
			contentLayout = new GridLayout(4, false);
		} else {
			if (parent.getDisplay().getBounds().width > 480) {
				contentLayout = new GridLayout(2, false);
			} else {
				contentLayout = new GridLayout(1, false);
			}
		}
		GridData layoutData = new GridData();
		layoutData.minimumHeight = parent.getBounds().height;
		layoutData.minimumWidth = parent.getBounds().width;
		content.setLayout(contentLayout);
		content.setLayoutData(layoutData);
		return content;
	}

	@Override
	public void l10n() {

	}

	@Override
	public boolean canClose() {
		return true;
	}

	@Override
	public void setViewController(ViewController viewController) {

	}

}
