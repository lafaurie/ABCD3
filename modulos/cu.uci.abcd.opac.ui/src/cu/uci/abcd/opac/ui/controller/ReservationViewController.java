package cu.uci.abcd.opac.ui.controller;
  
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abos.api.ui.ViewController;

public class ReservationViewController implements ViewController {

	private ProxyController proxyController;
	   

	public Reservation findReservation(Long idReservation) {
		return proxyController.getIOpacReservationService().findResertion(
				idReservation);
	}
  

	public void deleteReservation(Long idReservation) {
		proxyController.getIOpacReservationService().deleteReservation(
				idReservation);
	}

	public void setProxyController(ProxyController proxyController) {
		this.proxyController = proxyController;
	}
}
