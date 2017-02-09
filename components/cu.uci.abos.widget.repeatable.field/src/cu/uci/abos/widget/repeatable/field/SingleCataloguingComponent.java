package cu.uci.abos.widget.repeatable.field;

import java.util.ArrayList;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.repeatable.field.domain.SimpleFieldDomain;
import cu.uci.abos.widget.repeatable.field.listener.EventAddField;
import cu.uci.abos.widget.repeatable.field.listener.EventDeteleField;
import cu.uci.abos.widget.repeatable.field.listener.EventDown;
import cu.uci.abos.widget.repeatable.field.listener.EventTabFolder;
import cu.uci.abos.widget.repeatable.field.listener.EventUp;
import cu.uci.abos.widget.repeatable.field.util.CreateControlType;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.SubFieldStructure;

public class SingleCataloguingComponent extends Composite {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Control control;
	private ControlType controlType;
	private boolean white = true;
	private ToolBar plusMinusBar;
	private ToolBar loupeBar;
	private ToolItem loupeItem;
	private ToolItem plusMinusItem;
	private Label label;
	private Composite parent;
	private Composite father;
	private Composite content;
	private ExpandItem expandIem;
	private CTabFolder tabFolder;
	private boolean plus;
	private SubFieldStructure subFieldStructure;
	private ToolBar upDowndToolBar;
	private ToolItem up;
	private ToolItem down;
	private boolean notPlusItem = false;
	//private Label mandatoryField;
	//private boolean mandatory;

	public SingleCataloguingComponent(Composite parent, int style, ExpandItem expandItem, CTabFolder tabFolder, 
			ArrayList<SubFieldStructure> subFieldStructures, ArrayList<Control> controlls,
			SimpleFieldDomain simpleFieldDomain, ArrayList<FieldStructure> children) {
		super(parent, style);

		this.parent = parent;
		this.controlType = simpleFieldDomain.getControlType();
		this.expandIem = expandItem;
		this.plus = simpleFieldDomain.isPlus();
		this.tabFolder = tabFolder;
		//this.mandatory = simpleFieldDomain.isMandatory();

		//get properties
		String labelText = simpleFieldDomain.getLabelText();
		String[] comboList = simpleFieldDomain.getComboList();
		boolean haveSubFields = simpleFieldDomain.isHaveSubfields();
		String expandItemText = simpleFieldDomain.getExpandItemText();
		String defaultValue = simpleFieldDomain.getDefaultValue();
		int tag = simpleFieldDomain.getTag();
		String description = simpleFieldDomain.getDescription();
		String subFieldCode = simpleFieldDomain.getSubFieldCode();
		FieldStructure topComponent = simpleFieldDomain.getTopComponent();
		boolean repeatedField = simpleFieldDomain.isRepeatedField();
		int type = simpleFieldDomain.getType();
		int subFieldCount = simpleFieldDomain.getSubFieldCount();
		this.notPlusItem = simpleFieldDomain.getNotPlusItem();

		startAtributes(labelText);
		createControl(comboList,haveSubFields, expandItemText, defaultValue, subFieldCount);
		controlls.add(control);

		SubFieldStructure subFieldStructure = null;

		if(haveSubFields){
			subFieldStructure = new SubFieldStructure(tag, control, description, subFieldCode);
			subFieldStructures.add(subFieldStructure);

			if(subFieldCount > 1){
				up.addListener(SWT.Selection, new EventUp(topComponent, subFieldStructure,parent, expandItem,this,
						repeatedField, children));
				down.addListener(SWT.Selection, new EventDown(topComponent, subFieldStructure,parent, expandItem, this,
						repeatedField, children));
			}
		}

		//particular cases
		if(type == 2 || type == 4){
			twoType();

			if(type == 4){
				threeType(content);
				FormDatas.attach(plusMinusBar).atTopTo(content, 4).atLeftTo(loupeBar, 0);
				plusMinusItem.addListener(SWT.Selection, new EventAddField(label.getText(), father, expandItem ,parent,4,controlType,
						tabFolder,description,tag, topComponent,subFieldCode,comboList,plusMinusBar,plusMinusItem,content,subFieldStructure,
						repeatedField,upDowndToolBar, subFieldCount, children));
			}
		}
		else if(type == 3){
			threeType(content);
			FormDatas.attach(plusMinusBar).atTopTo(content, 4).atLeftTo(control, 10);
			if(plus && !notPlusItem)
				plusMinusItem.addListener(SWT.Selection, new EventAddField(label.getText(), father, expandItem, parent,3,controlType,
						tabFolder,description,tag, topComponent,subFieldCode,comboList,plusMinusBar,plusMinusItem,content,subFieldStructure,
						repeatedField,upDowndToolBar, subFieldCount, children));
			else
				plusMinusItem.addListener(SWT.Selection, new EventDeteleField(parent, expandItem, father, content, subFieldStructure,
						topComponent, children));
		}

	}

