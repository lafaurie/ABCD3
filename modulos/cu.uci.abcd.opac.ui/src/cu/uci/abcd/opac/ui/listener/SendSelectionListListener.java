package cu.uci.abcd.opac.ui.listener;

import java.util.ArrayList;
import java.util.List;
   
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.opac.SelectionList;
import cu.uci.abcd.domain.opac.SelectionListData;
import cu.uci.abcd.opac.IsisSelectionListContent;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.contribution.SendEmail;
import cu.uci.abcd.opac.ui.controller.SelectionListViewController;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;

public class SendSelectionListListener implements TreeColumnListener {
	ServiceProvider serviceProvider;
	ViewController controller;
	User user;

	IsisSelectionListContent isisSelectionList;

	List<IsisSelectionListContent> isisSelectionListContent;

	List<RecordIsis> recordIsis;

	List<SelectionListData> selectionListData;

	List<String> controlNumber;
	List<String> isisHome;
	List<String> databaseName;

	String currentIsisHome = "";
	String currentDataBaseName = "";

	public SendSelectionListListener(ServiceProvider serviceProvider, ViewController controller, User user) {
		this.serviceProvider = serviceProvider;
		this.controller = controller;
		this.user = user;
	}   

	@Override
	public void handleEvent(TreeColumnEvent event) {

		final ContributorService pageService = serviceProvider.get(ContributorService.class);
		SendEmail sendEmail = (SendEmail) ((OpacContributorServiceImpl) pageService).getContributorMap().get("sendEmailID");

		SelectionList selectionList = (SelectionList) event.entity.getRow();

		selectionListData = new ArrayList<SelectionListData>();
		controlNumber = new ArrayList<String>();
		isisHome = new ArrayList<String>();
		databaseName = new ArrayList<String>();
		recordIsis = new ArrayList<RecordIsis>();

		isisSelectionListContent = new ArrayList<IsisSelectionListContent>();

		selectionListData.addAll(selectionList.getSelectionListData());

		if (!selectionListData.isEmpty()) {

			for (int i = 0; i < selectionListData.size(); i++) {

				currentIsisHome = selectionListData.get(i).getIsisHome();
				currentDataBaseName = selectionListData.get(i).getIsisdatabasename();

				for (int j = i; j < selectionListData.size(); j++)
					if (currentDataBaseName.equals(selectionListData.get(j).getIsisdatabasename()) && currentIsisHome.equals(selectionListData.get(j).getIsisHome())) {

						controlNumber.add(selectionListData.get(j).getIsisRecordID());
						selectionListData.remove(j);
						j--;
					}

				isisSelectionList = new IsisSelectionListContent(controlNumber, currentDataBaseName, currentIsisHome);
				isisSelectionListContent.add(isisSelectionList);

				controlNumber = new ArrayList<String>();
			}

			try {

				for (int i = 0; i < isisSelectionListContent.size(); i++) {

					recordIsis.addAll(((SelectionListViewController) controller).findRecordByControlNumber(isisSelectionListContent.get(i).getControlNumber(), isisSelectionListContent.get(i).getDataBaseName(), isisSelectionListContent.get(i).getIsisHome()));

				}

			} catch (JisisDatabaseException e1) {
				e1.printStackTrace();
			}

			sendEmail.setMySelectedRecord(recordIsis);
			pageService.selectContributor("sendEmailID");

		} else
			sendEmail.showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_INF_SELECTION_LIST_EMPTY));

	}

}
