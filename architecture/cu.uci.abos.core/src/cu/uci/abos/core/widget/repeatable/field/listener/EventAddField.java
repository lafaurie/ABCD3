package cu.uci.abos.core.widget.repeatable.field.listener;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.repeatable.field.CataloguingComponent;
import cu.uci.abos.core.widget.repeatable.field.ControlType;
import cu.uci.abos.core.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.core.widget.repeatable.field.util.SubFieldStructure;

public class EventAddField implements Listener {

	private static final long serialVersionUID = 1L;

	private String textName = "";
	private Composite father;
	private ExpandItem expandItem;
	private Composite parent;
	private int type;
	private ControlType controlType;
	private CTabFolder tabFolder;
	private String description;
	private int tag;
	private String moreBig;
	private FieldStructure topComponent;
	private String subFieldCode;
	private String[] comboList;
	private ToolBar bar;
	private ToolItem toolItem;
	private Composite content;
	private SubFieldStructure subFieldStructure;
	private boolean isRepeatable;
	private ToolBar upDownBar;

	public EventAddField(String text, Composite father, ExpandItem expandItem, Composite parent, int type, ControlType controlType, CTabFolder tabFolder, String description, int tag, String moreBig,
			FieldStructure topComponent, String subFieldCode, String[] comboList, ToolBar bar, ToolItem toolItem, Composite content, SubFieldStructure subFieldStructure, boolean isRepeatable,
			ToolBar upDownBar) {

		this.textName = text;
		this.father = father;
		this.expandItem = expandItem;
		this.parent = parent;
		this.type = type;
		this.controlType = controlType;
		this.tabFolder = tabFolder;
		this.description = description;
		this.tag = tag;
		this.moreBig = moreBig;
		this.topComponent = topComponent;
		this.subFieldCode = subFieldCode;
		this.comboList = comboList;
		this.bar = bar;
		this.toolItem = toolItem;
		this.content = content;
		this.subFieldStructure = subFieldStructure;
		this.isRepeatable = isRepeatable;
		this.upDownBar = upDownBar;
	}

	@Override
	public void handleEvent(Event arg0) {

		upDownBar.dispose();

		toolItem.dispose();

		ToolItem minus = new ToolItem(bar, SWT.PUSH);

		Image image = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));

		minus.setImage(image);

		CataloguingComponent component = new CataloguingComponent(father, 0, type, textName, controlType, expandItem, tabFolder, description, tag, moreBig, topComponent, subFieldCode, comboList,
				subFieldStructure, parent, isRepeatable);

		component.setLayout(new FormLayout());

		int childrenCount = father.getChildren().length;

		// Posicionar abajo

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

		expandItem.setHeight(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

		minus.addListener(SWT.Selection, new EventDeteleField(parent, expandItem, father, content, subFieldStructure, topComponent));
	}
}
