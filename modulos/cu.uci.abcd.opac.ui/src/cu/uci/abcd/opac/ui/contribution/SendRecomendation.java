package cu.uci.abcd.opac.ui.contribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.opac.Recommendation;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class SendRecomendation extends ContributorPage {

	private Composite result;
	private User user;
	private RecordIsis record;
	private Button sendBtn;
	private Button emptyListBtn;
	private Label userNameLb;
	private String userName;

	private CRUDTreeTable userTable;
	private CRUDTreeTable userTableForSend;

	private ArrayList<User> userSendList = new ArrayList<User>();

	static String orderUserByUserName = "username";
	static int direction = 1024;

	@Override
	public Control createUIControl(Composite parent) {

		result = parent;

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
			userSendList.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Label point = new Label(result, SWT.NORMAL);
		FormDatas.attach(point).atLeft(100);

		final Text userNameTxt = new Text(result, SWT.NORMAL);
		FormDatas.attach(userNameTxt).atTop(20).atLeftTo(point, 5).withWidth(200).withHeight(10);

		userNameLb = new Label(result, SWT.NORMAL);
		FormDatas.attach(userNameLb).atTop(25).atRightTo(userNameTxt, 10);

		Image addUserImage = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("right-arrow"));

		Column addUserColumn = new Column(addUserImage, new TreeColumnListener() {

			@Override
			public void handleEvent(TreeColumnEvent event) {
				try {

					User user = ((User) event.entity.getRow());

					for (int i = 0; i < userSendList.size(); i++)
						if (user.getUserID() == userSendList.get(i).getUserID())
							throw new Exception();

					userSendList.add(user);

					userTableForSend.clearRows();
					userTableForSend.setTotalElements(userSendList.size());
					userTableForSend.setRows(userSendList);
					userTableForSend.refresh();

					userTableForSend.getPaginator().goToFirstPage();

				} catch (Exception e) {
					MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_ERROR), "Este usuario ya está en su lista de envío.", null);
				}

			}
		});

		addUserColumn.setToolTipText("Vincular");

		userTable = new CRUDTreeTable(result, SWT.NONE);
		userTable.setEntityClass(User.class);
		userTable.addActionColumn(addUserColumn);

		TreeTableColumn userColumns[] = { new TreeTableColumn(100, 0, "getUsername") };

		userTable.createTable(userColumns);
		userTable.setPageSize(10);
		FormDatas.attach(userTable).atTopTo(userNameTxt, 20).atLeft(5).withWidth(350);

		userTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				searchUserFilter(event.currentPage - 1, event.pageSize);
			}
		});

		userTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		Image eraseImage = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("trash"));

		emptyListBtn = new Button(result, SWT.PUSH);
		emptyListBtn.setImage(eraseImage);
		emptyListBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(emptyListBtn).atTop(15).atRight(100);

		Image sendImage = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-send"));

		sendBtn = new Button(result, SWT.PUSH);
		sendBtn.setImage(sendImage);
		sendBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(sendBtn).atTop(15).atRightTo(emptyListBtn);

		userTableForSend = new CRUDTreeTable(result, SWT.NONE);
		userTableForSend.setEntityClass(User.class);
		userTableForSend.setDelete(true);

		TreeTableColumn userForSendColumns[] = { new TreeTableColumn(100, 0, "getUsername") };

		userTableForSend.createTable(userForSendColumns);
		userTableForSend.setPageSize(10);
		FormDatas.attach(userTableForSend).atTopTo(userNameTxt, 20).atLeftTo(userTable, 20).atRight(5);

		userTableForSend.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();

			}
		});

		userTableForSend.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				createTableForSend(event.currentPage - 1, event.pageSize);
				refresh();
			}
		});

		userTableForSend.addDeleteListener(new TreeColumnListener() {
			@Override
			public void handleEvent(final TreeColumnEvent event) {

				userTableForSend.clearRows();

				userSendList.remove(event.entity.getRow());

				userTableForSend.setTotalElements(userSendList.size());
				userTableForSend.setRows(userSendList);
				userTableForSend.refresh();
				userTableForSend.getPaginator().goToFirstPage();

			}

		});

		sendBtn.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {

					if (!userSendList.isEmpty()) {

						Recommendation recommendation;

						for (User currentUser : userSendList) {

							recommendation = new Recommendation();
							recommendation.setDuser(user);
							recommendation.setMaterial(record.getControlNumber());
							recommendation.setTitle(record.getTitle());
							recommendation.setLibrary(user.getLibrary());
							recommendation.setActionDate(new java.sql.Date(new Date().getTime()));
							recommendation.setDestinationUser(currentUser);

							((AllManagementOpacViewController) controller).addRecommendation(recommendation);

						}

						showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT);

					} else
						showErrorMessage(AbosMessages.get().MSG_ERROR_AT_LEAST_ONE_USER_SELECTED);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		emptyListBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				userSendList = new ArrayList<User>();

				userTableForSend.clearRows();
				userTableForSend.setTotalElements(userSendList.size());
				userTableForSend.setRows(userSendList);
				userTableForSend.refresh();
				userTableForSend.getPaginator().goToFirstPage();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		try {
			searchUserFilter(0, userTable.getPaginator().getPageSize());
			userTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		userNameTxt.addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {

				userName = userNameTxt.getText();
				searchUserFilter(0, userTable.getPaginator().getPageSize());
				userTable.getPaginator().goToFirstPage();
				refresh();

			}
		});

		l10n();
		return result;
	}

	private void searchUserFilter(int page, int size) {

		userTable.clearRows();
		Page<User> list;

		if (userName != null)
			list = ((AllManagementOpacViewController) controller).findAllUsersByLibraryAndUserName(user.getUserID(), user.getLibrary().getLibraryID(), userName, page, size, direction, orderUserByUserName);
		else
			list = ((AllManagementOpacViewController) controller).findAllUsersByLibrary(user.getUserID(), user.getLibrary().getLibraryID(), page, size, direction, orderUserByUserName);

		userTable.getPaginator().setTotalElements((int) list.getTotalElements());
		userTable.setRows(list.getContent());
		userTable.refresh();
	}

	@Override
	public String getID() {
		return "sendRecommendationID";
	}

	@Override
	public void l10n() {
		userNameLb.setText(MessageUtil.unescape(AbosMessages.get().USER_RECOMMENDATION));
		userTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().USER_LIBRARY)));
		emptyListBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_EMPTY_LIST));
		sendBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEND));
		userTableForSend.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().USER_SEND_RECOMMENDATION)));
		refresh();

	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().RECOMMEND);
	}

	public RecordIsis getRecord() {
		return record;
	}

	public void setRecord(RecordIsis record) {
		this.record = record;
	}

	private void createTableForSend(int page, int size) {
		userTableForSend.setTotalElements((int) userSendList.size());
		if (userSendList.size() <= page * size + size) {
			userTableForSend.setRows(userSendList.subList(page * size, userSendList.size()));
		} else {
			userTableForSend.setRows(userSendList.subList(page * size, page * size + size));
		}
		userTableForSend.refresh();
	}

}
