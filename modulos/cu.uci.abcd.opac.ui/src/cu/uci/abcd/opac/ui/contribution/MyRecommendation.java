package cu.uci.abcd.opac.ui.contribution;

import java.util.Arrays;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.opac.Recommendation;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class MyRecommendation extends ContributorPage {

	private User user;
	private Composite result;

	private CRUDTreeTable myRecommendationTable;

	static String orderByString = "destinationUser";
	static int direction = 1024;

	@Override
	public Control createUIControl(Composite parent) {
		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		result = parent;

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);
  
		myRecommendationTable = new CRUDTreeTable(result, SWT.NONE);
		myRecommendationTable.setEntityClass(Recommendation.class);
		myRecommendationTable.setDelete(true);

		TreeTableColumn recommendationColumns[] = { new TreeTableColumn(40, 0, "getDuser.getUsername"), new TreeTableColumn(60, 1, "getTitle") };

		myRecommendationTable.createTable(recommendationColumns);
		myRecommendationTable.setPageSize(10);
		FormDatas.attach(myRecommendationTable).atTop(10).atRight(5).atLeft(5);

		myRecommendationTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				searchMyRecommendation(event.currentPage - 1, event.pageSize);
				refresh();
			}
		});

		myRecommendationTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		try {
			searchMyRecommendation(0, myRecommendationTable.getPageSize());
			myRecommendationTable.getPaginator().goToFirstPage();
			myRecommendationTable.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}

		myRecommendationTable.addDeleteListener(new TreeColumnListener() {
			@Override
			public void handleEvent(final TreeColumnEvent event) {
				try {

					final Recommendation recommendation = (Recommendation) event.entity.getRow();

					DialogCallback callback = new DialogCallback() {
						private static final long serialVersionUID = 1L;
     
						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {

								((AllManagementOpacViewController) controller).deleteRecommendation(recommendation.getId());
								searchMyRecommendation(0, myRecommendationTable.getPaginator().getPageSize());
								showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM));
								searchMyRecommendation(0, myRecommendationTable.getPaginator().getPageSize());
								myRecommendationTable.getPaginator().goToFirstPage();
								refresh();

							}
						}
					};

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_WARNING), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), callback);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		l10n();
		return result;

	}

	private void searchMyRecommendation(int page, int size) {

		myRecommendationTable.clearRows();
		Page<Recommendation> list = ((AllManagementOpacViewController) controller).findAllRecommendationByUserAndLibrary(user.getUserID(), user.getLibrary().getLibraryID(), page, size, direction, orderByString);
		myRecommendationTable.getPaginator().setTotalElements((int) list.getTotalElements());
		myRecommendationTable.setRows(list.getContent());
		myRecommendationTable.refresh();
	}

	@Override
	public String getID() {
		return "MyRecomendationID";
	}

	@Override
	public void l10n() {
		myRecommendationTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().USER_RECOMMENDATION), MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_TITLE)));

		refresh();

	}

	@Override
	public boolean canClose() {
		return true;
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_RECOMMENDATION);
	}
}
