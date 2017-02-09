package cu.uci.abos.core.widget.repeatable.field.util;

import java.util.ArrayList;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandItem;

public class FieldStructure {
	
	private int tag;
	private Control control;
	private ArrayList<SubFieldStructure> subFields = new ArrayList<SubFieldStructure>();
	private int occurrence;
	private String expandItemText;
	private ExpandItem expandItem;
	private CTabItem tabItem;
	private boolean repeatableField;
	
	public int getTag(){return tag;}
	public Control getControl(){return control;}
	public ArrayList<SubFieldStructure> getSubfields(){return subFields;}
	public void setControl(Control control){this.control = control;}
	public int getOccurrence(){return occurrence;}
	public void setOccurrence(int occurrence){this.occurrence = occurrence;}
	public String getExpandItemText(){return expandItemText;}
	public ExpandItem getExpandItem(){return expandItem;}
	public CTabItem getTabItem(){return tabItem;}
	public boolean isRepeatableField(){return this.repeatableField;}
	
	public FieldStructure(int tag, Control control, String expandItemText){	
		this.tag = tag;
		this.control = control;
		occurrence = 0;
		this.expandItemText = expandItemText;
	}
	
	public FieldStructure(int tag, String expandItemText, ExpandItem expandItem, CTabItem tabItem,
			boolean repeatableField){	
		this.tag = tag;
		occurrence = 0;
		this.expandItemText = expandItemText;
		this.expandItem = expandItem;
		this.tabItem = tabItem;
		this.repeatableField = repeatableField;
	}
	
	public FieldStructure(ArrayList<SubFieldStructure> subFields, String expandItemText, int tag, ExpandItem expandItem,
			CTabItem tabItem, boolean repeatableField){
		this.expandItem = expandItem;
		this.tabItem = tabItem;
		this.subFields = subFields;
		occurrence = 0;
		this.expandItemText = expandItemText;
		this.tag = tag;
		this.repeatableField = repeatableField;
	}

}
