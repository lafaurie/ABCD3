package cu.uci.abcd.opac.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class TabItemResizeListener implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Cache size
	private int width = -1;

	@Override
	public void handleEvent(Event arg0) {
		ScrolledComposite parent = (ScrolledComposite) ((Composite) arg0.widget)
				.getParent();
		int newWidth = ((Composite) arg0.widget).getSize().x;
		if (newWidth != width) {
			parent.setMinHeight(((Composite) arg0.widget).computeSize(
					newWidth, SWT.DEFAULT).y);
			parent.getShell().layout(true, true);
			parent.getShell().redraw();
			parent.getShell().update();
			width = newWidth;
		}
	}

}