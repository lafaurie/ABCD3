package cu.uci.abcd.circulation.ui.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.ui.ContributorPage;

public class EventConsultReservation implements Listener {

	private static final long serialVersionUID = 1L;
	private Composite compoLoanUser;
	private Composite compoLoanUserAdvanced;
	private Composite compoReservations;
	private Composite compoReservationAdvanced;
	private Link link;
	private ContributorPage page;
	
	public EventConsultReservation(Composite compoLoanUser, Composite compoLoanUserAdvanced, Composite compoReservations, Composite compoReservationAdvanced, Link link, ContributorPage page) {
		super();
		this.page = page;
		this.compoLoanUser = compoLoanUser;
		this.compoLoanUserAdvanced = compoLoanUserAdvanced;
		this.compoReservations = compoReservations;
		this.compoReservationAdvanced = compoReservationAdvanced;
		this.link = link;
	}
//FIXME BORRAR CODIGO COMENTARIADO
	//FIXME CODIGO COMPLEJO
	@Override
	public void handleEvent(Event arg0) {
		if (compoLoanUserAdvanced.getVisible() == false) {
			if (link.getText().equals("<a>Advanced Search</a>")) {
				link.setText("<a>Basic Search</a>");
			} else
				link.setText("<a>Búsqueda básica</a>");


			compoLoanUserAdvanced.setVisible(true);
			page.insertComposite(compoLoanUserAdvanced, compoLoanUser);
		
			compoReservationAdvanced.setVisible(true);
			page.insertComposite(compoReservationAdvanced, compoReservations);
			
			compoLoanUserAdvanced.getShell().layout(true, true);
			compoLoanUserAdvanced.getShell().redraw();
			compoLoanUserAdvanced.getShell().update();
			
			compoReservationAdvanced.getShell().layout(true, true);
			compoReservationAdvanced.getShell().redraw();
			compoReservationAdvanced.getShell().update();
		
		
		} else if (compoLoanUserAdvanced.getVisible() == true) {
			if (link.getText().equals("<a>Basic Search</a>")) {
				link.setText("<a>Advanced Search</a>");
			} else
				link.setText("<a>Búsqueda avanzada</a>");

			compoLoanUserAdvanced.setVisible(false);
			page.insertComposite(compoLoanUserAdvanced, compoLoanUser);
			
			compoReservationAdvanced.setVisible(false);
			page.insertComposite(compoReservationAdvanced, compoReservations);
			
			compoLoanUserAdvanced.getShell().layout(true, true);
			compoLoanUserAdvanced.getShell().redraw();
			compoLoanUserAdvanced.getShell().update();
			
			compoReservationAdvanced.getShell().layout(true, true);
			compoReservationAdvanced.getShell().redraw();
			compoReservationAdvanced.getShell().update();
		}
		
		page.refresh();
	}
}
