package cu.uci.abos.widget.repeatable.field;

import java.util.ArrayList;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.repeatable.field.listener.EventSimpleAdd;
import cu.uci.abos.widget.repeatable.field.util.SimpleFieldStructure;
import cu.uci.abos.widget.repeatable.field.util.CreateControlType;

public class SimpleCataloguingComponent extends Composite {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private boolean white = true;
	private ControlType controlType;
	private Control control;
	private boolean plus;
	private Composite content;
	private ToolBar plusMinusBar;
	private ToolItem plusMinusItem;
	private Composite father;
	private Label label;
	private ToolBar upDowndToolBar;
	private ToolItem up;
	private ToolItem down;

	private ArrayList<SimpleFieldStructure> children = new ArrayList<SimpleFieldStructure>();

	public SimpleCataloguingComponent(Composite parent, int style, String labelText, 
			ControlType controlType,String[] picklist) {
		super(parent, style);

		this.white = false;
		this.controlType = controlType;

		startAtributes(labelText);

		createControl(picklist,false,"",null, -1);

		SimpleFieldStructure simpleFieldStructure = new SimpleFieldStructure(labelText, control);

		this.children.add(simpleFieldStructure);

		this.plus = true;

		threeType(content);

		FormDatas.attach(plusMinusBar).atTopTo(content, 1).atLeftTo(control, 10);

		plusMinusItem.addListener(SWT.Selection, new EventSimpleAdd(labelText, father, parent, controlType, plusMinusBar, plusMinusItem, content,
				simpleFieldStructure, picklist, this));

		layout(true);
	}

	//for event
	public SimpleCataloguingComponent(Composite parent, int style, String textName, ControlType controlType,boolean plus, String[] pickList){
		super(parent, style);

		this.white = false;
		this.controlType = controlType;

		startAtributtesEvents(textName);

		createControl(pickList,false,"", null, -1);

		SimpleFieldStructure simpleFieldStructure = new SimpleFieldStructure(textName, control);

		this.children.add(simpleFieldStructure);

		this.plus = plus;

		threeType(this);

		FormDatas.attach(plusMinusBar).atTopTo(this, 1).atLeftTo(control, 10);

		plusMinusItem.addListener(SWT.Selection, new EventSimpleAdd(textName, parent, parent.getParent(), controlType,plusMinusBar,plusMinusItem,
				this,simpleFieldStructure,pickList, this));

		layout(true);

	}

	private void startAtributes(String labelText){

		father = new Composite(this, 0);

		content = new Composite(father, 0);

		setLayout(new FormLayout());

		father.setLayout(new FormLayout());

		content.setLayout(new FormLayout());

		if(!white){

			this.setData(RWT.CUSTOM_VARIANT,"gray_background");

			father.setData(RWT.CUSTOM_VARIANT,"gray_background");

			content.setData(RWT.CUSTOM_VARIANT,"gray_background");

		}

		FormDatas.attach(father).atTopTo(this, 0).atLeftTo(this, 0);

		FormDatas.attach(content).atTopTo(father, 0).atLeftTo(father, 0);

		label = new Label(content, SWT.WRAP|SWT.RIGHT);

		if(labelText != null)
			label.setText(labelText);
		else
			label.setText("");

		layout(true);
	}

	private void startAtributtesEvents(String labelText){

		setLayout(new FormLayout());

		this.content = this;

		if(!white){

			this.setData(RWT.CUSTOM_VARIANT,"gray_background");

		}

		label = new Label(content, SWT.WRAP|SWT.RIGHT);
		label.setText(labelText);

		layout(true);
	}

	private void createControl(String[] comboList, boolean haveSubfields,
			String expandItemText, String defaultValue, int subFieldCount){

		String text = label.getText();

		CreateControlType createControlType = new CreateControlType(control, controlType);
		this.control = createControlType.create(defaultValue, content, label, true, text, comboList);

		if(subFieldCount > 1){
			upDowndToolBar = new ToolBar(content, SWT.WRAP | SWT.FLAT);
			up = new ToolItem(upDowndToolBar, 0);
			down = new ToolItem(upDowndToolBar, 0);
			Image imageUp = new Image(content.getDisplay(), RWT.getResourceManager().getRegisteredContent("up"));
			Image imageDown = new Image(content.getDisplay(), RWT.getResourceManager().getRegisteredContent("down"));
			up.setImage(imageUp);
			down.setImage(imageDown);
			FormDatas.attach(upDowndToolBar).atLeftTo(content, 10).atTopTo(content, 4);
			FormDatas.attach(label).withWidth(200).atLeftTo(upDowndToolBar, 5).atTopTo(content, 10);
		}

		content.getShell().layout(true, true);
		content.getShell().redraw();
		content.getShell().update();

	}

	private void threeType(Composite parent){

		plusMinusBar = new ToolBar(parent, SWT.WRAP|SWT.FLAT);

		plusMinusItem = new ToolItem(plusMinusBar, SWT.PUSH);

		Image image;

		if(plus)
			image = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
		else
			image = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));

		plusMinusItem.setImage(image);
	}

	public ArrayList<SimpleFieldStructure> getChildrens(){
		return this.children;
	}

	public ToolItem getUp(){
		return this.up;
	}

	public ToolItem getDown(){
		return this.down;
	}

	public ToolItem getPlusMinusItem(){return this.plusMinusItem;}
	public ToolBar getPlusMinusBar(){return plusMinusBar;}

}
