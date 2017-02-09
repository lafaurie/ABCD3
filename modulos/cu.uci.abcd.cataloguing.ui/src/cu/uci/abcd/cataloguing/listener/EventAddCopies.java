package cu.uci.abcd.cataloguing.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.Record;
import org.unesco.jisis.corelib.record.StringOccurrence;
import org.unesco.jisis.corelib.record.Subfield;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AllRecordsView;
import cu.uci.abcd.cataloguing.ui.CopiesManagement;
import cu.uci.abcd.cataloguing.util.Constant;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abos.widget.template.util.Util;


public class EventAddCopies implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Composite view;
	private ProxyController proxyController;
	private int width;
	private IJisisDataProvider service;
	private String dataBaseName;
	private LoanObject loanObject;
	private int height;
	private AllRecordsView allRecordsView;

	public EventAddCopies(Composite view, ProxyController proxyController, 
			int width, String dataBaseName, int height, AllRecordsView allRecordsView){
		this.view = view;
		this.proxyController = proxyController;
		this.width = width-5;
		this.service = proxyController.getDataBaseManagerService().getService();
		this.dataBaseName = dataBaseName;
		this.height = height;
		this.allRecordsView = allRecordsView;
	}

	@Override
	public void handleEvent(Event arg0) {

		//erase
		Composite superArg0 = view.getParent();  
		view.dispose();
			
	    superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
	    superArg0.getShell().update();
		
		long currentMFN = allRecordsView.getCurrentRecord().getMfn();
		Record currentRecord = null;
		String isisDefHome = Util.getDefHome();
		
		try {
			currentRecord = service.findByMfn(currentMFN, dataBaseName, isisDefHome);
		} catch (JisisDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 loanObject = new LoanObject();
		//setLoanObjectValues
		try {
			//controlNumber
			loanObject.setControlNumber(currentRecord.getField(Constant.CONTROL_NUMBER).getStringFieldValue());
			
			//title
			Field field = (Field) currentRecord.getField(Constant.TITLE);
			StringOccurrence occurrence = (StringOccurrence) field.getOccurrence(0);
			Subfield[] subFieldsRecord = occurrence.getSubfields();
			int count1 = subFieldsRecord.length;
			for (int i = 0; i < count1; i++) {
				if(Constant.TITLE_SUB_FIELD_CODE == subFieldsRecord[i].getSubfieldCode()){
					loanObject.setTitle(subFieldsRecord[i].getData());
					break;
				}
			}

			//author
			Field field2 = (Field) currentRecord.getField(Constant.AUTHOR);
			StringOccurrence occurrence2 = (StringOccurrence) field2.getOccurrence(0);
			if(occurrence2 != null){
				Subfield[] subFieldsRecord2 = occurrence2.getSubfields();
				int count2 = subFieldsRecord2.length;
				for (int i = 0; i < count2; i++) {
					if(Constant.AUTHOR_SUB_FIELD_CODE == subFieldsRecord[i].getSubfieldCode()){
						loanObject.setAuthor(subFieldsRecord2[i].getData());
						break;
					}
				}
			}

			//isisDataBaseName
			loanObject.setIsisDataBaseName(dataBaseName);

			//editionNumber
			Field field3 = (Field) currentRecord.getField(Constant.EDITION_NUMBER);
			StringOccurrence occurrence3 = (StringOccurrence) field3.getOccurrence(0);
			if(occurrence3 != null){
				Subfield[] subFieldsRecord3 = occurrence3.getSubfields();
				int count3 = subFieldsRecord3.length;
				for (int i = 0; i < count3; i++) {
					if(Constant.EDITION_SUB_FIELD_CODE == subFieldsRecord[i].getSubfieldCode()){
						loanObject.setEditionNumber(subFieldsRecord3[i].getData());
						break;
					}
				}
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CopiesManagement exemplaryInsert = new CopiesManagement();

		exemplaryInsert.setAllRecordsView(allRecordsView);
		exemplaryInsert.setLoanObject(loanObject);
		exemplaryInsert.setProxyController(proxyController); 
		exemplaryInsert.setMfn(currentMFN);
		exemplaryInsert.setWidth(width);
		exemplaryInsert.setHeight(height);
		exemplaryInsert.setController(proxyController);
		exemplaryInsert.setDataBaseName(dataBaseName);
		exemplaryInsert.setService(service);

		exemplaryInsert.createUIControl(superArg0);

		superArg0.setSize(SWT.DEFAULT, superArg0.computeSize(SWT.DEFAULT,
				SWT.DEFAULT).y);

		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();
	}

	public LoanObject getLoanObject() {
		return loanObject;
	}

	public void setLoanObject(LoanObject loanObject) {
		this.loanObject = loanObject;
	}
}
