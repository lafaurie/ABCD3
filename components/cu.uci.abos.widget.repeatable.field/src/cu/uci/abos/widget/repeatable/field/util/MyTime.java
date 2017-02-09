package cu.uci.abos.widget.repeatable.field.util;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;

import cu.uci.abos.core.util.FormDatas;

public class MyTime extends Composite{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private DateTime time;

	public MyTime(Composite parent, int style) {
		super(parent, style);

		this.setData(RWT.CUSTOM_VARIANT,"gray_background");

		this.setLayout(new FormLayout());

		time = new DateTime(this, SWT.BORDER|SWT.TIME|SWT.SHORT);

		FormDatas.attach(time).withWidth(100).withHeight(23).atTopTo(parent, 0).atLeftTo(parent, 0);

		this.getShell().layout(true, true);
		this.getShell().redraw();
		this.getShell().update();
	}

	public String getText(){
		String value = "";

		value = String.valueOf(time.getHours()) + String.valueOf(time.getMinutes());

		return value;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



}
