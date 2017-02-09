package cu.uci.abos.core.widget.repeatable.field.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.widget.repeatable.field.CataloguingComponent;
import cu.uci.abos.core.widget.repeatable.field.util.FieldStructure;

public class EventDelete implements Listener {

	private static final long serialVersionUID = 1L;

	private ExpandBar expandBar;
	private ExpandItem expandItem;
	private CataloguingComponent component;

	public EventDelete(ExpandItem expandItem, ExpandBar expandBar, CataloguingComponent component) {

		this.expandItem = expandItem;
		this.expandBar = expandBar;
		this.component = component;
	}

	@Override
	public void handleEvent(Event arg0) {

		FieldStructure fieldStructure = component.getFieldStructure();

		int positionField = CataloguingComponent.childrens.indexOf(fieldStructure);

		CataloguingComponent.childrens.remove(positionField);

		expandItem.dispose();

		expandBar.getShell().layout(true, true);
		expandBar.getShell().redraw();
		expandBar.getShell().update();

	}

}
