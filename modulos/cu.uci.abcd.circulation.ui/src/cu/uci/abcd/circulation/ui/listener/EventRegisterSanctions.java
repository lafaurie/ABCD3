package cu.uci.abcd.circulation.ui.listener;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.circulation.ui.RegisterSanction;
import cu.uci.abos.core.ui.ContributorPage;

public class EventRegisterSanctions implements Listener {

	private static final long serialVersionUID = 1L;
	private Composite composite;
	private Button rdbSMulta;
	private Composite registerPenalty;
	private ContributorPage page;
	private RegisterSanction registerSanction;

	public EventRegisterSanctions(Composite composite, Composite registerPenalty, Button rdbSMulta, ContributorPage page) {
		super();
		this.composite = composite;
		this.rdbSMulta = rdbSMulta;
		this.page = page;
		this.registerPenalty = registerPenalty;
	}

	public EventRegisterSanctions(Composite composite, Composite registerPenalty, RegisterSanction registerSanction, Button rdbSMulta, ContributorPage page) {
		super();
		this.composite = composite;
		this.rdbSMulta = rdbSMulta;
		this.page = page;
		this.registerPenalty = registerPenalty;
		this.registerSanction = registerSanction;
	}

	@Override
	public void handleEvent(Event arg0) {
		if (rdbSMulta.getSelection() == true) {

			composite.setVisible(true);
			page.insertComposite(composite, registerPenalty);

			composite.getShell().layout(true, true);
			composite.getShell().redraw();
			composite.getShell().update(); 			       
			
			if (registerSanction != null)
				registerSanction.refresh();

		} else if (rdbSMulta.getSelection() == false) {

			composite.setVisible(false);
			page.insertComposite(composite, registerPenalty);

			composite.setVisible(false);
			composite.getShell().layout(true, true);
			composite.getShell().redraw();
			composite.getShell().update();			

			if (registerSanction != null)
				registerSanction.refresh();
		}

		page.l10n();
	}

}
