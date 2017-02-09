package cu.uci.abcd.management.security.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.management.security.communFragment.RegisterPersonFragment;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;

public class RegisterPerson extends ContributorPage {

	private RegisterPersonFragment registerPerson;
	private int dimension;

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

	@Override
	public Control createUIControl(Composite parent) {
		dimension = parent.getParent().getParent().getParent().getBounds().width;
		ContributorService contributorService = getContributorService();
		registerPerson = new RegisterPersonFragment(null, controller, dimension, this, contributorService);
		return registerPerson.createUIControl(parent);
	}

	@Override
	public String getID() {
		return "addPersonID";
	}

	@Override
	public void l10n() {
		registerPerson.l10n();

	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().PERSON);
	}

}
