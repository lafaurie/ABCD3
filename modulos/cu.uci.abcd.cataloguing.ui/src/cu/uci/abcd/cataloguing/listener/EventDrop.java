package cu.uci.abcd.cataloguing.listener;

import org.eclipse.rap.rwt.widgets.DropDown;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.template.component.TemplateCompound;
import cu.uci.abcd.cataloguing.controller.ProxyController;

public class EventDrop implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private DropDown drop;
	private ViewController controller;
	private Composite view;
	private Composite content;
	private Composite downdContent;

	public EventDrop(DropDown drop, ViewController controller, Composite view, Composite content,
			Composite downdContent){
		this.drop = drop;
		this.controller = controller;
		this.view = view;
		this.content = content;
		this.downdContent = downdContent;
	}

	@Override
	public void handleEvent(Event arg0) {

		String[] items = drop.getItems();

		int position = drop.getSelectionIndex();

		String dataBaseName = items[position];

		//erase view
		if(!content.isDisposed()){
			for (int i = 0; i < content.getChildren().length; i++) {
				content.getChildren()[i].dispose();
			}

			content.dispose();

		}

		if(!downdContent.isDisposed()){
			for (int i = 0; i < downdContent.getChildren().length; i++) {
				downdContent.getChildren()[i].dispose();
			}

			downdContent.dispose();

		}

		try{

			IJisisDataProvider service;

			IDataBaseManager dataBaseManager = ((ProxyController)controller).getDataBaseManagerService();

			service = dataBaseManager.getService();

			FormDatas.attach(view).atLeft(0).atRight(0);

			TemplateCompound compound = new TemplateCompound(view, SWT.BORDER,
					dataBaseName, service);

			compound.createComponent();
			compound.setLayout(new FormLayout());

			view.pack();
			view.layout(true, true);
			view.redraw();
			view.update();

			Button buttonSave = (Button) compound.getButtonSave();

			IRecord record = Record.createRecord();

			buttonSave.addListener(SWT.Selection, new EventSaveRecord((ProxyController) controller, dataBaseName,
					compound, (Record)record, false));
		}
		catch(Exception e){

		}

	}


}
