package cu.uci.abcd.circulation.ui.controller;

import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abos.api.ui.ViewController;

public class TransactionViewController implements ViewController {
	private AllManagementLoanUserViewController allManagementLoanUserViewController;

	public AllManagementLoanUserViewController getAllManagementLoanUserViewController() {
		return allManagementLoanUserViewController;
	}

	public void setAllManagementLoanUserViewController(AllManagementLoanUserViewController allManagementLoanUserViewController) {
		this.allManagementLoanUserViewController = allManagementLoanUserViewController;
	}

	/* transaction */
	public Transaction addTransaction(Transaction transaction) {
		return allManagementLoanUserViewController.getManageTransaction().addTransaction(transaction);
	}

	public void deleteTransaction(Long idTransaction) {
		allManagementLoanUserViewController.getManageTransaction().deleteTransaction(idTransaction);
	}

	public CirculationRule findCirculationRule(Nomenclator circulationRuleState,Nomenclator loanUserType, Nomenclator recordType, Long actorID) {
		return allManagementLoanUserViewController.getManageTransaction().findCirculationRule(circulationRuleState,loanUserType, recordType, actorID);
	}

}
