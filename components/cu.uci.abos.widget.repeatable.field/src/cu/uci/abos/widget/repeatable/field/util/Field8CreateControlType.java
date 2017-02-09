package cu.uci.abos.widget.repeatable.field.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.repeatable.field.ControlType;

public class Field8CreateControlType {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private Control control;
	private ControlType controlType;

	public Field8CreateControlType(Control control, ControlType controlType) {
		this.control = control;
		this.controlType = controlType;
	}

	public Control create(String defaultValue, Composite content, Label label, boolean simple,
			String text, String[] comboList){

		switch (controlType) {
		case Text:
			control = new Text(content, SWT.PUSH);

			if(defaultValue != null)
				((Text)control).setText(defaultValue);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(10);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(10);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(250).withHeight(10);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case Combo:
			control = new Combo(content, SWT.READ_ONLY);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(270).withHeight(23);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(content, 10).withWidth(270).withHeight(23);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(270).withHeight(23);

			int count = comboList.length;

			((Combo)control).add("-seleccione-");

			int selectPosition = 0;

			int characterCount = defaultValue.length();

			for (int i = 0; i < count; i++) {
				String item = comboList[i].replaceAll("\n","");
				((Combo)control).add(item);
				if(defaultValue != null && !defaultValue.equals("")){
					if(defaultValue.equals(comboList[i].substring(0, characterCount))){
						selectPosition = i+1;
					}
				}
			}

			((Combo)control).select(selectPosition);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case DateTime:
			control = new MyDateTime(content, 0);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(365).withHeight(23);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case Date:
			control = new MyDate(content, 0);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(365).withHeight(23);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);
			break;

		case Time:
			control = new MyTime(content, 0);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(365).withHeight(23);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case TextArea:
			control = new Text(content, SWT.MULTI|SWT.WRAP);

			if(defaultValue != null)
				((Text)control).setText(defaultValue);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(100);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(content, 10).withWidth(250).withHeight(100);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(350).withHeight(100);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case NW:
			control = new Text(content, SWT.MULTI|SWT.WRAP);
			((Text)control).setEditable(false);

			if(defaultValue != null)
				((Text)control).setText(defaultValue);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(10);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(content, 10).withWidth(250).withHeight(10);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(350).withHeight(100);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;		

		default:
			break;
		}

		return control;

	}

}
