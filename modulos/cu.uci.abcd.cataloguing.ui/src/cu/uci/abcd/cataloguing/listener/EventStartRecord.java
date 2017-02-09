package cu.uci.abcd.cataloguing.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.RegisterBibliographicRecord;

public class EventStartRecord implements Listener{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Composite view;
	private ProxyController controller;

	public EventStartRecord(Composite view, ProxyController controller){
		this.view = view;
		this.controller = controller;
	}

	@Override
	public void handleEvent(Event arg0) {

	    Composite superArg0 = view.getParent();  
		view.dispose();
			
		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();

		RegisterBibliographicRecord bibliographicRecord = new RegisterBibliographicRecord();

		bibliographicRecord.setViewController(controller);
		bibliographicRecord.createUIControl(superArg0);

		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();
	}

}
