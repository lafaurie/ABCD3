package cu.uci.abcd.statistic.ui.controller;

import cu.uci.abcd.domain.statistic.Report;
import cu.uci.abos.api.ui.ViewController;



public class ReportController implements ViewController {
	
	private AllManagementController allManagementController;

	public AllManagementController getAllManagementController() {
		return allManagementController;
	}
	public void setAllManagementController(AllManagementController allManagementController) {
		this.allManagementController = allManagementController;
	}
	public Report addReport(Report report) {
		return allManagementController.getReport().addReport(report);
	}
	public void deleteReport(long reportID) {
		allManagementController.getReport().deleteReport(reportID);
	}
}
