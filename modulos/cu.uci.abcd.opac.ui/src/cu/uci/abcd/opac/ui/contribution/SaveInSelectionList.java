package cu.uci.abcd.opac.ui.contribution;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.opac.SelectionList;
import cu.uci.abcd.domain.opac.SelectionListData;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.controller.SelectionListViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

public class SaveInSelectionList extends ContributorPage {

	public Composite result;

	private User user;
	public RecordIsis record;
	public List<RecordIsis> records;

	private Label authorLabel;
	private Link titleContent;
	private Label authorContent;

	public Button save;
	public Button saveSelection;

	private boolean insert;
	private int countInsert;
	private String bookTitle = "";
	private String bookAuthor = "";
	private String message = "";

	private Text nameSelectionList;
	private boolean selectionBoolean;

	private ValidatorUtils validator;

	private Label sub;
	private Group saveInList;
	private Label addToListLabel;
	private Group saveInANewList;
	private Label addToNewListLabel;
	private Label categorySelectionList;

	private List<SelectionList> mySelectionList = new ArrayList<SelectionList>();
	private Combo selectionListCombo;

	private Combo categoryCombo;

	@Override
	public Control createUIControl(Composite parent) {

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
			e.printStackTrace();
		}

		validator = new ValidatorUtils(new CustomControlDecoration());

		result = parent;

		sub = new Label(result, 0);
		sub.setFont(new Font(parent.getDisplay(), "Arial", 16, SWT.BOLD));
		FormDatas.attach(sub).atTop(15).atLeft(30);

