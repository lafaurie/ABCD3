package cu.uci.abos.widget.repeatable.field;

import java.util.ArrayList;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;
import cu.uci.abos.widget.repeatable.field.util.Field8CreateControlType;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;

public class SingleField8Component extends Composite {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Control control;
	private Composite father;
	private Composite content;
	private ControlType controlType;
	private boolean white = true;
	private Label label;

	public SingleField8Component(Composite parent, int style, FieldDomain fieldDomain, ArrayList<FieldStructure> children,
			FieldStructure fieldStructure, Control control) {
		super(parent, style);
		this.control = control;
		this.controlType =  fieldDomain.getSubfieldDescription().getControl();

		//getProperties
		String labelText = fieldDomain.getDescription();
		String[] comboList = fieldDomain.getSubfieldDescription().getPicklist();
		String defaultValue = fieldDomain.getDefaultValue();

		startAtributes(labelText);
		createControl(comboList, defaultValue, fieldStructure);
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

	private void createControl(String[] comboList, String defaultValue, FieldStructure fieldStructure){	
		String text = label.getText();

		Field8CreateControlType createControlType = new Field8CreateControlType(control, controlType);
		this.control = createControlType.create(defaultValue, content, label, false, text, comboList);
		fieldStructure.setControl(control);

		FormDatas.attach(label).withWidth(200).atLeftTo(content, 5).atTopTo(content, 10);

		content.getShell().layout(true, true);
		content.getShell().redraw();
		content.getShell().update();	
	}

}
