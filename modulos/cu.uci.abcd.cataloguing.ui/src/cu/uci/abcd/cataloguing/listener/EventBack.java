package cu.uci.abcd.cataloguing.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AdvancedQuery;
import cu.uci.abcd.cataloguing.ui.AuthoritiesQuery;
import cu.uci.abcd.cataloguing.ui.CatalogQuery;
import cu.uci.abcd.cataloguing.ui.RegisterBibliographicRecord;
import cu.uci.abcd.cataloguing.ui.ReferenceView;

public class EventBack implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Composite view;
	private ProxyController controller;
	private ReferenceView referenceView;

	public EventBack(Composite view, ProxyController controller, ReferenceView referenceView){
		this.view = view;
		this.controller = controller;
		this.referenceView = referenceView;
	}

	@Override
	public void handleEvent(Event arg0) {
		/*if(register){
			MessageDialogUtil.openQuestion(view.getShell(), "Pregunta", "Se perderán los datos entrados,"
					+ " deseas ir a la vista inicial",
					new DialogCallback() {
				*//**
				 * Created by Basilio Puentes Rodríguez 
				 *//*
				private static final long serialVersionUID = 1L;

				@Override
				public void dialogClosed(int arg0) {
					if(arg0 == 0)
						back();
				}
			});
		}
		else*/
			back();
	}

	private void back(){
		Composite superArg0 = view.getParent();
		view.dispose();

		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();
		
		switch (referenceView) {
		case Register:
			RegisterBibliographicRecord bibliographicRecord = new RegisterBibliographicRecord();
			bibliographicRecord.setViewController(controller);

			bibliographicRecord.createUIControl(superArg0);
			break;
			
		case Catalog:
			CatalogQuery catalogQuery = new CatalogQuery();
			catalogQuery.setViewController(controller);

			catalogQuery.createUIControl(superArg0);
			break;
			
		case Authorities:
			AuthoritiesQuery authoritiesQuery = new AuthoritiesQuery();
			authoritiesQuery.setViewController(controller);

			authoritiesQuery.createUIControl(superArg0);
			break;
			
		case Advance:
			AdvancedQuery advanceQuery = new AdvancedQuery();
			advanceQuery.setViewController(controller);

			advanceQuery.createUIControl(superArg0);
			break;	

		default:
			break;
		}
		
		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();
	}
}
