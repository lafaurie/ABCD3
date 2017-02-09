package cu.uci.abcd.cataloguing.util;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.common.Global;
import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.IField;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.ISubfield;
import org.unesco.jisis.corelib.record.StringOccurrence;
import org.unesco.jisis.corelib.record.Subfield;

import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.MyDate;
import cu.uci.abos.widget.repeatable.field.util.MyDateTime;
import cu.uci.abos.widget.repeatable.field.util.MyFieldUpLoad;
import cu.uci.abos.widget.repeatable.field.util.MyTime;
import cu.uci.abos.widget.repeatable.field.util.SubFieldStructure;

public class JisisRegistration {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private String databaseName;
	private byte[] byteArray = null;
	
	public JisisRegistration(String databaseName){
		this.databaseName = databaseName;
	}

	public boolean save(ArrayList<FieldStructure> children, IRecord record){

		boolean result = false;

		try{
			int fieldCount = children.size();
			int fieldNotEmpty = 0;

			for (int i = 0; i < fieldCount; i++) {
				FieldStructure fieldStructure = children.get(i);
				int occurrenceCount = 0;
				int tag = fieldStructure.getTag();

				if(fieldStructure.isRepeatableField()){
					boolean terminate = false;

					for (int j = i+1; j < fieldCount && terminate == false; j++) {
						int tagj = -1;
						FieldStructure fieldStructurej = children.get(j);

						if(fieldStructurej.getSubfields().isEmpty())
							tagj = fieldStructurej.getTag();
						else
							tagj = fieldStructurej.getSubfields().get(0).getTag();

						if(tagj == tag)
							occurrenceCount++;
						else
							terminate = true;
					}
				}

				if(occurrenceCount > 0){         //has occurrence
					int occurrenceBegin = 0;
					IField field = null;

					for (int j = 0; j < occurrenceCount+1; j++) {
						FieldStructure fieldStructureSave = children.get(i+j);
						int subFieldsCount = fieldStructureSave.getSubfields().size();

						if(subFieldsCount > 0){       //has subfields
							ArrayList<Subfield> subfields = new ArrayList<Subfield>();
							int subFieldWithValueCount = 0;

							for (int k = 0; k < subFieldsCount; k++) {
								SubFieldStructure subFieldStructure = fieldStructureSave.getSubfields().get(k);
								String value = "";

								Control control = subFieldStructure.getControl();
								value = getValue(control, tag);

								String code = subFieldStructure.getSubFieldCode();
								char realCode = code.charAt(code.length()-1);

								if(!subFieldStructure.getControl().isDisposed() && !value.equals("") && !value.equals(null) &&
										!value.equals("-seleccione-")){
									ISubfield subfield = new Subfield(realCode, value);
									subfields.add((Subfield)subfield);
									subFieldWithValueCount++;
								}
							}

							if(subFieldWithValueCount > 0){
								StringOccurrence occurrence = new StringOccurrence();
								Subfield[] subfieldsArray = subfields.toArray(new Subfield[subfields.size()]);
								occurrence.setSubfields(subfieldsArray);

								if(0==j){	
									field = new Field(tag, Global.FIELD_TYPE_ALPHANUMERIC);
									field.setOccurrence(occurrenceBegin, occurrence);

									record.addField(field);
									fieldNotEmpty++;
								}
								else{
									int occurrenceCountfieldLast = field.getOccurrenceCount();
									field.setOccurrence(occurrenceCountfieldLast, occurrence);
								}
							}
						}
						else{                       //do not has subfields
							String value = "";
							Control control = fieldStructureSave.getControl();
							value = getValue(control, tag);

							if(!fieldStructureSave.getControl().isDisposed() && !value.equals("") && !value.equals(null) &&
									!value.equals("-seleccione-")){

								if(0==j){
									field = new Field(tag, Global.FIELD_TYPE_ALPHANUMERIC);
									field.setFieldValue(value);

									record.addField(field);
									fieldNotEmpty++;
								}
								else{
									int occurrenceCountfieldLast = field.getOccurrenceCount();
									field.setOccurrence(occurrenceCountfieldLast, value);
								}
							}
						}
						occurrenceBegin++;	
					}
					i+=occurrenceCount;
				}
				else{
					int subFieldsCount = fieldStructure.getSubfields().size();          // do not has occurrence
					IField field;

					if(subFieldsCount > 0){           //has subfields
						ArrayList<Subfield> subfields = new ArrayList<Subfield>();
						int subFieldWithValueCount = 0;

						for (int j = 0; j < subFieldsCount; j++) {
							SubFieldStructure subFieldStructure = fieldStructure.getSubfields().get(j);
							String value = "";
							Control control = subFieldStructure.getControl();
							
							value = getValue(control, tag);

							String code = subFieldStructure.getSubFieldCode();
							char realCode = code.charAt(code.length()-1);

							if(!subFieldStructure.getControl().isDisposed() && !value.equals("") && null != value &&
									!value.equals("-seleccione-")){
								ISubfield subfield = new Subfield(realCode, value);
								subfields.add((Subfield)subfield);
								subFieldWithValueCount++;
							}
						}

						if(subFieldWithValueCount > 0){
							StringOccurrence occurrence = new StringOccurrence();
							Subfield[] subfieldsArray = subfields.toArray(new Subfield[subfields.size()]);
							occurrence.setSubfields(subfieldsArray);

							field = new Field(tag, Global.FIELD_TYPE_ALPHANUMERIC);
							field.setOccurrence(occurrenceCount, occurrence);

							record.addField(field);
							fieldNotEmpty++;
						}
					}
					else{                     //do not has subfields
						String value = "";
						Control control = fieldStructure.getControl();
						
						
						
						
						
						
						//file        moment
						if(control instanceof MyFieldUpLoad){
							
							byteArray = ((MyFieldUpLoad)control).getByteArray();
							
						}
						else
						value = getValue(control, tag);

						if(!fieldStructure.getControl().isDisposed() && ((!value.equals("") && null != value && 
								!value.equals("-seleccione-")||(null !=byteArray)))){				    		
							
							if(control instanceof MyFieldUpLoad){
								
								field = new Field(tag, Global.FIELD_TYPE_BLOB);
								field.setFieldValue(byteArray);
					
								record.addField(field);
								fieldNotEmpty++;
								
							}
							else{
								
								field = new Field(tag, Global.FIELD_TYPE_ALPHANUMERIC);
								field.setFieldValue(value);
					
								record.addField(field);
								fieldNotEmpty++;
								
							}
							
						}			    		
					}
				}
			}
			if(fieldNotEmpty > 0)
				result = true;

		}catch (DbException e){
			RetroalimentationUtils.showErrorShellMessage(
					"Problema de conección con JISIS");
		}
		return result;
	}

	private String getValue(Control control, int tag){

		String response = null;

		if(control instanceof Text){
		   if(databaseName.equals(Constant.EXEMPLARY_DATABASE_NAME) && tag == Constant.FIELD_8){
			   response = ((Text)control).getText();
		   }
		   else
			   response = ((Text)control).getText().replaceAll(" +"," ").trim();
		}	
		else if(control instanceof Combo)
			response = ((Combo)control).getText();
		else if(control instanceof MyDateTime)
			response = ((MyDateTime)control).getText();
		else if(control instanceof MyDate)
			response = ((MyDate)control).getText();
		else if(control instanceof MyTime)
			response = ((MyTime)control).getText();

		return response;
	}
}
