package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.opac.ui.contribution.OpacHeaderProvider;
import cu.uci.abcd.opac.ui.contribution.OpacPerfilMenu;
import cu.uci.abos.api.util.ServiceProvider;

public class OpacAutenticateListener implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServiceProvider service;	
    
	public OpacAutenticateListener(ServiceProvider service) {

		this.service = service;		
	}

	@Override
	public void handleEvent(Event e) {
		Composite composite = ((Button) e.widget).getParent();
		composite.setVisible(false);
		OpacHeaderProvider headerProvider = service.get(OpacHeaderProvider.class);

		// se oculta la opción entrar
		headerProvider.login.setVisible(false);

		// se muestra le area que contiene la información del usuario autenticado
				
		headerProvider.login();

		// se muestra el area definida para la adquiscion
		OpacPerfilMenu adquisitionMenu = service.get(OpacPerfilMenu.class);
		adquisitionMenu.result.setVisible(true);

		composite.getShell().layout(true, true);
		composite.getShell().redraw();
		composite.getShell().update();

	}

}
