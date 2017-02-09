package cu.uci.abos.core.widget.repeatable.field;

import java.util.ArrayList;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.repeatable.field.listener.EventAddField;
import cu.uci.abos.core.widget.repeatable.field.listener.EventDown;
import cu.uci.abos.core.widget.repeatable.field.listener.EventSimpleAdd;
import cu.uci.abos.core.widget.repeatable.field.listener.EventTabFolder;
import cu.uci.abos.core.widget.repeatable.field.listener.EventUp;
import cu.uci.abos.core.widget.repeatable.field.util.AttachDigitalFileComposite;
import cu.uci.abos.core.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.core.widget.repeatable.field.util.SimpleFieldStructure;
import cu.uci.abos.core.widget.repeatable.field.util.SubFieldDescription;
import cu.uci.abos.core.widget.repeatable.field.util.SubFieldStructure;

public class CataloguingComponent extends Composite {
	
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
	private ExpandItem ei;
	private CTabFolder tabFolder;
	private boolean plus;
	private FieldStructure fieldStructure;
	private SubFieldStructure subFieldStructure;
	private ToolBar upDowndToolBar;
	private ToolItem up;
	private ToolItem down;
	public static ArrayList<FieldStructure> childrens = new ArrayList<FieldStructure>();
	public static ArrayList<SimpleFieldStructure> simpleChildrens = new ArrayList<SimpleFieldStructure>();
	
	public ToolItem getPlusMinusItem(){return plusMinusItem;}
	
	public ToolBar getPlusMinusBar(){return plusMinusBar;}
	
	public FieldStructure getFieldStructure(){return fieldStructure;}
	
	public SubFieldStructure getSubFieldStructure(){return subFieldStructure;}
	
	//simple one children
	public CataloguingComponent(Composite parent, int style, String labelText, 
			ControlType controlType,String[] picklist){
		super(parent, style);
		
		this.white = false;
		
		startAtributes(labelText);
		
		createControl(controlType, content, true, null,picklist,false,"",-1);
		
		SimpleFieldStructure simpleFieldStructure = new SimpleFieldStructure(labelText, control);
		
		simpleChildrens.add(simpleFieldStructure);
		
		this.plus = true;
		
		threeType(content);
		
		FormDatas.attach(plusMinusBar).atTopTo(content, 1).atLeftTo(control, 10);
		
		plusMinusItem.addListener(SWT.Selection, new EventSimpleAdd(labelText, father, parent, controlType,plusMinusBar,plusMinusItem, content,simpleFieldStructure,picklist));
       
		layout(true);
	}
	
	//for event to simple one children 
	public CataloguingComponent(Composite parent, int style, String textName, ControlType controlType,boolean plus, String[] pickList){
		super(parent, style);
		
		this.white = false;
		
		startAtributtesEvents(textName);
		
		createControl(controlType, this, true, null,pickList,false,"",-1);
		
        SimpleFieldStructure simpleFieldStructure = new SimpleFieldStructure(textName, control);
		
		simpleChildrens.add(simpleFieldStructure);
		
		this.plus = plus;
		
        threeType(this);
		
        FormDatas.attach(plusMinusBar).atTopTo(this, 1).atLeftTo(control, 10);
        
        plusMinusItem.addListener(SWT.Selection, new EventSimpleAdd(textName, parent, parent.getParent(), controlType,plusMinusBar,plusMinusItem, this,simpleFieldStructure,pickList));
		
		layout(true);
		
	}
	
