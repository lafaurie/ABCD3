package cu.uci.abos.widget.repeatable.field.listener;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.widget.repeatable.field.ControlType;
import cu.uci.abos.widget.repeatable.field.SimpleCataloguingComponent;
import cu.uci.abos.widget.repeatable.field.util.SimpleFieldStructure;
import cu.uci.abos.core.util.FormDatas;

public class EventSimpleAdd implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private String textName = "";
	private Composite father;
	private Composite parent;
	private ControlType controlType;
	private ToolBar parentBar;
	private ToolItem parentToolItem;
	private Composite content;
	private SimpleFieldStructure topComponent;
	private String[] picklist;
	private SimpleCataloguingComponent component;

	public EventSimpleAdd(String text, Composite father, Composite parent, ControlType controlType, ToolBar parentBar, ToolItem parentToolItem,
			Composite content, SimpleFieldStructure topComponent, String[] picklist, SimpleCataloguingComponent component) {

		this.component = component;
		this.textName = text;
		this.father = father;
		this.parent = parent;
		this.controlType = controlType;
		this.parentBar = parentBar;
		this.parentToolItem = parentToolItem;
		this.content = content;
		this.topComponent = topComponent;
		this.picklist = picklist;
	}

	@Override
	public void handleEvent(Event arg0) {

		parentToolItem.dispose();

		ToolItem minus = new ToolItem(parentBar, SWT.PUSH);
		Image image = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));
		minus.setImage(image);
		minus.addListener(SWT.Selection, new EventSimpleDelete(parent, father, content, topComponent, component));

		SimpleCataloguingComponent component = new SimpleCataloguingComponent(father, 0, textName, controlType,
				true, picklist);
		component.setLayout(new FormLayout());

		int childrenCount = father.getChildren().length;

		// Posicionar Abajo
		for (int i = 0; i < childrenCount; i++) {

			if (i == 0) {
				FormDatas.attach(father.getChildren()[i]).atTopTo(father, 0);
			} else {
				FormDatas.attach(father.getChildren()[i]).atTopTo(father.getChildren()[i - 1], 10);
			}
		}

		father.getShell().layout(true, true);
		father.getShell().redraw();
		father.getShell().update();

		parent.getShell().layout(true, true);
		parent.getShell().redraw();
		parent.getShell().update();
	}

}