		Link horSeparator = new Link(result, SWT.NORMAL);
		horSeparator
				.setText("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
		horSeparator.setFont(new Font(parent.getDisplay(), "Arial", 4, SWT.NONE));
		FormDatas.attach(horSeparator).atTopTo(sub, -7).atLeft(28);

		titleContent = new Link(result, SWT.WRAP);
		titleContent.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(titleContent).atTopTo(sub, 10).atLeft(40).atRight();

		authorLabel = new Label(result, SWT.NORMAL);
		authorLabel.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.None));
		authorLabel.setVisible(true);
		FormDatas.attach(authorLabel).atTopTo(titleContent, 5).atLeft(40);

		authorContent = new Label(result, SWT.NORMAL);
		authorContent.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(authorContent).atTopTo(titleContent, 5).atLeftTo(authorLabel, 5);

		saveInList = new Group(result, 0);
		saveInList.setBackground(result.getBackground());
		saveInList.setLayout(new FormLayout());
		FormDatas.attach(saveInList).atTopTo(authorLabel, 20).atLeft(100).atRight(150);

		Label temp = new Label(saveInList, SWT.NORMAL);
		FormDatas.attach(temp).atLeft(160);

		addToListLabel = new Label(saveInList, SWT.NORMAL);
		FormDatas.attach(addToListLabel).atTop(20).atRightTo(temp);

		selectionListCombo = new Combo(saveInList, SWT.READ_ONLY);
		FormDatas.attach(selectionListCombo).atTop(15).atLeftTo(temp, 5).withWidth(172);

		save = new Button(saveInList, 0);
		FormDatas.attach(save).atTopTo(selectionListCombo, 15).atLeftTo(temp, 5);

		try {

			mySelectionList = ((SelectionListViewController) controller).findAllSelectionListsByUser(user.getUserID(), user.getLibrary().getLibraryID());

			String[] combo = new String[mySelectionList.size()];

			for (int i = 0; i < combo.length; i++)
				combo[i] = mySelectionList.get(i).getSelectionListName();

			selectionListCombo.setItems(combo);
			selectionListCombo.setText(combo[0]);

		} catch (Exception e) {
			save.setEnabled(false);
			e.printStackTrace();
		}

		save.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {

					SelectionList selectionList = mySelectionList.get(selectionListCombo.getSelectionIndex());

					SelectionListData tempSelectionListData = new SelectionListData();

					List<SelectionListData> sectionListData = selectionList.getSelectionListData();

					insert = true;
					countInsert = 0;

					if (record != null) {

						try {
							tempSelectionListData.setIsisRecordID(record.getControlNumber());
						} catch (Exception e) {
							e.printStackTrace();
						}

						tempSelectionListData.setIsisdatabasename(record.getDataBaseName());
						tempSelectionListData.setIsisHome(record.getLibrary().getIsisDefHome());

						for (SelectionListData selData : sectionListData)
							if (selData.getIsisHome().equals(tempSelectionListData.getIsisHome()) && selData.getIsisdatabasename().equals(tempSelectionListData.getIsisdatabasename()) && selData.getIsisRecordID().equals(tempSelectionListData.getIsisRecordID())) {
								insert = false;
								break;
							}

						if (insert) {
							sectionListData.add(tempSelectionListData);

							selectionList.setSelectionListData(sectionListData);

							((SelectionListViewController) controller).addSelectionList(selectionList);

							showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
						} else
							showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_SELECTION_LIST_ONE_EXISTING_DATA));

					} else {

						for (RecordIsis record : records) {

							tempSelectionListData = new SelectionListData();

							try {
								tempSelectionListData.setIsisRecordID(record.getControlNumber());
							} catch (Exception e) {
								e.printStackTrace();
							}

							tempSelectionListData.setIsisdatabasename(record.getDataBaseName());
							tempSelectionListData.setIsisHome(record.getLibrary().getIsisDefHome());

							insert = true;
							bookTitle = "";
							bookAuthor = "";

							for (SelectionListData selData : sectionListData)
								if (selData.getIsisHome().equals(tempSelectionListData.getIsisHome()) && selData.getIsisdatabasename().equals(tempSelectionListData.getIsisdatabasename()) && (selData.getIsisRecordID().equals(tempSelectionListData.getIsisRecordID()))) {

									bookTitle = record.getTitle();
									try {
										bookAuthor = record.getAuthor();

									} catch (Exception e) {
										bookAuthor = "";
									}

									message += "\n" + bookTitle;

									if (bookAuthor != "")
										message += " por " + bookAuthor;

									insert = false;
									break;
								}

							if (insert) {
								sectionListData.add(tempSelectionListData);
								countInsert++;
							}

						}

						if (countInsert > 0) {

							selectionList.setSelectionListData(sectionListData);
							((SelectionListViewController) controller).addSelectionList(selectionList);

							if (countInsert == records.size())
								showInformationMessage(countInsert + " " + MessageUtil.unescape(AbosMessages.get().MSG_INF_NEW_DATA_IN_SELECTION_LIST));
							else {
								showWarningMessage(countInsert + " " + MessageUtil.unescape(AbosMessages.get().MSG_INF_NEW_DATA_IN_SELECTION_LIST + "\n\n" + AbosMessages.get().MSG_ERROR_SELECTION_LIST_EXISTING_DATA + "\n\n" + message));
								message = "";
							}
						} else {
							showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_SELECTION_LIST_EXISTING_DATA) + "\n\n" + message);
							message = "";
						}
					}
				} catch (Exception e) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
					e.printStackTrace();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// /////////////

		saveInANewList = new Group(result, 0);
		saveInANewList.setBackground(result.getBackground());
		saveInANewList.setLayout(new FormLayout());
		FormDatas.attach(saveInANewList).atTopTo(saveInList, 15).atLeft(100).atRight(150);

		Label point = new Label(saveInANewList, SWT.NORMAL);
		FormDatas.attach(point).atLeft(160);

		addToNewListLabel = new Label(saveInANewList, SWT.NORMAL);
		FormDatas.attach(addToNewListLabel).atTop(15).atRightTo(point);

		nameSelectionList = new Text(saveInANewList, 0);
