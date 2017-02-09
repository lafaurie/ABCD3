package cu.uci.abcd.management.security.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.management.security.communFragment.RegisterPerfilFragment;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;

public class RegisterProfile extends ContributorPage implements Contributor {
	private RegisterPerfilFragment registerPerfil;
	private int dimension;

	@Override
	public Control createUIControl(Composite parent) {
		dimension = parent.getParent().getParent().getParent().getBounds().width;
		ContributorService contributorService = getContributorService();
		registerPerfil =  new RegisterPerfilFragment(null, controller, dimension, this, contributorService);
		return registerPerfil.createUIControl(parent);
	}

	@Override
	public String getID() {
		return "addProfileID";
	}

	@Override
	public void l10n() {
		registerPerfil.l10n();

	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().PROFILE);
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

}
