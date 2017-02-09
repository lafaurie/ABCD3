package cu.uci.abos.core.widget.repeatable.field.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.repeatable.field.CataloguingComponent;
import cu.uci.abos.core.widget.repeatable.field.util.SimpleFieldStructure;

public class EventSimpleDelete implements Listener {

	private static final long serialVersionUID = 1L;

	private Composite parent;
	private Composite father;
	private Composite content;
	private SimpleFieldStructure simpleFieldStructure;

	public EventSimpleDelete(Composite parent, Composite father, Composite content, SimpleFieldStructure simpleFieldStructure) {

		this.parent = parent;
		this.father = father;
		this.content = content;
		this.simpleFieldStructure = simpleFieldStructure;
	}

	public void handleEvent(Event arg0) {

		content.dispose();

		int position = CataloguingComponent.simpleChildrens.indexOf(simpleFieldStructure);
		CataloguingComponent.simpleChildrens.remove(position);
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
