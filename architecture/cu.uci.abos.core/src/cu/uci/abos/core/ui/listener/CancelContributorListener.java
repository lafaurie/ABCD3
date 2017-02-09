package cu.uci.abos.core.ui.listener;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;

public class CancelContributorListener extends SelectionAdapter{

	private static final long serialVersionUID = 2892153490171814546L;
	
	private ContributorPage page ;
	
	public CancelContributorListener(ContributorPage page) {
		super();
		this.page = page;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {				
		MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(AbosMessages.get().MSG_WARN_CANCEL_OPERATION),
				new DialogCallback() {
		private static final long serialVersionUID = 1L;
			@Override
			public void dialogClosed(int returnCode) {
				if (returnCode == 0) {
					try {
						RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_INF_CANCEL_ACTION);
						ContributorService contributorService = page.getContributorService();
						page.notifyListeners(SWT.Dispose, new Event()); 
						contributorService.selectContributor(page.getID());
					} catch (Exception e2) {
						RetroalimentationUtils.showInformationMessage(AbosMessages.get().MESSAGE_ERROR_USED_DATA);
					}							
				}						
			}					
		} );	
	}
}