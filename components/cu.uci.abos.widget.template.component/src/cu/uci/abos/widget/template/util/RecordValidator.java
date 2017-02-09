package cu.uci.abos.widget.template.util;

import java.util.ArrayList;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Text;

import cu.abos.l10n.template.component.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.MyDate;
import cu.uci.abos.widget.repeatable.field.util.MyDateTime;
import cu.uci.abos.widget.repeatable.field.util.MyTime;
import cu.uci.abos.widget.repeatable.field.util.SubFieldStructure;

public class RecordValidator {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private ArrayList<FieldStructure> notNull;
	private ArrayList<FieldStructure> children;
	private boolean response;
	private Color color;
	private Image image;
	private final int MAX_LENGTH = 300;

	/**
	 * @param children
	 */
	public RecordValidator(ArrayList<FieldStructure> notNull, ArrayList<FieldStructure> children) {
		this.notNull = notNull;
		this.children = children;
		this.response = true;
	}

	public boolean validate(){
		int count = notNull.size();
		for (int i = 0; i < count; i++) {
			FieldStructure fieldStructure = notNull.get(i);
			ArrayList<SubFieldStructure> subFields = fieldStructure.getSubfields();
			int subFieldsCount = subFields.size();

			if(subFieldsCount > 0){
				ArrayList<Integer> positionSubFieldsObligatory = fieldStructure.getPositionObligatorySubfields();

				for (int j = 0; j < subFieldsCount; j++) {
					if(positionSubFieldsObligatory.contains(j)){
						Control control = subFields.get(j).getControl();
						validateControl(control, fieldStructure);
					}
				}
			}
			else{
				Control control = fieldStructure.getControl();
				validateControl(control, fieldStructure);
			}
		}
		if(!response)
			ThrowMandatoryMessage();
		else{
		    checkLength();
		    if(!response)
		    	ThrowMaxLengthMessage();
		}
		return response;
	}

	private void validateControl(Control control, FieldStructure fieldStructure){
		String value = valueOfTheControl(control);
		if(value == null || value.equals("") || value.equals(" "))
			requireValue(control, fieldStructure);
		else
			checked(control, fieldStructure);
	}

	private String valueOfTheControl(Control control){
		String value = null;
		if(control instanceof Text){
			value = ((Text)control).getText();
			value = removeDobleSpaces(value);
		}
		else if(control instanceof Combo){
			int selectedPosition = ((Combo)control).getSelectionIndex();
			if(selectedPosition > 0)
				value = "ok";
		}
		else if(control instanceof MyDateTime)
			value = ((MyDateTime)control).getText();
		else if(control instanceof MyDate)
			value = ((MyDate)control).getText();
		else if(control instanceof MyTime)
			value = ((MyTime)control).getText();
		
		return value;
	}

	private void requireValue(Control control, FieldStructure fieldStructure){
		CTabItem tabItem = fieldStructure.getTabItem();
		ExpandItem expandItem = fieldStructure.getExpandItem();
		itIsWrong(control, expandItem, tabItem);
		this.response = false;
	}

	private void checked(Control control, FieldStructure fieldStructure){
		CTabItem tabItem = fieldStructure.getTabItem();
		ExpandItem expandItem = fieldStructure.getExpandItem();
		itIsOk(control, expandItem, tabItem);
	}

	private void ThrowMandatoryMessage(){
		RetroalimentationUtils.showErrorShellMessage(
				MessageUtil
				.unescape("Los campos de las pestañas resaltadas no pueden ser vacíos."));
	}
	
	private void checkLength(){
		int count = children.size();
		for (int i = 0; i < count; i++) {
			FieldStructure current = children.get(i);
			analizeItem(current);
		}
	}
	
	private void analizeItem(FieldStructure fieldStructure){
		Control control = fieldStructure.getControl();
		
		//has subfields
		if(control == null){
			ArrayList<SubFieldStructure> subFields = fieldStructure.getSubfields();
			int subFieldsCount = subFields.size();
			
			boolean analizeAll = true;
			
			for (int i = 0; i < subFieldsCount; i++) {
				Control currentControl = subFields.get(i).getControl();
				boolean currentAnalize = analizeControl(fieldStructure, currentControl, true);
				if(!currentAnalize)
					analizeAll = false;
			}
			if(!analizeAll){
				 image = new Image(fieldStructure.getTabItem().getDisplay(), RWT.getResourceManager()
						 .getRegisteredContent("redcross"));
				 fieldStructure.getTabItem().setImage(image);
				 fieldStructure.getExpandItem().setImage(image);
				 fieldStructure.getExpandItem().setExpanded(true);
			}
			else{
				if(fieldStructure.getTabItem().getImage() == null)
				fieldStructure.getTabItem().setImage(null);
				fieldStructure.getExpandItem().setImage(null);
				fieldStructure.getExpandItem().setExpanded(true);
			}
		}
		else
			analizeControl(fieldStructure, control, false);
	}
	
	private boolean analizeControl(FieldStructure fieldStructure, Control control, boolean isSubField){
		
		boolean controlTest = true;
		
		if(control instanceof Text){
			   String content = ((Text)control).getText();
			   
			   content = removeDobleSpaces(content);
			   int contentLength = content.length();
			   
			   CTabItem tabItem = fieldStructure.getTabItem();
			   ExpandItem expandItem = fieldStructure.getExpandItem();
			   if(contentLength > MAX_LENGTH){
				   if(isSubField)
				   itIsSubFieldWrong(control);
				   else
					   itIsWrong(control, expandItem, tabItem);
				   this.response = false;
				   controlTest = false;
			   }
			   else{
				   if(isSubField)
					   itIsSuFieldOk(control);
				   else
					   itIsOk(control, expandItem, tabItem);
			   }
			}
		
		return controlTest;
	}
	
	private void ThrowMaxLengthMessage(){
		RetroalimentationUtils.showErrorShellMessage(
				MessageUtil
				.unescape("Los campos de las pestañas resaltadas exceden el tamaño definido, que es "+
				String.valueOf(MAX_LENGTH)+"."));
	}
	
	private void itIsWrong(Control control, ExpandItem expandItem,
			CTabItem tabItem){
		 color = new Color(control.getDisplay(), 255, 204, 153);
		 control.setBackground(color);
		 image = new Image(control.getDisplay(), RWT.getResourceManager().getRegisteredContent("redcross"));
		 tabItem.setImage(image);
		 expandItem.setImage(image);
		 expandItem.setExpanded(true);
	}
	
   private void itIsOk(Control control, ExpandItem expandItem,
		   CTabItem tabItem){
	   color = new Color(control.getDisplay(), 255, 255, 255);
	   control.setBackground(color);
	   tabItem.setImage(null);
	   expandItem.setImage(null);
	   expandItem.setExpanded(true);
	}
   
   private void itIsSubFieldWrong(Control control){
		 color = new Color(control.getDisplay(), 255, 204, 153);
		 control.setBackground(color);
	}
	
  private void itIsSuFieldOk(Control control){
	   color = new Color(control.getDisplay(), 255, 255, 255);
	   control.setBackground(color);
	}
   
   private String removeDobleSpaces(String text){
	   return text.replaceAll(" +"," ").trim();
   }

}
