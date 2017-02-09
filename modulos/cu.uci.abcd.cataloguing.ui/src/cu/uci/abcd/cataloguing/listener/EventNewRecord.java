package cu.uci.abcd.cataloguing.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AllRecordsView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.template.component.TemplateCompound;

public class EventNewRecord implements Listener{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	private static final long serialVersionUID = 1L;
	
	private Composite view;
	private ProxyController controller;
	private String dataBaseName;
	private boolean integration;
	private AllRecordsView allRecordsView;
	
	public EventNewRecord(Composite view, ProxyController controller, 
			String dataBaseName, boolean integration, AllRecordsView allRecordsView){
		this.view = view;
		this.controller = controller;
		this.dataBaseName = dataBaseName;
		this.integration = integration;
		this.allRecordsView = allRecordsView;
	}

	@Override
	public void handleEvent(Event arg0) {
		//erase current view
		Composite superArg0 = view.getParent();  
		view.dispose();
		
		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();

		IJisisDataProvider service = controller.getDataBaseManagerService().getService();

		TemplateCompound compound;
		try {
			compound = new TemplateCompound(superArg0, SWT.BORDER,
					dataBaseName, service);
			compound.createComponent();
			compound.setLayout(new FormLayout());
			FormDatas.attach(compound).atTopTo(superArg0, 0).atLeftTo(superArg0, 0);

			Button buttonSave = (Button) compound.getButtonSave();
			
			IRecord record = Record.createRecord();

			buttonSave.addListener(SWT.Selection, new EventSaveRecord((ProxyController) controller, dataBaseName,
					compound, (Record) record, integration));
			
			ToolItem back = compound.getBackItem();
			
			back.addListener(SWT.Selection, new EventBackAllRecords(compound, dataBaseName, service, controller, true, allRecordsView));
			
			superArg0.getShell().layout(true, true);
			superArg0.getShell().redraw();
			superArg0.getShell().update();

		} catch (JisisDatabaseException e) {
			RetroalimentationUtils.showErrorShellMessage("Problema de conección con JISIS");
		}
	}
}
