package cu.uci.abcd.cataloguing.listener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AcquisitionIntegration;

public class EventProcessingExemplary implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Composite view;
	private ProxyController controller;
	private String dataBaseName;

	public EventProcessingExemplary(Composite view, ProxyController controller,
			String dataBaseName){
		this.view = view;
		this.controller = controller;
		this.dataBaseName = dataBaseName;
	}

	@Override
	public void handleEvent(Event arg0) {
		Composite superArg0 = view.getParent();
		view.dispose();

		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();

		AcquisitionIntegration acquisitionIntegration = new AcquisitionIntegration();

		acquisitionIntegration.setProxyController(controller);
		acquisitionIntegration.setDataBaseName(dataBaseName);

		acquisitionIntegration.createUIControl(superArg0);

		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();
	}
}
