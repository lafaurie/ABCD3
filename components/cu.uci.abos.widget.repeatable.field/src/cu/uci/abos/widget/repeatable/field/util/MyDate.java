package cu.uci.abos.widget.repeatable.field.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;

import cu.uci.abos.core.util.FormDatas;

public class MyDate extends Composite{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private DateTime date;

	public MyDate(Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FormLayout());
		date = new DateTime(this, SWT.BORDER|SWT.DROP_DOWN|SWT.DATE);
		FormDatas.attach(date).withWidth(272).withHeight(23).atTopTo(parent, 0).atLeftTo(parent, 0);

		this.getShell().layout(true, true);
		this.getShell().redraw();
		this.getShell().update();
	}

	public String getText(){
		String value = "";

		value = String.valueOf(date.getDay()) + "/" + String.valueOf(date.getMonth()+1) + "/" + String.valueOf(date.getYear());

		return value;
	}

	public String getDate(){
		String value = "";

		String year = String.valueOf(date.getYear());
		String yearCode = year.substring(2, year.length());

		int month = date.getMonth() + 1;
		String monthCode;

		if(month < 10)
			monthCode = "0"+String.valueOf(month);
		else
			monthCode = String.valueOf(month);

		int day = date.getDay();
		String dayCode;

		if(day < 10)
			dayCode = "0"+String.valueOf(day);
		else
			dayCode = String.valueOf(day);

		value =  yearCode + monthCode + dayCode;

		return value;
	}

	public DateTime getDateTime(){
		return date;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
