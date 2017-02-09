package cu.uci.abcd.opac.ui.contribution;

import java.util.ArrayList;
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
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.opac.SelectionList;
import cu.uci.abcd.domain.opac.SelectionListData;
import cu.uci.abcd.opac.IsisSelectionListContent;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.auxiliary.Auxiliary;
import cu.uci.abcd.opac.ui.controller.SelectionListViewController;
import cu.uci.abcd.opac.ui.listener.ViewLogDataListener;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

public class MySelectionListContent extends ContributorPage {

	private ContributorService pageService;
	private MySelectionLists mySelectionLists;
	private User user;

	private SelectionList selectionList = new SelectionList();
	private List<SelectionListData> selectionListData;

	private IsisSelectionListContent isisSelectionList;

	private Composite result;
	private Composite exportToCompo;
	private Composite headers;

	private Composite row;
	private Group editGroup;

	private Label listNameLbl;
	private Text selectionListName;
	private Label tipoListaLbl;
	private Combo selectionListCategoryCb;
	private Button save;
	private Button cancel;

	private Button deleteItemBtn;
	private Button exportList;
	private Button editList;
	private Button deleteList;

	List<IsisSelectionListContent> isisSelectionListContent;

	List<String> controlNumber;
	List<String> isisHome;
	List<String> databaseName;

	String currentIsisHome = "";
	String currentDataBaseName = "";

//	private List<RecordIsis> records;
	public List<RecordIsis> records = new ArrayList<RecordIsis>();
	private List<RecordIsis> mySelectedRecord;

	private String bookTitle;
	private String bookAuthor;
	private String publication;
	private String recordType;

	private Label txtIconLb;
	private Label pdfIconLb;
	private Label htmlIconLb;

	private ServiceProvider serviceProvider;
	private ValidatorUtils validator;

	private Composite registers;

	private Label sub;
	private Link htmlLk;
	private Link plainText;
	private Link pdf;
	private Link year;
	private Link headerType;
	private Link headerName;

	public MySelectionListContent(ServiceProvider service) {
		this.serviceProvider = service;
	}

	public void setSelectionList(SelectionList selectionList) {
		this.selectionList = selectionList;
	}

	@Override
	public Control createUIControl(final Composite parent) {

		validator = new ValidatorUtils(new CustomControlDecoration());
   
		selectionListData = new ArrayList<SelectionListData>();
		controlNumber = new ArrayList<String>();
		isisHome = new ArrayList<String>();
		databaseName = new ArrayList<String>();
//		records = new ArrayList<RecordIsis>();
		mySelectedRecord = new ArrayList<RecordIsis>();
		isisSelectionListContent = new ArrayList<IsisSelectionListContent>();


		addComposite(parent);

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);

		result = new Composite(parent, 0);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		result.setLayout(new FormLayout());
		FormDatas.attach(result).atLeft(0).atRight(0).atTop(0).atBottom(0);

		editGroup = new Group(parent, 0);
		editGroup.setBackground(parent.getBackground());
		editGroup.setLayout(new FormLayout());

		FormDatas.attach(editGroup).atTop(0).atLeft(10).atRight(10);

		listNameLbl = new Label(editGroup, SWT.NORMAL);
		add(listNameLbl);

		selectionListName = new Text(editGroup, SWT.NORMAL);
//		selectionListName.setTextLimit(20);
		validator.applyValidator(selectionListName, "selectionListName", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(selectionListName, "selectionListName1", DecoratorType.ALPHA_NUMERICS_SPACES, true, 20);
		add(selectionListName);

		br();

		tipoListaLbl = new Label(editGroup, SWT.NORMAL);
		add(tipoListaLbl);

		selectionListCategoryCb = new Combo(editGroup, SWT.READ_ONLY);
		selectionListCategoryCb.setLayoutData(new FormData());
		add(selectionListCategoryCb);
		initialize(selectionListCategoryCb, ((SelectionListViewController) controller).findAllNomencaltors(Nomenclator.CATEGORY_SELECTION_LIST));
		validator.applyValidator(selectionListCategoryCb, "selectionListCategoryCb", DecoratorType.REQUIRED_FIELD, true);

		br();

		cancel = new Button(editGroup, SWT.PUSH);
		add(cancel);

		save = new Button(editGroup, SWT.PUSH);
		add(save);

		records = new ArrayList<RecordIsis>();

		pageService = this.serviceProvider.get(ContributorService.class);
		mySelectionLists = (MySelectionLists) ((OpacContributorServiceImpl) pageService).getContributorMap().get("ListasDeSeleccionID");

		final Auxiliary aux = new Auxiliary(controller);

		sub = new Label(result, 0);
		sub.setFont(new Font(parent.getDisplay(), "Arial", 16, SWT.BOLD));
		FormDatas.attach(sub).atTop(10).atLeft(30);

		Label nameLabel = new Label(result, 0);
		nameLabel.setText(selectionList.getSelectionListName());
		nameLabel.setFont(new Font(parent.getDisplay(), "Arial", 16, SWT.BOLD));
		FormDatas.attach(nameLabel).atTop(10).atLeftTo(sub, 10);

		Link horSeparator = new Link(result, SWT.NORMAL);
		horSeparator.setText("__________________________________________________________________________________________________________________________________________________");
		horSeparator.setFont(new Font(parent.getDisplay(), "Arial", 4, SWT.NONE));
		FormDatas.attach(horSeparator).atTopTo(nameLabel, -7).atLeft(28);

		if (checkUserLogin()) {

			deleteItemBtn = new Button(result, SWT.PUSH);
			Image deleteItemImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("trash"));
			deleteItemBtn.setImage(deleteItemImage);
			deleteItemBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
			FormDatas.attach(deleteItemBtn).atTopTo(horSeparator, 10).atLeft(30);
		}

