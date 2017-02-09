package cu.uci.abos.widget.repeatable.field.util;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;

import cu.uci.abos.core.util.FormDatas;

public class MyDateTime extends Composite{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private DateTime date;
	private DateTime time;

	public MyDateTime(Composite parent, int style) {
		super(parent, style);

		this.setData(RWT.CUSTOM_VARIANT,"gray_background");
		this.setLayout(new FormLayout());

		date = new DateTime(this, SWT.BORDER|SWT.DROP_DOWN|SWT.DATE);

		FormDatas.attach(date).withWidth(250).withHeight(23).atTopTo(parent, 0).atLeftTo(parent, 0);

		time = new DateTime(this, SWT.BORDER|SWT.TIME|SWT.SHORT);

		FormDatas.attach(time).atLeftTo(date, 15).withHeight(23).withWidth(100).atTopTo(parent, 0);

		this.getShell().layout(true, true);
		this.getShell().redraw();
		this.getShell().update();
	}

	public String getText(){
		String value = "";
		
		String year = String.valueOf(date.getYear());
		int month = date.getMonth()+1;
		String monthCode;
		int day = date.getDay();
		String dayCode;
		String hours = String.valueOf(time.getHours());
		String minutes = String.valueOf(time.getMinutes());
		String secounds = String.valueOf(time.getSeconds());
		
		if(month < 10)
			monthCode = "0"+String.valueOf(month);
		else
			monthCode = String.valueOf(month);
		
		if(day < 10)
			dayCode = "0"+String.valueOf(day);
		else
			dayCode = String.valueOf(day);

		value = year+monthCode+dayCode+hours+minutes+secounds;

		return value;
	}

}
