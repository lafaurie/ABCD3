package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.opac.ui.contribution.OpacLoginProvider;
import cu.uci.abcd.opac.ui.contribution.OpacPerfilMenu;
import cu.uci.abos.api.util.ServiceProvider;

public class OpacLoginListener implements Listener {

	private static final long serialVersionUID = 1L;
	private ServiceProvider service;

	public OpacLoginListener(ServiceProvider service) {

		this.service = service;
	}

	@Override
	public void handleEvent(Event e) {

		Composite composite = ((Link) e.widget).getParent();

		OpacLoginProvider loginProvider = service.get(OpacLoginProvider.class);
		loginProvider.compoVisible(true);

		OpacPerfilMenu adquisitionMenu = service.get(OpacPerfilMenu.class);
		adquisitionMenu.result.setVisible(false);

		composite.getShell().layout(true, true);
		composite.getShell().redraw();
		composite.getShell().update();

	}
}