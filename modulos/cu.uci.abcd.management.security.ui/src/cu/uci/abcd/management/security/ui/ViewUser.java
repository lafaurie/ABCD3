package cu.uci.abcd.management.security.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.management.security.communFragment.ViewUserFragment;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.util.Auxiliary;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;

public class ViewUser extends ContributorPage implements Contributor{
	
	private User user;
	private ContributorService contributorService;
	private Composite parentComposite;
	private ViewUserFragment viewUserFragment;
	
	@Override
	public void setParameters(Object... parameters) {
		user = ((User)parameters[0]);
		contributorService = ((ContributorService)parameters[2]);
	}

	@Override
	public Control createUIControl(Composite parent) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		viewUserFragment = new ViewUserFragment(user, true, this, contributorService);
		parentComposite = (Composite) viewUserFragment.createUIControl(parent);
		
		RetroalimentationUtils.showInformationMessage(viewUserFragment.getMsg(), MessageUtil
				.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
		return parentComposite;
	}
	
	@Override
	public String getID() {
		return "viewUser";
	}
	
	@Override
	public void l10n() {
		viewUserFragment.l10n();
		Auxiliary.refresh(parentComposite);
	}
	
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().USER);
	}
	
}
