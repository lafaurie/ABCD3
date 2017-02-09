package cu.uci.abcd.management.security.communFragment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPResponse;
import com.novell.ldap.LDAPResponseQueue;
import com.novell.ldap.LDAPSearchResults;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.RegisterUser;
import cu.uci.abcd.management.security.ui.controller.UserViewController;
import cu.uci.abcd.management.security.ui.model.Item;
import cu.uci.abcd.management.security.util.Auxiliary;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.ui.Popup;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.Digest;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.UiUtils;
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

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterUserFragment extends FragmentPage implements FragmentContributor {
	private Label registerWorkerLabel;
	private Label ccc;
	private Label aaa;
	private Label userType;
	private ViewController controller;
	private Library library;
	private Ldap lastSelectedLdap;
	private Button activeButton;
	private int derecha;
	private Button gestionSystem;
	private Button opac;
	private Button inactiveButton;
	private boolean flagLocal = true;
	private boolean flagDomain = false;

	public Button getGestionSystem() {
		return gestionSystem;
	}

	public void setGestionSystem(Button gestionSystem) {
		this.gestionSystem = gestionSystem;
	}

	public Button getOpac() {
		return opac;
	}

	public void setOpac(Button opac) {
		this.opac = opac;
	}

	public Button getActiveButton() {
		return activeButton;
	}

	public void setActiveButton(Button activeButton) {
		this.activeButton = activeButton;
	}

	public List<Button> getCheckedList() {
		return checkedList;
	}

	public void setCheckedList(List<Button> checkedList) {
		this.checkedList = checkedList;
	}

	public Button getDomainButton() {
		return domainButton;
	}

	public void setDomainButton(Button domainButton) {
		this.domainButton = domainButton;
	}

	public Text getUserPasswordConfirmText() {
		return userPasswordConfirmText;
	}

	public void setUserPasswordConfirmText(Text userPasswordConfirmText) {
		this.userPasswordConfirmText = userPasswordConfirmText;
	}

	public Button getLocalButton() {
		return localButton;
	}

	public void setLocalButton(Button localButton) {
		this.localButton = localButton;
	}

	public Text getUserPasswordText() {
		return userPasswordText;
	}

	public void setUserPasswordText(Text userPasswordText) {
		this.userPasswordText = userPasswordText;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	private Combo workerTypeCombo;
	private DateTime registerDateTime;
	private Text descriptionText;
	private Button saveBtn;
	private Button cancelBtn;
	private Label workerDataListLabel;
	private Person person;
	private ViewUserFragment viewUserFragment;
	private List<Room> asignedRooms = new ArrayList<>();
	private Link headerCopyCant;
	private Link headerType;
	private Button domainButton;
	private Button localButton;
	private Button checkButton;

	@SuppressWarnings("unused")
	private int direction = 1024;
	@SuppressWarnings("unused")
	private String orderByString = "profileName";
	@SuppressWarnings("unused")
	private String profileNameConsult = null;

	private List<Profile> profileSelected = new ArrayList<>();

	public List<Profile> getProfileSelected() {
		return profileSelected;
	}

	public void setProfileSelected(List<Profile> profileSelected) {
		this.profileSelected = profileSelected;
	}

	private Button checked;

	public Composite row;

	@SuppressWarnings("unused")
	private String bookTitle;
	private String bookAuthor;
	private String publication;

	private Text userPasswordText;
	private Text userPasswordConfirmText;

	// private Text userCheckText;
	// private Text passText;
	private Popup searchUserCredentianls;
	Label iconLabel;

	public Label getIconLabel() {
		return iconLabel;
	}

	public void setIconLabel(Label iconLabel) {
		this.iconLabel = iconLabel;
	}

	public Image getCorrectImage() {
		return correctImage;
	}

	public void setCorrectImage(Image correctImage) {
		this.correctImage = correctImage;
	}

	public Image getIncorrectImage() {
		return incorrectImage;
	}

	public void setIncorrectImage(Image incorrectImage) {
		this.incorrectImage = incorrectImage;
	}

	boolean userCheckedDomain = false;
	boolean userCheckedLocal = false;
	String userChecked = "";
	// UserClass userClass;

	// private List<UserClass> userClassList = new ArrayList<>();

	private List<Button> checkedList = new ArrayList<>();

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Room> getAsignedRooms() {
		return asignedRooms;
	}

	public void setAsignedRooms(List<Room> asignedRooms) {
		this.asignedRooms = asignedRooms;
	}

	private String orderByStringPerson = "firstName";

	private Label sugestLabel;
	private Text searchText;
	private Button searchButton;
	private CRUDTreeTable tablePerson;

	private PagePainter painter;
	private IPersonService personService;
	private String searchTextConsult = null;
	private RegisterUser registerUser;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private ContributorService contributorService;
	private int dimension;

	CRUDTreeTable userTable;
	boolean flag = true;

	public RegisterUserFragment(CRUDTreeTable userTable, User user, Person person, ViewController controller, int dimension) {
		this.userTable = userTable;
		this.user = user;
		this.person = person;
		this.controller = controller;
		this.dimension = dimension;
		this.personService = ServiceProviderUtil.getService(IPersonService.class);
		this.flag = false;
	}

	public RegisterUserFragment(CRUDTreeTable userTable, ViewController controller, Library library, Person person, User user, int dimension, RegisterUser registerUser, ContributorService contributorService) {
		this.userTable = userTable;
		this.controller = controller;
		this.dimension = dimension;
		this.library = library;
		this.person = person;
		this.personService = ServiceProviderUtil.getService(IPersonService.class);
		this.registerUser = registerUser;
		this.user = user;
		this.contributorService = contributorService;
	}

	Composite personDataComposite;
	Composite viewPersonDataComposite;
	Composite searchPersonComposite;
	Composite tablePersonComposite;
	Composite userDataComposite;

	int heightPersonDataComposite;
	int heightViewPersonDataComposite;
	int heightSearchPersonComposite;
	int heightTablePersonComposite;
	int heightWorkerDataComposite;
	int heightTablePerson;

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
		return userDataComposite.getSize().y;
	}

	Label fullName;
	Label userValue;
	Label birthday;
	Label sex;
	Label identification;

	int tableHeight;

	Label pictureLabel;

	private ValidatorUtils validator;

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	Label personDataLabel;
	Group personGroup;
	Label fullNameLabel;
	Label userLabel;
	Label birthdayLabel;
	Label sexLabel;
	Label identificationLabel;
	Button unassociate;

	Composite register;
	Composite show;
	Text userNameText;

	public Text getUserNameText() {
		return userNameText;
	}

	public void setUserNameText(Text userNameText) {
		this.userNameText = userNameText;
	}

	Composite userAndPasswordComposite;

	Label userName;
	Label userPassword;
	Label userPasswordConfirm;

	@SuppressWarnings("unused")
	private Ldap domain;
	Image correctImage;

	public Ldap getLastSelectedLdap() {
		return lastSelectedLdap;
	}

	public void setLastSelectedLdap(Ldap lastSelectedLdap) {
		this.lastSelectedLdap = lastSelectedLdap;
	}

	public boolean isFlagLocal() {
		return flagLocal;
	}

	public void setFlagLocal(boolean flagLocal) {
		this.flagLocal = flagLocal;
	}

	public boolean isFlagDomain() {
		return flagDomain;
	}

	public void setFlagDomain(boolean flagDomain) {
		this.flagDomain = flagDomain;
	}

	Image incorrectImage;

	private Map<String, Control> controls = new HashMap<String, Control>();

	public Map<String, Control> getControls() {
		return controls;
	}

	public void setControls(Map<String, Control> controls) {
		this.controls = controls;
	}

	List<Ldap> ldapList = new ArrayList<>();

	public List<Ldap> getLdapList() {
		return ldapList;
	}

	public void setLdapList(List<Ldap> ldapList) {
		this.ldapList = ldapList;
	}

	Composite msg;

	@SuppressWarnings("serial")
	@Override
	public Control createUIControl(final Composite parent) {

		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		domain = (Ldap) SecurityUtils.getService().getPrincipal().getByKey("domain");
		ldapList = ((UserViewController) controller).getAllManagementSecurityViewController().getLdapService().findAllByLibrary(library.getLibraryID());

		correctImage = new Image(Display.getDefault(), AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/correct.png", false).getImageData());
		incorrectImage = new Image(Display.getDefault(), AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/incorrect.png", false).getImageData());
		validator = new ValidatorUtils(new CustomControlDecoration());
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

		Label separator1 = new Label(register, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(separator1).atTopTo(registerWorkerLabel, 15).atLeft(15).atRight(15);

		ccc = new Label(register, SWT.NONE);
		ccc.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(ccc).atTopTo(separator1, 15).atLeft(15);

		activeButton = new Button(register, SWT.RADIO);
		activeButton.setSelection(true);
		FormDatas.attach(activeButton).atTopTo(ccc, 9).atLeft(15);

		inactiveButton = new Button(register, SWT.RADIO);
		FormDatas.attach(inactiveButton).atTopTo(ccc, 10).atLeftTo(activeButton, 15);

		userType = new Label(register, SWT.NONE);
		userType.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(userType).atTopTo(inactiveButton, 15).atLeft(15);

		gestionSystem = new Button(register, SWT.CHECK);
		FormDatas.attach(gestionSystem).atTopTo(userType, 15).atLeft(15);
		gestionSystem.setSelection(true);

		opac = new Button(register, SWT.CHECK);
		FormDatas.attach(opac).atTopTo(userType, 15).atLeftTo(gestionSystem, 25);

		opac.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (!opac.getSelection()) {
					gestionSystem.setSelection(true);
				}

			}
		});

		gestionSystem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (!gestionSystem.getSelection()) {
					opac.setSelection(true);
				}

			}
		});    

		personDataComposite = new Composite(register, SWT.NONE);
		personDataComposite.setLayout(new FormLayout());
		personDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personDataComposite).atTopTo(opac, 10).atLeft(15).atRight(15);
		   
		
		viewPersonDataComposite = new Composite(personDataComposite, SWT.NONE);
		viewPersonDataComposite.setLayout(new FormLayout());
		viewPersonDataComposite.setVisible(false);
		viewPersonDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(viewPersonDataComposite).atTopTo(personDataComposite).withHeight(220);

		personDataLabel = new Label(viewPersonDataComposite, SWT.NONE);
		FormDatas.attach(personDataLabel).atTopTo(viewPersonDataComposite, 15).atLeft(0);

		personGroup = new Group(viewPersonDataComposite, SWT.NONE);
		personGroup.setLayout(new FormLayout());
		personGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personGroup).atTopTo(viewPersonDataComposite, 20).atLeft(0);

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

		userValue = new Label(personGroup, SWT.NONE);
		FormDatas.attach(userValue).atTopTo(fullNameLabel, 6).atLeftTo(userLabel, 5);

		Label secondSeparator = new Label(personGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(secondSeparator).atTopTo(userValue, 1).atLeftTo(pictureLabel, 5).atRight(5);

		birthdayLabel = new Label(personGroup, SWT.NONE);
		birthdayLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(birthdayLabel).atTopTo(userLabel, 6).atRightTo(userValue, 5);

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
				person = null;
				dissociate();
			}
		});

		searchPersonComposite = new Composite(personDataComposite, SWT.NONE);
		searchPersonComposite.setLayout(new FormLayout());
		searchPersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(searchPersonComposite).atTopTo(personDataComposite);

		aaa = new Label(searchPersonComposite, SWT.NONE);
		aaa.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(aaa).atTopTo(searchPersonComposite, 15).atLeft(0);

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
		tablePersonComposite.setVisible(false);
		FormDatas.attach(tablePersonComposite).atTopTo(searchPersonComposite, 15).atLeft(0).atRight(0);

		tablePerson = new CRUDTreeTable(tablePersonComposite, SWT.NONE);
		tablePerson.setEntityClass(Person.class);
		tablePerson.setSearch(false);
		tablePerson.setSaveAll(false);
		tablePerson.setPageSize(10);

		Column columnPerson = new Column("associate", parent.getDisplay(), new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				Long idPerson = ((Person) event.entity.getRow()).getPersonID();
				User userFound = ((UserViewController) controller).getAllManagementSecurityViewController().getUserService().readUserByPerson(idPerson);
				Person personSelected = (Person) event.entity.getRow();
				if (userFound == null || (user != null && user.getUserID() == userFound.getUserID())) {
					person = personSelected;
					associate();

					userDataComposite.setVisible(true);

					refresh();

				} else {
					// ALERT_PERSON_IS_USER
					RetroalimentationUtils.showErrorMessage(MessageUtil.unescape(AbosMessages.get().ALERT_PERSON_IS_USER));

				}
			}
		});
		columnPerson.setToolTipText(MessageUtil.unescape(AbosMessages.get().BUTTON_ASSOCIATE));
		tablePerson.addActionColumn(columnPerson);

		TreeTableColumn columnsPerson[] = { new TreeTableColumn(60, 0, "getFullName"), new TreeTableColumn(20, 1, "getDNI"), new TreeTableColumn(20, 2, "getSex.getNomenclatorDescription") };
		tablePerson.createTable(columnsPerson);

		tablePerson.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 8817895862824622805L;

			@Override
			public void handleEvent(Event event) {
				refresh();
			}
		});

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
				refresh();
			}
		});
		FormDatas.attach(tablePerson).atTopTo(tablePersonComposite).atLeft().atRight();

		userDataComposite = new Composite(register, SWT.NONE);
		userDataComposite.setLayout(new FormLayout());
		userDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		userDataComposite.setVisible(false);
		FormDatas.attach(userDataComposite).atTopTo(personDataComposite, 5).atLeft(15).atRight(15);

		userAndPasswordComposite = new Composite(userDataComposite, SWT.NONE);
		userAndPasswordComposite.setLayout(new FormLayout());
		userAndPasswordComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(userAndPasswordComposite);
		FormDatas.attach(userAndPasswordComposite).atLeft(-10).atRight(-10);

		workerDataListLabel = new Label(userAndPasswordComposite, SWT.NORMAL);
		painter.addHeader(workerDataListLabel);

		if (!ldapList.isEmpty()) {
			domainButton = new Button(userAndPasswordComposite, SWT.RADIO);
			domainButton.setSelection(true);
			flagDomain = true;
			flagLocal = false;
			painter.add(domainButton, Percent.W10);

			localButton = new Button(userAndPasswordComposite, SWT.RADIO);
			painter.add(localButton, Percent.W25);
			ajustRezise(userAndPasswordComposite, 145);
			refresh(userAndPasswordComposite);

			localButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (localButton.getSelection()) {
						flagDomain = false;
						flagLocal = true;
						iconLabel.setVisible(false);
						checkButton.setVisible(false);
						iconLabel.setImage(correctImage);
						if ((user == null || (user != null && user.getDomain() != null)) && flag) {
							ajustRezise(userAndPasswordComposite, 220);
							refresh(userAndPasswordComposite);
							refresh(userAndPasswordComposite.getParent());
						}

						validator.applyValidator(userPasswordText, 30, "userPasswordText111");
						validator.applyValidator(userPasswordText, "userPasswordTextRequired", DecoratorType.REQUIRED_FIELD, true);
						validator.applyValidator(userPasswordConfirmText, 30, "userPasswordConfirmText111");
						validator.applyValidator(userPasswordConfirmText, "userPasswordConfirmTextRequired", DecoratorType.REQUIRED_FIELD, true);

					}
					refresh();
				}
			});

			domainButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (domainButton.getSelection()) {
						flagDomain = true;
						flagLocal = false;

						iconLabel.setVisible(true);
						checkButton.setVisible(true);
						ajustRezise(userAndPasswordComposite, 145);
						refresh(userAndPasswordComposite);
						refresh(userAndPasswordComposite.getParent());
						iconLabel.setVisible(true);
						iconLabel.setToolTipText(MessageUtil.unescape(AbosMessages.get().USER_NOT_CHECKED));
						iconLabel.setImage(incorrectImage);
						iconLabel.getParent().layout(true, true);
						iconLabel.getParent().redraw();
						iconLabel.getParent().update();

						validator.decorationFactory.removeControlDecoration("userPasswordText111");
						validator.decorationFactory.removeControlDecoration("userPasswordTextRequired");
						validator.decorationFactory.removeControlDecoration("userPasswordConfirmText111");
						validator.decorationFactory.removeControlDecoration("userPasswordConfirmTextRequired");

					}
					refresh();
				}
			});

		}

		painter.reset();
		userName = new Label(userAndPasswordComposite, SWT.NONE);
		painter.add(userName, Percent.W20);

		userNameText = new Text(userAndPasswordComposite, SWT.NONE);
		painter.add(userNameText, Percent.W15);

		validator.applyValidator(userNameText, "userNameTextRequired", DecoratorType.REQUIRED_FIELD, true);

		validator.applyValidator(userNameText, "userNameText1", DecoratorType.USER_NAME, true, 20);

		painter.reset();

		userNameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (userNameText.getText().length() > 0) {
					if (!ldapList.isEmpty()) {
						iconLabel.setToolTipText(MessageUtil.unescape(AbosMessages.get().USER_NOT_CHECKED));
						iconLabel.setImage(incorrectImage);
						iconLabel.getParent().layout(true, true);
						iconLabel.getParent().redraw();
						iconLabel.getParent().update();
					}
				}
			}
		});

		userPassword = new Label(userAndPasswordComposite, SWT.NONE);
		painter.add(userPassword, Percent.W20);

		userPasswordText = new Text(userAndPasswordComposite, SWT.PASSWORD);
		painter.add(userPasswordText, Percent.W15);
		painter.reset();

		validator.applyValidator(userPasswordText, 50);

		userPasswordConfirm = new Label(userAndPasswordComposite, SWT.NONE);
		painter.add(userPasswordConfirm, Percent.W20);

		userPasswordConfirmText = new Text(userAndPasswordComposite, SWT.PASSWORD);
		painter.add(userPasswordConfirmText, Percent.W15);

		validator.applyValidator(userPasswordConfirmText, 50);

		painter.reset();
		painter.add(new Label(userAndPasswordComposite, SWT.NONE), Percent.W100);

		if (!ldapList.isEmpty()) {
			iconLabel = new Label(userAndPasswordComposite, SWT.NONE);
			FormDatas.attach(iconLabel).atTopTo(userNameText, -20).atLeftTo(userNameText, 15);

			checkButton = new Button(userAndPasswordComposite, SWT.NONE);

			FormDatas.attach(checkButton).atLeftTo(userNameText, 35).atTopTo(userNameText, -23).withHeight(20);

			checkButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = -3999754098778755988L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (userNameText.getText().length() > 0) {
						searchUserCredentianls = new LoginSearchUser(personDataComposite.getShell(), SWT.APPLICATION_MODAL | SWT.CLOSE, AbosMessages.get().AUTHENTICATE, personDataComposite, new SelectionListener() {
							private static final long serialVersionUID = -5702216441589389303L;

							@Override
							public void widgetSelected(SelectionEvent arg0) {
								if (((LoginSearchUser) searchUserCredentianls).getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
									RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
								} else {
									if (((LoginSearchUser) searchUserCredentianls).getValidator().decorationFactory.AllControlDecorationsHide()) {
										try {
											search();
										} catch (LoginException | UnsupportedEncodingException | LDAPException e) {
											e.printStackTrace();
										}
									} else {
										RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
									}
								}
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent arg0) {
							}
						});
						// FIXME CAlcular medio de pantalla y dimensiones
						searchUserCredentianls.setBounds(500, 200, 300, 220);
						searchUserCredentianls.open();
						// ((SearchUserCredentianls) searchUserCredentianls)
						// .selectDomain(library);
						((LoginSearchUser) searchUserCredentianls).setUserName(userNameText.getText());

					} else {
						RetroalimentationUtils.showErrorMessage(msg, MessageUtil.unescape(AbosMessages.get().SHOULD_ESPECIFICAD_USER_NAME));
					}
				}
			});
		}

		// Composite last = userAndPasswordComposite;
		// if(user==null || (user!=null && user.getSystemuser() ) ){
		Label bbb = new Label(userDataComposite, SWT.NONE);
		bbb.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		bbb.setText(AbosMessages.get().CONSULT_PROFILES);
		FormDatas.attach(bbb).atTopTo(userAndPasswordComposite, 10).atLeft(0);

		final Composite headers = new Composite(userDataComposite, SWT.BORDER);
		headers.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		headers.setLayout(new FormLayout());
		FormDatas.attach(headers).atTopTo(bbb, 10).atLeft(0).atRight(0).withHeight(20);

		derecha = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;

		Composite headerCopyCantCompo = new Composite(headers, SWT.BORDER);
		headerCopyCantCompo.setBackground(headers.getBackground());
		headerCopyCantCompo.setLayout(new FormLayout());
		FormDatas.attach(headerCopyCantCompo).atTop(-10).atRight(-20).atBottom(-12).atLeft(derecha / 4);

		headerCopyCant = new Link(headerCopyCantCompo, 0);

		headerCopyCant.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(headerCopyCant).atTop(7).atBottom(5).atLeft(15);

		Composite headerTypeCompo = new Composite(headers, SWT.BORDER);
		headerTypeCompo.setBackground(headers.getBackground());
		headerTypeCompo.setLayout(new FormLayout());
		FormDatas.attach(headerTypeCompo).atTop(-10).atBottom(-12).atRightTo(headerCopyCantCompo, 0).atLeft(22);

		headerType = new Link(headerTypeCompo, 0);

		headerType.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(headerType).atTop(7).atBottom(5).atLeft(15);

		Composite headerLeft = new Composite(headers, SWT.BORDER);
		headerLeft.setBackground(headers.getBackground());
		headerLeft.setLayout(new FormLayout());
		FormDatas.attach(headerLeft).atTop(-10).atBottom(-12).atRightTo(headerTypeCompo, 0).atLeft(-20);

		Collection<Profile> list = ((UserViewController) controller).getAllManagementSecurityViewController().getProfileService().findProfileByLibarry(library);

		Composite header = new Composite(userDataComposite, SWT.BORDER);
		header.setLayout(new FormLayout());
		header.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(header).atLeft(0).atRight(0).atTopTo(headers, -1);

		Composite last = headers;
		for (final Profile profile : list) {
			Item item = new Item(profile);
			boolean checked = false;
			if (user != null) {
				List<Long> listIds = new ArrayList<>();
				for (Profile profile1 : user.getAsignedProfiless()) {
					listIds.add(profile1.getId());
				}
				if (listIds.contains(profile.getId())) {
					checked = true;
					profileSelected.add(profile);
				}
			}
			row = CreateRows(header, checked, item);
			FormDatas.attach(row).atTopTo(last, 0).atLeft(0).atRight(0);
			last = row;
		}
   
		// }

		if (user == null) {
			cancelBtn = new Button(userDataComposite, SWT.PUSH);
			FormDatas.attach(cancelBtn).atTopTo(last, 10).atRight(0).withHeight(23);

			/*
		 
		 */

			cancelBtn.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
						private static final long serialVersionUID = 1L;

						@Override
						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
								registerUser.notifyListeners(SWT.Dispose, new Event());
							}
						}
					});

				}
			});

			saveBtn = new Button(userDataComposite, SWT.PUSH);
			FormDatas.attach(saveBtn).atTopTo(last, 10).atRightTo(cancelBtn, 5).withHeight(23);

			saveBtn.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 2615553092700551346L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
						RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
					} else {
						if (person != null) {
							if ((gestionSystem.getSelection() && profileSelected.size() > 0) || (!gestionSystem.getSelection() && opac.getSelection())) {
								if ((flagDomain && Auxiliary.getValue(userNameText.getText()) != null) || getValidator().decorationFactory.AllControlDecorationsHide() || (person.getUser() != null && !person.getUser().getSystemuser())) {

									String userName = userNameText.getText().replaceAll(" +", " ").trim();
									boolean flag = false;
									if (ldapList.isEmpty() || (!ldapList.isEmpty() && iconLabel.getImage() == correctImage) || !iconLabel.isVisible() || (person.getUser() != null && !person.getUser().getSystemuser())) {
										flag = (person.getUser() != null && !person.getUser().getSystemuser());
										Ldap ldap = null;
										User userExist = null;
										if (ldapList.isEmpty() && !flag) {
											ldap = null;
											userExist = ((UserViewController) controller).findLocalUser(userName, null, library);
										} else {
											if (!flag) {
												ldap = lastSelectedLdap;
												if (flagDomain) {
													userExist = ((UserViewController) controller).findDomainUser(userName, library, ldap);
												} else {
													ldap = null;
													userExist = ((UserViewController) controller).findLocalUser(userName, null, library);

												}
											}
										}

										if (userExist != null && !flag) {
											RetroalimentationUtils.showErrorMessage(msg, MessageUtil.unescape(AbosMessages.get().USER_ALREADY_EXIST));
										} else {

											if (flag || (!flag && ((flagLocal && userPasswordText.getText().equals(userPasswordConfirmText.getText())) || flagDomain))) {
												String userPassword = null;
												if (flagDomain && !flag) {
													userPassword = null;
												} else {
													if (!flag) {
														userPassword = Digest.digest(userPasswordText.getText(), "SHA1");
													}
												}

												if (user != null || person.getUser() == null) {
													user = new User();
												} else {
													user = person.getUser();
												}

												user.setSystemuser(gestionSystem.getSelection());
												user.setOpacuser(opac.getSelection());

												java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
												user.setCreationDate(sqlDate);
												if (!flag) {
													user.setLibraryOwner(person.getLibrary());
													user.setPerson(person);
													user.setDomain(ldap);
													user.setUsername(userName);
													user.setUserPassword(userPassword);

												}
												// if( opac.getSelection() &&
												// !gestionSystem.getSelection()
												// ){

												// List<Profile> tmp = new
												// ArrayList<>();
												// user.setAsignedProfiless(tmp);
												// }else{
												user.setAsignedProfiless(profileSelected);
												// }
												user.setEnabled(activeButton.getSelection());
												User userSaved = ((UserViewController) controller).addUser(user);

												registerUser.notifyListeners(SWT.Dispose, new Event());
												contributorService.selectContributor("viewUser", userSaved, registerUser, contributorService);

											} else {
												if (!flag) {
													RetroalimentationUtils.showErrorMessage(msg, MessageUtil.unescape(AbosMessages.get().PASSWORD_NOT_MATCH));
												}
											}
										}

									} else {
										RetroalimentationUtils.showErrorMessage(msg, MessageUtil.unescape(AbosMessages.get().ENTERED_USER_IS_NOT_CORRECT));
									}
								} else {
									RetroalimentationUtils.showErrorMessage(msg, MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
								}
							} else {
								RetroalimentationUtils.showErrorMessage(msg, MessageUtil.unescape(AbosMessages.get().SHOULD_SELECT_PROFILE));
							}
						} else {
							RetroalimentationUtils.showErrorMessage(msg, MessageUtil.unescape(AbosMessages.get().SHOULD_INDICATE_A_PERSON_AS_USER));
						}
					}
				}
			});
			saveBtn.getShell().setDefaultButton(saveBtn);
		}
		LoadUserData();
		l10n();
		return parent;
	}

	public void LoadUserData() {
		// initialize(rommVisited, ((AccessRecordViewController) controller)
		// .findRoomByLibrary(library.getLibraryID()));
		if (user != null) {
			// UiUtils.selectValue(rommVisited, accessRecord.getRoom());
			// motivo.setText(accessRecord.getMotivation() != null ?
			// accessRecord.getMotivation() : "");
			userDataComposite.setVisible(true);

			if (user.getEnabled()) {
				activeButton.setSelection(true);
				inactiveButton.setSelection(false);
			} else {
				activeButton.setSelection(false);
				inactiveButton.setSelection(true);
			}

			opac.setSelection(user.getOpacuser());
			gestionSystem.setSelection(user.getSystemuser());
			/*
			 * if( user.getOpacuser() && !user.getSystemuser() ){ for (int i =
			 * 0; i < checkedList.size(); i++) {
			 * checkedList.get(i).setEnabled(false); } }else{ for (int i = 0; i
			 * < checkedList.size(); i++) { checkedList.get(i).setEnabled(true);
			 * } }
			 */

			userNameText.setText(user.getUsername());
			if (!ldapList.isEmpty()) {
				iconLabel.setImage(correctImage);
				iconLabel.getParent().layout(true, true);
				iconLabel.getParent().redraw();
				iconLabel.getParent().update();
				iconLabel.setToolTipText(MessageUtil.unescape(AbosMessages.get().USER_CORRECT));
			}

			if (!ldapList.isEmpty()) {
				if (user.getUserPassword() == null) {
					domainButton.setSelection(true);
					localButton.setSelection(false);
				} else {
					localButton.setSelection(true);
					domainButton.setSelection(false);

					iconLabel.setVisible(false);
					checkButton.setVisible(false);
					iconLabel.setImage(correctImage);
				}
			}
			// userPasswordText.setText(
			// (user.getUserPassword()==null)?"":user.getUserPassword() );
			// userPasswordConfirmText.setText(
			// (user.getUserPassword()==null)?"":user.getUserPassword() );

			if (user.getDomain() == null) {
				flagDomain = false;
				flagLocal = true;
				if (localButton == null) {
					ajustRezise(userAndPasswordComposite, 100);
				} else {
					ajustRezise(userAndPasswordComposite, 145);
				}
				refresh(userAndPasswordComposite);
				refresh(userAndPasswordComposite.getParent());

				// iconLabel.setVisible(false);
				// checkButton.setVisible(false);
				// iconLabel.setImage(correctImage);

			}
			refresh();

			associate();

			if (user.getDomain() != null) {
				localButton.setEnabled(false);
				// localButton.setVisible(false);
			}
		}
	}

	public Combo getWorkerTypeCombo() {
		return workerTypeCombo;
	}

	public void setWorkerTypeCombo(Combo workerTypeCombo) {
		this.workerTypeCombo = workerTypeCombo;
	}

	public Text getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(Text descriptionText) {
		this.descriptionText = descriptionText;
	}

	public DateTime getRegisterDateTime() {
		return registerDateTime;
	}

	public void setRegisterDateTime(DateTime registerDateTime) {
		this.registerDateTime = registerDateTime;
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	private void searchPerson(int page, int size) {
		tablePerson.clearRows();
		Library library = (Library) SecurityUtils.getPrincipal().getByKey("library");
		Page<Person> list = personService.findAll(library, searchTextConsult, page, size, size, orderByStringPerson);
		tablePerson.setTotalElements((int) list.getTotalElements());
		tablePerson.setRows(list.getContent());
		tablePerson.refresh();
	}

	public void ajustRezise(Composite parent, int height) {
		parent.setSize(parent.getSize().x, height);
		FormData tempo = (FormData) parent.getLayoutData();
		tempo.height = height;
		parent.setLayoutData(tempo);
		refresh();
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

	public void associate() {
		fullName.setText((getPerson() != null) ? getPerson().getFullName() : "< No hay datos >");

		Image image;
		if (getPerson() == null || getPerson().getPhoto() == null) {
			image = new Image(Display.getDefault(), AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/photo.png", false).getImageData().scaledTo(100, 100));
		} else {
			image = getPerson().getPhoto().getImage();
		}
		pictureLabel.setImage(image);

		userValue.setText((getUser() != null) ? getUser().getUsernameToString() : "-");

		birthday.setText((getPerson() != null) ? new SimpleDateFormat("dd-MM-yyyy").format(getPerson().getBirthDate()) : "< No hay datos >");

		identification.setText((getPerson() != null) ? getPerson().getDNI() : "< No hay datos >");
		// THERE_IS_NO_DATA
		sex.setText((getPerson() != null) ? getPerson().getSex().getNomenclatorName() : "< No hay datos >");

		viewPersonDataComposite.setVisible(true);
		showPerson();
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
		// !ldapList.isEmpty()
		if (person != null && localButton != null && !localButton.getSelection()) {
			ajustRezise(userAndPasswordComposite, 145);
			refresh(userAndPasswordComposite);
			refresh(userAndPasswordComposite.getParent());
			refresh();
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

	@Override
	public void l10n() {
		ccc.setText(AbosMessages.get().LABEL_STATE);
		activeButton.setText(AbosMessages.get().LABEL_STATE_ACTIVE);
		inactiveButton.setText(AbosMessages.get().LABEL_STATE_INACTIVE);
		userType.setText(AbosMessages.get().LABEL_USER_TYPE);
		gestionSystem.setText(AbosMessages.get().LABEL_SYSTEM);
		opac.setText(AbosMessages.get().OPAC);
		aaa.setText(AbosMessages.get().LABEL_PERSON_DATA);
		tablePerson.l10n();
		if (user == null) {
			registerWorkerLabel.setText(MessageUtil.unescape(AbosMessages.get().REGISTER_USER));
		} else {
			registerWorkerLabel.setText(MessageUtil.unescape(AbosMessages.get().UPDATE_USER));
		}

		personDataLabel.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PERSON));
		personGroup.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA));
		fullNameLabel.setText(MessageUtil.unescape(AbosMessages.get().NAME_AND_SURNAME) + " : ");
		userLabel.setText(MessageUtil.unescape(AbosMessages.get().USER) + " : ");
		birthdayLabel.setText(MessageUtil.unescape(AbosMessages.get().BIRTHDAY) + " : ");
		sexLabel.setText(MessageUtil.unescape(AbosMessages.get().SEX) + " : ");
		identificationLabel.setText(MessageUtil.unescape(AbosMessages.get().IDENTIFICATION) + " : ");
		unassociate.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_UNASSOCIATE));
		sugestLabel.setText(MessageUtil.unescape(AbosMessages.get().SHOULD_INDICATE_A_PERSON_AS_USER));

		searchText.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_NAME) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_LAST_NAME) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));

		searchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));

		workerDataListLabel.setText(MessageUtil.unescape(MessageUtil.unescape(AbosMessages.get().USER_DATA)));

		userName.setText(MessageUtil.unescape(MessageUtil.unescape(AbosMessages.get().USER)));
		userPassword.setText(MessageUtil.unescape(MessageUtil.unescape(AbosMessages.get().PASSWORD)));
		userPasswordConfirm.setText(MessageUtil.unescape(MessageUtil.unescape(AbosMessages.get().CONFIRM_PASSWORD)));

		tablePerson.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().NAME_AND_SURNAME), MessageUtil.unescape(AbosMessages.get().IDENTIFICATION), MessageUtil.unescape(AbosMessages.get().SEX)));
		if (!ldapList.isEmpty()) {
			domainButton.setText(MessageUtil.unescape(AbosMessages.get().DOMAIN));
			localButton.setText(MessageUtil.unescape(AbosMessages.get().LOCAL));
		}
		if (!ldapList.isEmpty()) {
			checkButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		}
		if (user == null) {
			saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
			cancelBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		}
		headerCopyCant.setText(MessageUtil.unescape(AbosMessages.get().PERMISSIONS));
		headerType.setText(MessageUtil.unescape(AbosMessages.get().PROFILE));
		if (viewUserFragment != null) {
			viewUserFragment.l10n();
		}
	}

	public Composite CreateRows(Composite parent, boolean select, final Item item) {
		parent = new Composite(userDataComposite, SWT.BORDER);
		parent.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		parent.setLayout(new FormLayout());
		try {
			bookAuthor = item.getProfile().getProfileName();
			if (derecha < 850) {
				publication = item.Order(derecha / 68);
			} else {
				if (derecha < 1000) {
					publication = item.Order(derecha / 40);
				} else {
					publication = item.Order(derecha / 20);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Composite copyCantCompo = new Composite(parent, SWT.BORDER);
		copyCantCompo.setBackground(parent.getBackground());
		copyCantCompo.setLayout(new FormLayout());
		FormDatas.attach(copyCantCompo).atTop(-2).atRight(0).atBottom(-2).atLeft(derecha / 4 + 20);
		Label copyCantLabel = new Label(copyCantCompo, 0);
		copyCantLabel.setText(publication);
		FormDatas.attach(copyCantLabel).atTop(10).atLeft(10).atBottom(10);

		Composite buttonCompo = new Composite(parent, SWT.BORDER);
		buttonCompo.setBackground(parent.getBackground());
		buttonCompo.setLayout(new FormLayout());
		FormDatas.attach(buttonCompo).atTop(-2).atBottom(-2).atLeft(0).withWidth(40);
		checked = new Button(buttonCompo, SWT.CHECK);
		checked.setSelection(select);
		FormDatas.attach(checked).atTop(5).atLeft(10);
		checkedList.add(checked);
		item.setChecked(checked);
		item.getChecked().addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -2957428772556980347L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (item.getChecked().getSelection()) {
					profileSelected.add(item.getProfile());
				} else {
					profileSelected.remove(item.getProfile());
				}
			}
		});
		Composite typeCompo = new Composite(parent, SWT.BORDER);
		typeCompo.setBackground(parent.getBackground());
		typeCompo.setLayout(new FormLayout());
		FormDatas.attach(typeCompo).atTop(-2).atBottom(-2).atRightTo(copyCantCompo).atLeftTo(buttonCompo);
		Label typeLabel = new Label(typeCompo, 0);
		typeLabel.setText(bookAuthor);
		FormDatas.attach(typeLabel).atTop(10).atLeft(10);
		return parent;
	}

	public void search() throws LoginException, UnsupportedEncodingException, LDAPException {
		iconLabel.setVisible(true);
		String username = ((LoginSearchUser) searchUserCredentianls).getUserText().getText();
		String userPassword = ((LoginSearchUser) searchUserCredentianls).getPasswordText().getText();
		lastSelectedLdap = (Ldap) UiUtils.getSelected(((LoginSearchUser) searchUserCredentianls).getDomainList());
		if (((LoginSearchUser) searchUserCredentianls).getChkrecordar().getSelection()) {
			try {
				RWT.getSettingStore().setAttribute("userSearch", username);
				RWT.getSettingStore().setAttribute("passWordSearch", userPassword);
				RWT.getSettingStore().setAttribute("domainSearch", lastSelectedLdap.getDomain());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				RWT.getSettingStore().removeAttribute("userSearch");
				RWT.getSettingStore().removeAttribute("passWordSearch");
				RWT.getSettingStore().removeAttribute("domainSearch");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LDAPConnection connection = new LDAPConnection();
		LDAPResponseQueue responseQueue = null;
		byte[] passwordBytes = userPassword.getBytes("UTF8");
		String domain = lastSelectedLdap.getDomain();
		String loginDN = String.format("%s@%s", username, domain);
		connection.connect(lastSelectedLdap.getHost(), lastSelectedLdap.getPort());
		responseQueue = connection.bind(LDAPConnection.LDAP_V3, loginDN, passwordBytes, (LDAPResponseQueue) null);
		LDAPResponse response = (LDAPResponse) responseQueue.getResponse();
		int bindResult = response.getResultCode();
		if (bindResult == LDAPException.SUCCESS) {
			String[] baseDnArray = domain.split("\\.");
			String baseDn = "dc=" + baseDnArray[0];
			for (int i = 1; i < baseDnArray.length; i++) {
				baseDn = baseDn + ", dc=" + baseDnArray[i];
			}
			// String baseDn = "dc=uci,dc=cu";
			LDAPSearchResults results = connection.search(baseDn, LDAPConnection.SCOPE_SUB, "(&(sAMAccountName=" + ((LoginSearchUser) searchUserCredentianls).getUserName() + "))", null, false);
			try {
				results.next();
				((LoginSearchUser) searchUserCredentianls).close();
				if (domainButton.getSelection()) {
					iconLabel.setImage(correctImage);
					iconLabel.setToolTipText(MessageUtil.unescape(AbosMessages.get().USER_CORRECT));
				} else {
					iconLabel.setImage(incorrectImage);
					iconLabel.setToolTipText(MessageUtil.unescape(AbosMessages.get().USER_NOT_AVAILABLE));
				}
				iconLabel.getParent().layout(true, true);
				iconLabel.getParent().redraw();
				iconLabel.getParent().update();
				connection.disconnect();
				userCheckedDomain = true;
				userChecked = ((LoginSearchUser) searchUserCredentianls).getUserName();
			} catch (LDAPException ex) {
				((LoginSearchUser) searchUserCredentianls).close();
				if (domainButton.getSelection()) {
					iconLabel.setImage(incorrectImage);
					iconLabel.setToolTipText(MessageUtil.unescape(AbosMessages.get().USER_NOT_EXIST_IN_DOMAIN));

				} else {
					iconLabel.setImage(correctImage);
					iconLabel.setToolTipText(MessageUtil.unescape(AbosMessages.get().USER_AVAILABLE));
				}
				iconLabel.getParent().layout(true, true);
				iconLabel.getParent().redraw();
				iconLabel.getParent().update();
				connection.disconnect();
				userCheckedDomain = false;
				userChecked = "";
			}
		} else {
			((LoginSearchUser) searchUserCredentianls).close();
			iconLabel.setImage(incorrectImage);
			iconLabel.setToolTipText(MessageUtil.unescape(AbosMessages.get().USER_NOT_CHECKED));
			iconLabel.getParent().layout(true, true);
			iconLabel.getParent().redraw();
			iconLabel.getParent().update();
			connection.disconnect();
			RetroalimentationUtils.showInformationMessage(MessageUtil.unescape(AbosMessages.get().USER_OR_PASSWORD_INCORRECT));
		}

	}
}
