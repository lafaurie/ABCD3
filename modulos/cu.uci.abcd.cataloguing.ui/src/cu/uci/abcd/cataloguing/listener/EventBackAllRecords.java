package cu.uci.abcd.cataloguing.listener;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AllRecordsView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abos.core.util.MessageDialogUtil;

public class EventBackAllRecords implements Listener{

	/**
	 * Basilio Puentes Rodríguez
	 */
	private static final long serialVersionUID = 1L;
	
	private Composite view;
	private String dataBaseName;
	private IJisisDataProvider service;
	private ProxyController controller;
	private boolean messages;
	private AllRecordsView allRecordsView;
	
	public EventBackAllRecords(Composite view, String dataBaseName, IJisisDataProvider service,
			ProxyController controller, boolean messages, AllRecordsView allRecordsView){
		this.view = view;
		this.dataBaseName = dataBaseName;
		this.service = service;
		this.controller = controller;
		this.messages = messages;
		this.allRecordsView = allRecordsView;
	}
 
	@Override
	public void handleEvent(Event arg0) {
		if(messages){
			MessageDialogUtil.openQuestion(view.getShell(), "Pregunta", "¿Se perderán los datos, deseas ir a la vista anterior?", new DialogCallback() {

				/**
				 * Created by Basilio Puentes Rodríguez
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void dialogClosed(int arg0) {

					if(arg0 == 0){
						back();
					}
				}
			});
		}
		else
			back();
	}
	
	private void back(){
		Composite superArg0 = view.getParent();
		view.dispose();

		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();
		
		//llamar a vista
		int width = superArg0.getShell().getBounds().width - 295;
		int height = superArg0.getShell().getBounds().height - 160;
		
		AllRecordsView allRecordsView2 = new AllRecordsView();
		
		allRecordsView2.setCurrentRecord(allRecordsView.getCurrentRecord());
	    allRecordsView2.setWidth(width);
	    allRecordsView2.setHeight(height);
	    allRecordsView2.setDataBaseName(dataBaseName);
	    allRecordsView2.setService(service);
	    allRecordsView2.setProxyController((ProxyController) controller);
	    allRecordsView2.setCurrentView("RAW");
		
	    allRecordsView2.createUIControl(superArg0);
		
		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();
	}
}
