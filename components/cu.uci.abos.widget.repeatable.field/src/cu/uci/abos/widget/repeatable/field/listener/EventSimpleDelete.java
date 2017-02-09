package cu.uci.abos.widget.repeatable.field.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.repeatable.field.SimpleCataloguingComponent;
import cu.uci.abos.widget.repeatable.field.util.SimpleFieldStructure;

public class EventSimpleDelete implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Composite parent;
	private Composite father;
	private Composite content;
	private SimpleFieldStructure simpleFieldStructure;
	private SimpleCataloguingComponent component;

	public EventSimpleDelete(Composite parent, Composite father, Composite content, SimpleFieldStructure simpleFieldStructure,
			SimpleCataloguingComponent component) {

		this.component = component;
		this.parent = parent;
		this.father = father;
		this.content = content;
		this.simpleFieldStructure = simpleFieldStructure;
	}

	public void handleEvent(Event arg0) {

		content.dispose();

		int position = component.getChildrens().indexOf(simpleFieldStructure);
		component.getChildrens().remove(position);
		int childrenCount = father.getChildren().length;

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
