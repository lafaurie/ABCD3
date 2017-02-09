package cu.uci.abcd.circulation.ui.controller;

import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abos.api.ui.ViewController;

public class PenaltyViewController implements ViewController {

	private AllManagementLoanUserViewController allManagementLoanUserViewController;

	public AllManagementLoanUserViewController getAllManagementLoanUserViewController() {
		return allManagementLoanUserViewController;
	}

	public void setAllManagementLoanUserViewController(AllManagementLoanUserViewController allManagementLoanUserViewController) {
		this.allManagementLoanUserViewController = allManagementLoanUserViewController;
	}

	public Penalty addPenalty(Penalty penalty) {
		return allManagementLoanUserViewController.getManagePenalty().addPenalty(penalty);
	}

	public void deletePenalty(Long idPenalty) {
		allManagementLoanUserViewController.getManagePenalty().deletePenalty(idPenalty);
	}

	public Penalty findOnePenalty(Long idPenalty) {
		return allManagementLoanUserViewController.getManagePenalty().findOnePenalty(idPenalty);
	}

}
