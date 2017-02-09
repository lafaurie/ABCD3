package cu.uci.abos.core.widget.advanced.query;

import java.util.ArrayList;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.advanced.query.domain.QueryStructure;
import cu.uci.abos.core.widget.advanced.query.listener.EventAdd;

public class QueryComponent extends Composite {

	public static ArrayList<QueryStructure> children = new ArrayList<QueryStructure>();

	public QueryComponent(Composite parent, int style, String[] pickList, ColorType colorType) {
		super(parent, style);

		Composite father = new Composite(this, 0);

		Composite content = new Composite(father, 0);

		this.setLayout(new FormLayout());

		father.setLayout(new FormLayout());

		content.setLayout(new FormLayout());

		if (colorType.compareTo(ColorType.Gray) == 0)
			this.setData(RWT.CUSTOM_VARIANT, "gray_background");
		else
			this.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		if (colorType.compareTo(ColorType.Gray) == 0)
			father.setData(RWT.CUSTOM_VARIANT, "gray_background");
		else
			father.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		if (colorType.compareTo(ColorType.Gray) == 0)
			content.setData(RWT.CUSTOM_VARIANT, "gray_background");
		else
			content.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		Combo field = new Combo(content, 0);

		// field.setText("Palabra clave");

		int claveCount = pickList.length;

		for (int i = 0; i < claveCount; i++) {

			field.add(pickList[i], i);

		}

		field.select(0);

		FormDatas.attach(field).atTopTo(content, 0).atLeftTo(content, 0).withWidth(172);

		Text term = new Text(content, 0);

		FormDatas.attach(term).atTopTo(content, 0).atLeftTo(field, 10).withWidth(150);

		QueryStructure queryStructure = new QueryStructure(field, term);

		children.add(queryStructure);

		ToolBar bar = new ToolBar(content, 0);

		ToolItem toolItem = new ToolItem(bar, SWT.PUSH);

		Image image = new Image(content.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));

		toolItem.setImage(image);

		toolItem.addListener(SWT.Selection, new EventAdd(parent, father, content, toolItem, term, pickList, queryStructure, bar, colorType));

		FormDatas.attach(bar).atTopTo(content, 0).atLeftTo(term, 10);

		layout(true);
	}

	// for event
	public QueryComponent(Composite parent, int style, String[] pickList, boolean state, ColorType colorType) {
		super(parent, style);

		setLayout(new FormLayout());

		if (colorType.compareTo(ColorType.Gray) == 0)
			this.setData(RWT.CUSTOM_VARIANT, "gray_background");
		else
			this.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		Combo field = new Combo(this, 0);

		// field.setText("Palabra clave");

		int claveCount = pickList.length;

		for (int i = 0; i < claveCount; i++) {

			field.add(pickList[i], i);

		}

		field.select(0);

		FormDatas.attach(field).atTopTo(this, 0).atLeftTo(this, 0).withWidth(172);

		Text term = new Text(this, 0);

		FormDatas.attach(term).atTopTo(this, 0).atLeftTo(field, 10).withWidth(150);

		QueryStructure queryStructure = new QueryStructure(field, term);

		children.add(queryStructure);

		ToolBar bar = new ToolBar(this, 0);

		ToolItem toolItem = new ToolItem(bar, SWT.PUSH);

		Image image = new Image(this.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));

		toolItem.setImage(image);

		toolItem.addListener(SWT.Selection, new EventAdd(parent.getParent(), parent, this, toolItem, term, pickList, queryStructure, bar, colorType));

		FormDatas.attach(bar).atTopTo(this, 0).atLeftTo(term, 10);

		layout(true);
	}

	public static void resetComponents() {

		children.removeAll(children);
		children = new ArrayList<QueryStructure>();

	}

	private static final long serialVersionUID = 1L;

}
