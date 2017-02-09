package cu.uci.abcd.statistic.ui.controller;

import cu.uci.abcd.domain.common.Statistic;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Erdin Espinosa González
 * 
 */
public class AdminTableController implements ViewController {

	private AllManagementController allManagementController;

	public AllManagementController getAllManagementController() {
		return allManagementController;
	}

	public void setAllManagementController(AllManagementController allManagementController) {
		this.allManagementController = allManagementController;
	}

	public Statistic addStatistic(Statistic statistic) {
		return allManagementController.getAdminTable().addStatistic(statistic);
	}

	public void DeleteTable(Long idStatistic) {
		allManagementController.getAdminTable().deleteStatistic(idStatistic);
	}

}