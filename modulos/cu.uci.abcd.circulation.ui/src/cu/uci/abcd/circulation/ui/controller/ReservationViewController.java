package cu.uci.abcd.circulation.ui.controller;

import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abos.api.ui.ViewController;

public class ReservationViewController implements ViewController {
	private AllManagementLoanUserViewController allManagementLoanUserViewController;

	public AllManagementLoanUserViewController getAllManagementLoanUserViewController() {
		return allManagementLoanUserViewController;
	}

	public void setAllManagementLoanUserViewController(AllManagementLoanUserViewController allManagementLoanUserViewController) {
		this.allManagementLoanUserViewController = allManagementLoanUserViewController;
	}

	public Reservation addReservation(Reservation reservation) {
		return allManagementLoanUserViewController.getManageReservation().addReservation(reservation);
	}

	public void deleteReservation(Long idReservation) {
		allManagementLoanUserViewController.getManageReservation().deleteReservation(idReservation);
	}

}
