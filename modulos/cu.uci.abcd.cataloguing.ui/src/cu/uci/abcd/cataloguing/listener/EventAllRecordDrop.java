package cu.uci.abcd.cataloguing.listener;

import org.eclipse.rap.rwt.widgets.DropDown;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AllRecordsView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.widget.template.util.Util;

public class EventAllRecordDrop implements Listener{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	private static final long serialVersionUID = 1L;
	
	private DropDown drop;
	private Composite view;
	private ViewController controller;
	
	public EventAllRecordDrop(DropDown drop, Composite view,
			ViewController controller) {
		this.drop = drop;
		this.view = view;
		this.controller = controller;
	}

	@Override
	public void handleEvent(Event arg0) {
		
		String[] items = drop.getItems();

		int position = drop.getSelectionIndex();

		String dataBaseName = items[position];

		//erase view
		Composite superArg0 = view.getParent();  
		view.dispose();
		
		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();

		try{
			IJisisDataProvider service;

			IDataBaseManager dataBaseManager = ((ProxyController)controller).getDataBaseManagerService();
			service = dataBaseManager.getService();

		    //llamar a vista
			int width = superArg0.getShell().getBounds().width - 295;
			int height = superArg0.getShell().getBounds().height - 160;
			
		    AllRecordsView allRecords = new AllRecordsView();
		    
		    Record firstRecord = service.getFirstRecord(dataBaseName, Util.getDefHome());
		    
		    allRecords.setCurrentRecord(firstRecord);
		    allRecords.setWidth(width);
			allRecords.setHeight(height);
			allRecords.setDataBaseName(dataBaseName);
			allRecords.setService(service);
			allRecords.setProxyController((ProxyController) controller);
			allRecords.setCurrentView("RAW");
			
			allRecords.createUIControl(superArg0);
			
			superArg0.getShell().layout(true, true);
			superArg0.getShell().redraw();
			superArg0.getShell().update();
		}
		catch(Exception e){  
             e.printStackTrace();
		}
		
	}

}