	//for event
	public SingleCataloguingComponent(Composite parent,int style, int type, String labelText, ControlType ctrType ,ExpandItem ei, CTabFolder tabFolder,
			String description, int tag, FieldStructure topComponent, String subFieldCode, String[] comboList, SubFieldStructure subFieldTop,
			Composite superComposite, boolean isRepeatable, int subFieldCount, ArrayList<FieldStructure> children) {
		super(parent, style);

		this.plus = true;
		this.controlType = ctrType;

		startAtributtesEvents(labelText);

		//common to all
		createControl(comboList,true, "", null, subFieldCount);

		int positionField = children.indexOf(topComponent);

		ArrayList<SubFieldStructure> subFields = children.get(positionField).getSubfields();

		subFieldStructure = new SubFieldStructure(tag, control, description, subFieldCode);

		int positionTop = children.get(positionField).getSubfields().indexOf(subFieldTop);

		subFields.add(positionTop+1,subFieldStructure);

		up.addListener(SWT.Selection, new EventUp(topComponent, subFieldStructure,superComposite, ei,this.getParent().getParent(),
				isRepeatable, children));
		down.addListener(SWT.Selection, new EventDown(topComponent, subFieldStructure,superComposite, ei, this.getParent().getParent(),
				isRepeatable, children));

		//particular cases
		if(type == 2 || type == 4){
			twoType();

			if(type == 4){
				threeType(this);
				FormDatas.attach(plusMinusBar).atTopTo(this, 4).atLeftTo(loupeBar, 0);
				plusMinusItem.addListener(SWT.Selection, new EventAddField(label.getText(), parent, ei,parent.getParent().getParent(),4,ctrType,
						tabFolder,description,tag, topComponent,subFieldCode,comboList,plusMinusBar,plusMinusItem,this,
						subFieldStructure,isRepeatable, upDowndToolBar, subFieldCount, children));
			}

		}
		else if(type == 3){
			threeType(this);
			FormDatas.attach(plusMinusBar).atTopTo(this, 4).atLeftTo(control, 10);
			if(plus && !notPlusItem)
				plusMinusItem.addListener(SWT.Selection, new EventAddField(label.getText(), parent, ei,parent.getParent().getParent(),3,ctrType,
						tabFolder,description,tag, topComponent,subFieldCode,comboList,plusMinusBar,plusMinusItem,this,subFieldStructure,
						isRepeatable, upDowndToolBar, subFieldCount, children));
			else
				plusMinusItem.addListener(SWT.Selection, new EventDeteleField(parent, ei, tabFolder, superComposite, subFieldTop,
						topComponent, children));
		}
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

	private void createControl(String[] comboList, boolean haveSubfields, String expandItemText,
			String defaultValue, int subFieldCount){

		String text = label.getText();

		CreateControlType createControlType = new CreateControlType(control, controlType);
		this.control = createControlType.create(defaultValue, content, label, false, text, comboList);

		if(subFieldCount > 1){
			upDowndToolBar = new ToolBar(content, SWT.WRAP | SWT.FLAT);
			up = new ToolItem(upDowndToolBar, 0);
			down = new ToolItem(upDowndToolBar, 0);
			Image imageUp = new Image(content.getDisplay(), RWT.getResourceManager().getRegisteredContent("up"));
			Image imageDown = new Image(content.getDisplay(), RWT.getResourceManager().getRegisteredContent("down"));
			up.setImage(imageUp);
			up.setToolTipText("Subir subcampo");
			down.setImage(imageDown);
			down.setToolTipText("Bajar subcampo");
			FormDatas.attach(upDowndToolBar).atLeftTo(content, 10).atTopTo(content, 4);
			FormDatas.attach(label).withWidth(200).atLeftTo(upDowndToolBar, 5).atTopTo(content, 10);
		}

		content.getShell().layout(true, true);
		content.getShell().redraw();
		content.getShell().update();
	}

	private void twoType(){

		Composite treePoints = new Composite(tabFolder,SWT.V_SCROLL|SWT.BORDER);
		treePoints.setLayout(new FormLayout());
		FormDatas.attach(treePoints).atTopTo(parent, 0).atLeftTo(parent, 0).withHeight(325);
		/*
    	AttachDigitalFileComposite attachDigitalFile = new AttachDigitalFileComposite(treePoints, 0,tabFolder);
    	attachDigitalFile.setLayout(new FormLayout());
		 */	
		//FormDatas.attach(attachDigitalFile).atTopTo(treePoints, 0);

		String subFieldName = label.getText();

		if(!label.getText().equals(""))
			subFieldName = subFieldName.substring(0, subFieldName.length()-1);

		String fieldNumber = expandIem.getText();
		fieldNumber = fieldNumber.substring(0, 3);

		loupeBar = new ToolBar(father, SWT.WRAP|SWT.FLAT);

		loupeItem = new ToolItem(loupeBar, SWT.PUSH);

		Image image = new Image(father.getDisplay(), RWT.getResourceManager().getRegisteredContent("search"));

		loupeItem.setImage(image);

		loupeItem.addListener(SWT.Selection, new EventTabFolder(treePoints, tabFolder, fieldNumber, subFieldName));

	}

	private void threeType(Composite parent){

		plusMinusBar = new ToolBar(parent, SWT.WRAP|SWT.FLAT);

		plusMinusItem = new ToolItem(plusMinusBar, SWT.PUSH);

		Image image;

		if(plus && !notPlusItem){
			image = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
			plusMinusItem.setToolTipText("Adicionar ocurrencia");
		}
		else{
			image = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));
			plusMinusItem.setToolTipText("Eliminar ocurrencia");
		}

		plusMinusItem.setImage(image);
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

	public ToolItem getUp(){
		return this.up;
	}

	public ToolItem getDown(){
		return this.down;
	}

	public ToolItem getPlusMinusItem(){return this.plusMinusItem;}

	public ToolBar getPlusMinusBar(){return plusMinusBar;}

	public SubFieldStructure getSubFieldStructure(){return subFieldStructure;}

}
