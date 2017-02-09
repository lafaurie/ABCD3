package cu.uci.abos.core.ui.internal.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class TabItemResizeListener implements Listener {

	private static final long serialVersionUID = 1L;
 
	public TabItemResizeListener() {
		super();
	}

	@Override
	public void handleEvent(Event event) {
		Composite content =(Composite) event.widget;
		final Rectangle r = content.getClientArea();
		content.setSize(content.computeSize(r.width, SWT.DEFAULT));
	}

}
