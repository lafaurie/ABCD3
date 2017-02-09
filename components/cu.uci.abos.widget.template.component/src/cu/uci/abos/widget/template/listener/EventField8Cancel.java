package cu.uci.abos.widget.template.listener;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class EventField8Cancel implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private CTabItem tabItem;
	private CTabItem tabItemSelection;
	private CTabFolder tabFolder;

	public EventField8Cancel(CTabItem tabItem, CTabItem tabItemSelection, CTabFolder tabFolder) {
		this.tabItem = tabItem;
		this.tabItemSelection = tabItemSelection;
		this.tabFolder = tabFolder;
	}

	@Override
	public void handleEvent(Event arg0) {
		tabFolder.setSelection(tabItemSelection);
		tabItem.dispose();
	}

}
