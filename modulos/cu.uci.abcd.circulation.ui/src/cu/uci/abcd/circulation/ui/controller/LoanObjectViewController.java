package cu.uci.abcd.circulation.ui.controller;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abos.api.ui.ViewController;

public class LoanObjectViewController implements ViewController {

	private AllManagementLoanUserViewController allManagementLoanUserViewController;

	public AllManagementLoanUserViewController getAllManagementLoanUserViewController() {
		return allManagementLoanUserViewController;
	}

	public void setAllManagementLoanUserViewController(AllManagementLoanUserViewController allManagementLoanUserViewController) {
		this.allManagementLoanUserViewController = allManagementLoanUserViewController;
	}

	/* LoanObject */

	public LoanObject addLoanObject(LoanObject loanObject) {
		return allManagementLoanUserViewController.getManageObject().addLoanObject(loanObject);
	}

	public LoanObject findOneLoanUser(Long idLoanObject) {
		return allManagementLoanUserViewController.getManageObject().findOneLoanObject(idLoanObject);
	}

}
