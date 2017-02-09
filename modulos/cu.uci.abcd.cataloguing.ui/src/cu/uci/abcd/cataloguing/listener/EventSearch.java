package cu.uci.abcd.cataloguing.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AuthoritiesQuery;
import cu.uci.abcd.cataloguing.ui.CatalogQuery;
import cu.uci.abos.widget.template.util.BibliographicConstant;

public class EventSearch implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	private static final long serialVersionUID = 1L;
	
	private Composite view;
	private ProxyController controller;
	private String dataBaseName;
	
	public EventSearch(Composite view,
			ProxyController controller, String dataBaseName){
		this.view = view;
		this.controller = controller;
		this.dataBaseName = dataBaseName;
	}

	@Override
	public void handleEvent(Event arg0) {
	
		//erase current view
		Composite superArg0 = view.getParent();  
		view.dispose();
				
		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();
		
		//create new view
		if(dataBaseName.equals(BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE)){
			CatalogQuery catalogQuery = new CatalogQuery();
			catalogQuery.setController(controller);
			catalogQuery.createUIControl(superArg0);
		}
		else{
			AuthoritiesQuery authoritiesQuery = new AuthoritiesQuery();
			authoritiesQuery.setController(controller);
			authoritiesQuery.createUIControl(superArg0);
		}
		
		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();
	}
}
