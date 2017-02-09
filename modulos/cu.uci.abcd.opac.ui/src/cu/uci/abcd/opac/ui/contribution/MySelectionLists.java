package cu.uci.abcd.opac.ui.contribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.opac.SelectionList;
import cu.uci.abcd.domain.opac.SelectionListData;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.controller.SelectionListViewController;
import cu.uci.abcd.opac.ui.listener.SendSelectionListListener;
import cu.uci.abcd.opac.ui.model.SelectionListUpdateArea;
import cu.uci.abcd.opac.ui.model.SelectionListViewDetails;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class MySelectionLists extends ContributorPage {

	private ServiceProvider service;
	private User user = null;

	public MySelectionLists(ServiceProvider service) {
		this.service = service;
	}

	private Combo selectionListCategoryCb;
	private Label listNameLabel;
	private Label TipoLista;
	private Button save;
	private Button cancel;
	private TabItem tabItemPublicList;
	private TabItem tabItemMyList;
	private Group agregar;
	private Button addLists;

	private Composite publicListCompo;
	private Composite myListCompo;
	private CRUDTreeTable myListsTable;
	private CRUDTreeTable publicListsTable;

	private Link countMySelectionList;
	private Link countSelectionList;

	private static String orderByString = "selectionListName";
	private static int direction = 1024;

	private Text selectionListName;
	private ValidatorUtils validator;
   
	// Copiar lista de selección
	private List<SelectionListData> selectionListDataForCoppy;

	@Override
	public Control createUIControl(final Composite parent) {

		addComposite(parent);

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);

		validator = new ValidatorUtils(new CustomControlDecoration());

		final TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		FormDatas.attach(tabFolder).atTop(5).atLeft().atRight().atBottom();

		publicListCompo = new Composite(tabFolder, SWT.V_SCROLL);
		publicListCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		publicListCompo.setLayout(new FormLayout());

		myListCompo = new Composite(tabFolder, SWT.V_SCROLL);
		myListCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		myListCompo.setLayout(new FormLayout());

		tabItemPublicList = new TabItem(tabFolder, SWT.None);
		tabItemPublicList.setControl(publicListCompo);

		tabItemMyList = new TabItem(tabFolder, SWT.None);
		tabItemMyList.setControl(myListCompo);

		// ////// Add List //////////////////

		agregar = new Group(parent, SWT.NONE);
		agregar.setBackground(parent.getBackground());
		agregar.setLayout(new FormLayout());

		FormDatas.attach(agregar).atTop(0).atLeft(10).atRight(10);

		listNameLabel = new Label(agregar, SWT.NORMAL);
		add(listNameLabel);

		selectionListName = new Text(agregar, SWT.NORMAL);
//		selectionListName.setTextLimit(20);
		add(selectionListName);
		validator.applyValidator(selectionListName, "selectionListName", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(selectionListName, "selectionListName1", DecoratorType.ALPHA_NUMERICS_SPACES, true, 20);

		br();

		TipoLista = new Label(agregar, SWT.NORMAL);
		add(TipoLista);

		selectionListCategoryCb = new Combo(agregar, SWT.READ_ONLY);
		selectionListCategoryCb.setLayoutData(new FormData());
		add(selectionListCategoryCb);
		initialize(selectionListCategoryCb, ((SelectionListViewController) controller).findAllNomencaltors(Nomenclator.CATEGORY_SELECTION_LIST));
		validator.applyValidator(selectionListCategoryCb, "selectionListCategoryCb", DecoratorType.REQUIRED_FIELD, true);

		br();

		cancel = new Button(agregar, SWT.PUSH);
		add(cancel);

		save = new Button(agregar, SWT.PUSH);
		add(save);

		agregar.setVisible(false);

		addLists = new Button(myListCompo, SWT.CONTROL);
		addLists.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(addLists).atTop(5).atLeft(20).withHeight(25).withWidth(90);

		countMySelectionList = new Link(myListCompo, SWT.NORMAL);
		countMySelectionList.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(countMySelectionList).atTopTo(addLists, 5).atLeft(30);

		Image sendImage = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-send"));
		Column sendColumn = new Column(sendImage, new SendSelectionListListener(service, controller, user));

		sendColumn.setToolTipText("Enviar");			
		    
		Image viewContentImage = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("list-alt"));
		Column viewContentColumn = new Column(viewContentImage, new SelectionListViewDetails(service));
  
		viewContentColumn.setToolTipText("Detalles");		
		
		// //// My table lists Selection////////

		myListsTable = new CRUDTreeTable(myListCompo, SWT.NONE);
		myListsTable.setEntityClass(SelectionList.class);
		myListsTable.addActionColumn(viewContentColumn);
		myListsTable.addActionColumn(sendColumn);
		myListsTable.setUpdate(true, new SelectionListUpdateArea(controller, myListsTable));
		myListsTable.setDelete(true);
		
		myListsTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		TreeTableColumn selectionListColumns[] = { new TreeTableColumn(65, 0, "getSelectionListName"), new TreeTableColumn(35, 1, "getCategory.getNomenclatorName"), };

		myListsTable.createTable(selectionListColumns);
		myListsTable.setPageSize(10);
		FormDatas.attach(myListsTable).atTopTo(countMySelectionList).atRight(5).atLeft(5);

		// //// Table lists of public selection ////////

		countSelectionList = new Link(publicListCompo, SWT.NORMAL);
		countSelectionList.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(countSelectionList).atTop(8).atLeft(30);
		
		Image coppyImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("copy"));
		Column coppyColumn = new Column(coppyImg, new TreeColumnListener() {

			@Override
			public void handleEvent(TreeColumnEvent event) {

				SelectionList entity = (SelectionList) event.entity.getRow();
				SelectionList selectionList = new SelectionList();
				selectionListDataForCoppy = new ArrayList<SelectionListData>();

				if (user == null) {
					showInformationMessage(MessageUtil.unescape(cu.uci.abcd.opac.l10n.AbosMessages.get().MSG_ERROR_NEED_BE_AUTHENTICATED));
					tabFolder.setSelection(0);
				} else {
					boolean selectionBoolean = ((SelectionListViewController) controller).findAllSelectionListsByName(entity.getSelectionListName(), user.getUserID().longValue());

					if (selectionBoolean == true) {
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_EXITS_COPY_LIST));
					} else {

						selectionList.setDuser(user);
						selectionList.setCategory(((SelectionListViewController) controller).findNomenclator(Nomenclator.CATEGORY_SELECTION_PRIVATE));
						selectionList.setOrderBy(entity.getOrderBy());
						selectionList.setActionDate(entity.getActionDate());
						selectionList.setSelectionListName(entity.getSelectionListName());
						selectionList.setLibrary(user.getLibrary());

						SelectionListData listData;

						for (int i = 0; i < entity.getSelectionListData().size(); i++) {

							listData = new SelectionListData();
							listData.setIsisdatabasename(entity.getSelectionListData().get(i).getIsisdatabasename().replaceAll(" +", " ").trim());
							listData.setIsisHome(entity.getSelectionListData().get(i).getIsisHome());
							listData.setIsisRecordID(entity.getSelectionListData().get(i).getIsisRecordID());
							selectionListDataForCoppy.add(listData);

						}     
    
						selectionList.setSelectionListData(selectionListDataForCoppy);

						((SelectionListViewController) controller).addSelectionList(selectionList);

						MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION), MessageUtil.unescape(AbosMessages.get().MSG_COPY_LIST), null);

						searchMySelectionList(0, myListsTable.getPaginator().getPageSize());
						myListsTable.getPaginator().goToFirstPage();
						searchPublicSelectionList(0, publicListsTable.getPaginator().getPageSize());
						publicListsTable.getPaginator().goToFirstPage();
					}
				}
			}
		});
      
		coppyColumn.setToolTipText("Copiar");

		publicListsTable = new CRUDTreeTable(publicListCompo, SWT.NONE);
		publicListsTable.setEntityClass(SelectionList.class);
		publicListsTable.addActionColumn(viewContentColumn);
		publicListsTable.addActionColumn(sendColumn);
		publicListsTable.addActionColumn(coppyColumn); 

		TreeTableColumn publicListColumns[] = { new TreeTableColumn(65, 0, "getSelectionListName"), new TreeTableColumn(35, 1, "getCategory.getNomenclatorName"), };

		publicListsTable.createTable(publicListColumns);
		publicListsTable.setPageSize(10);
		FormDatas.attach(publicListsTable).atTopTo(countSelectionList).atRight(5).atLeft(5);

		myListsTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				
				searchMySelectionList(event.currentPage - 1, event.pageSize);
				refresh();
			}
		});

		publicListsTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				searchPublicSelectionList(event.currentPage - 1, event.pageSize);
				refresh();
			}
		});
		
		publicListsTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		save.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent event) {

				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else if (validator.decorationFactory.AllControlDecorationsHide()) {

					boolean selectionBoolean = ((SelectionListViewController) controller).findAllSelectionListsByName(selectionListName.getText().replaceAll(" +", " ").trim(), user.getUserID().longValue());

					if (selectionBoolean == true) {
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_INF_SELECTION_LIST_EXISTS));
					} else {

						try {

							SelectionList selectionList = new SelectionList();

							selectionList.setDuser(user);

							Nomenclator nomenclator = null;

							if (selectionListCategoryCb.getSelectionIndex() == 1)
								nomenclator = ((SelectionListViewController) controller).findNomenclator(Nomenclator.CATEGORY_SELECTION_PRIVATE);
							else
								nomenclator = ((SelectionListViewController) controller).findNomenclator(Nomenclator.CATEGORY_SELECTION_PUBLIC);

							selectionList.setCategory(nomenclator);

							selectionList.setActionDate(new java.sql.Date(new Date().getTime()));
							selectionList.setSelectionListName(selectionListName.getText().replaceAll(" +", " ").trim());

							selectionList.setLibrary(user.getLibrary());

							((SelectionListViewController) controller).addSelectionList(selectionList);

							MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT), null);

							searchMySelectionList(0, myListsTable.getPaginator().getPageSize());
							myListsTable.getPaginator().goToFirstPage();
							searchPublicSelectionList(0, publicListsTable.getPaginator().getPageSize());
							publicListsTable.getPaginator().goToFirstPage();

							agregar.setVisible(false);
							tabFolder.setVisible(true);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		final DialogCallback cancelCallBack = new DialogCallback() {
			private static final long serialVersionUID = 1L;

			@Override
			public void dialogClosed(int returnCode) {
				if (returnCode == 0) {
					agregar.setVisible(false);
					tabFolder.setVisible(true);
				}
			}
		};

		cancel.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), cancelCallBack);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		addLists.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {
					user = null;
					user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
				} catch (Exception e) {

				}

				if (user != null) {

					agregar.setVisible(true);
					tabFolder.setVisible(false);

					selectionListName.setText("");
					selectionListName.setBackground(null);
					selectionListCategoryCb.select(0);
					selectionListCategoryCb.setBackground(null);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		myListsTable.addDeleteListener(new TreeColumnListener() {
			@Override
			public void handleEvent(final TreeColumnEvent event) {
				try {
					final Long selectionListID = ((SelectionList) event.entity.getRow()).getId();

					DialogCallback callback = new DialogCallback() {
						private static final long serialVersionUID = 1L;

						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {

								((SelectionListViewController) controller).deleteSelectionList(selectionListID);
								searchMySelectionList(0, myListsTable.getPaginator().getPageSize());
								myListsTable.getPaginator().goToFirstPage();
								MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM), null);
							}
						}
					};

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_WARNING), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), callback);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		tabFolder.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {   

				try {
					user = null;
					user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
				} catch (Exception e) {

				}
				
				if (user == null && myListCompo.getVisible() == true) {
					showInformationMessage(MessageUtil.unescape(cu.uci.abcd.opac.l10n.AbosMessages.get().MSG_ERROR_NEED_BE_AUTHENTICATED));
					tabFolder.setSelection(0);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		tabFolder.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (publicListCompo.getVisible() == true)
					publicTable();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		privateTable();
		publicTable();

		l10n();
		return parent;
	}

	private void searchMySelectionList(int page, int size) {

		user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");

		myListsTable.clearRows();
		Integer userID = user.getUserID().intValue();
		Page<SelectionList> list = ((SelectionListViewController) controller).findAllSelectionListPageByUser(userID, user.getLibrary().getLibraryID(), page, size, direction, orderByString);
		myListsTable.getPaginator().setTotalElements((int) list.getTotalElements());
		countMySelectionList
				.setText((MessageUtil.unescape(AbosMessages.get().TAB_ITEM_MY_LIST) + " (" + " " + ((SelectionListViewController) controller).findAllSelectionListsByUser(user.getUserID(), user.getLibrary().getLibraryID()).size() + " " + MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_LIST
						+ " )")));
		myListsTable.setRows(list.getContent());
		myListsTable.refresh();
	}

	private void searchPublicSelectionList(int page, int size) {

		publicListsTable.clearRows();
		Page<SelectionList> list = ((SelectionListViewController) controller).findAllPublicSelectionListPage(page, size, direction, orderByString);
		publicListsTable.getPaginator().setTotalElements((int) list.getTotalElements());
		countSelectionList.setText((MessageUtil.unescape(AbosMessages.get().TAB_ITEM_PUBLIC_LIST) + " (" + " " + (((SelectionListViewController) controller).findAllPublicSelectionLists()) + " " + MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_LIST + " )")));
		publicListsTable.setRows(list.getContent());
		publicListsTable.refresh();

	}

	@Override
	public String getID() {
		return "ListasDeSeleccionID";
	}

	@Override
	public void l10n() {
		myListsTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_LIST), MessageUtil.unescape(AbosMessages.get().LABEL_KIND_LIST)));
		publicListsTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_LIST), MessageUtil.unescape(AbosMessages.get().LABEL_KIND_LIST)));
		listNameLabel.setText(MessageUtil.unescape((AbosMessages.get().LABEL_NAME_LIST)) + " :");
		TipoLista.setText(MessageUtil.unescape(AbosMessages.get().LABEL_KIND_LIST) + " :");
		tabItemPublicList.setText(MessageUtil.unescape(AbosMessages.get().TAB_ITEM_PUBLIC_LIST));
		tabItemMyList.setText(MessageUtil.unescape(AbosMessages.get().TAB_ITEM_MY_LIST));
		agregar.setText(MessageUtil.unescape((AbosMessages.get().GROUP_NEW_SELECTION_LIST)));
		addLists.setText(AbosMessages.get().BUTTON_ADD_LIST);
		save.setText(MessageUtil.unescape((AbosMessages.get().ACCEPT)));
		cancel.setText(MessageUtil.unescape((AbosMessages.get().CANCEL)));
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_MY_SELECTION_LIST);
	}

	public void publicTable() {
		try {
			searchPublicSelectionList(0, publicListsTable.getPaginator().getPageSize());
			publicListsTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void privateTable() {
		try {
			searchMySelectionList(0, myListsTable.getPaginator().getPageSize());
			myListsTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}