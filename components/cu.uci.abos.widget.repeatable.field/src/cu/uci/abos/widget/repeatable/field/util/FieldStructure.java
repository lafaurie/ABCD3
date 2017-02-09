package cu.uci.abos.widget.repeatable.field.util;

import java.util.ArrayList;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandItem;

public class FieldStructure {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private int tag;
	private int subtag;
	private Control control;
	private ArrayList<SubFieldStructure> subFields = new ArrayList<SubFieldStructure>();
	private int occurrence;
	private String expandItemText;
	private ExpandItem expandItem;
	private CTabItem tabItem;
	private boolean repeatableField;
	private ArrayList<Integer> positionObligatorySubfields;

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

	public FieldStructure(int tag, Control control, boolean repeatableField){
		this.tag = tag;
		this.control = control;
		this.repeatableField = repeatableField;
	}

	public FieldStructure(int tag, int subtag, Control control, String expandItemText){	
		this.tag = tag;
		this.control = control;
		occurrence = 0;
		this.expandItemText = expandItemText;
		this.subtag = subtag;
	}

	public FieldStructure(int tag, int subtag, String expandItemText, ExpandItem expandItem, CTabItem tabItem,
			boolean repeatableField){	
		this.tag = tag;
		occurrence = 0;
		this.expandItemText = expandItemText;
		this.expandItem = expandItem;
		this.tabItem = tabItem;
		this.repeatableField = repeatableField;
		this.subtag = subtag;
	}

	public FieldStructure(ArrayList<SubFieldStructure> subFields, String expandItemText, int tag, int subtag,
			ExpandItem expandItem, CTabItem tabItem, boolean repeatableField){
		this.expandItem = expandItem;
		this.tabItem = tabItem;
		this.subFields = subFields;
		occurrence = 0;
		this.expandItemText = expandItemText;
		this.tag = tag;
		this.repeatableField = repeatableField;
		this.subtag = subtag;
		this.positionObligatorySubfields = new ArrayList<Integer>();
	}

	public int getSubtag() {
		return subtag;
	}

	public void setSubtag(int subtag) {
		this.subtag = subtag;
	}
	public ArrayList<Integer> getPositionObligatorySubfields() {
		return positionObligatorySubfields;
	}
	public void setPositionObligatorySubfields(
			ArrayList<Integer> positionObligatorySubfields) {
		this.positionObligatorySubfields = positionObligatorySubfields;
	}
}
