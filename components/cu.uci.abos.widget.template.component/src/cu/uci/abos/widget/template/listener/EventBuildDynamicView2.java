package cu.uci.abos.widget.template.listener;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.template.util.FieldView;

public class EventBuildDynamicView2 implements SelectionListener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private CTabFolder tabFolder;
	private CTabItem tabItem;
	private ArrayList<FieldDomain> fieldsList;
	private Composite compound;
	private Composite view;
	private Composite father;
	private ArrayList<FieldStructure> children;
	private ArrayList<FieldStructure> notNull;
	private ArrayList<Integer> mandatoryExpandItem;
	private IJisisDataProvider service;
	private boolean register;

	public EventBuildDynamicView2(CTabFolder tabFolder, Composite compound, Composite view, Composite father,
			ArrayList<FieldStructure> children, ArrayList<FieldStructure> notNull, ArrayList<Integer> mandatoryExpandItem,
			IJisisDataProvider service, boolean register) throws JisisDatabaseException{
		this.tabFolder = tabFolder;
		this.compound = compound;
		this.view = view;
		this.father = father;
		this.children = children;
		this.notNull = notNull;
		this.mandatoryExpandItem = mandatoryExpandItem;
		this.service = service;
		this.register = register;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {

	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {

		this.tabItem = (CTabItem) arg0.item;
		@SuppressWarnings("unchecked")
		ArrayList<FieldDomain> data = (ArrayList<FieldDomain>) tabItem.getData("fieldsList");
		this.fieldsList = data;

		boolean hasControl;

		Control ctr = tabItem.getControl();

		if(ctr == null)
			hasControl = false;
		else
			hasControl = true;

		if(!hasControl){

			FieldView fieldView = new FieldView(tabFolder, children, notNull, mandatoryExpandItem,
					father, register);

			Composite page = new Composite(tabFolder, SWT.BORDER);
			page.setLayout(new FormLayout());
			FormDatas.attach(page).atTop(0).atLeft(0);

			ExpandBar expandBar = new ExpandBar(page, SWT.V_SCROLL|SWT.H_SCROLL|SWT.BORDER);
			FormDatas.attach(expandBar).atLeft(0).atRight(0).atTop(0).atBottom(0);

			fieldView.setTabItem(tabItem);
			fieldView.setExpandBar(expandBar);

			int fieldsListSize = fieldsList.size();

			for (int i = 0; i < fieldsListSize; i++) {

				String expandItemText = fieldsList.get(i).getExpandItemText();
				ExpandItem expandItem = new ExpandItem(expandBar, SWT.PUSH);
				expandItem.setText(expandItemText);

				fieldView.setFieldDomain(fieldsList.get(i));
				fieldView.setExpandItem(expandItem);

				try {
					fieldView.buildingView(fieldsList.get(i).getSubfieldDescription(), expandItemText,
							compound, view, service);
				} catch (JisisDatabaseException e) {
					e.printStackTrace();
				}
			}

			page.pack();
			page.redraw();
			page.update();
			page.layout(true, true);

			tabItem.setControl(page);

			tabFolder.layout();
			tabFolder.pack();

			father.layout();
			father.pack();

			compound.layout();
			compound.pack();

			view.pack(true);
		}

	}

}
