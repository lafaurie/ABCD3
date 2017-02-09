package cu.uci.abcd.acquisition.ui.updateArea;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.template.component.TemplateCompound;

public class EventEditExemplaryRecord implements Listener {

	private static final long serialVersionUID = 1L;

	private Composite view;
	private Composite parent;
	private Record record;
	private ViewController controller;
	private String dataBaseName;
	private ArrayList<FieldStructure> children;
	private IDataBaseManager dataBaseManager;

	public EventEditExemplaryRecord(Composite view, Composite parent, Record record, ViewController controller, String dataBaseName) {
		this.view = view;
		this.parent = parent;
		this.record = record;
		this.controller = controller;
		this.dataBaseName = dataBaseName;
	}
	/*public EventEditExemplaryRecord(Composite view, Composite parent, Record record, ViewController controller, String dataBaseName,ArrayList<FieldStructure> children, IDataBaseManager dataBaseManager) {
		this.view = view;
		this.parent = parent;
		this.record = record;
		this.controller = controller;
		this.dataBaseName = dataBaseName;
		this.children = children;
		this.dataBaseManager = dataBaseManager;
	}*/
	@Override
	public void handleEvent(Event arg0) {

			/*QuickSort sort = new QuickSort();
			sort.quickSort(0, children.size()-1, children);
			
			JisisRegistration jisis = new JisisRegistration();

			boolean save = jisis.save(children, record);

			   try {
				   
					if(save){
						
						dataBaseManager.updateRecord(record, dataBaseName, Util.getDefHome());
						
						//For loan object
						//actualizar todos los loan object que tengan el mismo numero de control los datos comunes del registro
						
					}

				} catch (JisisDatabaseException e) {
					e.printStackTrace();
				}
			 //  return record;
		
	}*/
		
		for (int i = 0; i < parent.getChildren().length; i++) {
			parent.getChildren()[i].dispose();
		}

		parent.dispose();

		view.getShell().layout(true, true);
		view.getShell().redraw();
		view.getShell().update();

		/*
		 * Composite newParent = new Composite(view, 0);
		 * 
		 * newParent.setLayout(new FormLayout());
		 */

		IJisisDataProvider service = ((AllManagementController) controller).getAcquisition().getService();

		TemplateCompound compound;
		try {
			view.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			compound = new TemplateCompound(view, 0, record, dataBaseName, service);

			compound.setLayout(new FormLayout());

			FormDatas.attach(compound).atTopTo(view, 0).atLeftTo(view, 0);

			
			  Button buttonSave = (Button) compound.getButtonSave();
			  
			 Shell shell = view.getShell();
			  
			  Library userLogLibrary = compound.getUserLogLibrary();
			  
			  buttonSave.addListener(SWT.Selection, new EventButtonSave(compound,(AllManagementController) controller,userLogLibrary,dataBaseName,null,null,view));
					  
					  
			 // (AllManagementController) controller, dataBaseName, shell,
			//  compound, view, userLogLibrary
			 

		} catch (JisisDatabaseException e) {
			e.printStackTrace();
		}
	}
}