	 //One children
	public CataloguingComponent(Composite parent,int style, int type, String labelText,
			ControlType ctrType ,ExpandItem ei, boolean plus, boolean repeatedField, 
			CTabFolder tabFolder, String description, int tag, String moreBig, boolean haveSubfields,
			ArrayList<SubFieldStructure> subFieldStructures, ArrayList<Control> controlls, String subFieldCode,
			String[] comboList, FieldStructure topComponent, String expandItemText, long registrationNumber){
		super(parent,style);
		
		this.parent = parent;
		this.controlType = ctrType;
		this.ei = ei;
		this.plus = plus;
		this.tabFolder = tabFolder;
		
		startAtributes(labelText);
		
		//common to all
		createControl(ctrType, content, false, moreBig,comboList,haveSubfields, expandItemText, registrationNumber);
		
		controlls.add(control);
		
		SubFieldStructure subFieldStructure = null;
	
		if(haveSubfields){
	        subFieldStructure = new SubFieldStructure(tag, control, description, subFieldCode);
			subFieldStructures.add(subFieldStructure);
			up.addListener(SWT.Selection, new EventUp(topComponent, subFieldStructure,parent, ei,this, repeatedField));
			down.addListener(SWT.Selection, new EventDown(topComponent, subFieldStructure,parent, ei, this, repeatedField));
		}	
		//particular cases
		if(type == 2 || type == 4){
			twoType();
			
        	if(type == 4){
        		threeType(content);
        		FormDatas.attach(plusMinusBar).atTopTo(content, 4).atLeftTo(loupeBar, 0);
        		plusMinusItem.addListener(SWT.Selection, new EventAddField(label.getText(), father, this.ei,parent,4,controlType,
        				tabFolder,description,tag,moreBig, topComponent,subFieldCode,comboList,plusMinusBar,plusMinusItem,content,subFieldStructure,repeatedField,upDowndToolBar));
        	}
			
		}
		else if(type == 3){
			threeType(content);
			FormDatas.attach(plusMinusBar).atTopTo(content, 4).atLeftTo(control, 10);
			plusMinusItem.addListener(SWT.Selection, new EventAddField(label.getText(), father, this.ei,parent,3,controlType,
					tabFolder,description,tag,moreBig, topComponent,subFieldCode,comboList,plusMinusBar,plusMinusItem,content,subFieldStructure,repeatedField,upDowndToolBar));
		}
		
		layout(true);
		
	}
	
	//for event to one children 
	public CataloguingComponent(Composite parent,int style, int type, String labelText,
			ControlType ctrType ,ExpandItem ei, CTabFolder tabFolder, String description, 
			int tag, String moreBig, FieldStructure topComponent, String subFieldCode,String[] comboList, SubFieldStructure subFieldTop, Composite superComposite,
			boolean isRepeatable) {
		super(parent,style);
		
		this.plus = true;
		
		startAtributtesEvents(labelText);
		
		//common to all
		createControl(ctrType, this, false,moreBig,comboList,true, "",-1);
		
		int positionField = childrens.indexOf(topComponent);
		
		ArrayList<SubFieldStructure> subFields = childrens.get(positionField).getSubfields();
		
		subFieldStructure = new SubFieldStructure(tag, control, description, subFieldCode);
		
		int positionTop = childrens.get(positionField).getSubfields().indexOf(subFieldTop);
		
		subFields.add(positionTop+1,subFieldStructure);
		
		up.addListener(SWT.Selection, new EventUp(topComponent, subFieldStructure,superComposite, ei,this.getParent().getParent(),isRepeatable));
		down.addListener(SWT.Selection, new EventDown(topComponent, subFieldStructure,superComposite, ei, this.getParent().getParent(), isRepeatable));
		
		//particular cases
		if(type == 2 || type == 4){
			twoType();
			
        	if(type == 4){
        		threeType(this);
        		FormDatas.attach(plusMinusBar).atTopTo(this, 4).atLeftTo(loupeBar, 0);
        		plusMinusItem.addListener(SWT.Selection, new EventAddField(label.getText(), parent, ei,parent.getParent().getParent(),4,ctrType,
        				tabFolder,description,tag,moreBig, topComponent,subFieldCode,comboList,plusMinusBar,plusMinusItem,this,subFieldStructure,isRepeatable, upDowndToolBar));
        	}
			
		}
		else if(type == 3){
			threeType(this);
			FormDatas.attach(plusMinusBar).atTopTo(this, 4).atLeftTo(control, 10);
			plusMinusItem.addListener(SWT.Selection, new EventAddField(label.getText(), parent, ei,parent.getParent().getParent(),3,ctrType,
					tabFolder,description,tag,moreBig, topComponent,subFieldCode,comboList,plusMinusBar,plusMinusItem,this,subFieldStructure,isRepeatable, upDowndToolBar));
		}
		
		layout(true);
	}
	
