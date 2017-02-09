package cu.uci.abos.widget.template.listener;

import java.util.ArrayList;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.repeatable.field.CataloguingComponent;
import cu.uci.abos.widget.repeatable.field.listener.EventAdd;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.SubFieldDescription;
import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;
import cu.uci.abos.widget.template.component.TemplateCompound;

public class EventBuildDynamicView implements ExpandListener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private CTabFolder tabFolder;
	private CTabItem tabItem;
	private ExpandBar expandBar;
	private ExpandItem expandItem;
	private CataloguingComponent componet;
	private FieldDomain fieldDomain;
	private ArrayList<FieldStructure> children;
	private ArrayList<FieldStructure> notNull;
	private SubFieldDescription subFieldDescription;
	private String expandItemText;
	private Composite compound;
	private Composite view;
	private int width;
	private int height;
	private Composite father;
	private IJisisDataProvider service;
	private ToolBar bar;
	private boolean register;

	public EventBuildDynamicView(ExpandBar expandBar, CTabFolder tabFolder, CTabItem tabItem, Composite compound, Composite view,
			ArrayList<FieldStructure> children, ArrayList<FieldStructure> notNull, int width,
			int height, Composite father, IJisisDataProvider service, boolean register) {
		this.expandBar = expandBar;
		this.tabFolder = tabFolder;
		this.tabItem = tabItem;
		this.compound = compound;
		this.view = view;
		this.children = children;
		this.notNull = notNull;
		this.width = width;
		this.height = height;
		this.father = father;
		this.service = service;
		this.register = register;
	}

	@Override
	public void itemCollapsed(ExpandEvent arg0) {

	}

	@Override
	public void itemExpanded(ExpandEvent arg0) {

		this.bar = null;
		this.expandItem = (ExpandItem) arg0.item;
		this.fieldDomain = (FieldDomain) expandItem.getData("fieldDomain");
		this.subFieldDescription = (SubFieldDescription) expandItem.getData("subFieldDescription");

		boolean hasControl;

		Control ctr = expandItem.getControl();

		if(ctr == null)
			hasControl = false;
		else
			hasControl = true;

		if (!hasControl) {

			Composite expandItemComposite = new Composite(expandBar, 0);
			expandItemComposite.setLayout(new FormLayout());

			// Creating the view of the field
			boolean repeatable = fieldDomain.isRepeatableField();

			ToolItem toolItem = null;
			ToolItem referencedToolItem = null;

			if (repeatable)
				toolItem = addPlusItem(repeatable, expandItemComposite, subFieldDescription,
						subFieldDescription.getComboList());

			if(fieldDomain.isReferenceDataBase())
				try {
					referencedToolItem = referenceDataBase(expandItemComposite, fieldDomain.getReferencedDataBase(),
							service);
				} catch (JisisDatabaseException e) {
					e.printStackTrace();
				}

			componet = new CataloguingComponent(expandItemComposite, 0, subFieldDescription, expandItem, tabFolder,
					expandItemText, tabItem, fieldDomain, children, notNull, register);

			componet.setLayout(new FormLayout());

			if (repeatable) {
				toolItem.addListener(SWT.Selection, new EventAdd(componet, expandBar, expandItem, tabFolder, subFieldDescription, bar, toolItem,
						expandItemText, tabItem, fieldDomain, referencedToolItem, children, notNull, register));
			}

			updateExpandItem(expandItemComposite);
			expandItemComposite.setEnabled(true);

			expandBar.layout();
			//expandBar.pack();
		}
	}

	private ToolItem addPlusItem(boolean repeatedField, Composite expandItemComposite, SubFieldDescription subFieldDescription,
			ArrayList<String[]> comboList) {
		bar = new ToolBar(expandItemComposite, SWT.WRAP|SWT.FLAT);
		ToolItem toolItem = new ToolItem(bar, 0);
		Image image = new Image(expandItemComposite.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
		toolItem.setImage(image);
		toolItem.setToolTipText("Adicionar ocurrencia");
		FormDatas.attach(bar).atTop(5).atLeft(10);
		return toolItem;
	}

	private ToolItem referenceDataBase(Composite expandItemComposite, String dataBaseReferenced,
			IJisisDataProvider service) throws JisisDatabaseException{
		if(bar == null){
			bar = new ToolBar(expandItemComposite, SWT.WRAP|SWT.FLAT);
			FormDatas.attach(bar).atTop(5).atLeft(10);
		}
		ToolItem referencedToolItem = new ToolItem(bar, 0);
		Image image = new Image(expandItemComposite.getDisplay(), RWT.getResourceManager().getRegisteredContent("search"));
		referencedToolItem.setImage(image);
		referencedToolItem.setToolTipText("Ver base de datos referenciada");
		referencedToolItem.addListener(SWT.Selection, new EventStructureField8(dataBaseReferenced,
				service, tabFolder, width, height, father, notNull));
		TemplateCompound.dataReferencedName = dataBaseReferenced;

		return referencedToolItem;
	}

	private void updateExpandItem(Composite expandItemComposite) {

		expandItemComposite.layout();
		expandItemComposite.pack();

		expandItem.setExpanded(true);
		expandItem.setHeight(expandItemComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

		// SetControl
		expandItem.setControl(expandItemComposite);

		compound.layout();
		compound.pack();

		view.pack(true);
	}



}
