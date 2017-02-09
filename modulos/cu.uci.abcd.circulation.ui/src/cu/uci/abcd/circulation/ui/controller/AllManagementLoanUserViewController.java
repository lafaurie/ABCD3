package cu.uci.abcd.circulation.ui.controller;

import java.util.Map;

import cu.uci.abcd.circulation.ILoanObjectJISISService;
import cu.uci.abcd.circulation.ILoanObjectService;
import cu.uci.abcd.circulation.ILoanUserService;
import cu.uci.abcd.circulation.IPenaltyService;
import cu.uci.abcd.circulation.IPersonService;
import cu.uci.abcd.circulation.IReservationService;
import cu.uci.abcd.circulation.ITransactionService;
import cu.uci.abos.api.ui.ViewController;

public class AllManagementLoanUserViewController implements ViewController {

	/**
	 * 
	 * @author Yanet Crespo Diaz
	 * 
	 */

	private ILoanUserService manageLoanUser;
	private IPenaltyService managePenalty;
	private IReservationService manageReservation;
	private ILoanObjectService manageObject;
	private ITransactionService manageTransaction;
	private IPersonService managePerson;
	private ILoanObjectJISISService manageLoanObjectJISIS;

	public IPenaltyService getManagePenalty() {
		return managePenalty;
	}

	public void setManagePenalty(IPenaltyService managePenalty) {
		this.managePenalty = managePenalty;
	}

	public ILoanUserService getManageLoanUser() {
		return manageLoanUser;
	}

	public void setManageLoanUser(ILoanUserService manageLoanUser) {
		this.manageLoanUser = manageLoanUser;
	}

	public IReservationService getManageReservation() {
		return manageReservation;
	}

	public void setManageReservation(IReservationService reservationService) {
		this.manageReservation = reservationService;
	}

	public ILoanObjectService getManageObject() {
		return manageObject;
	}

	public void setManageObject(ILoanObjectService manageObject) {
		this.manageObject = manageObject;
	}

	public ITransactionService getManageTransaction() {
		return manageTransaction;
	}

	public void setManageTransaction(ITransactionService manageTransaction) {
		this.manageTransaction = manageTransaction;
	}

	public IPersonService getManagePerson() {
		return managePerson;
	}

	public void setManagePerson(IPersonService managePerson) {
		this.managePerson = managePerson;
	}
	
	public ILoanObjectJISISService getManageLoanObjectJISIS() {
		return manageLoanObjectJISIS;
	}

	public void setManageLoanObjectJISIS(
			ILoanObjectJISISService manageLoanObjectJISIS) {
		this.manageLoanObjectJISIS = manageLoanObjectJISIS;
	}

	public void bindLoanObjectJISIS(ILoanObjectJISISService loanObjectJISIS, Map<?, ?> properties) {
		this.setManageLoanObjectJISIS(loanObjectJISIS);		
	}
	
	public void bindManageLoanUser(ILoanUserService manageLoanUser, Map<?, ?> properties) {
		this.setManageLoanUser(manageLoanUser);
	}

	public void bindPersonService(IPersonService personService, Map<?, ?> properties) {
		this.setManagePerson(personService);

	}

	public void bindManagePenalty(IPenaltyService managePenalty, Map<?, ?> properties) {
		this.setManagePenalty(managePenalty);
	}

	public void bindReservationService(IReservationService manageReservation, Map<?, ?> properties) {
		this.setManageReservation(manageReservation);

	}

	public void bindManageObject(ILoanObjectService manageObject, Map<?, ?> properties) {
		this.setManageObject(manageObject);
	}

	public void bindManageTransaction(ITransactionService manageTransaction, Map<?, ?> properties) {
		this.setManageTransaction(manageTransaction);
	}

}