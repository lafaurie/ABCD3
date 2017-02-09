package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.contribution.Circulation;
import cu.uci.abcd.opac.ui.contribution.MainContent;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.util.MessageUtil;

public class CirculationDataListener implements Listener {

	private static final long serialVersionUID = 1L;

	private ServiceProvider serviceProvider;
	private RecordIsis record;
	private ViewController controller;
	private User user;
	private LoginService login;
	private String controlNumber;
	private Circulation circulation;
	private MainContent mainContent;

	public CirculationDataListener(ServiceProvider serviceProvider, RecordIsis record, MainContent mainContent) {
		this.serviceProvider = serviceProvider;
		this.record = record;
		this.mainContent = mainContent;
	}

	@Override
	public void handleEvent(Event arg0) {

		if (SecurityUtils.getService() != null)
			login = SecurityUtils.getService();

		if (login.isLoggedIn()) {

			try {

				user = (User) login.getPrincipal().getByKey("user");

				final ContributorService pageService = serviceProvider.get(ContributorService.class);

				circulation = (Circulation) ((OpacContributorServiceImpl) pageService).getContributorMap().get("CirculationID");
				controller = circulation.getController();
				circulation.setRecord(record);

				try {

					controlNumber = record.getRecord().getField(1).getStringFieldValue();

				} catch (Exception e) {
					e.printStackTrace();
				}
     
				if (user.getPerson() != null) {

					circulation.setLoanUser(((AllManagementOpacViewController) controller).findLoanUserByPersonIdAndIdLibrary(user.getPerson().getPersonID(), user.getLibrary().getLibraryID()));
					circulation.setLoanObject(((AllManagementOpacViewController) controller).findAvailableLoanObjectByControlNumberAndLibrary(controlNumber, record.getDataBaseName(), user.getLibrary().getLibraryID()));

					if (circulation.getLoanUser() != null)
						if (!circulation.getLoanObject().isEmpty())
							pageService.selectContributor("CirculationID");
						else
							mainContent.showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_LOAN_OBJECT_NOT_AVAILABLE));
					else
						mainContent.showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_NEED_BE_LOAN_USER));

				} else    
					mainContent.showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_NEED_BE_LOAN_USER));

			} catch (Exception e) {
				mainContent.showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_START_RESERVATION));
			}

		} else
			mainContent.showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_NEED_BE_AUTHENTICATED));
	}
}
