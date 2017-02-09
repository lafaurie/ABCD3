package cu.uci.abcd.management.security.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.communFragment.ViewProfileFragment;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.util.Auxiliary;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;

public class ViewProfile extends ContributorPage implements Contributor{

	private Profile profile;
	private ContributorService contributorService;
	private Composite parentComposite;
	private ViewProfileFragment viewProfileFragment;
	
	@Override
	public void setParameters(Object... parameters) {
		profile = ((Profile)parameters[0]);
		contributorService = ((ContributorService)parameters[2]);

	}
	
	@Override
	public Control createUIControl(Composite parent) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		viewProfileFragment = new ViewProfileFragment(profile, true, this, contributorService);
		parentComposite = (Composite) viewProfileFragment.createUIControl(parent);
		
		RetroalimentationUtils.showInformationMessage(viewProfileFragment.getMsg(), MessageUtil
				.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
		return parentComposite;
	}

	
	@Override
	public String getID() {
		return "viewProfile";
	}
	
	@Override
	public void l10n() {
		viewProfileFragment.l10n();
		Auxiliary.refresh(parentComposite);
	}
	
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().PROFILE);
	}
	
}
