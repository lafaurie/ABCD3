package cu.uci.abcd.cataloguing.listener;

import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.ExpandItem;

public class EventAllwaysOpen implements ExpandListener{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	private static final long serialVersionUID = 1L;
	
	public EventAllwaysOpen(){
	}

	@Override
	public void itemCollapsed(ExpandEvent arg0) {
		// TODO Auto-generated method stub
		ExpandItem expandItem = (ExpandItem) arg0.item;
		expandItem.setExpanded(true);
	}

	@Override
	public void itemExpanded(ExpandEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
