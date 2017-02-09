package cu.uci.abcd.opac.listener;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.opac.contribution.OpacAdquisitionMenu;
import cu.uci.abcd.opac.contribution.OpacCirculationMenu;
import cu.uci.abcd.opac.contribution.OpacHeaderProvider;
import cu.uci.abos.util.api.IServiceProvider;

public class OpacAutenticateListener implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceProvider service;
	private Object user;

	public OpacAutenticateListener(IServiceProvider service, Object user) {

		this.service=service;
		this.user=user;
	}

	@Override
	public void handleEvent(Event e) {
		    Composite composite = ((Button) e.widget).getParent();
		    composite.setVisible(false);
		OpacHeaderProvider headerProvider=service.get(OpacHeaderProvider.class);
		//se oculta la opción entrar
		headerProvider.login.setVisible(false);	
		// se muestra le area que contiene la información del usuario autenticado
	//	Composite result=headerProvider.result;
		String usuario=(String)user;
		headerProvider.Login(usuario);
		
		// se muestra el area definida para la adquiscion
		OpacAdquisitionMenu adquisitionMenu=service.get(OpacAdquisitionMenu.class);
		adquisitionMenu.composite.setVisible(true);
		
		OpacCirculationMenu circulationMenu=service.get(OpacCirculationMenu.class);
		circulationMenu.composite.setVisible(true);
		
            
			composite.getShell().layout(true, true);
			composite.getShell().redraw();
			composite.getShell().update();

		

	}

}
