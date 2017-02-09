package cu.uci.abcd.management.security.communFragment;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.security.AccessRecord;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.RegisterAccessControl;
import cu.uci.abcd.management.security.ui.controller.AccessRecordViewController;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterAccessControlFragment extends FragmentPage implements FragmentContributor {

	private Label registerWorkerLabel;
	private Button saveBtn;
	private Button cancelBtn;
	private Label descriptionLabel;
	private ViewAccessControlFragment viewAccessControlFragment = null;
	@SuppressWarnings("unused")
	private int direction = 1024;
	@SuppressWarnings("unused")
	private String orderByString = "roomName";
	private String orderByStringPerson = "firstName";
	private Label sugestLabel;
	private Text searchText;
	private Button searchButton;
	private CRUDTreeTable tablePerson;
	@SuppressWarnings("unused")
	private IPersonService personService;
	private String searchTextConsult = null;
	private Composite personDataComposite;
	private Composite viewPersonDataComposite;
	private Composite searchPersonComposite;
	private Composite tablePersonComposite;
	private Composite workerDataComposite;
	@SuppressWarnings("unused")
	private int heightTablePersonComposite;
	private int heightTablePerson;
	private Label fullName;
	private Label user;
	private Label birthday;
	private Label sex;
	private Label identification;
	@SuppressWarnings("unused")
	private int tableHeight;
	private Label pictureLabel;
	private ValidatorUtils validator;
	private Group personGroup;
	private Label fullNameLabel;
	private Label userLabel;
	private Label birthdayLabel;
	private Label sexLabel;
	private Label identificationLabel;
	private Button unassociate;
	private Composite register;
	@SuppressWarnings("unused")
	private Composite show;
	@SuppressWarnings("unused")
	private LoginService log;
	private ViewController controller;
	private Library library;
	private Label accessDataLabel;
	private Label rommVisitedLabel;
	private Label motivoLabel;
	@SuppressWarnings("unused")
	private Button cancelButton;
	@SuppressWarnings("unused")
	private Button acceptButton;
	@SuppressWarnings("unused")
	private Composite associatePersonComposite;
	private Person person;
	@SuppressWarnings("unused")
	private Composite parent;
	private Map<String, Control> controls = new HashMap<String, Control>();
	private Combo rommVisited;
	private Text motivo;
	private CRUDTreeTable accessTable;
	private IVisualEntityManager manager;
	private AccessRecord accessRecord;
	private PagePainter painter;
	@SuppressWarnings("unused")
	private Composite up;
	private int dimension;
	private ContributorService contributorService;
	private RegisterAccessControl registerAccessControl;

	public RegisterAccessControlFragment(AccessRecord accessRecord, ViewController controller, int dimension, RegisterAccessControl registerAccessControl, ContributorService contributorService, Library library, Person person) {
		this.accessRecord = accessRecord;
		this.controller = controller;
		this.dimension = dimension;
		this.contributorService = contributorService;
		this.registerAccessControl = registerAccessControl;
		this.library = library;
		this.person = person;
	}

	public RegisterAccessControlFragment(AccessRecord accessRecord, ViewController controller, int dimension, Library library, Person person) {
		this.accessRecord = accessRecord;
		this.controller = controller;
		this.dimension = dimension;
		this.person = person;
		this.library = library;
	}

	public Control createUIControl(final Composite parent) {
		painter = new FormPagePainter();
		painter.setDimension(dimension);
		parent.setLayout(new FormLayout());
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(parent).atLeft(0).atRight(0);
		Composite scroll = new Composite(parent, SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(parent, 25).atLeft(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		validator = new ValidatorUtils(new CustomControlDecoration());
		register = new Composite(parent, SWT.NONE);
		register.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(register);
		registerWorkerLabel = new Label(register, SWT.NONE);
		registerWorkerLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(registerWorkerLabel).atTopTo(register, 25).atLeft(15);
		Label separator = new Label(register, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(separator).atTopTo(registerWorkerLabel, 15).atLeft(15).atRight(15);
		personDataComposite = new Composite(register, SWT.NONE);
		personDataComposite.setLayout(new FormLayout());
		personDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personDataComposite).atTopTo(separator, 10).atLeft(15).atRight(15);
		viewPersonDataComposite = new Composite(personDataComposite, SWT.NONE);
		viewPersonDataComposite.setLayout(new FormLayout());
		viewPersonDataComposite.setVisible(false);
		viewPersonDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(viewPersonDataComposite).atTopTo(personDataComposite).withHeight(100);
		personGroup = new Group(viewPersonDataComposite, SWT.NONE);
		personGroup.setLayout(new FormLayout());
		personGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personGroup).atTopTo(viewPersonDataComposite, 15).atLeft(0);

		pictureLabel = new Label(personGroup, SWT.BORDER);
		FormDatas.attach(pictureLabel).atTopTo(personGroup, 10).atLeft(15);
		Image image = new Image(Display.getDefault(), AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/photo.png", false).getImageData().scaledTo(100, 100));
		pictureLabel.setImage(image);

		fullNameLabel = new Label(personGroup, SWT.NONE);
		fullNameLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(fullNameLabel).atTopTo(personGroup, 10).atLeftTo(pictureLabel, 40);

		fullName = new Label(personGroup, SWT.NONE);
		FormDatas.attach(fullName).atTopTo(personGroup, 10).atLeftTo(fullNameLabel, 5).atRight(15);

		Label firstSeparator = new Label(personGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(firstSeparator).atTopTo(fullName, 1).atLeftTo(pictureLabel, 5).atRight(5);

		userLabel = new Label(personGroup, SWT.NONE);
		userLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(userLabel).atTopTo(fullNameLabel, 6).atRightTo(fullName, 5);

		user = new Label(personGroup, SWT.NONE);
		FormDatas.attach(user).atTopTo(fullNameLabel, 6).atLeftTo(userLabel, 5);

		Label secondSeparator = new Label(personGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(secondSeparator).atTopTo(user, 1).atLeftTo(pictureLabel, 5).atRight(5);

		birthdayLabel = new Label(personGroup, SWT.NONE);
		birthdayLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(birthdayLabel).atTopTo(userLabel, 6).atRightTo(user, 5);

		birthday = new Label(personGroup, SWT.NONE);
		FormDatas.attach(birthday).atTopTo(userLabel, 6).atLeftTo(birthdayLabel, 5);

		Label thirdSeparator = new Label(personGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(thirdSeparator).atTopTo(birthday, 1).atLeftTo(pictureLabel, 5).atRight(5);

		sexLabel = new Label(personGroup, SWT.NONE);
		sexLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(sexLabel).atTopTo(birthdayLabel, 6).atRightTo(birthday, 5);

		sex = new Label(personGroup, SWT.NONE);
		FormDatas.attach(sex).atTopTo(birthdayLabel, 6).atLeftTo(sexLabel, 5);

		Label fourthSeparator = new Label(personGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(fourthSeparator).atTopTo(sex, 1).atLeftTo(pictureLabel, 5).atRight(5);

		identificationLabel = new Label(personGroup, SWT.NONE);
		identificationLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(identificationLabel).atTopTo(sexLabel, 6).atRightTo(sex, 5);

		identification = new Label(personGroup, SWT.NONE);
		FormDatas.attach(identification).atTopTo(sexLabel, 6).atLeftTo(identificationLabel, 5);

		unassociate = new Button(viewPersonDataComposite, SWT.NONE);
		FormDatas.attach(unassociate).atTopTo(personGroup, -25).withHeight(23).atLeftTo(personGroup, 5);
		unassociate.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 7932356419459592823L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				dissociate();
				refresh();
			}
		});

		searchPersonComposite = new Composite(personDataComposite, SWT.NONE);
		searchPersonComposite.setLayout(new FormLayout());
		searchPersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(searchPersonComposite).atTopTo(personDataComposite);

		Label aaa = new Label(searchPersonComposite, SWT.NONE);
		aaa.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		aaa.setText("Datos de la persona");
		FormDatas.attach(aaa).atTopTo(parent, 10).atLeft(0);

		sugestLabel = new Label(searchPersonComposite, SWT.NONE);
		FormDatas.attach(sugestLabel).atTopTo(aaa, 10).atLeft(0);

		searchText = new Text(searchPersonComposite, 0);
		FormDatas.attach(searchText).atTopTo(sugestLabel, 5).withHeight(10).atLeft(0).withWidth(250);

		searchText.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void focusLost(FocusEvent arg0) {
				searchButton.getShell().setDefaultButton(null);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				searchButton.getShell().setDefaultButton(searchButton);
			}
		});

		searchButton = new Button(searchPersonComposite, SWT.NONE);
		FormDatas.attach(searchButton).atTopTo(sugestLabel, 5).withHeight(20).atLeftTo(searchText, 10);

		searchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8115718469128343452L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				Search();
			}
		});

		tablePersonComposite = new Composite(personDataComposite, SWT.NONE);
		tablePersonComposite.setLayout(new FormLayout());
		tablePersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(tablePersonComposite).atTopTo(searchPersonComposite, 15).atLeft(0).atRight(0);
		tablePersonComposite.setVisible(false);

		tablePerson = new CRUDTreeTable(tablePersonComposite, SWT.NONE);
		tablePerson.setEntityClass(Person.class);
		tablePerson.setSearch(false);
		tablePerson.setSaveAll(false);
		tablePerson.setPageSize(10);

		Column columnPerson = new Column("associate", parent.getDisplay(), new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				person = (Person) event.entity.getRow();
				associate();
				showPerson();
				workerDataComposite.setVisible(true);
			}
		});
		columnPerson.setToolTipText(MessageUtil.unescape(AbosMessages.get().BUTTON_ASSOCIATE));
		tablePerson.addActionColumn(columnPerson);

		tablePerson.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 8817895862824622805L;

			@Override
			public void handleEvent(Event event) {
				refresh();
			}
		});

		TreeTableColumn columnsPerson[] = { new TreeTableColumn(60, 0, "getFullName"), new TreeTableColumn(20, 1, "getDNI"), new TreeTableColumn(20, 2, "getSex.getNomenclatorDescription") };
		tablePerson.createTable(columnsPerson);

		tablePerson.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "dni";
						break;
					}
				}
				searchPerson(event.currentPage - 1, event.pageSize);
				int rowsCount = tablePerson.getRows().size();

				heightTablePerson = (rowsCount * 28) + 105;
				ajustRezise(tablePersonComposite, heightTablePerson);
				ajustRezise(personDataComposite, getHeightSearchPersonComposite() + getHeightTablePersonComposite() + 10);
				refresh(personDataComposite.getParent().getShell());
			}

		});

		FormDatas.attach(tablePerson).atTopTo(tablePersonComposite).atLeft().atRight();

		workerDataComposite = new Composite(register, SWT.NONE);
		workerDataComposite.setLayout(new FormLayout());
		workerDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(workerDataComposite);
		FormDatas.attach(workerDataComposite).atTopTo(personDataComposite).atRight(0).atLeft(0);

		accessDataLabel = new Label(workerDataComposite, SWT.NORMAL);
		painter.addHeader(accessDataLabel);

		rommVisitedLabel = new Label(workerDataComposite, SWT.NONE);
		painter.add(rommVisitedLabel);

		rommVisited = new Combo(workerDataComposite, SWT.READ_ONLY);
		controls.put("rommVisited", rommVisited);
		painter.add(rommVisited);

		motivoLabel = new Label(workerDataComposite, SWT.NONE);
		painter.add(motivoLabel);

		motivo = new Text(workerDataComposite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		controls.put("motivo", motivo);
		painter.add(motivo);

		validator.applyValidator(motivo, 256);

		if (accessRecord == null) {
			cancelBtn = new Button(workerDataComposite, SWT.PUSH);
			painter.add(cancelBtn);
			FormDatas.attach(cancelBtn).atTopTo(motivo, 10).atRight(20).withHeight(23);
			cancelBtn.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
						private static final long serialVersionUID = 1L;

						@Override
						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
								registerAccessControl.notifyListeners(SWT.Dispose, new Event());
							}
						}
					});
				}
			});

			saveBtn = new Button(workerDataComposite, SWT.PUSH);
			painter.add(saveBtn);
			FormDatas.attach(saveBtn).atTopTo(motivo, 10).atRightTo(cancelBtn, 5).withHeight(23);

			saveBtn.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 2615553092700551346L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (person != null) {
						if (getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
							RetroalimentationUtils.showErrorMessage(register, MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
						} else {
							if (getValidator().decorationFactory.AllControlDecorationsHide()) {
								accessRecord = new AccessRecord();
								accessRecord.setPerson(person);
								Room room = (Room) UiUtils.getSelected(rommVisited);

								accessRecord.setLibrary(library);
								accessRecord.setRoom(room);
								User authenticatedUser = (User) SecurityUtils.getPrincipal().getByKey("user");
								accessRecord.setAuthenticatedUser(authenticatedUser);

								Timestamp sqlDate = new Timestamp(new Date().getTime());
								accessRecord.setAccessDate(sqlDate);
								accessRecord.setMotivation(motivo.getText().replaceAll(" +", " ").trim());

								AccessRecord accessrecordeSaved = ((AccessRecordViewController) controller).addAccessRecord(accessRecord);

								registerAccessControl.notifyListeners(SWT.Dispose, new Event());
								contributorService.selectContributor("viewAccessControl", accessrecordeSaved, registerAccessControl, contributorService);
							} else {
								RetroalimentationUtils.showErrorMessage(register, MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));

							}
						}
					} else {
						RetroalimentationUtils.showErrorMessage(register, AbosMessages.get().MESSAGE_SELECT);
					}
				}
			});

			saveBtn.getShell().setDefaultButton(saveBtn);

		} else {
			Label espacio = new Label(workerDataComposite, SWT.NORMAL);
			FormDatas.attach(espacio).atTopTo(descriptionLabel, 40);
		}

		initialize(rommVisited, ((AccessRecordViewController) controller).findRoomByLibrary(library.getLibraryID()));

		validator.applyValidator(rommVisited, "rommVisitedRequired", DecoratorType.REQUIRED_FIELD, true);

		LoadAccessRecordData();
		l10n();
		return parent;
	}

	public void LoadAccessRecordData() {
		if (accessRecord != null) {
			workerDataComposite.setVisible(true);
			UiUtils.selectValue(rommVisited, accessRecord.getRoom());
			motivo.setText(accessRecord.getMotivation() != null ? accessRecord.getMotivation() : "");
			associate();
			showPerson();
		} else {
			workerDataComposite.setVisible(false);
		}
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		tablePerson.l10n();
		if (accessRecord == null) {
			registerWorkerLabel.setText(AbosMessages.get().REGISTER_ACCESS);
		} else {
			registerWorkerLabel.setText(AbosMessages.get().UPDATE_ACCESS);
		}
		personGroup.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA));
		fullNameLabel.setText(MessageUtil.unescape(AbosMessages.get().FULL_NAME) + " : ");
		userLabel.setText(MessageUtil.unescape(AbosMessages.get().USER) + " : ");
		birthdayLabel.setText(MessageUtil.unescape(AbosMessages.get().BIRTHDAY) + " : ");

		sexLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEX) + " : ");
		identificationLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));

		unassociate.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_UNASSOCIATE));
		sugestLabel.setText(AbosMessages.get().MESSAGE_SELECT);
		searchText.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_NAME) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_LAST_NAME) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));

		searchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		accessDataLabel.setText(MessageUtil.unescape(AbosMessages.get().ACCESS_DATA));
		rommVisitedLabel.setText(MessageUtil.unescape(AbosMessages.get().ROOM));
		motivoLabel.setText(MessageUtil.unescape(AbosMessages.get().REASON));

		tablePerson.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME), MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION), MessageUtil.unescape(AbosMessages.get().LABEL_SEX)));

		if (accessRecord == null) {
			saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
			cancelBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		}
		accessDataLabel.getParent().layout(true, true);
		accessDataLabel.getParent().redraw();
		accessDataLabel.getParent().update();
		if (viewAccessControlFragment != null) {
			viewAccessControlFragment.l10n();
		}

	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public Map<String, Control> getControls() {
		return controls;
	}

	public void setControls(Map<String, Control> controls) {
		this.controls = controls;
	}

	public CRUDTreeTable getAccessTable() {
		return accessTable;
	}

	public void setAccessTable(CRUDTreeTable accessTable) {
		this.accessTable = accessTable;
	}

	public IVisualEntityManager getManager() {
		return manager;
	}

	public void setManager(IVisualEntityManager manager) {
		this.manager = manager;
	}

	public void ajustRezise(Composite parent, int height) {
		parent.setSize(parent.getSize().x, height);
		FormData tempo = (FormData) parent.getLayoutData();
		tempo.height = height;
		parent.setLayoutData(tempo);

		parent.setLayoutData(tempo);
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	public void refresh(Composite parent) {
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	public void Search() {
		viewPersonDataComposite.setVisible(false);
		tablePersonComposite.setVisible(true);
		tablePerson.clearRows();
		searchTextConsult = (searchText.getText() != "") ? searchText.getText() : null;
		orderByStringPerson = "firstName";
		direction = 1024;
		tablePerson.getPaginator().goToFirstPage();

		if (tablePerson.getRows().isEmpty()) {
			RetroalimentationUtils.showInformationMessage(register, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
		}

	}

	public void dissociate() {
		viewPersonDataComposite.setVisible(false);
		searchPersonComposite.setVisible(true);
		tablePersonComposite.setVisible(true);
		person = null;

		heightTablePersonComposite = heightTablePerson;
		ajustRezise(personDataComposite, getHeightSearchPersonComposite() + getHeightTablePersonComposite() + 16);
		refresh(personDataComposite.getParent().getShell());

	}

	public void associate() {
		fullName.setText(getPerson().getFullName());
		Image image;
		if (getPerson().getPhoto() == null) {
			image = new Image(Display.getDefault(), AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/photo.png", false).getImageData().scaledTo(100, 100));
		} else {
			image = getPerson().getPhoto().getImage();
		}
		pictureLabel.setImage(image);
		user.setText((getPerson().getUser() != null) ? getPerson().getUser().getUsernameToString() : "-");
		birthday.setText((getPerson().getBirthDate() != null) ? new SimpleDateFormat("dd-MM-yyyy").format(getPerson().getBirthDate()) : "-");

		identification.setText(getPerson().getDNI());
		sex.setText(getPerson().getSex().getNomenclatorName());

		viewPersonDataComposite.setVisible(true);
		ajustRezise(viewPersonDataComposite, 185);
		refresh(personDataComposite.getParent().getShell());

		viewPersonDataComposite.layout(true, true);
		viewPersonDataComposite.redraw();
		viewPersonDataComposite.update();

		searchPersonComposite.setVisible(false);
		tablePersonComposite.setVisible(false);

	}

	public void showPerson() {
		heightTablePerson = tablePerson.getSize().y;
		ajustRezise(personDataComposite, getHeightViewPersonDataComposite() + 5);
		refresh(personDataComposite.getParent().getShell());
	}

	private void searchPerson(int page, int size) {
		tablePerson.clearRows();
		Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		Page<Person> list = ((AccessRecordViewController) controller).getAllManagementSecurityViewController().getPersonService().findAll(library, searchTextConsult, page, size, size, orderByStringPerson);
		tablePerson.setTotalElements((int) list.getTotalElements());
		tablePerson.setRows(list.getContent());
		tablePerson.refresh();
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public int getHeightTablePerson() {
		return heightTablePerson;
	}

	public void setHeightTablePerson(int heightTablePerson) {
		this.heightTablePerson = heightTablePerson;
	}

	public int getHeightPersonDataComposite() {
		return personDataComposite.getSize().y;
	}

	public int getHeightViewPersonDataComposite() {
		return viewPersonDataComposite.getSize().y;
	}

	public int getHeightSearchPersonComposite() {
		return searchPersonComposite.getSize().y;
	}

	public int getHeightTablePersonComposite() {
		return tablePersonComposite.getSize().y;
	}

	public int getHeightWorkerDataComposite() {
		return workerDataComposite.getSize().y;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public Composite getRegister() {
		return register;
	}

	public void setRegister(Composite register) {
		this.register = register;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

}
