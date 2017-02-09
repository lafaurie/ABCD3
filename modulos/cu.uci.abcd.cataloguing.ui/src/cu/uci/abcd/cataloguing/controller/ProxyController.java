package cu.uci.abcd.cataloguing.controller;

import java.util.Map;

import cu.uci.abcd.cataloguing.IAuthoritiesRecord;
import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.cataloguing.IExemplaryRecord;
import cu.uci.abcd.cataloguing.ILoanObjectCreation;
import cu.uci.abos.api.ui.ViewController;

public class ProxyController implements ViewController {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private IDataBaseManager dataBaseManagerService;
	private ILoanObjectCreation loanObjectCreationService;
	private IExemplaryRecord exemplaryRecordService;
	private IAuthoritiesRecord authoritiesRecordService;

	//gets and sets
	public IDataBaseManager getDataBaseManagerService(){return dataBaseManagerService;}
	public ILoanObjectCreation getLoanObjectCreationService(){return loanObjectCreationService;}
	public IExemplaryRecord getExemplaryRecordService(){return exemplaryRecordService;}
	public IAuthoritiesRecord getAuthoritiesRecordService(){return authoritiesRecordService;}

	public void setDataBaseManagerService(IDataBaseManager service){
		this.dataBaseManagerService = service;
	}

	public void setLoanObjectCreationService(ILoanObjectCreation service){
		this.loanObjectCreationService = service;
	}

	public void setExemplaryRecordService(IExemplaryRecord service){
		this.exemplaryRecordService = service;
	}

	public void setAuthoritiesRecordService(IAuthoritiesRecord service){
		this.authoritiesRecordService = service;
	}

	//bindsMethods
	public void bindDataBaseManager(IDataBaseManager dataBaseManager, Map<?, ?> properties) {
		this.setDataBaseManagerService(dataBaseManager);
	}

	public void bindLoanObjectCreation(ILoanObjectCreation loanObjectCreation, Map<?, ?> properties) {
		this.setLoanObjectCreationService(loanObjectCreation);
	}

	public void bindExemplaryRecord(IExemplaryRecord exemplaryRecord, Map<?, ?> properties) {
		this.setExemplaryRecordService(exemplaryRecord);
	}

	public void bindAuthoritiesRecord(IAuthoritiesRecord authoritiesRecord, Map<?, ?> properties) {
		this.setAuthoritiesRecordService(authoritiesRecord);
	}

}
