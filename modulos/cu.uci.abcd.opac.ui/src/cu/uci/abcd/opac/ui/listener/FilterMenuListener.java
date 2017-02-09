package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class FilterMenuListener implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilterMenuListener() {

	}

	@Override
	public void handleEvent(Event e) {
		Composite composite = ((Button) e.widget).getParent();
	
		Composite temp=((Button)e.widget).getParent();
		
		Control[]hijosControls=temp.getShell().getChildren();
		hijosControls[2].setVisible(true);


			composite.getShell().layout(true, true);
			composite.getShell().redraw();
			composite.getShell().update();

		

	}

}