		exportList = new Button(result, 0);
		Image exportListImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("save"));
		exportList.setImage(exportListImage);
		exportList.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");

		if (checkUserLogin())
			FormDatas.attach(exportList).atTopTo(horSeparator, 10).atLeftTo(deleteItemBtn, 5);
		else
			FormDatas.attach(exportList).atTopTo(horSeparator, 10).atLeft(30);

		exportToCompo = new Composite(result, SWT.BORDER);
		exportToCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		exportToCompo.setLayout(new FormLayout());
		FormDatas.attach(exportToCompo).atTopTo(exportList, -5).atLeft(10).atRight(10);
		exportToCompo.setVisible(false);

		htmlIconLb = new Label(exportToCompo, SWT.NORMAL);
		htmlIconLb.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		String srcHtml = RWT.getResourceManager().getLocation("opac-file-html");
		htmlIconLb.setText("<img  width='" + 16 + "' height='" + 16 + "' src='" + srcHtml + "'></img> ");
		FormDatas.attach(htmlIconLb).atTop(3).atBottom(3).atLeft(300);

		htmlLk = new Link(exportToCompo, 0);
		htmlLk.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.ITALIC | SWT.BOLD));
		FormDatas.attach(htmlLk).atTop(3).atBottom(3).atLeftTo(htmlIconLb);

		txtIconLb = new Label(exportToCompo, SWT.NORMAL);
		txtIconLb.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		String srcTxt = RWT.getResourceManager().getLocation("opac-file-txt");
		txtIconLb.setText("<img  width='" + 16 + "' height='" + 16 + "' src='" + srcTxt + "'></img> ");
		FormDatas.attach(txtIconLb).atTop(3).atBottom(3).atLeftTo(htmlLk, 10);

		plainText = new Link(exportToCompo, 0);
		plainText.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.ITALIC | SWT.BOLD));
		FormDatas.attach(plainText).atTop(3).atBottom(3).atLeftTo(txtIconLb);

		pdfIconLb = new Label(exportToCompo, SWT.NORMAL);
		pdfIconLb.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		String srcPdf = RWT.getResourceManager().getLocation("opac-file-pdf");
		pdfIconLb.setText("<img  width='" + 16 + "' height='" + 16 + "' src='" + srcPdf + "'></img> ");
		FormDatas.attach(pdfIconLb).atTop(3).atBottom(3).atLeftTo(plainText, 10);

		pdf = new Link(exportToCompo, 0);
		pdf.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.ITALIC | SWT.BOLD));
		FormDatas.attach(pdf).atTop(3).atBottom(3).atLeftTo(pdfIconLb);

		exportList.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (exportToCompo.getVisible())
					exportToCompo.setVisible(false);
				else
					exportToCompo.setVisible(true);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		plainText.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mySelectedRecord.size() != 0)
					aux.exportToPlainText(mySelectedRecord);
				else
					showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MUST_SELECT_ONE_ELEMENT));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		htmlLk.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mySelectedRecord.size() != 0)
					aux.exportToHTML(mySelectedRecord);
				else
					showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MUST_SELECT_ONE_ELEMENT));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		pdf.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mySelectedRecord.size() != 0)
					aux.exportToPdf(mySelectedRecord);
				else
					showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MUST_SELECT_ONE_ELEMENT));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		if (checkUserLogin()) {

			editList = new Button(result, 0);
			Image editListImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("pencil"));
			editList.setImage(editListImage);
			editList.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
			FormDatas.attach(editList).atTopTo(horSeparator, 10).atLeftTo(exportList, 5);

			deleteList = new Button(result, 0);
			Image deleteListImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("redcross"));
			deleteList.setImage(deleteListImage);
			deleteList.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
			FormDatas.attach(deleteList).atTopTo(horSeparator, 10).atLeftTo(editList, 5);

			deleteItemBtn.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					
					if (!mySelectedRecord.isEmpty()) {

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), new DialogCallback() {
        
						private static final long serialVersionUID = 1L;

						@Override
						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
								if (!mySelectedRecord.isEmpty()) {

									for (int i = 0; i < mySelectedRecord.size(); i++)
										for (int j = 0; j < selectionList.getSelectionListData().size(); j++) {
											if (selectionList.getSelectionListData().get(j).getIsisdatabasename().equals(mySelectedRecord.get(i).getDataBaseName()) && selectionList.getSelectionListData().get(j).getIsisRecordID().toString().equals(mySelectedRecord.get(i).getControlNumber())) {
    
												selectionList.getSelectionListData().remove(j--);
												records.remove(mySelectedRecord.get(i));

											}    
										}     

									showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM);
									((SelectionListViewController) controller).deleteSelectionList(selectionList.getId());

									selectionList.setSelectionListData(selectionList.getSelectionListData());

									((SelectionListViewController) controller).addSelectionList(selectionList);

									Control[] temp = registers.getChildren();
									for (int i = 1; i < temp.length; i++)
										temp[i].dispose();

									Composite last = headers;

									for (int i = 0; i < records.size(); i++) {
										row = CreateRows(registers, false, records.get(i));
										FormDatas.attach(row).atTopTo(last, -2).atLeft(-2).atRight(-2).withHeight(60);

										last = row;
									}
									mySelectedRecord.clear();
									l10n();

									if (selectionList.getSelectionListData().isEmpty())
										MySelectionListContent.this.notifyListeners(SWT.Dispose, new Event());
								}
