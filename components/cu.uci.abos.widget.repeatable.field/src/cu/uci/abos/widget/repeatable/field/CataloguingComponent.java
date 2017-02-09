package cu.uci.abos.widget.repeatable.field;

import java.util.ArrayList;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;
import cu.uci.abos.widget.repeatable.field.domain.SimpleFieldDomain;
import cu.uci.abos.widget.repeatable.field.domain.SubFieldDomain;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.SubFieldDescription;
import cu.uci.abos.widget.repeatable.field.util.SubFieldStructure;
import cu.uci.abos.core.util.FormDatas;

public class CataloguingComponent extends Composite {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private FieldStructure fieldStructure;
	private SubFieldStructure subFieldStructure;

	public CataloguingComponent(Composite parent, int style, SubFieldDescription subFieldDescription, ExpandItem expandItem,
			CTabFolder tabFolder, String expandItemText, CTabItem tabItem, FieldDomain fieldDomain,
			ArrayList<FieldStructure> children, ArrayList<FieldStructure> notNull, boolean register){
		super(parent,style);

		boolean repeatedField = fieldDomain.isRepeatableField();
		int tag = fieldDomain.getTag();
		String description = fieldDomain.getDescription();

		ArrayList<SubFieldStructure> subFieldStructures = new ArrayList<SubFieldStructure>();
		ArrayList<Control> controlls =new  ArrayList<Control>();

		boolean validate = false;

		if(subFieldDescription.itHasSubfields()){
			this.fieldStructure = new FieldStructure(subFieldStructures, expandItemText, tag, fieldDomain.getSubtag(),
					expandItem, tabItem,repeatedField);

			ArrayList<SubFieldDomain> subFields = fieldDomain.getSubFields();
			int count = subFields.size();
			for (int i = 0; i < count; i++) {
				SubFieldDomain subFieldDomain = subFields.get(i);
				if(subFieldDomain.isObligatory()){
					validate = true; 
					this.fieldStructure.getPositionObligatorySubfields().add(i);
				}
			}
		}
		else{
			this.fieldStructure = new FieldStructure(tag, fieldDomain.getSubtag(),expandItemText, expandItem, tabItem, repeatedField);
			if(fieldDomain.isObligatory())
				validate = true;
		}

		children.add(fieldStructure);
		if(validate)
			notNull.add(fieldStructure);

		SimpleFieldDomain simpleFieldDomain = new SimpleFieldDomain(fieldDomain.isRepeatableField(), description, tag,
				subFieldDescription.itHasSubfields(), fieldStructure, expandItemText,
				subFieldDescription.getComponentCount(), true);

		if(subFieldDescription.itHasSubfields()){

			int componentsSize = subFieldDescription.getComponentCount();
			for (int i = 0; i < componentsSize; i++) {

				simpleFieldDomain.setType(subFieldDescription.getTypes().get(i));
				simpleFieldDomain.setLabelText(subFieldDescription.getTexts().get(i));
				simpleFieldDomain.setControlType(subFieldDescription.getControls().get(i));
				simpleFieldDomain.setSubFieldCode(subFieldDescription.getSubFieldCode().get(i));
				simpleFieldDomain.setComboList(subFieldDescription.getComboList().get(i));
				simpleFieldDomain.setDefaultValue(fieldDomain.getSubFields().get(i).getDefaultValue());
				simpleFieldDomain.setRegister(register);
				simpleFieldDomain.setMandatoryField(fieldDomain.isObligatory());

				if(!register){

					if(fieldDomain.getSubFields().get(i).isRepeatableSubField()){

						if(i+1 < fieldDomain.getSubFieldCount()){

							if(fieldDomain.getSubFields().get(i + 1).getDescription().equals(fieldDomain.getSubFields().get(i).getDescription()))
								simpleFieldDomain.setNotPlusItem(true);
							else
								simpleFieldDomain.setNotPlusItem(false);

						}

					}

				}

				SingleCataloguingComponent sigleComponent = new SingleCataloguingComponent(this, style, expandItem, tabFolder, subFieldStructures,
						controlls, simpleFieldDomain, children); 
				sigleComponent.setLayout(new FormLayout());

				if(i==0){
					if(repeatedField || fieldDomain.isReferenceDataBase())
						FormDatas.attach(this.getChildren()[i]).atTopTo(parent, 30);
					else
						FormDatas.attach(this.getChildren()[i]).atTopTo(parent, 5);

					if(subFieldDescription.getComponentCount() > 1){
						ToolItem up = ((SingleCataloguingComponent)(this.getChildren()[i])).getUp();
						if(up != null)
							up.setEnabled(false);
					}
				}   
				else
					FormDatas.attach(this.getChildren()[i]).atTopTo(this.getChildren()[i-1], 0);

				if(i == componentsSize - 1){				    	   
					ToolItem down = ((SingleCataloguingComponent)(this.getChildren()[i])).getDown();
					if(down != null)
						down.setEnabled(false);
				}
			}
		}
		else{
			simpleFieldDomain.setType(subFieldDescription.getType());
			simpleFieldDomain.setControlType(subFieldDescription.getControl());
			simpleFieldDomain.setComboList(subFieldDescription.getPicklist());
			simpleFieldDomain.setDefaultValue(fieldDomain.getDefaultValue());

			SingleCataloguingComponent singleComponent = new SingleCataloguingComponent(this, style, expandItem, tabFolder, subFieldStructures,
					controlls, simpleFieldDomain, children);
			singleComponent.setLayout(new FormLayout());

			if(repeatedField || fieldDomain.isReferenceDataBase())
				FormDatas.attach(this.getChildren()[0]).atTopTo(parent, 30);
			else
				FormDatas.attach(this.getChildren()[0]).atTopTo(parent, 5);
		}

		if(!subFieldDescription.itHasSubfields())
			this.fieldStructure.setControl(controlls.get(0));
	}

	public FieldStructure getFieldStructure(){return fieldStructure;}
	public SubFieldStructure getSubFieldStructure(){return subFieldStructure;}

	private static final long serialVersionUID = 1L;
}
