package cu.uci.abcd.management.security.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.management.security.communFragment.RegisterAccessControlFragment;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;

public class RegisterAccessControl extends ContributorPage {

	RegisterAccessControlFragment registerAccessControl;
	private int dimension;

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().ACCESS);
	}

	@Override
	public String getID() {
		return "addAccessControlID";
	}

	@Override
	public Control createUIControl(Composite parent) {
		dimension = parent.getParent().getParent().getParent().getBounds().width;
		ContributorService contributorService = getContributorService();
		Library library = (Library) SecurityUtils
				.getPrincipal().getByKey("library");
		
		registerAccessControl = new RegisterAccessControlFragment(null, controller, dimension, this, contributorService, library, null);
		return registerAccessControl.createUIControl(parent);
	}
	
	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		registerAccessControl.l10n();
	}
}
