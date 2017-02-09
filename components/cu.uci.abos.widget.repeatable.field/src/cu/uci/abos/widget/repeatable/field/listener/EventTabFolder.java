package cu.uci.abos.widget.repeatable.field.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class EventTabFolder implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Composite view;
	private CTabFolder tabFolder;
	private String fieldNumber;
	private String subFieldName;

	public EventTabFolder(Composite view, CTabFolder tabFolder, String fieldNumber, String subFieldName) {
		this.view = view;
		this.tabFolder = tabFolder;
		this.fieldNumber = fieldNumber;
		this.subFieldName = subFieldName;
	}

	@Override
	public void handleEvent(Event arg0) {
		String enterText = "";

		if (!subFieldName.equals(""))
			enterText = fieldNumber + "-  " + subFieldName;
		else
			enterText = fieldNumber;

		int cantItems = tabFolder.getItemCount();
		boolean foundItem = false;
		int position = cantItems;

		for (int i = 0; i < cantItems && foundItem == false; i++) {
			String text = tabFolder.getItem(i).getText();
			if (text.equals(enterText)) {
				foundItem = true;
				position = i;
			}
		}

		if (foundItem)
			tabFolder.setSelection(position);
		else {

			CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
			tabItem.setText(enterText);
			tabItem.setShowClose(true);
			tabItem.setControl(view);

			tabFolder.setSelection(position);

			view.getShell().layout(true, true);
			view.getShell().redraw();
			view.getShell().update();

			if (tabItem.isDisposed())
				tabFolder.setSelection(0);
		}

	}

}
