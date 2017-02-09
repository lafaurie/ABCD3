package cu.uci.abcd.opac.ui.contribution;

import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class RecentAcquisitions extends ContributorPage {

	private LoginService loginService;
	private Composite result;
	private Label libraryLB;
	private Combo libraryCB;
	private CRUDTreeTable recentLoanObjects;
	private Link countRecentLoanObjects;
	private int count;

	static String orderByString = "title";
	static int direction = 1024;

	@Override
	public Control createUIControl(final Composite parent) {

		loginService = SecurityUtils.getService();

		result = new Composite(parent, 0);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		result.setLayout(new FormLayout());

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);

		FormDatas.attach(result).atLeft(0).atRight(0).atTop(0).atBottom(0);

		libraryLB = new Label(result, SWT.NORMAL);
		FormDatas.attach(libraryLB).atLeft(20).atTop(20);

		libraryCB = new Combo(result, SWT.READ_ONLY);
		FormDatas.attach(libraryCB).atLeftTo(libraryLB, 10).atTop(15).withHeight(25);

		countRecentLoanObjects = new Link(result, SWT.NORMAL);
		countRecentLoanObjects.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(countRecentLoanObjects).atTopTo(libraryCB, 15).atLeft(30);

		// //// My table lists Selection////////

		recentLoanObjects = new CRUDTreeTable(result, SWT.NONE);
		recentLoanObjects.setEntityClass(LoanObject.class);

		TreeTableColumn recentLoanObjectColumns[] = { new TreeTableColumn(40, 0, "getTitle"), new TreeTableColumn(40, 1, "getAuthor"), new TreeTableColumn(20, 2, "getRegistrationDate") };

		recentLoanObjects.createTable(recentLoanObjectColumns);
		recentLoanObjects.setPageSize(10);
		FormDatas.attach(recentLoanObjects).atTopTo(countRecentLoanObjects).atRight(5).atLeft(5);

		recentLoanObjects.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				searchRecentAdquisition(((Library) UiUtils.getSelected(libraryCB)).getLibraryID(), event.currentPage - 1, event.pageSize);
				refresh();
			}
		});

		recentLoanObjects.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		libraryCB.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent event) {

				try {

					searchRecentAdquisition(((Library) UiUtils.getSelected(libraryCB)).getLibraryID(), 0, recentLoanObjects.getPaginator().getPageSize());
					recentLoanObjects.getPaginator().goToFirstPage();
					refresh();
					l10n();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		parent.notifyListeners(SWT.Resize, new Event());
		loadLibraries();
		l10n();
		return parent;
	}

	private void searchRecentAdquisition(Long libraryId, int page, int size) {

		recentLoanObjects.clearRows();
		List<LoanObject> list = ((AllManagementOpacViewController) controller).findAllRecentAcquisitions(libraryId);
		recentLoanObjects.setTotalElements((int) list.size());
		count = list.size();
		if (list.size() <= page * size + size) {
			recentLoanObjects.setRows(list.subList(page * size, list.size()));
		} else {
			recentLoanObjects.setRows(list.subList(page * size, page * size + size));
		}
		recentLoanObjects.refresh();
		l10n();

	}

	@Override
	public String getID() {
		return "RecentAcquisitionsID";
	}

	@Override
	public void l10n() {
		libraryLB.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIBRARYS));
		if (libraryCB.getSelectionIndex() != 0)
			countRecentLoanObjects.setText((MessageUtil.unescape(AbosMessages.get().RECENT_ACQUISITIONS) + " (" + " " + count + " " + MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_LIST + " )")));
		recentLoanObjects.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_TITLE), MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_AUTHOR), MessageUtil.unescape(AbosMessages.get().REGISTRATION_DATE)));

		refresh();
	}

	private void loadLibraries() {
		UiUtils.initialize(libraryCB, loginService.loadParams(), "- Biblioteca -");
		if (RWT.getSettingStore().getAttribute("library") != null) {
			libraryCB.select(Integer.parseInt(RWT.getSettingStore().getAttribute("library")));
		}
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_RECENT_ACQUISITIONS);
	}

}
