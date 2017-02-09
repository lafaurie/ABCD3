package cu.uci.abcd.opac.ui.model;

import cu.uci.abcd.domain.opac.SelectionList;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.contribution.MySelectionListContent;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;

public class SelectionListViewDetails implements TreeColumnListener {

	ServiceProvider serviceProvider;

	public SelectionListViewDetails(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
     
	@Override
	public void handleEvent(TreeColumnEvent event) {

		final ContributorService pageService = serviceProvider.get(ContributorService.class);

		MySelectionListContent selectionListContent = (MySelectionListContent) ((OpacContributorServiceImpl) pageService).getContributorMap().get("SelectionListContentID");

		SelectionList selectionList = event.entity.getRow();
		if (!selectionList.getSelectionListData().isEmpty()) {

			selectionListContent.setSelectionList(selectionList);

			try {

				pageService.selectContributor("SelectionListContentID");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else
			selectionListContent.showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_INF_SELECTION_LIST_EMPTY));
	}

}