//		nameSelectionList.setTextLimit(20);
		validator.applyValidator(nameSelectionList, "bookTitleText", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(nameSelectionList, "bookTitleText1", DecoratorType.ALPHA_NUMERICS_SPACES, true, 20);

		FormDatas.attach(nameSelectionList).atTop(15).atLeftTo(point, 5).withWidth(200);

		categorySelectionList = new Label(saveInANewList, SWT.NORMAL);
		FormDatas.attach(categorySelectionList).atTopTo(nameSelectionList, 5).atRightTo(point);

		categoryCombo = new Combo(saveInANewList, SWT.READ_ONLY);
		FormDatas.attach(categoryCombo).atTopTo(nameSelectionList, 5).atLeftTo(point, 5).withWidth(172);
		initialize(categoryCombo, ((SelectionListViewController) controller).findAllNomencaltors(Nomenclator.CATEGORY_SELECTION_LIST));
		validator.applyValidator(categoryCombo, "categoryCombo", DecoratorType.REQUIRED_FIELD, true);

		saveSelection = new Button(saveInANewList, 0);
		FormDatas.attach(saveSelection).atTopTo(categoryCombo, 15).atLeftTo(point, 5);

		saveSelection.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else if (validator.decorationFactory.AllControlDecorationsHide()) {

					selectionBoolean = ((SelectionListViewController) controller).findAllSelectionListsByName(nameSelectionList.getText(), user.getUserID().longValue());

					if (selectionBoolean == true) {
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_INF_SELECTION_LIST_EXISTS));
					} else {

						try {

							SelectionList selectionList = new SelectionList();

							selectionList.setDuser(user);

							selectionList.setLibrary(user.getLibrary());
    
							Nomenclator nomenclator = null;

							if (categoryCombo.getSelectionIndex() == 1)
								nomenclator = ((SelectionListViewController) controller).findNomenclator(Nomenclator.CATEGORY_SELECTION_PRIVATE);
							else
								nomenclator = ((SelectionListViewController) controller).findNomenclator(Nomenclator.CATEGORY_SELECTION_PUBLIC);

							selectionList.setCategory(nomenclator);

							selectionList.setOrderBy(((SelectionListViewController) controller).findNomenclator(Nomenclator.ORDERBY_SELECTION_LIST_TITLE));

							selectionList.setActionDate(new java.sql.Date(new Date().getTime()));

							selectionList.setSelectionListName(nameSelectionList.getText().replaceAll(" +", " ").trim());

							List<SelectionListData> selectionListData = new ArrayList<SelectionListData>();

							SelectionListData tempSelectionListData = null;

							if (record != null) {

								tempSelectionListData = new SelectionListData();

								try {
									tempSelectionListData.setIsisRecordID(record.getControlNumber());
								} catch (Exception e) {
									e.printStackTrace();
								}

								tempSelectionListData.setIsisdatabasename(record.getDataBaseName());
								tempSelectionListData.setIsisHome(record.getLibrary().getIsisDefHome());

								selectionListData.add(tempSelectionListData);

							} else
								for (RecordIsis record : records) {

									tempSelectionListData = new SelectionListData();

									try {
										tempSelectionListData.setIsisRecordID(record.getControlNumber());
									} catch (Exception e) {
										e.printStackTrace();
									}

									tempSelectionListData.setIsisdatabasename(record.getDataBaseName());
									tempSelectionListData.setIsisHome(record.getLibrary().getIsisDefHome());

									selectionListData.add(tempSelectionListData);
								}

							selectionList.setSelectionListData(selectionListData);
							((SelectionListViewController) controller).addSelectionList(selectionList);

							showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
							nameSelectionList.setText("");
							nameSelectionList.setBackground(null);
							categoryCombo.select(0);
							categoryCombo.setBackground(null);

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

		l10n();
		return result;
	}

	@Override
	public String getID() {
		return "SaveInSelectionList";
	}

	@Override
	public void l10n() {

		sub.setText(MessageUtil.unescape(AbosMessages.get().ADD_SELECTION_LIST));
		authorLabel.setText(MessageUtil.unescape(AbosMessages.get().AUTHOR));
		saveInList.setText(MessageUtil.unescape(AbosMessages.get().SELECT_LIST));
		addToListLabel.setText(MessageUtil.unescape(AbosMessages.get().ADD_TO_LIST) + " :");
		save.setText(MessageUtil.unescape(AbosMessages.get().SAVE));
		saveInANewList.setText(MessageUtil.unescape(AbosMessages.get().ADD_NEW_LIST));
		addToNewListLabel.setText(MessageUtil.unescape(AbosMessages.get().NAME_LIST) + " :");
		categorySelectionList.setText(MessageUtil.unescape(AbosMessages.get().LABEL_KIND_LIST) + " :");
		saveSelection.setText(MessageUtil.unescape(AbosMessages.get().SAVE));
            
		if (record != null) {
			try {
				titleContent.setText( record.getTitle());
				authorContent.setText(record.getAuthor());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			titleContent.setText(records.size() + " " +  MessageUtil.unescape(AbosMessages.get().SELECTED_RECORDS));
			authorLabel.setVisible(false);
		}

		update();
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_MY_SELECTION_LIST);
	}

	public void update() {
		result.layout(true, true);
		result.redraw();
		result.update();
	}

}