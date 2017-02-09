package cu.uci.abcd.management.security.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.domain.management.security.AccessRecord;
import cu.uci.abcd.management.security.communFragment.ViewAccessControlFragment;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.util.Auxiliary;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;

public class ViewAccessControl extends ContributorPage implements Contributor{

	private AccessRecord accessRecord;
	private ContributorService contributorService;
	private Composite parentComposite;
	private ViewAccessControlFragment viewAccessControlFragment;
	
	@Override
	public void setParameters(Object... parameters) {
		accessRecord = ((AccessRecord)parameters[0]);
		contributorService = ((ContributorService)parameters[2]);

	}
	
	@Override
	public Control createUIControl(Composite parent) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		viewAccessControlFragment = new ViewAccessControlFragment(accessRecord, true, this, contributorService);
		parentComposite = (Composite) viewAccessControlFragment.createUIControl(parent);
		
		RetroalimentationUtils.showInformationMessage(viewAccessControlFragment.getMsg(), MessageUtil
				.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
		return parentComposite;
	}
	
	@Override
	public String getID() {
		return "viewAccessControl";
	}
	
	@Override
	public void l10n() {
		viewAccessControlFragment.l10n();
		Auxiliary.refresh(parentComposite);
	}
	
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().ACCESS);
	}

}
