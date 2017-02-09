package cu.uci.abos.core.widget.grid.listener;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.widgets.Display;

import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;

public class DeleteColumnListener implements TreeColumnListener {
	private final IActionCommand command;

	public DeleteColumnListener(IActionCommand command) {
		this.command = command;
	}

	public void handleEvent(final TreeColumnEvent event) {
		event.performDelete = true;

		MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(AbosMessages.get().MSG_WARN_DELET_DATA),
				new DialogCallback() {

					private static final long serialVersionUID = 8415736231132589115L;

					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {
							try {
								command.execute(event);
								event.item.dispose();
								if (event.source.getClass().equals(SecurityCRUDTreeTable.class)||event.source.getClass().equals(CRUDTreeTable.class)) {
									CRUDTreeTable table = (CRUDTreeTable) event.source;
									if (table.getPaginator().getCurrentPage() > table.getPaginator().getTotalPages()) {
										table.getPaginator().goToLastPage();
									} else {
										table.getPaginator().goToPage(table.getPaginator().getCurrentPage());
									}
									event.source.refresh();
									
									RetroalimentationUtils.showInformationShellMessage(AbosMessages.get().MESSAGE_DELETE);
								}else{ 
								event.source.refresh();
								RetroalimentationUtils.showInformationMessage((AbosMessages.get().MESSAGE_DELETE));
								}
							} catch (Exception e) {
								RetroalimentationUtils.showInformationMessage((AbosMessages.get().MESSAGE_ERROR_USED_DATA));
							}
						}
					}
				});
	}
}