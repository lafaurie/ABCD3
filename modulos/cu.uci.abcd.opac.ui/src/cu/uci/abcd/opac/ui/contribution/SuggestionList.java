package cu.uci.abcd.opac.ui.contribution;

import java.util.Arrays;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.controller.SuggestionViewController;
import cu.uci.abcd.opac.ui.model.SuggestionUpdateArea;
import cu.uci.abcd.opac.ui.model.SuggestionViewArea;
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

public class SuggestionList extends ContributorPage {

	private User user;

	private TabItem tabItemSuggestion;
	private TabItem tabItemSuggestionPending;
	private Composite suggestionsCompo;
	private Composite suggestionsPendingCompo;
	private CRUDTreeTable suggestionTable;
	private CRUDTreeTable suggestionPendingTable;

	private Link countSuggestions;
	private Link countSuggestionPending;

	static String orderSuggestionByString = "title";
	static String orderBySuggestonPendingString = "title";
	static int direction = 1024;

	@Override
	public Control createUIControl(final Composite parent) {

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withHeight(Display.getCurrent().getBounds().height - 140);

		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		FormDatas.attach(tabFolder).atTop(5).atLeft().atRight().atBottom();

		suggestionsCompo = new Composite(tabFolder, SWT.V_SCROLL);
		suggestionsCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		suggestionsCompo.setLayout(new FormLayout());

		suggestionsPendingCompo = new Composite(tabFolder, SWT.V_SCROLL);
		suggestionsPendingCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		suggestionsPendingCompo.setLayout(new FormLayout());

		tabItemSuggestion = new TabItem(tabFolder, SWT.None);
		tabItemSuggestion.setControl(suggestionsCompo);

		tabItemSuggestionPending = new TabItem(tabFolder, SWT.None);
		tabItemSuggestionPending.setControl(suggestionsPendingCompo);

		countSuggestions = new Link(suggestionsCompo, SWT.NORMAL);
		countSuggestions.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(countSuggestions).atTop(10).atLeft(30);

		suggestionTable = new CRUDTreeTable(suggestionsCompo, SWT.NONE);
		suggestionTable.setEntityClass(SuggestionList.class);
		suggestionTable.setWatch(true, new SuggestionViewArea());

		TreeTableColumn suggestionColumns[] = { new TreeTableColumn(20, 0, "getTitle"), new TreeTableColumn(20, 1, "getAuthor"), new TreeTableColumn(20, 2, "getPublicationDate"), new TreeTableColumn(40, 3, "getState.getNomenclatorName") };

		suggestionTable.createTable(suggestionColumns);
		suggestionTable.setPageSize(10);
		FormDatas.attach(suggestionTable).atTopTo(countSuggestions).atRight(5).atLeft(5);
    
		suggestionTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				searchSuggestions(event.currentPage - 1, event.pageSize);
				refresh();
			}
		});
		    
		suggestionTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		countSuggestionPending = new Link(suggestionsPendingCompo, SWT.NORMAL);
		countSuggestionPending.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(countSuggestionPending).atTop(10).atLeft(30);

		suggestionPendingTable = new CRUDTreeTable(suggestionsPendingCompo, SWT.NONE);
		suggestionPendingTable.setEntityClass(SuggestionList.class);
		suggestionPendingTable.setUpdate(true, new SuggestionUpdateArea(controller, suggestionPendingTable));
		suggestionPendingTable.setWatch(true, new SuggestionViewArea());
		suggestionPendingTable.setDelete(true);

		TreeTableColumn suggestionPendingColumns[] = { new TreeTableColumn(20, 0, "getTitle"), new TreeTableColumn(20, 1, "getAuthor"), new TreeTableColumn(20, 2, "getPublicationDate"), new TreeTableColumn(40, 3, "getState.getNomenclatorName") };

		suggestionPendingTable.createTable(suggestionPendingColumns);
		suggestionPendingTable.setPageSize(10);

		FormDatas.attach(suggestionPendingTable).atTopTo(countSuggestionPending).atRight(5).atLeft(5);

		suggestionPendingTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				searchSuggestionsPending(event.currentPage - 1, event.pageSize);
				refresh();
			}
		});

		suggestionPendingTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();

			}
		});    

		suggestionPendingTable.addDeleteListener(new TreeColumnListener() {
			@Override
			public void handleEvent(final TreeColumnEvent event) {
				try {
					final Long idSuggestion = ((Suggestion) event.entity.getRow()).getSuggestionID();

					DialogCallback callback = new DialogCallback() {
						private static final long serialVersionUID = 1L;

						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {

								((SuggestionViewController) controller).deleteSuggestion(idSuggestion);
								searchSuggestionsPending(0, suggestionPendingTable.getPaginator().getPageSize());
								showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM));
								suggestionPendingTable.getPaginator().goToFirstPage();     
								
							}
						}
					};

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_WARNING), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), callback);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		createSuggestionTable();
		createSuggestionPendingTable();

		l10n();
		return parent;
	}

	private void searchSuggestions(int page, int size) {

		suggestionTable.clearRows();
		Page<Suggestion> list = ((SuggestionViewController) controller).findAllSuggestionByUserAndLibrary(user.getUserID(), user.getLibrary().getLibraryID(), page, size, direction, orderSuggestionByString);
		suggestionTable.setTotalElements((int) list.getTotalElements());
		countSuggestions.setText((MessageUtil.unescape(AbosMessages.get().TAB_ITEM_SUGGESTION) + " (" + " " + ((SuggestionViewController) controller).countSuggestionByUserAndLibrary(user.getUserID(), user.getLibrary().getLibraryID()) + " " + MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_LIST
				+ " )")));
		suggestionTable.setRows(list.getContent());
		suggestionTable.refresh();
	}

	private void searchSuggestionsPending(int page, int size) {

		suggestionPendingTable.clearRows();
		Page<Suggestion> list = ((SuggestionViewController) controller).findAllSuggestionPendingByUserAndLibrary(user.getUserID(), user.getLibrary().getLibraryID(), page, size, direction, orderBySuggestonPendingString);
		suggestionPendingTable.getPaginator().setTotalElements((int) list.getTotalElements());
		countSuggestionPending.setText((MessageUtil.unescape(AbosMessages.get().TAB_ITEM_SUGGESTION_PENDING) + " (" + " " + ((SuggestionViewController) controller).countSuggestionPendingByUserAndLibrary(user.getUserID(), user.getLibrary().getLibraryID()) + " " + MessageUtil.unescape(AbosMessages
				.get().LABEL_TOTAL_LIST + " )")));
		suggestionPendingTable.setRows(list.getContent());
		suggestionPendingTable.refresh();
	}

	@Override
	public String getID() {
		return "listViewSugerenciaID";
	}

	@Override
	public void l10n() {
		suggestionTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_TITLE), MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_AUTHOR), MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_PUBLICATION_DATE),
				MessageUtil.unescape(AbosMessages.get().LABEL_HISTORICAL_STATE_FILTER)));
		suggestionPendingTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_TITLE), MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_AUTHOR), MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_PUBLICATION_DATE),
				MessageUtil.unescape(AbosMessages.get().LABEL_HISTORICAL_STATE_FILTER)));
		suggestionTable.setCancelButtonText(AbosMessages.get().CANCEL);
		suggestionPendingTable.setCancelButtonText(AbosMessages.get().CANCEL);
		tabItemSuggestion.setText(MessageUtil.unescape(AbosMessages.get().TAB_ITEM_SUGGESTION));
		tabItemSuggestionPending.setText(MessageUtil.unescape(AbosMessages.get().TAB_ITEM_SUGGESTION_PENDING));

		refresh();

	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_MY_SUGGESTIONS);
	}

	private void createSuggestionTable() {
		try {
			searchSuggestions(0, suggestionTable.getPaginator().getPageSize());
			suggestionTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createSuggestionPendingTable() {
		try {
			searchSuggestionsPending(0, suggestionPendingTable.getPaginator().getPageSize());
			suggestionPendingTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}