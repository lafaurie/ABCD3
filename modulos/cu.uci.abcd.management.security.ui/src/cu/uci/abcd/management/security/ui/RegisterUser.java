package cu.uci.abcd.management.security.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.management.security.communFragment.RegisterUserFragment;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;

public class RegisterUser extends ContributorPage {

	private RegisterUserFragment registerUser;
	@SuppressWarnings("unused")
	private int dimension;

	@Override
	public void setViewController(ViewController controller) {
		this.setController(controller);
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().USER);
	}

	@Override
	public String getID() {
		return "addUserID";
	}

	@Override
	public Control createUIControl(Composite shell) {
		Library library = (Library) SecurityUtils.getPrincipal().getByKey("library");
		ContributorService contributorService = getContributorService();
		int dimension = shell.getParent().getParent().getParent().getBounds().width;
		registerUser = new RegisterUserFragment(null, controller, library, null, null, dimension, this, contributorService);
		return registerUser.createUIControl(shell);
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		registerUser.l10n();
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		super.controller = controller;
	}
}
