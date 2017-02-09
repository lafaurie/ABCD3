package cu.uci.abos.core.widget.advanced.query.listener;

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
import cu.uci.abos.core.widget.advanced.query.ColorType;
import cu.uci.abos.core.widget.advanced.query.QueryComponent;
import cu.uci.abos.core.widget.advanced.query.domain.QueryStructure;

public class EventAdd implements Listener {

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

	public EventAdd(Composite parent, Composite father, Composite content, ToolItem toolItem, Text term, String[] pickList, QueryStructure queryStructure, ToolBar bar, ColorType colorType) {

		this.parent = parent;

		this.father = father;

		this.content = content;

		this.toolItem = toolItem;

		this.term = term;

		this.pickList = pickList;

		this.queryStructure = queryStructure;

		this.bar = bar;

		this.colorType = colorType;

	}

	@Override
	public void handleEvent(Event arg0) {

		toolItem.dispose();

		ToolItem minus = new ToolItem(bar, SWT.PUSH);

		Image image = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));

		minus.setImage(image);

		Combo andOr = new Combo(content, 0);

		andOr.add("AND", 0);

		andOr.add("OR", 1);

		andOr.add("NOT", 2);

		andOr.select(0);

		int position = QueryComponent.children.indexOf(queryStructure);

		QueryComponent.children.get(position).setAndOr(andOr);

		FormDatas.attach(andOr).atTopTo(content, 0).atLeftTo(term, 10);

		FormDatas.attach(bar).atTopTo(content, 0).atLeftTo(andOr, 10);

		QueryComponent component = new QueryComponent(father, 0, pickList, true, colorType);

		component.setLayout(new FormLayout());

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

		minus.addListener(SWT.Selection, new EventDelete(parent, father, content, queryStructure));

	}

}
