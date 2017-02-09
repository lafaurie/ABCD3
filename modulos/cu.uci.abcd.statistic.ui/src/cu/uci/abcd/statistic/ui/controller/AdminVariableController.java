package cu.uci.abcd.statistic.ui.controller;

import cu.uci.abcd.domain.statistic.Variable;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Erdin Espinosa González
 *
 */
public class AdminVariableController implements ViewController {
	
	private AllManagementController allManagementController;

	public AllManagementController getAllManagementController() {
		return allManagementController;
	}
	public void setAllManagementController(AllManagementController allManagementController) {
		this.allManagementController = allManagementController;
	}
	
	public Variable addVariable(Variable variable ){
		return allManagementController.getAdminVariable().addVariable(variable);
	}
	
	public void deleteVariable(Long idVariable){
		 allManagementController.getAdminVariable().deleteVariable(idVariable);
	}
	
}
