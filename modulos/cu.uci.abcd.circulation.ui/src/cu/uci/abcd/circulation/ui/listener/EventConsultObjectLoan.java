package cu.uci.abcd.circulation.ui.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.ui.ContributorPage;

public class EventConsultObjectLoan implements Listener {
	private static final long serialVersionUID = 1L;

	private Composite busquedaA;
	private Composite top;
	private Link link;
	private ContributorPage page;

	public EventConsultObjectLoan(Composite compoSearchA, Link link, Composite busquedaB, ContributorPage page) {
		super();
		this.page = page;
		this.busquedaA = compoSearchA;
		this.link = link;
		this.top = busquedaB;
	}
	//FIXME BORRAR CODIGO COMENTARIADO
		//FIXME CODIGO COMPLEJO
	@Override
	public void handleEvent(Event arg0) {
		if (busquedaA.getVisible() == false) {
			if (link.getText().equals("<a>Advanced Search</a>")) {
				link.setText("<a>Basic Search</a>");
			} else
				link.setText("<a>Búsqueda básica</a>");

			busquedaA.setVisible(true);
			
			page.insertComposite(busquedaA, top);

			busquedaA.getShell().layout(true, true);
			busquedaA.getShell().redraw();
			busquedaA.getShell().update();

		} else if (busquedaA.getVisible() == true) {
			if (link.getText().equals("<a>Basic Search</a>")) {
				link.setText("<a>Advanced Search</a>");
			} else
				link.setText("<a>Búsqueda avanzada</a>");

			busquedaA.setVisible(false);
			page.insertComposite(busquedaA, top);
			busquedaA.getShell().layout(true, true);
			busquedaA.getShell().redraw();
			busquedaA.getShell().update();

		}
		
		page.refresh();

	}

}