//								else {
//									showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM);
//								}
							}
						}

					});
				}
					else {
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MUST_SELECT_ONE_ELEMENT));
//						showInformationMessage(AbosMessages.get().MSG_ERROR_MUST_SELECT_ONE_ELEMENT);
					}
				}		

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

			deleteList.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {

					DialogCallback callback = new DialogCallback() {
						private static final long serialVersionUID = 1L;

						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {

								((SelectionListViewController) controller).deleteSelectionList(selectionList.getId());
								showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM);
								mySelectionLists.privateTable();
								mySelectionLists.publicTable();
								MySelectionListContent.this.notifyListeners(SWT.Dispose, new Event());

							}
						}
					};

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_WARNING), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), callback);

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

			editList.addSelectionListener(new SelectionListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {

					result.setVisible(false);
					selectionListName.setText(selectionList.getSelectionListName());
					UiUtils.selectValue(selectionListCategoryCb, selectionList.getCategory());

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

		}

		registers = new Composite(result, SWT.BORDER);
		registers.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		registers.setLayout(new FormLayout());

		FormDatas.attach(registers).atTopTo(exportToCompo, -2).atLeft(10).atRight(10).atBottom(10);

		// ///// my Selections List Content \\\\\ \\

		headers = new Composite(registers, SWT.BORDER);
		headers.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		headers.setLayout(new FormLayout());
		FormDatas.attach(headers).atTop(-2).atLeft(-2).atRight(-2).withHeight(15);

		Composite headerCopyCantCompo = new Composite(headers, SWT.BORDER);
		headerCopyCantCompo.setBackground(headers.getBackground());
		headerCopyCantCompo.setLayout(new FormLayout());
		FormDatas.attach(headerCopyCantCompo).atTop(-10).atRight(-20).atBottom(-12).withWidth(200);

		year = new Link(headerCopyCantCompo, 0);
		year.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(year).atTop(7).atBottom(5).atLeft(15);

		Composite headerTypeCompo = new Composite(headers, SWT.BORDER);
		headerTypeCompo.setBackground(headers.getBackground());
		headerTypeCompo.setLayout(new FormLayout());
		FormDatas.attach(headerTypeCompo).atTop(-10).atBottom(-12).atRightTo(headerCopyCantCompo, -2).withWidth(100);

		headerType = new Link(headerTypeCompo, 0);
		headerType.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(headerType).atTop(7).atBottom(5).atLeft(15);

		Composite headerWhiteSpace = new Composite(headers, SWT.BORDER);
		headerWhiteSpace.setBackground(headers.getBackground());
		headerWhiteSpace.setLayout(new FormLayout());
		FormDatas.attach(headerWhiteSpace).atTop(-10).atBottom(-12).atLeft(-24).withWidth(42);

		final Button allSelect = new Button(headerWhiteSpace, SWT.CHECK);
		FormDatas.attach(allSelect).atTop(5).atLeft(15);

		Composite headerNameCompo = new Composite(headers, SWT.BORDER);
		headerNameCompo.setBackground(headers.getBackground());
		headerNameCompo.setLayout(new FormLayout());
		FormDatas.attach(headerNameCompo).atTop(-10).atBottom(-12).atLeftTo(headerWhiteSpace, -2).atRightTo(headerTypeCompo, -2);

		headerName = new Link(headerNameCompo, 0);
		headerName.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(headerName).atTop(7).atBottom(5).atLeft(15);

		// //////////////////////

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
    
					records.addAll(((SelectionListViewController) controller).findRecordByControlNumber(isisSelectionListContent.get(i).getControlNumber(), isisSelectionListContent.get(i).getDataBaseName(), isisSelectionListContent.get(i).getIsisHome()));

				}    

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		save.addSelectionListener(new SelectionListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else if (validator.decorationFactory.AllControlDecorationsHide()) {

					boolean selectionBoolean = ((SelectionListViewController) controller).findAllSelectionListsByName(selectionListName.getText(), selectionList.getDuser().getUserID().longValue(), selectionList.getId());

					if (selectionBoolean == true) {
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_INF_SELECTION_LIST_EXISTS));
						selectionListName.setFocus();
					} else {
						try {

							Nomenclator nomenclator = null;

							if (selectionListCategoryCb.getSelectionIndex() == 1)
								nomenclator = ((SelectionListViewController) controller).findNomenclator(Nomenclator.CATEGORY_SELECTION_PUBLIC);
							else
								nomenclator = ((SelectionListViewController) controller).findNomenclator(Nomenclator.CATEGORY_SELECTION_PRIVATE);

							selectionList.setCategory(nomenclator);

							selectionList.setOrderBy(nomenclator);

							selectionList.setSelectionListName(selectionListName.getText());

							((SelectionListViewController) controller).addSelectionList(selectionList);

							showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
				}

				mySelectionLists.privateTable();
				mySelectionLists.publicTable();
				result.setVisible(true);
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
					selectionListName.setText("");
					selectionListName.setBackground(null);
					selectionListCategoryCb.select(0);
					selectionListCategoryCb.setBackground(null);
					result.setVisible(true);
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

		Composite last = headers;
		cleanRegisters();

		for (int i = 0; i < records.size(); i++) {
			row = CreateRows(registers, false, records.get(i));
			FormDatas.attach(row).atTopTo(last, -2).atLeft(-2).atRight(-2);

			last = row;
		}
		

		allSelect.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
			
				if (allSelect.getSelection()) {

					for (int i = 0; i < records.size(); i++)
						if (!mySelectedRecord.contains(records.get(i)))
							mySelectedRecord.add(records.get(i));

					cleanRegisters();
					
					Composite last = headers;
					for (int i = 0; i < records.size(); i++) {
						row = CreateRows(registers, true, records.get(i));
						FormDatas.attach(row).atTopTo(last, -2).atLeft(-2).atRight(-2).withHeight(60);

						last = row;
					}
					
				} else {

					mySelectedRecord = new ArrayList<RecordIsis>();

					cleanRegisters();

					Composite last = headers;
					for (int i = 0; i < records.size(); i++) {
						row = CreateRows(registers, false, records.get(i));
						FormDatas.attach(row).atTopTo(last, -2).atLeft(-2).atRight(-2).withHeight(60);

						last = row;
					}
				}

				l10n();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		l10n();
		return result;
	}

	public Composite CreateRows(Composite parent, boolean select, final RecordIsis record) {

		parent = new Composite(parent, SWT.BORDER);
		parent.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		parent.setLayout(new FormLayout());

		bookTitle = record.getTitle();

		try {
			bookAuthor = record.getAuthor();
		} catch (Exception e) {
			bookAuthor = "";
		}

		publication = String.valueOf(record.getPublicationDate());
		recordType = record.getRecordType();
		
		Composite yearCompo = new Composite(parent, SWT.BORDER);
		yearCompo.setBackground(parent.getBackground());
		yearCompo.setLayout(new FormLayout());
		FormDatas.attach(yearCompo).atTop(-2).atRight().atBottom(-2).withWidth(200);
		Label yearLabel = new Label(yearCompo, 0);
		
		//OJO
		yearLabel.setText(publication);
		FormDatas.attach(yearLabel).atTop(15).atBottom(5).atLeft(10);

		Composite typeCompo = new Composite(parent, SWT.BORDER);
		typeCompo.setBackground(parent.getBackground());
		typeCompo.setLayout(new FormLayout());
		FormDatas.attach(typeCompo).atTop(-2).atBottom(-2).atRightTo(yearCompo, -2).withWidth(100);

		Label typeLabel = new Label(typeCompo, 0);		
		typeLabel.setText(recordType);
		FormDatas.attach(typeLabel).atTop(15).atBottom(5).atLeft(10);

		Composite buttonCompo = new Composite(parent, SWT.BORDER);
		buttonCompo.setBackground(parent.getBackground());
		buttonCompo.setLayout(new FormLayout());
		FormDatas.attach(buttonCompo).atTop(-2).atBottom(-2).atLeft(-2).withWidth(40);

		final Button check = new Button(buttonCompo, SWT.CHECK);
		check.setSelection(select);
		FormDatas.attach(check).atTop(20).atLeft(10);

		Composite nameCompo = new Composite(parent, SWT.BORDER);
		nameCompo.setBackground(parent.getBackground());
		nameCompo.setLayout(new FormLayout());
		FormDatas.attach(nameCompo).atTop(-2).atBottom(-2).atLeftTo(buttonCompo, -2).atRightTo(typeCompo, -2).withWidth(140);

		Link titleLink = new Link(nameCompo, SWT.WRAP);
		titleLink.setText("<a>" + bookTitle + "</a>");
		titleLink.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(titleLink).atTop(15).atLeft(15).atRight();

		titleLink.addListener(SWT.Selection, new ViewLogDataListener(serviceProvider, record));
		
		Label autorLabel = new Label(nameCompo, 0);
		autorLabel.setText(MessageUtil.unescape(AbosMessages.get().BY_SELECTION) + ": " + bookAuthor);
		FormDatas.attach(autorLabel).atTopTo(titleLink).atLeft(15);
		
		Label others = new Label(nameCompo, 0);
		others.setText(publication);
		FormDatas.attach(others).atTopTo(autorLabel).atLeft(15);

		check.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (check.getSelection())
					mySelectedRecord.add(record);
				else
					mySelectedRecord.remove(record);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		l10n();
		return parent;
	}

	private void cleanRegisters() {
		try {
			Control[] temp = registers.getChildren();
			for (int i = 1; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getID() {
		return "SelectionListContentID";
	}

	@Override
	public void l10n() {

		sub.setText(MessageUtil.unescape((AbosMessages.get().LIST)));

		exportList.setText(MessageUtil.unescape((AbosMessages.get().EXPORT_LIST)));

		htmlLk.setText("<a>HTML</a>");
		plainText.setText("<a>" + MessageUtil.unescape((AbosMessages.get().PLAIN_TEXT + "</a>")));
		pdf.setText("<a>PDF</a>");

		try {

			deleteItemBtn.setText(MessageUtil.unescape((AbosMessages.get().DELETE_ITEM)));
			editList.setText(MessageUtil.unescape((AbosMessages.get().EDIT_LIST)));
			deleteList.setText(MessageUtil.unescape((AbosMessages.get().DELETE_LIST)));

		} catch (Exception e) {
		}

		year.setText(MessageUtil.unescape((AbosMessages.get().YEAR_TITLE)));
		headerType.setText(MessageUtil.unescape((AbosMessages.get().KIND)));
		headerName.setText(MessageUtil.unescape((AbosMessages.get().COMBO_ORDER_TITLE)));
		editGroup.setText(MessageUtil.unescape((AbosMessages.get().GROUP_EDIT_SELECTION_LIST)));
		listNameLbl.setText(MessageUtil.unescape((AbosMessages.get().LABEL_NAME_LIST)));
		tipoListaLbl.setText(MessageUtil.unescape(AbosMessages.get().LABEL_KIND_LIST));
		save.setText(MessageUtil.unescape((AbosMessages.get().ACCEPT)));
		cancel.setText(MessageUtil.unescape((AbosMessages.get().CANCEL)));

		refresh();
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_MY_SELECTION_LIST_CONTENT);
	}

	private boolean checkUserLogin() {

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
			if (selectionList.getDuser().getUserID() == user.getUserID())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}