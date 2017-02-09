package cu.uci.abos.platform.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ContentResizeListener implements Listener {
	int width = -1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void handleEvent(Event arg0) {
		Composite parent = ((Composite) arg0.widget).getParent();
		ScrolledComposite scrolledComposite = (ScrolledComposite) parent.getParent();
		int newWidth = parent.getSize().x;
		if (newWidth != width) {
			scrolledComposite.setMinHeight(parent.computeSize(newWidth, SWT.DEFAULT).y);
			scrolledComposite.layout(true, true);
			scrolledComposite.redraw();
			scrolledComposite.update();
			width = newWidth;
		}
	}

}