  //n children's
	public CataloguingComponent(Composite parent,int style, SubFieldDescription subFieldDescription,
			ExpandItem ei, boolean repeatedField, CTabFolder tabFolder, String description,
			int tag, String expandItemText, long registrationNumber, CTabItem tabItem){
		
		super(parent,style);
		int componentsSize = subFieldDescription.getComponentCount();
		
		ArrayList<SubFieldStructure> subFieldStructures = new ArrayList<SubFieldStructure>();
		
		ArrayList<Control> controlls =new  ArrayList<Control>();
		
		if(subFieldDescription.itHasSubfields())
			this.fieldStructure = new FieldStructure(subFieldStructures, expandItemText, tag, ei, tabItem,repeatedField);
		else
			this.fieldStructure = new FieldStructure(tag, expandItemText, ei, tabItem, repeatedField);
	
		childrens.add(fieldStructure);
		
		if(subFieldDescription.itHasSubfields()){
			for (int i = 0; i < componentsSize; i++) {
				
				   CataloguingComponent gc = new CataloguingComponent(this, 0, subFieldDescription.getTypes()[i], subFieldDescription.getTexts()[i],
						   subFieldDescription.getControls()[i], ei, true, repeatedField, tabFolder,description, tag, subFieldDescription.getMoreBig(), subFieldDescription.itHasSubfields(), subFieldStructures,
						   controlls, subFieldDescription.getSubFieldCode()[i],
						   subFieldDescription.getComboList().get(i),this.fieldStructure, expandItemText, registrationNumber);
				   
				   gc.setLayout(new FormLayout());
				   
				   if(i==0){
					   if(repeatedField)
					   FormDatas.attach(this.getChildren()[i]).atTopTo(parent, 45);
					   else
					   FormDatas.attach(this.getChildren()[i]).atTopTo(parent, 5);	   
				   }   
				   else
					   FormDatas.attach(this.getChildren()[i]).atTopTo(this.getChildren()[i-1], 10).atLeftTo(this, 0);
				}
		}
		else{
			
			CataloguingComponent gc = new CataloguingComponent(this, 0, subFieldDescription.getType(), "",
					   subFieldDescription.getControl(), ei, true, repeatedField, tabFolder,description, tag, subFieldDescription.getMoreBig(), subFieldDescription.itHasSubfields(), subFieldStructures,
					   controlls, "",
					   subFieldDescription.getPicklist(),this.fieldStructure, expandItemText, registrationNumber);
			
			gc.setLayout(new FormLayout());
			
			if(repeatedField)
				   FormDatas.attach(this.getChildren()[0]).atTopTo(parent, 45);
				   else
				   FormDatas.attach(this.getChildren()[0]).atTopTo(parent, 5);
		}
		
		if(!subFieldDescription.itHasSubfields())
		this.fieldStructure.setControl(controlls.get(0));
		
	}
	
	public static void resetComponents(){
		
		childrens.removeAll(childrens);
		childrens = new ArrayList<FieldStructure>();
		
	}
	
	public static void resetSimpleComponents(){
		
		simpleChildrens.removeAll(simpleChildrens);
		simpleChildrens = new ArrayList<SimpleFieldStructure>();
		
	}
	
	
	//Private Methods
	
	private void twoType(){
		
		Composite treePoints = new Composite(tabFolder,SWT.V_SCROLL|SWT.BORDER);
    	treePoints.setLayout(new FormLayout());
    	FormDatas.attach(treePoints).atTopTo(parent, 0).atLeftTo(parent, 0).withHeight(325);
    
    	AttachDigitalFileComposite attachDigitalFile = new AttachDigitalFileComposite(treePoints, 0,tabFolder);
    	attachDigitalFile.setLayout(new FormLayout());
    	
    	FormDatas.attach(attachDigitalFile).atTopTo(treePoints, 0);
    	
    	String subFieldName = label.getText();
    	
    	if(!label.getText().equals(""))
    	subFieldName = subFieldName.substring(0, subFieldName.length()-1);
    	
    	String fieldNumber = ei.getText();
    	fieldNumber = fieldNumber.substring(0, 3);
    	
    	loupeBar = new ToolBar(father, 0);
    	
    	loupeItem = new ToolItem(loupeBar, SWT.PUSH);
    	
    	Image image = new Image(father.getDisplay(), RWT.getResourceManager().getRegisteredContent("search"));
    	
    	loupeItem.setImage(image);
    	
    	loupeItem.addListener(SWT.Selection, new EventTabFolder(treePoints, tabFolder, fieldNumber, subFieldName));
    	
	}
	
