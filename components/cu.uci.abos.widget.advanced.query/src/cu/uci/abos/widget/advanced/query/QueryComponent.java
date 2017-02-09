package cu.uci.abos.widget.advanced.query;

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
import cu.uci.abos.widget.advanced.query.domain.QueryStructure;
import cu.uci.abos.widget.advanced.query.listener.EventAdd;

public class QueryComponent extends Composite {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	
	private ArrayList<QueryStructure> children;
	private Composite root;
	
	public ArrayList<QueryStructure> getChildrens(){
		return this.children;
	}
	
	public Composite getRoot(){
		return this.root;
	}

	public QueryComponent(Composite parent, int style, String[] pickList, ColorType colorType) {
		super(parent, style);
		
		this.children = new ArrayList<QueryStructure>();
        this.root = parent;
		
		Composite father = new Composite(this, 0);
		Composite content = new Composite(father, 0);

		this.setLayout(new FormLayout());
		father.setLayout(new FormLayout());
		content.setLayout(new FormLayout());

		if (colorType.compareTo(ColorType.Gray) == 0){
			this.setData(RWT.CUSTOM_VARIANT, "gray_background");
			father.setData(RWT.CUSTOM_VARIANT, "gray_background");
			content.setData(RWT.CUSTOM_VARIANT, "gray_background");
		}
		else{
			this.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
			father.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
			content.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		}
			
		Combo field = new Combo(content, SWT.READ_ONLY);

		int claveCount = pickList.length;
		for (int i = 0; i < claveCount; i++) {
			field.add(pickList[i], i);
		}
		field.select(0);

		FormDatas.attach(field).atTopTo(content, 3).atLeftTo(content, 0).withWidth(230).withHeight(23);
        
		Text term = new Text(content, 0);
		FormDatas.attach(term).atTopTo(content, 5).atLeftTo(field, 10).withWidth(230).withHeight(10);

		QueryStructure queryStructure = new QueryStructure(field, term);
		children.add(queryStructure);

		ToolBar bar = new ToolBar(content, SWT.WRAP|SWT.FLAT);

		ToolItem toolItem = new ToolItem(bar, 0);
		Image image = new Image(content.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
		toolItem.setImage(image);

		toolItem.addListener(SWT.Selection, new EventAdd(parent, father, content, toolItem, term, pickList, queryStructure,
				bar, colorType, root, children));

		FormDatas.attach(bar).atTopTo(content, 0).atLeftTo(term, 2);
		layout(true);
	}

	// for event
	public QueryComponent(Composite parent, int style, String[] pickList, ColorType colorType,
			Composite root, ArrayList<QueryStructure> children) {
		super(parent, style);

		this.root = root;
		setLayout(new FormLayout());

		if (colorType.compareTo(ColorType.Gray) == 0)
			this.setData(RWT.CUSTOM_VARIANT, "gray_background");
		else
			this.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		Combo field = new Combo(this, SWT.READ_ONLY);

		int claveCount = pickList.length;
		for (int i = 0; i < claveCount; i++) {
			field.add(pickList[i], i);
		}
		field.select(0);

		FormDatas.attach(field).atTopTo(this, 3).atLeftTo(this, 0).withWidth(230).withHeight(23);

		Text term = new Text(this, 0);
		FormDatas.attach(term).atTopTo(this, 5).atLeftTo(field, 10).withWidth(230).withHeight(10);

		QueryStructure queryStructure = new QueryStructure(field, term);
		children.add(queryStructure);

		ToolBar bar = new ToolBar(this, SWT.WRAP|SWT.FLAT);

		ToolItem toolItem = new ToolItem(bar, 0);
		Image image = new Image(this.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
		toolItem.setImage(image);

		toolItem.addListener(SWT.Selection, new EventAdd(parent.getParent(), parent, this, toolItem, term, pickList, queryStructure, bar, colorType,
				root,children));

		FormDatas.attach(bar).atTopTo(this, 0).atLeftTo(term, 2);
		layout(true);
	}

	private static final long serialVersionUID = 1L;
}
