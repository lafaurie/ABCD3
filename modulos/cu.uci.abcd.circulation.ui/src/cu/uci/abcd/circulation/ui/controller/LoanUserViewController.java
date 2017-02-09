package cu.uci.abcd.circulation.ui.controller;

import java.util.List;

import cu.uci.abcd.circulation.ILoanUserService;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.api.ui.ViewController;

public class LoanUserViewController implements ViewController {

	/**
	 * @see ILoanUserService
	 */

	private AllManagementLoanUserViewController allManagementLoanUserViewController;

	public AllManagementLoanUserViewController getAllManagementLoanUserViewController() {
		return allManagementLoanUserViewController;
	}

	public void setAllManagementLoanUserViewController(AllManagementLoanUserViewController allManagementLoanUserViewController) {
		this.allManagementLoanUserViewController = allManagementLoanUserViewController;
	}

	/* Loan User */
	public LoanUser addLoanUser(LoanUser loanUser) {
		return allManagementLoanUserViewController.getManageLoanUser().addLoanUser(loanUser);
	}

	public void deleteLoanUser(Long idLoanUser) {
		allManagementLoanUserViewController.getManageLoanUser().deleteLoanUser(idLoanUser);
	}

	public LoanUser findOneLoanUser(Long idLoanUser) {
		return allManagementLoanUserViewController.getManageLoanUser().findOneLoanUser(idLoanUser);
	}

	public Nomenclator findByID(Long idLibrary,Long nomenclatorID) {
		return allManagementLoanUserViewController.getManageLoanUser().findByID(nomenclatorID);
	}

	public User findUserByPersonID(Long personID) {
		return allManagementLoanUserViewController.getManageLoanUser().findUserByPersonID(personID);
	}

	public List<Room> findRoomByLibraryID(Long libraryID) {
		return allManagementLoanUserViewController.getManageLoanUser().findRoomByLibrary(libraryID);
	}

	public List<Nomenclator> findByNomenclator(Long idLibrary, Long code) {
		return allManagementLoanUserViewController.getManageLoanUser().findByNomenclator(idLibrary, code);
	}

}
