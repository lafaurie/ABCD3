package cu.uci.abos.widget.repeatable.field.listener;

import java.util.ArrayList;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.widget.repeatable.field.CataloguingComponent;
import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.SubFieldDescription;
import cu.uci.abos.core.util.FormDatas;

public class EventAdd implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private CataloguingComponent topComponent;
	private ExpandBar expandBar;
	private ExpandItem parentExpandItem;
	private CTabFolder tabFolder;
	private SubFieldDescription subFieldDescription;
	private String expandBarText;
	private ToolBar parentBar;
	private ToolItem parentToolItem;
	private String expandItemText;
	private CTabItem tabItem;
	private FieldDomain fieldDomain;
	private ToolItem loupItem;
	private boolean isDataBaseReferenced = false;
	private ArrayList<FieldStructure> children;
	private ArrayList<FieldStructure> notNull;
	private boolean register;

	public EventAdd(CataloguingComponent topComponent, ExpandBar expandBar, ExpandItem parentExpandItem, CTabFolder tabFolder, SubFieldDescription subFielDescription,
			ToolBar parentBar, ToolItem parentToolItem, String expandItemText, CTabItem tabItem, FieldDomain fieldDomain,
			ToolItem luope, ArrayList<FieldStructure> children, ArrayList<FieldStructure> notNull, boolean register) {
		this.tabItem = tabItem;
		this.topComponent = topComponent;
		this.subFieldDescription = subFielDescription;
		this.expandBar = expandBar;
		this.tabFolder = tabFolder;
		this.expandBarText = String.valueOf(fieldDomain.getTag()) + "- " + fieldDomain.getDescription();
		this.parentBar = parentBar;
		this.parentToolItem = parentToolItem;
		this.parentExpandItem = parentExpandItem;
		this.expandItemText = expandItemText;
		this.fieldDomain = fieldDomain;
		int subtag = this.fieldDomain.getSubtag() + 1;
		this.fieldDomain.setSubtag(subtag);
		this.loupItem = luope;
		this.children = children;
		this.notNull = notNull;
		this.register = register;
	}

	@Override
	public void handleEvent(Event arg0) {

		parentToolItem.dispose();

		if(loupItem != null){
			loupItem.dispose();
			isDataBaseReferenced = true;
		}

		ToolItem minus = new ToolItem(parentBar, 0);

		Image imageMinus = new Image(expandBar.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));
		minus.setToolTipText("Eliminar ocurrencia");
		minus.setImage(imageMinus);

		minus.addListener(SWT.Selection, new EventDelete(parentExpandItem, expandBar, topComponent, children));

		Image image = new Image(expandBar.getDisplay(), RWT.getResourceManager().getRegisteredContent("search"));

		if(isDataBaseReferenced){
			ToolItem referencedToolItem1 = new ToolItem(parentBar, 0);
			referencedToolItem1.setImage(image);
		}

		int position = 0;

		boolean foundItem = false;

		for (int i = 0; i < expandBar.getItemCount() && foundItem == false; i++) {

			if (expandBar.getItem(i).getText().equals(expandBarText)) {
				if (i + 1 < expandBar.getItemCount()) {
					if (!expandBar.getItem(i + 1).getText().equals(expandBarText)) {
						foundItem = true;
						position = i;
					}
				} else {
					foundItem = true;
					position = i;
				}
			}

		}

		ExpandItem expandItem = new ExpandItem(expandBar, 0, position + 1);
		expandItem.setText(expandBarText);
		expandItem.setHeight(200);

		expandItem.setExpanded(true);

		Composite expandItemComposite = new Composite(expandBar, 0);
		expandItemComposite.setLayout(new FormLayout());

		ToolBar bar = new ToolBar(expandItemComposite, SWT.WRAP|SWT.FLAT);

		ToolItem toolItem = new ToolItem(bar, 0);

		Image imagePlus = new Image(expandItem.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
		toolItem.setToolTipText("Adicionar ocurrencia");
		toolItem.setImage(imagePlus);

		ToolItem referencedToolItem2 = null;

		if(isDataBaseReferenced){
			referencedToolItem2 = new ToolItem(bar, 0);
			referencedToolItem2.setImage(image);
		}

		FormDatas.attach(bar).atTop(5).atLeft(10);

		this.fieldDomain.setDefaultValue("");

		// Creating the view of the field
		CataloguingComponent gc = new CataloguingComponent(expandItemComposite, 0, subFieldDescription, expandItem, tabFolder,
				expandItemText, tabItem, fieldDomain, children, notNull, register);

		gc.setLayout(new FormLayout());
		FormDatas.attach(gc).atTopTo(expandItemComposite, 0).atLeftTo(expandItemComposite, 0);

		toolItem.addListener(SWT.Selection, new EventAdd(gc, expandBar, expandItem, tabFolder, subFieldDescription, bar,
				toolItem, expandBarText, tabItem, fieldDomain, referencedToolItem2, children, notNull, register));

		// Update
		expandItem.setHeight(expandItemComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

		// SetControl
		expandItem.setControl(expandItemComposite);

		// Change Position
		int last = children.size() - 1;
		FieldStructure fieldStructure = children.get(last);
		children.remove(last);
		int positionField = children.indexOf(topComponent.getFieldStructure());
		children.add(positionField + 1, fieldStructure);
	}

}
