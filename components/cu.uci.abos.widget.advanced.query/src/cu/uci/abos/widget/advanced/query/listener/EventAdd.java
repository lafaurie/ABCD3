package cu.uci.abos.widget.advanced.query.listener;

import java.util.ArrayList;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.advanced.query.ColorType;
import cu.uci.abos.widget.advanced.query.QueryComponent;
import cu.uci.abos.widget.advanced.query.domain.QueryStructure;

public class EventAdd implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Composite parent;
	private Composite father;
	private Composite content;
	private ToolItem toolItem;
	private Text term;
	private String[] pickList;
	private QueryStructure queryStructure;
	private ToolBar bar;
	private ColorType colorType;
	private Composite root;
	private ArrayList<QueryStructure> children;

	public EventAdd(Composite parent, Composite father, Composite content, ToolItem toolItem, Text term,
			String[] pickList, QueryStructure queryStructure, ToolBar bar, ColorType colorType,
			Composite root, ArrayList<QueryStructure> children) {
		this.parent = parent;
		this.father = father;
		this.content = content;
		this.toolItem = toolItem;
		this.term = term;
		this.pickList = pickList;
		this.queryStructure = queryStructure;
		this.bar = bar;
		this.colorType = colorType;
		this.root = root;
		this.children = children;
	}

	@Override
	public void handleEvent(Event arg0) {

		toolItem.dispose();

		ToolItem minus = new ToolItem(bar, 0);

		Image image = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));

		minus.setImage(image);
		
		Combo andOr = new Combo(content, SWT.READ_ONLY);

		andOr.add("y", 0);
		andOr.add("o", 1);
		andOr.add("no", 2);
		andOr.select(0);

		int position = children.indexOf(queryStructure);

		children.get(position).setAndOr(andOr);

		FormDatas.attach(andOr).atTopTo(content, 3).atLeftTo(term, 10).withHeight(23);
		FormDatas.attach(bar).atTopTo(content, 0).atLeftTo(andOr, 2);

		QueryComponent component = new QueryComponent(father, 0, pickList, colorType, root,
				children);
		component.setLayout(new FormLayout());

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
		
		minus.addListener(SWT.Selection, new EventDelete(parent, father, content, queryStructure,
				root, children));
	}
}
