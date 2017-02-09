package cu.uci.abos.core.ui.internal.listener;

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
	// Cache size
	private int width = -1;
	private int height = -1;

	@Override
	public void handleEvent(Event event) {
		ScrolledComposite parent = (ScrolledComposite) ((Composite) event.widget).getParent();
		int newWidth = ((Composite) event.widget).getSize().x;
		int newHeight = ((Composite) event.widget).getSize().y;
		if (newWidth != width || newHeight != height) {
			parent.setMinHeight(((Composite) event.widget).computeSize(newWidth, SWT.DEFAULT).y);
			parent.getShell().layout(true, true);
			parent.getShell().redraw();
			parent.getShell().update();
			width = newWidth;
			// height = newHeight;
		}
	}

}
