package cu.uci.abcd.statistic.ui.controller;

import java.util.List;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.api.ui.ViewController;


public class ManagableDataBaseController implements ViewController {

	private AllManagementController allManagementController;

	public AllManagementController getAllManagementController() {
		return allManagementController;
	}

	public void setAllManagementController(AllManagementController allManagementController) {
		this.allManagementController = allManagementController;
	}
	 
	public List<String>getDataBaseNames(String libraryJisis) throws JisisDatabaseException{
		return allManagementController.getManageDatabase().getDataBaseNames(libraryJisis);
	}
}