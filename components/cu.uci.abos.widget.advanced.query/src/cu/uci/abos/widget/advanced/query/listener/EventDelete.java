package cu.uci.abos.widget.advanced.query.listener;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.advanced.query.domain.QueryStructure;

public class EventDelete implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Composite parent;
	private Composite father;
	private Composite component;
	private QueryStructure queryStructure;
	private Composite root;
	private ArrayList<QueryStructure> children;

	public EventDelete(Composite parent, Composite father, Composite component, QueryStructure queryStructure,
			Composite root, ArrayList<QueryStructure> children) {
        this.root = root;
		this.parent = parent;
		this.father = father;
		this.component = component;
		this.queryStructure = queryStructure;
		this.children = children;
	}

	@Override
	public void handleEvent(Event arg0) {

		component.dispose();

		int position = children.indexOf(queryStructure);
		children.remove(position);

		int childrenCount = father.getChildren().length;
		for (int i = 0; i < childrenCount; i++) {
			if (i == 0) {
				FormDatas.attach(father.getChildren()[i]).atTopTo(father, 0);
			} else {
				FormDatas.attach(father.getChildren()[i]).atTopTo(father.getChildren()[i - 1], 10);
			}
		}
		
		/*FormDatas.attach(root).withWidth(root.getSize().x).withHeight(root.computeSize(SWT.DEFAULT, SWT.DEFAULT).y)
		.atTop(36).atLeft(0);*/
		
		root.getShell().layout(true, true);
		root.getShell().redraw();
		root.getShell().update();
		root.pack();
		
		father.getShell().layout(true, true);
		father.getShell().redraw();
		father.getShell().update();
		father.pack();
		
		parent.getShell().layout(true, true);
		parent.getShell().redraw();
		parent.getShell().update();
		parent.pack();
	}
}
