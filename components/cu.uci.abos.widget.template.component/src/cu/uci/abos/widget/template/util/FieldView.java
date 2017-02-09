package cu.uci.abos.widget.template.util;

import java.util.ArrayList;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.widget.repeatable.field.CataloguingComponent;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.SubFieldDescription;
import cu.uci.abos.widget.repeatable.field.listener.EventAdd;
import cu.uci.abos.widget.repeatable.field.listener.EventDelete;
import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;
import cu.uci.abos.widget.template.component.TemplateCompound;
import cu.uci.abos.widget.template.listener.EventBuildDynamicView;
import cu.uci.abos.widget.template.listener.EventStructureField8;
import cu.uci.abos.core.util.FormDatas;

public class FieldView {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private CTabFolder tabFolder;
	private CTabItem tabItem;
	private ExpandBar expandBar;
	private ExpandItem expandItem;
	private FieldDomain fieldDomain;
	private ArrayList<FieldStructure> children;
	private ArrayList<FieldStructure> notNull;
	private ArrayList<Integer> mandatoryExpandItem;
	private int width;
	private int height;
	private Composite father;
	private boolean register;
	private boolean notPlusItem;
	private ToolBar bar;
	private EventDelete eventDelete;

	public void setNoPlusItem(boolean value){
		this.notPlusItem = value;
	}

	public FieldView(CTabFolder tabFolder, ArrayList<FieldStructure> children, ArrayList<FieldStructure> notNull,
			ArrayList<Integer> mandatoryExpandItem, Composite father, boolean register) {
		this.tabFolder = tabFolder;
		this.children = children;
		this.notNull = notNull;
		this.mandatoryExpandItem = mandatoryExpandItem;
		this.mandatoryExpandItem.add(0,1);
		this.father = father;
		this.register = register;
		this.notPlusItem = false;
	}

	public void buildingView(SubFieldDescription subFieldDescription, String expandItemText,
			Composite compound, Composite view, IJisisDataProvider service)throws JisisDatabaseException{

		this.bar = null;
		int tag = fieldDomain.getTag();

		if (mandatoryExpandItem.contains(tag)){

			Composite expandItemComposite = createComposite();

			expandItem.setExpanded(true);

			// Creating the view of the field
			boolean repeatable = fieldDomain.isRepeatableField();

			ToolItem toolItem = null;
			ToolItem referencedToolItem = null;
			CataloguingComponent component = null;
			this.eventDelete = null;

			if (repeatable)
				toolItem = addPlusItem(repeatable, expandItemComposite, subFieldDescription,
						subFieldDescription.getComboList(), component);

			if(fieldDomain.isReferenceDataBase())
				referencedToolItem = referenceDataBase(expandItemComposite, fieldDomain.getReferencedDataBase(),
						service);

			component = new CataloguingComponent(expandItemComposite, 0, subFieldDescription, expandItem, tabFolder,
					expandItemText, tabItem, fieldDomain, children, notNull, register);
			component.setLayout(new FormLayout());

			if (repeatable) {
				if(!notPlusItem)
					toolItem.addListener(SWT.Selection, new EventAdd(component, expandBar, expandItem, tabFolder, subFieldDescription, bar, toolItem,
							expandItemText, tabItem, fieldDomain, referencedToolItem, children, notNull, register));
				else
					eventDelete.setComponent(component);
			}
			updateExpandItem(expandItemComposite);

			expandBar.layout();
			expandBar.pack();

		} else {			
			expandItem.setData("fieldDomain",fieldDomain);
			expandItem.setData("subFieldDescription", subFieldDescription);

			expandBar.addExpandListener(new EventBuildDynamicView(expandBar, tabFolder, tabItem,
					compound, view, children, notNull, width, height, father, service, register));
		}
	}

	private Composite createComposite() {
		Composite composite = new Composite(expandBar, 0);
		composite.setLayout(new FormLayout());
		FormDatas.attach(composite).atTop(0).atLeft(0);
		return composite;
	}

	private ToolItem addPlusItem(boolean repeatedField, Composite expandItemComposite, SubFieldDescription subFieldDescription,
			ArrayList<String[]> comboList, CataloguingComponent component) {
		bar = new ToolBar(expandItemComposite, SWT.WRAP|SWT.FLAT);
		ToolItem toolItem = new ToolItem(bar, 0);
		Image image = new Image(expandItemComposite.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
		toolItem.setImage(image);
		toolItem.setToolTipText("Adicionar ocurrencia");
		if(!register){
			if(notPlusItem){
				toolItem.dispose();

				ToolItem minusToolItem = new ToolItem(bar, 0);
				Image minusImage = new Image(expandItemComposite.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));
				minusToolItem.setImage(minusImage);
				minusToolItem.setToolTipText("Eliminar ocurrencia");
				eventDelete = new EventDelete(expandItem, expandBar, component, children);
				minusToolItem.addListener(SWT.Selection, eventDelete);
			}

		}

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
		// UpdateSize
		expandItem.setHeight(expandItemComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

		// SetControl
		expandItem.setControl(expandItemComposite);
	}

	public void setFieldDomain(FieldDomain fieldDomain){
		this.fieldDomain = fieldDomain;
	}

	public void setExpandItem(ExpandItem expandItem){
		this.expandItem = expandItem;
	}

	public void setTabItem(CTabItem tabItem){
		this.tabItem = tabItem;
	}

	public void setExpandBar(ExpandBar expandBar){
		this.expandBar = expandBar;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setHeight(int height){
		this.height = height;
	}

}
