package cu.uci.abcd.statistic.ui.controller;

import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abos.api.ui.ViewController;

public class IndicatorController implements ViewController {
	
	private AllManagementController allManagementController;

	public AllManagementController getAllManagementController() {
		return allManagementController;
	}
	public void setAllManagementController(AllManagementController allManagementController) {
		this.allManagementController = allManagementController;
	}
	
	public Indicator addIndicator(Indicator indicator) {
		return allManagementController.getIndicator().addIndicator(indicator);
	}
	
	public boolean deleteIndicator(long indicatorID) {
	   return allManagementController.getIndicator().deleteIndicator(indicatorID);
	}
	public Indicator searchIndicator(Long idIndicator) {
		return allManagementController.getIndicator().searchIndicator(idIndicator);
	}
}
