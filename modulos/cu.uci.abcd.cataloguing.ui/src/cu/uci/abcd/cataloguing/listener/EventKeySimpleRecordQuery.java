package cu.uci.abcd.cataloguing.listener;

import java.util.List;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.cataloguing.IExemplaryRecord;
import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.RecordQuery;
import cu.uci.abcd.cataloguing.ui.ReferenceView;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.util.RetroalimentationUtils;

public class EventKeySimpleRecordQuery implements KeyListener{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	private static final long serialVersionUID = 1L;
	
	private ProxyController controller;
	private String dataBaseName;
	private Composite view;
	private Text textBox;
	
	public EventKeySimpleRecordQuery(ProxyController controller, String dataBaseName, Composite view,
			Text textBox){
		this.controller = controller;
		this.dataBaseName = dataBaseName;
		this.view = view;
		this.textBox = textBox;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent service) {
		if(service.keyCode == 13){
			String term = textBox.getText();
			term = term.replaceAll(" +"," ").trim();
			
			if(term != null && !term.equals("")){
				List<Record> records = buildQuery(term);
				
				if(records == null || records.size() == 0){
					RetroalimentationUtils.showErrorShellMessage(    				
							"No se encontró ningún resultado.");
				}
				else{
					//erase view
					Composite superArg0 = view.getParent();  
					view.dispose();
							
					superArg0.getShell().layout(true, true);
					superArg0.getShell().redraw();
					superArg0.getShell().update();
					
					//Paso a la vista
					RecordQuery recordQuery = new RecordQuery();
					recordQuery.setRecords(records);
					recordQuery.setController(controller);
					recordQuery.setDataBaseName(dataBaseName);
					recordQuery.setReferenceView(ReferenceView.Catalog);

					recordQuery.createUIControl(superArg0);
					//recordQuery.setToolBarVisible(false);

					superArg0.getShell().layout(true, true);
					superArg0.getShell().redraw();
					superArg0.getShell().update();
				}
			}
			else
				RetroalimentationUtils.showErrorShellMessage(
						"Debe proporcionar algún criterio de búsqueda");
		}
	}
	
	private List<Record> buildQuery(String term){

		List<Record> records = null;

		IDataBaseManager dataBaseManager = controller.getDataBaseManagerService();
		IExemplaryRecord exemplaryRecord = controller.getExemplaryRecordService();
		
		try {
			
			records = exemplaryRecord.findExemplaryRecords(term, dataBaseName, dataBaseManager);
			
		} catch (JisisDatabaseException e) {
			RetroalimentationUtils.showErrorShellMessage(
					"Problema de conección con JISIS");
		}

		return records;
	}
}
