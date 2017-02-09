package cu.uci.abos.widget.repeatable.field.listener;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import cu.uci.abos.widget.repeatable.field.CataloguingComponent;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;

public class EventDelete implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private ExpandBar expandBar;
	private ExpandItem expandItem;
	private CataloguingComponent component;
	private ArrayList<FieldStructure> children;

	public EventDelete(ExpandItem expandItem, ExpandBar expandBar, CataloguingComponent component,
			ArrayList<FieldStructure> children) {
		this.children = children;
		this.expandItem = expandItem;
		this.expandBar = expandBar;
		this.component = component;
	}

	@Override
	public void handleEvent(Event arg0) {

		FieldStructure fieldStructure = component.getFieldStructure();

		int positionField = children.indexOf(fieldStructure);

		children.remove(positionField);

		expandItem.dispose();

		expandBar.getShell().layout(true, true);
		expandBar.getShell().redraw();
		expandBar.getShell().update();
	}

	public void setComponent(CataloguingComponent component){
		this.component = component;
	}

}
