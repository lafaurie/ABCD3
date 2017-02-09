package cu.uci.abcd.administration.library.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.administration.library.communFragment.ViewWorkerFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ViewWorker extends ContributorPage implements Contributor{

	private Worker worker;
	private ViewWorkerFragment viewWorkerFragment;
	private Composite parentComposite;
	private ContributorService contributorService;
	
	@Override
	public void setParameters(Object... parameters) {
		worker = ((Worker)parameters[0]);
		contributorService = ((ContributorService)parameters[2]);

	}
	
	@Override
	public Control createUIControl(Composite parent) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		viewWorkerFragment = new ViewWorkerFragment(worker, true, this, contributorService);
		parentComposite = (Composite) viewWorkerFragment.createUIControl(parent);
		viewWorkerFragment.l10n();
		RetroalimentationUtils.showInformationMessage(viewWorkerFragment.getMsg(), MessageUtil
				.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
		
		return parentComposite;
	}
	
	@Override
	public String getID() {
		return "viewWorker";
	}
	
	@Override
	public void l10n() {
		viewWorkerFragment.l10n();
		Auxiliary.refresh(parentComposite);
	}
	
	@Override
	public String contributorName() {
		return AbosMessages.get().REGISTER_WORKER;
	}


}
