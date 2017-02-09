package cu.uci.abcd.cataloguing.listener;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;
import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.IExemplaryRecord;
import cu.uci.abcd.cataloguing.ILoanObjectCreation;
import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AllRecordsView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.template.component.TemplateCompound;
import cu.uci.abos.widget.template.util.BibliographicConstant;

public class EventEditRecord implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Composite view;
	private Record record;
	private ProxyController controller;
	private String dataBaseName;
	private boolean integration;
	private AllRecordsView allRecordsView;

	public EventEditRecord(Composite view, ProxyController controller,
			String dataBaseName, boolean integration, AllRecordsView allRecordsView){
		this.view = view;
		this.controller = controller;
		this.dataBaseName = dataBaseName;
		this.integration = integration;
		this.allRecordsView = allRecordsView;
	}

	@Override
	public void handleEvent(Event arg0) {
		
		record = allRecordsView.getCurrentRecord();
		String controlNumber = null;
		try {
			controlNumber = record.getField(BibliographicConstant.CONTROL_NUMBER).getStringFieldValue();
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(controlNumber != null){
			IExemplaryRecord exemplaryRecord = controller.getExemplaryRecordService();
			ILoanObjectCreation loanObjectCreation = controller.getLoanObjectCreationService();
			
			List<LoanObject> loanObjects = loanObjectCreation.findAllByControlNumber(controlNumber);
			
			boolean canEdit = exemplaryRecord.canEditAndRemove(loanObjects);
			
			if(canEdit){
				
				//erase current view
				Composite superArg0 = view.getParent();  
				view.dispose();
				
				superArg0.getShell().layout(true, true);
				superArg0.getShell().redraw();
				superArg0.getShell().update();

				IJisisDataProvider service = controller.getDataBaseManagerService().getService();

				TemplateCompound compound;
				try {
					compound = new TemplateCompound(superArg0, SWT.BORDER, record,
							dataBaseName, service);
					compound.createEditComponent();
					compound.setLayout(new FormLayout());
					FormDatas.attach(compound).atTopTo(superArg0, 0).atLeftTo(superArg0, 0);

					Button buttonSave = (Button) compound.getButtonSave();

					buttonSave.addListener(SWT.Selection, new EventSaveRecord((ProxyController) controller, dataBaseName,
							compound, record, integration));
					
					ToolItem back = compound.getBackItem();
					
					back.addListener(SWT.Selection, new EventBackAllRecords(compound, dataBaseName, service, controller, false, allRecordsView));
			
					superArg0.getShell().layout(true, true);
					superArg0.getShell().redraw();
					superArg0.getShell().update();

				} catch (JisisDatabaseException e) {
					RetroalimentationUtils.showErrorShellMessage("Problema de conección con JISIS");
				}
			}
			else{
				RetroalimentationUtils.showErrorShellMessage("El registro posee alguna copia prestada y no puede ser editado.");
			}
		}
		else{
			RetroalimentationUtils.showErrorShellMessage("El registro no posee número de control.");
		}
	}
}
