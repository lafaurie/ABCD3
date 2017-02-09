package cu.uci.abcd.cataloguing.listener;

import org.eclipse.rap.rwt.widgets.DropDown;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.api.ui.ViewController;

public class EventAllRecords implements Listener{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	private static final long serialVersionUID = 1L;
	
	private DropDown drop;
	//private DropDown drop2;
	private Composite view;
	private ViewController controller;
	
	public EventAllRecords(DropDown drop, Composite view,
			ViewController controller){
		this.drop = drop;
		this.view = view;
		this.controller = controller;
	}

	@Override
	public void handleEvent(Event arg0) {
		
		/*boolean visible2 = drop2.getVisible();
		if(visible2)
			drop2.setVisible(false);*/
		
		boolean visible = drop.getVisible();

		if(visible)
			drop.setVisible(false);
		else{
			drop.setVisible(true);

			if(!drop.isListening(SWT.Selection))
				drop.addListener(SWT.Selection, new EventAllRecordDrop(drop, view, controller));
		}
		
	}

}
