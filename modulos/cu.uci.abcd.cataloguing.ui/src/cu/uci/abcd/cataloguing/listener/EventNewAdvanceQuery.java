package cu.uci.abcd.cataloguing.listener;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.advanced.query.ColorType;
import cu.uci.abos.widget.advanced.query.QueryComponent;

public class EventNewAdvanceQuery implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EventAdvanceQuery advanceQuery;
	private QueryComponent component;
	private Composite parent;
	private String[] picklist;
	private ArrayList<Button> buttons;
	private Label logic;
	
	public EventNewAdvanceQuery(QueryComponent component, Composite parent, String[] picklist,
			EventAdvanceQuery advanceQuery, ArrayList<Button> buttons, Label logic){
		this.advanceQuery = advanceQuery;
		this.component = component;
		this.parent = parent;
		this.picklist = picklist;
		this.buttons = buttons;
		this.logic = logic;
	}

	@Override
	public void handleEvent(Event arg0) {
		
		component.dispose();
		
		int count = buttons.size();
		for (int i = 0; i < count; i++) {
			buttons.get(i).setSelection(false);
		}
		
		QueryComponent component = new QueryComponent(parent, 0, picklist, ColorType.Gray);
		FormDatas.attach(component).atTopTo(parent, 0).atLeftTo(parent, 0);
		
		setComponent(component);
		advanceQuery.setComponent(component);
		
		parent.getShell().layout(true, true);
		parent.getShell().redraw();
		parent.getShell().update();
		parent.pack();
		
		logic.setVisible(false);
	}
	
	private void setComponent(QueryComponent component){
		this.component = component;
	}

}
