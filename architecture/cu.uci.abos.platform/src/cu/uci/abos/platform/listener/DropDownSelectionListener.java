package cu.uci.abos.platform.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolItem;

public class DropDownSelectionListener implements Listener {

	private static final long serialVersionUID = 1L;
	private final Menu menu;

	public DropDownSelectionListener(Menu menu) {
		this.menu = menu;
	}

	public void handleEvent(Event event) {
		ToolItem dropDown = (ToolItem) event.widget;
		if (event.detail == SWT.ARROW) {
			Point point = dropDown.getParent().toDisplay(event.x, event.y);
			menu.setLocation(point);
			menu.setVisible(true);
		}
	}
}
