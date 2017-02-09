package cu.uci.abcd.cataloguing.listener;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AcquisitionIntegration;
import cu.uci.abos.core.ui.ContributorPage;

public class EventAcquisitionIntegration implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private ProxyController controller;
	private String dataBaseName;
	private Composite view;

	public EventAcquisitionIntegration(Composite view, String dataBaseName, ProxyController controller) {
		this.view = view;
		this.dataBaseName = dataBaseName;
		this.controller = controller;
	}

	@Override
	public void handleEvent(Event arg0) {

		// erase view
		Composite superArg0 = view.getParent();
		view.dispose();

		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();

		AcquisitionIntegration acquisitionIntegration = new AcquisitionIntegration();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("NOT_SCROLLED", Boolean.FALSE);

		((ContributorPage) acquisitionIntegration).setProperties(properties);
		acquisitionIntegration.setProxyController(controller);
		acquisitionIntegration.setDataBaseName(dataBaseName);

		acquisitionIntegration.createUIControl(superArg0);

		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();
	}

}