	private void threeType(Composite parent){
		
		plusMinusBar = new ToolBar(parent, 0);
		
		plusMinusItem = new ToolItem(plusMinusBar, SWT.PUSH);
		
		Image image;
		
		if(plus)
    	  image = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
		else
			image = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));
		
		plusMinusItem.setImage(image);
    	
	}
	
	private void createControl(ControlType controlType, Composite parent, boolean simple, String moreBig, String[] comboList, boolean haveSubfields,
			String expandItemText, long registrationNumber){
		
		String text = label.getText();
		
		int margin = -1;
		
		Label labelSize = new Label(parent, 0);
		
		if(!simple && haveSubfields)
		labelSize.setText(moreBig);
		else
			labelSize.setText(text);
		
		int labelLength = labelSize.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		
		if(haveSubfields)
		margin = 100 + labelLength;
		else
			margin = 20 + labelLength;
		
		labelSize.dispose();
		
		switch (controlType) {
		case Text:
			control = new Text(parent, 0);
			
			if(!simple){
			   if(!text.equals(""))
			     FormDatas.attach(control).atTopTo(parent, 8).atLeftTo(parent, margin).withWidth(250).withHeight(10);
			   else
				 FormDatas.attach(control).atTopTo(parent, 8).atLeftTo(parent, 10).withWidth(250).withHeight(10);
			   
			   if(expandItemText.equals("1- Número de Control") || expandItemText.equals("1- Control Number")){
				   ((Text)control).setText(Long.toString(registrationNumber));
				   ((Text)control).setEditable(false);
			   }
			}
			else
			   FormDatas.attach(control).atTopTo(parent, 5).atLeftTo(parent, margin).withWidth(250).withHeight(10);
				
			break;
			
		case Combo:
			control = new Combo(parent, 0);
			
			if(!simple){
			if(!text.equals(""))
			FormDatas.attach(control).atTopTo(parent, 8).atLeftTo(parent, margin).withWidth(270).withHeight(23);
			else
				FormDatas.attach(control).atTopTo(parent, 8).atLeftTo(parent, margin).withWidth(270).withHeight(23);
			}
			else
				FormDatas.attach(control).atTopTo(parent, 5).atLeftTo(parent, margin).withWidth(270).withHeight(23);
			
			int count = comboList.length;
			
			for (int i = 0; i < count; i++) {
				
				((Combo)control).add(comboList[i], i);
				
			}
			
			((Combo)control).setText("Seleccione");
			
			break;
			
		default:
			break;
		}
		
		if(haveSubfields){
			upDowndToolBar = new ToolBar(parent, 0);
			up = new ToolItem(upDowndToolBar, 0);
			down = new ToolItem(upDowndToolBar, 0);
			Image imageUp = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("up"));
			Image imageDown = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("down"));
			up.setImage(imageUp);
			down.setImage(imageDown);
			FormDatas.attach(upDowndToolBar).atLeftTo(parent, 10).atTopTo(parent, 4);
		}
		
		if(!simple)
		FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
		else
			FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);
		
		parent.getShell().layout(true, true);
		parent.getShell().redraw();
		parent.getShell().update();
		
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
		
		label = new Label(content, 0);
		
		label.setText(labelText);
		
		layout(true);
		
	}
	
	private void startAtributtesEvents(String labelText){
		
		setLayout(new FormLayout());
		
		if(!white){
		
		this.setData(RWT.CUSTOM_VARIANT,"gray_background");
		
        }
		
		label = new Label(this, 0);
		label.setText(labelText);
		
		layout(true);
		
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}
