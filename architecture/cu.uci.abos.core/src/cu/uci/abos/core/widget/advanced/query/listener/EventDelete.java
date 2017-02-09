package cu.uci.abos.core.widget.advanced.query.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.advanced.query.QueryComponent;
import cu.uci.abos.core.widget.advanced.query.domain.QueryStructure;

public class EventDelete implements Listener {

	private static final long serialVersionUID = 1L;

	private Composite parent;
	private Composite father;
	private Composite component;
	private QueryStructure queryStructure;

	public EventDelete(Composite parent, Composite father, Composite component, QueryStructure queryStructure) {

		this.parent = parent;

		this.father = father;

		this.component = component;

		this.queryStructure = queryStructure;

	}

	@Override
	public void handleEvent(Event arg0) {

		component.dispose();

		int position = QueryComponent.children.indexOf(queryStructure);

		QueryComponent.children.remove(position);

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
