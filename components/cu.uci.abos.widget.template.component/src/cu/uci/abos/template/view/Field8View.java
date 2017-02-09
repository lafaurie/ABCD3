package cu.uci.abos.template.view;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.repeatable.field.Field8Component;
import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.template.listener.EventField8Cancel;
import cu.uci.abos.widget.template.listener.EventSaveField8;


public class Field8View implements Contributor {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private ArrayList<FieldDomain> fieldsDomain;
	private ArrayList<FieldStructure> children;
	private int width;
	private int height;
	private CTabItem tabItem;
	private CTabItem tabItemSelection;
	private CTabFolder tabFolder;
	private IJisisDataProvider service;
	private String dataBaseName;
	private Text field8Text;

	public void setField8Text(Text text){
		this.field8Text = text;
	}

	public void setDataBaseName(String dataBaseName){
		this.dataBaseName = dataBaseName;
	}

	public void setService(IJisisDataProvider service){
		this.service = service;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public void setFieldsDomain(ArrayList<FieldDomain> fieldsDomain){
		this.fieldsDomain = fieldsDomain;
	}

	public void setTabItem(CTabItem tabItem){
		this.tabItem = tabItem;
	}

	public void setTabItemSelection(CTabItem tabItemSelection){
		this.tabItemSelection = tabItemSelection;
	}

	public void setTabFolder(CTabFolder tabFolder){
		this.tabFolder = tabFolder;
	}

	@Override
	public Control createUIControl(Composite arg0) {

		children = new ArrayList<FieldStructure>();

		Composite view = new Composite(arg0, 0);
		view.setLayout(new FormLayout());
		FormDatas.attach(view).atLeft(5).atRight(5).atTop(5).atBottom(5).withHeight(height).withWidth(width);

		Composite head = new Composite(view, 0);
		head.setLayout(new FormLayout());
		FormDatas.attach(head).atLeft(0).atRight(0);

		Button save = new Button(head, SWT.PUSH);
		save.setText("Guardar");

		Button cancel = new Button(head, SWT.PUSH);
		cancel.setText("Cancelar");

		FormDatas.attach(save).atTopTo(head, 0).atLeftTo(head, 5);
		FormDatas.attach(cancel).atTopTo(head, 0).atLeftTo(save, 5);

		Composite body = new Composite(view, 0);
		body.setLayout(new FormLayout());
		FormDatas.attach(body).atTopTo(head, 5).atBottom(0).atLeft(0).atRight(0);

		ExpandBar bar = new ExpandBar(body, SWT.V_SCROLL|SWT.H_SCROLL);
		FormDatas.attach(bar).atLeft(0).atRight(0).atTop(0).atBottom(0);
		bar.addExpandListener(new ExpandListener() {
			
			/**
			 * Created by Basilio Puentes Rodríguez
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void itemExpanded(ExpandEvent arg0) {
				
			}
			
			@Override
			public void itemCollapsed(ExpandEvent arg0) {
				ExpandItem expandItem = (ExpandItem) arg0.item;
				expandItem.setExpanded(true);
			}
		});

		ExpandItem item = new ExpandItem(bar, SWT.PUSH);
		item.setText("Base de datos: "+dataBaseName);

		Composite page = new Composite(bar, 0);
		page.setLayout(new FormLayout());

		Field8Component component = new Field8Component(page, 0, fieldsDomain, children);
		component.setLayout(new FormLayout());
		FormDatas.attach(component).atTop(10).atLeft(10);

		page.redraw();
		page.update();
		page.layout(true, true);

		item.setHeight(page.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

		item.setControl(page);
		item.setExpanded(true);

		view.redraw();
		view.update();
		view.layout(true, true);

		cancel.addListener(SWT.Selection, new EventField8Cancel(tabItem, tabItemSelection, tabFolder));

		save.addListener(SWT.Selection, new EventSaveField8(children, service,
				dataBaseName, tabFolder, tabItem, tabItemSelection, field8Text));

		return arg0;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String contributorName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setViewController(ViewController arg0) {
		// TODO Auto-generated method stub

	}

}
