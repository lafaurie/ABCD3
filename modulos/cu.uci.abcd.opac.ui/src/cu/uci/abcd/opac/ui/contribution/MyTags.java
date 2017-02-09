package cu.uci.abcd.opac.ui.contribution;

import java.util.Arrays;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.opac.Tag;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abos.api.util.ServiceProvider;
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

public class MyTags extends ContributorPage {

	private Composite result;
	private User user;

	Composite myTags;
	Composite otherUsersTags;

	CRUDTreeTable myTagsTable;
	CRUDTreeTable otherUserTagsTable;

	Page<Tag> list;
	static String orderByString = "tagName";
	static int direction = 1024;

	long[] tempMnf = new long[1];
	String title;
	private TabItem tabItemTransactions;
	private TabItem tabItemPenaltys;

	public MyTags(ServiceProvider service) {
		// this.serviceProvider = service;
	}	

	@Override
	public Control createUIControl(Composite parent) {
		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);
				
		result = new Composite(parent, 0);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		result.setLayout(new FormLayout());		
		
		FormDatas.attach(result).atLeft(0).atRight(0).atTop(0).atBottom(0);

		TabFolder tabFolder = new TabFolder(result, SWT.NONE);
		FormDatas.attach(tabFolder).atTop(15).atLeft().atRight().atBottom();

		myTags = new Composite(tabFolder, SWT.V_SCROLL);
		myTags.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		myTags.setLayout(new FormLayout());

		otherUsersTags = new Composite(tabFolder, SWT.V_SCROLL);
		otherUsersTags.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		otherUsersTags.setLayout(new FormLayout());

		tabItemTransactions = new TabItem(tabFolder, SWT.None);
		tabItemTransactions.setControl(myTags);

		tabItemPenaltys = new TabItem(tabFolder, SWT.None);
		tabItemPenaltys.setControl(otherUsersTags);
         
		// My table lists Selection \\
    
		myTagsTable = new CRUDTreeTable(myTags, SWT.NONE);
		myTagsTable.setEntityClass(Tag.class);
		myTagsTable.setDelete(true);
		TreeTableColumn tagsColumns[] = { new TreeTableColumn(30, 0, "getTagName"), new TreeTableColumn(45, 1, "getTitle"), new TreeTableColumn(25, 2, "getActionDate") };

		myTagsTable.createTable(tagsColumns);
		myTagsTable.setPageSize(10);
		FormDatas.attach(myTagsTable).atTop(15).atRight(5).atLeft(5);

		otherUserTagsTable = new CRUDTreeTable(otherUsersTags, SWT.NONE);
		otherUserTagsTable.setEntityClass(Tag.class);
		TreeTableColumn othersTagsColumns[] = { new TreeTableColumn(30, 0, "getTagName"), new TreeTableColumn(45, 1, "getTitle"), new TreeTableColumn(25, 2, "getActionDate") };

		otherUserTagsTable.createTable(othersTagsColumns);
		otherUserTagsTable.setPageSize(10);
		FormDatas.attach(otherUserTagsTable).atTop(15).atRight(5).atLeft(5);

		otherUserTagsTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				
				searchOtherUsersTags(event.currentPage - 1, event.pageSize);
				refresh();
			}
		});

		myTagsTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				
				searchMyTags(event.currentPage - 1, event.pageSize);
				refresh();
			}
		});
		
		otherUserTagsTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});
		
		myTagsTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		myTagsTable.addDeleteListener(new TreeColumnListener() {
			@Override
			public void handleEvent(final TreeColumnEvent event) {
				try {

					final Tag tag = (Tag) event.entity.getRow();

					DialogCallback callback = new DialogCallback() {
						private static final long serialVersionUID = 1L;

						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {

								((AllManagementOpacViewController) controller).deleteTag(tag.getId());
								searchMyTags(0, myTagsTable.getPaginator().getPageSize());
								myTagsTable.getPaginator().goToFirstPage();

								MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION),
										MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM), null);
							}
						}
					};

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_WARNING),
							MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), callback);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {
			searchMyTags(0, myTagsTable.getPaginator().getPageSize());
			myTagsTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			searchOtherUsersTags(0, otherUserTagsTable.getPaginator().getPageSize());
			otherUserTagsTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
       
		l10n();
		return result;
	}

	private void searchMyTags(int page, int size) {
            
		myTagsTable.clearRows();
		Page<Tag> list = ((AllManagementOpacViewController) controller).findAllTagsByUserAndLibrary(user.getUserID(), user.getLibrary().getLibraryID(), page, size, direction, orderByString);
		myTagsTable.getPaginator().setTotalElements((int) list.getTotalElements());
		myTagsTable.setRows(list.getContent());
		myTagsTable.refresh();
	}

	private void searchOtherUsersTags(int page, int size) {

		otherUserTagsTable.clearRows();
		Page<Tag> list = ((AllManagementOpacViewController) controller).searchTagsByLibrary(user.getUserID(), user.getLibrary().getLibraryID(), page, size, direction, orderByString);
		otherUserTagsTable.getPaginator().setTotalElements((int) list.getTotalElements());
		otherUserTagsTable.setRows(list.getContent());
		otherUserTagsTable.refresh();
	}
   
	@Override
	public String getID() {
		return "MisEtiquetasID";
	}   
    
	@Override
	public void l10n() {
		tabItemTransactions.setText(MessageUtil.unescape(AbosMessages.get().MY_TAGS));
		tabItemPenaltys.setText(MessageUtil.unescape(AbosMessages.get().TAGS_OTHER_USER));
		myTagsTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TERM),
				(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_TITLE)), (MessageUtil.unescape(AbosMessages.get().CREATION_DATE))));
		otherUserTagsTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TERM),
				(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_TITLE)), (MessageUtil.unescape(AbosMessages.get().CREATION_DATE))));
		refresh();
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_MY_TAGS);
	}

}
