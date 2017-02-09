package cu.uci.abcd.opac.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.opac.contribution.OpacLoginProvider;
import cu.uci.abos.util.api.IServiceProvider;

public class OpacLoginListener implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceProvider service;

	public OpacLoginListener(IServiceProvider service) {

		this.service=service;
	}

	@Override
	public void handleEvent(Event e) {
		    Composite composite = ((Link) e.widget).getParent();
		         
             OpacLoginProvider loginProvider=service.get(OpacLoginProvider.class);
             loginProvider.composite.setVisible(true);
             
			composite.getShell().layout(true, true);
			composite.getShell().redraw();
			composite.getShell().update();

		

	}

}
