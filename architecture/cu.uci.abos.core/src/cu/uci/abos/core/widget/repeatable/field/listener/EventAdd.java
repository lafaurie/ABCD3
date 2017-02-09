package cu.uci.abos.core.widget.repeatable.field.listener;

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

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.repeatable.field.CataloguingComponent;
import cu.uci.abos.core.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.core.widget.repeatable.field.util.SubFieldDescription;

public class EventAdd implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CataloguingComponent topComponent;
	private ExpandBar expandBar;
	private ExpandItem parentExpandItem;
	private CTabFolder tabFolder;
	private SubFieldDescription subFieldDescription;
	private int tag;
	private String description;
	private String expandBarText;
	private ToolBar parentBar;
	private ToolItem parentToolItem;
	private String expandItemText;
	private Long registrationNumber;
	private CTabItem tabItem;

	public EventAdd(CataloguingComponent topComponent, ExpandBar expandBar, ExpandItem parentExpandItem, CTabFolder tabFolder, SubFieldDescription subFielDescription, int tag, String description,
			ToolBar parentBar, ToolItem parentToolItem, String expandItemText, long registrationNumber, CTabItem tabItem) {
		this.tabItem = tabItem;
		this.topComponent = topComponent;
		this.subFieldDescription = subFielDescription;
		this.expandBar = expandBar;
		this.tabFolder = tabFolder;
		this.tag = tag;
		this.description = description;
		this.expandBarText = String.valueOf(tag) + "- " + description;
		this.parentBar = parentBar;
		this.parentToolItem = parentToolItem;
		this.parentExpandItem = parentExpandItem;
		this.expandItemText = expandItemText;
		this.registrationNumber = registrationNumber;
	}

	@Override
	public void handleEvent(Event arg0) {

		parentToolItem.dispose();

		ToolItem minus = new ToolItem(parentBar, SWT.PUSH);

		Image imageMinus = new Image(expandBar.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));

		minus.setImage(imageMinus);

		minus.addListener(SWT.Selection, new EventDelete(parentExpandItem, expandBar, topComponent));

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

		ToolBar bar = new ToolBar(expandItemComposite, 0);

		ToolItem toolItem = new ToolItem(bar, SWT.PUSH);

		Image imagePlus = new Image(expandItem.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));

		toolItem.setImage(imagePlus);

		FormDatas.attach(bar).atTop(5).atLeft(10);

		// Creating the view of the field
		CataloguingComponent gc = new CataloguingComponent(expandItemComposite, 0, subFieldDescription, expandItem, true, tabFolder, description, tag, expandItemText, registrationNumber, tabItem);

		gc.setLayout(new FormLayout());

		FormDatas.attach(gc).atTopTo(expandItemComposite, 0).atLeftTo(expandItemComposite, 0);

		toolItem.addListener(SWT.Selection, new EventAdd(gc, expandBar, expandItem, tabFolder, subFieldDescription, tag, description, bar, toolItem, expandBarText, registrationNumber, tabItem));

		// Update
		expandItem.setHeight(expandItemComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

		// SetControl
		expandItem.setControl(expandItemComposite);

		// Change Position
		int last = CataloguingComponent.childrens.size() - 1;
		FieldStructure fieldStructure = CataloguingComponent.childrens.get(last);
		CataloguingComponent.childrens.remove(last);
		int positionField = CataloguingComponent.childrens.indexOf(topComponent.getFieldStructure());
		CataloguingComponent.childrens.add(positionField + 1, fieldStructure);

	}

}
