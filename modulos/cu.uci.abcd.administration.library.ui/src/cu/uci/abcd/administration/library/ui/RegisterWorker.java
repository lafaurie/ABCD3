package cu.uci.abcd.administration.library.ui;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.administration.library.communFragment.RegisterWorkerFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.SecurityUtils;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterWorker extends ContributorPage implements Contributor{
	private RegisterWorkerFragment registerWorker;
	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}
	
	@Override
	public String contributorName() {
		return AbosMessages.get().REGISTER_WORKER;
	}

	@Override
	public String getID() {
		return "addWorkerID";
	}

	@Override
	public Control createUIControl(Composite shell) {
		
		Library library = (Library) SecurityUtils
				.getService().getPrincipal()
				.getByKey("library");
		ContributorService contributorService = getContributorService();
		registerWorker = new RegisterWorkerFragment(null, controller, library, null, this, contributorService);
		return registerWorker.createUIControl(shell);
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		registerWorker.l10n();
	}
}
