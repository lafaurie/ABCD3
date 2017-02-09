package cu.uci.abcd.statistic.ui.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.Statistic;
import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.domain.statistic.Report;
import cu.uci.abcd.domain.statistic.Variable;
import cu.uci.abcd.statistic.IAdminTable;
import cu.uci.abcd.statistic.IAdminVariable;
import cu.uci.abcd.statistic.IIndicator;
import cu.uci.abcd.statistic.IManageDatabase;
import cu.uci.abcd.statistic.IReport;
import cu.uci.abos.api.ui.ViewController;


public class AllManagementController implements ViewController {

	private IAdminTable adminTable;
	private IIndicator indicator;
	private IReport report;
	private IAdminVariable adminVariable;
	private IManageDatabase manageDatabase;
	

	public IManageDatabase getManageDatabase() {
		return manageDatabase;
	}

	public void setManageDatabase(IManageDatabase manageDatabase) {
		this.manageDatabase = manageDatabase;
	}

	public IAdminVariable getAdminVariable() {
		return adminVariable;
	}

	public void setAdminVariable(IAdminVariable adminVariable) {
		this.adminVariable = adminVariable;
	}
	

	public IReport getReport() {
		return report;
	}

	public void setReport(IReport report) {
		this.report = report;
	}

	public IIndicator getIndicator() {
		return indicator;
	}

	public void setIndicator(IIndicator indicator) {
		this.indicator = indicator;
	}

	public IAdminTable getAdminTable() {
		return adminTable;
	}

	public void setAdminTable(IAdminTable adminTable) {
		this.adminTable = adminTable;
	}


	public void bindAdminTable(IAdminTable adminTable, Map<?, ?> properties) {
		this.setAdminTable(adminTable);
	}

	public void bindIndicator(IIndicator indicator, Map<?, ?> properties) {
		this.setIndicator(indicator);
	}

	public void bindReport(IReport report, Map<?, ?> properties) {
		this.setReport(report);

	}

	public void bindAdminVariable(IAdminVariable adminVariable, Map<?, ?> properties) {
		this.setAdminVariable(adminVariable);
	}

	public void bindManageDatabase(IManageDatabase manageDatabase, Map<?, ?> properties) {
		this.setManageDatabase(manageDatabase);
	}

	public Page<Statistic> FindAllStatisticSearch(String databasetype, String tablename, int page, int size, int direction, String orderByString) {
		//return getAdminTable().FinAllStatistic(databasetype, tablename, getPage(page, size, direction, orderByString));
		return null;
	}

	public Page<Variable> FindAllVariableSearch(String databaseName, String variableheader, String field, int page, int size, int direction, String orderByString) {
		return getAdminVariable().FindAllVariable(databaseName, variableheader, field, page, size, direction, orderByString);
	}
	
	public Collection<Variable> FindVariablesByDatabase(String databaseName,
			String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return getAdminVariable().findVariableByDatabase(databaseName, libraryIsisDatabasesHomeFolder);
	}

	public long generateStatisticID() {
		//FIXME OIGRES, COMO ES ESTO ACA...
		List<Statistic> generar = (List<Statistic>) getAdminTable().findAllStatistic();
		if (generar.size() == 0)
			return 1;
		return generar.get(generar.size() - 1).getTableID() + 1;
	}

	public Page<Indicator> ListAllIndicator( String nameIndicator, String numIndicator, int page, int size, int direction, String orderByString) {
		return getIndicator().listAllIndicator(nameIndicator, numIndicator, page, size, direction, orderByString);
	}
	
	public Indicator findIndicatorByNumberAndName(String number, String name){
		return getIndicator().findIndicatorByNumberAndName(number, name);
	}
	
	public List<Indicator> ListIndicator( String nameIndicator, String numIndicator) {
		return getIndicator().listIndicator(nameIndicator, numIndicator);
	}
	
	public Page<Report> ListAllReport( String nameReport, int page, int size, int direction, String orderByString) {
		return getReport().listAllReport(nameReport, page, size, direction, orderByString);
	}
	
	public Report findReportByName(String nameReport){
		return getReport().findReportByName(nameReport);
	}

	
	@SuppressWarnings("unchecked")
	public Page<Indicator> findByNameIndicator(long id, int page, int size, int direction, String orderByString) {
		//return getIndicator().FinAllIndicator(new StatisticSpecification().searchIndicatorId(id), Auxiliary.ppp(page, size));
		return (Page<Indicator>) Collections.EMPTY_LIST;
	}
	
}