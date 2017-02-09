package cu.uci.abcd.cataloguing.listener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.IAuthoritiesRecord;
import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.cataloguing.IExemplaryRecord;
import cu.uci.abcd.cataloguing.ILoanObjectCreation;
import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AllRecordsView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.domain.cataloguing.CataloguingNomenclator;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.template.component.TemplateCompound;
import cu.uci.abos.widget.template.util.BibliographicConstant;
import cu.uci.abos.widget.template.util.Util;

public class EventSaveRecord implements Listener {
	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private ProxyController controller;
	private String dataBaseName;
	private TemplateCompound component;
	private String exemplaryDataBase = BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE;
	private Record record;
	private boolean register;
	private Boolean successfull;
	private boolean integration;
	private IExemplaryRecord exemplaryRecord;

	public EventSaveRecord(ProxyController controller, String dataBaseName, TemplateCompound component,
			Record record, boolean integration){
		this.controller = controller;
		this.dataBaseName = dataBaseName;
		this.component = component;
		this.record = record;
		this.integration = integration;
	}

	@Override
	public void handleEvent(Event arg0) {

		if(controller != null){

			boolean validation = component.validateRecord();
			successfull = false;

			if(validation){
				int countRecordField = 0;

				try {
					countRecordField = record.getFieldCount();

				} catch (DbException e1) {
					RetroalimentationUtils.showErrorShellMessage("Problema de conección con JISIS");
				}

				if(countRecordField == 0)
					register = true;
				else
					register = false;

				ArrayList<FieldStructure> children = component.getChildrens();

				IDataBaseManager dataBaseManager = controller.getDataBaseManagerService();

				//create or edit exemplary record
				if(dataBaseName.equals(exemplaryDataBase)){
					exemplaryRecord = controller.getExemplaryRecordService();

					ILoanObjectCreation loanObjectCreation = controller.getLoanObjectCreationService();

					if(register)
						successfull = exemplaryRecord.registerExemplaryRecord(children, dataBaseManager,
								dataBaseName, loanObjectCreation);
					else
						successfull = exemplaryRecord.editExemplaryRecord(children, dataBaseManager,
								dataBaseName, loanObjectCreation, record);
				}
				//create or edit in others dataBases 
				else{
					IAuthoritiesRecord authoritiesRecord = controller.getAuthoritiesRecordService();

					if(register)
						successfull = authoritiesRecord.registerAuthoritiesRecord(children, dataBaseManager, dataBaseName);
					else
						successfull = authoritiesRecord.editAuthoritiesRecord(children, dataBaseManager, dataBaseName, record);
				}

				if(successfull){

					try{
						Record currentRecord = null;

						if(register || !dataBaseName.equals(exemplaryDataBase))
							currentRecord = dataBaseManager.getLastRecord(dataBaseName);
						else{
							Field field = (Field) record.getField(BibliographicConstant.CONTROL_NUMBER);
							String term = field.getStringFieldValue();
							IJisisDataProvider service = controller.getDataBaseManagerService().getService();
							
							String isisDefHome = Util.getDefHome();
							List<String> controlNumbers = new ArrayList<String>();
							controlNumbers.add(term);
							
							List<Record> records = service.findByRecordNumber(controlNumbers, dataBaseName, isisDefHome);
							currentRecord = records.get(0);
						}
						
						//only for integration set situation and state
						if(integration){	
							ILoanObjectCreation loanObjectCreation = controller.getLoanObjectCreationService();
							String controlNumber = currentRecord.getField(BibliographicConstant.CONTROL_NUMBER).getStringFieldValue();
							
							List<LoanObject> loanObjects = loanObjectCreation.findAllByControlNumber(controlNumber);
							int loanObjectsSize = loanObjects.size();
							
							Nomenclator loanObjectSituation = loanObjectCreation.getNomenclator(
									CataloguingNomenclator.SITUATION_LOANOBJECT);
							
							Nomenclator availableState = loanObjectCreation.getNomenclator(
									CataloguingNomenclator.LOANOBJECT_STATE_AVAILABLE);
							
							for (int i = 0; i < loanObjectsSize; i++) {
								LoanObject currentLoanObject = loanObjects.get(i);
								currentLoanObject.setSituation(loanObjectSituation);
								currentLoanObject.setLoanObjectState(availableState);
								loanObjectCreation.addLoanObject(currentLoanObject);
							}
						}
						
						int height = component.getHeight();
						int width = component.getWidth();

						//erase current view
						Composite superArg0 = component.getParent();
						component.dispose();

						superArg0.getShell().layout(true, true);
						superArg0.getShell().redraw();
						superArg0.getShell().update();

						//menu view
						AllRecordsView allRecordsView = new AllRecordsView();
						
						allRecordsView.setCurrentRecord(currentRecord);
						allRecordsView.setCurrentView("RAW");
						allRecordsView.setWidth(width+5);
						allRecordsView.setHeight(height);
						allRecordsView.setDataBaseName(dataBaseName);
						allRecordsView.setService(controller.getDataBaseManagerService().getService());
						allRecordsView.setProxyController((ProxyController) controller);
						if(integration)
							allRecordsView.setIntegration(true);
							
						allRecordsView.createUIControl(superArg0);
							
						superArg0.layout(true, true);
						superArg0.redraw();
						superArg0.update();
					}
					catch(Exception e){
						RetroalimentationUtils.showErrorShellMessage(
								"Problema de conección con JISIS");
					}
				}
			}
		}
		else
			RetroalimentationUtils.showErrorShellMessage(
					"Problema de conección con JISIS");
	}	

}
