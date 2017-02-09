package cu.uci.abcd.administration.library.communFragment;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.rap.rwt.RWT;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.ProviderViewController;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
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

public class RegisterProviderFragment extends ContributorPage implements
		FragmentContributor {

	private ViewController controller;
	private Label registerProviderLabel;
	private Composite parent;
	@SuppressWarnings("unused")
	private Button defaultButton;
	private Label separador;
	private Text searchText;
	private Button searchButton;
	private Text searchPersonText;
	private Button searchPersonButton;
	private String searchTextConsult = null;
	private String searchTextPersonConsult = null;
	private CRUDTreeTable tablePerson;
	private CRUDTreeTable tablePersonPerson;
	private Map<String, Control> controls = new HashMap<String, Control>();
	private Label providerData;
	private Label providerNameLabel;
	private Text providerNameText;
	private Label rifLabel;
	private Text rifText;
	private Label nitLabel;
	private Text nitText;
	private Combo providerStateCombo;
	private Label providerStateLabel;
	private Label providerTypeLabel1;
	private Button canjeCheckButton1;
	private Button donateCheckButton1;
	private Button commercialCheckButton1;
	private Person representant = null;
	private Person representantPerson = null;
	private Composite otherComposite;
	private ValidatorUtils validatorIntitutional;
	private ValidatorUtils validatorPersonal;
	private int dimension;
	private int option;
	private Label sugestLabel;
	private Label sugestPersonLabel;
	private Label pictureLabel;
	private Label fullName;
	private Label picturePersonLabel;
	private Label fullPersonName;
	private Label user;
	private Label userPerson;
	private Label birthday;
	private Label sex;
	private Label birthdayPerson;
	private Label sexPerson;
	private Label identification;
	private Label identificationPerson;
	private Label personDataLabel;
	private Group personGroup;
	private Label personDataPersonLabel;
	private Group personPersonGroup;
	private Label fullNameLabel;
	private Label fullNamePersonLabel;
	private Label userLabel;
	private Label birthdayLabel;
	private Label sexLabel;
	private Label userPersonLabel;
	private Label birthdayPersonLabel;
	private Label sexPersonLabel;
	private Label identificationLabel;
	private Label identificationPersonLabel;
	private Label firstPhoneNumberLabel;
	private Text firstPhoneNumberText;
	private Label secondPhoneNumberLabel;
	private Text secondPhoneNumberText;
	private Label faxLabel;
	private Text faxText;
	private Label emailLabel;
	private Text emailText;
	private Label webPageLabel;
	private Text webPageText;
	private Label addreesLabel;
	private Text addreesText;
	private Button unassociate;
	private Button unassociatePerson;
	private String orderByStringPerson = "firstName";
	private String orderByStringPersonPerson = "firstName";
	private PagePainter painter;
	private IPersonService personService;
	private Provider provider;
	private Composite personDataComposite;
	private Composite viewPersonDataComposite;
	private Composite viewPersonDataPersonComposite;
	private Composite searchPersonComposite;
	private Composite searchPersonPersonComposite;
	private Composite tablePersonComposite;
	private Composite tablePersonPersonComposite;
	private int heightTablePerson;
	private int heightTablePersonPerson;
	private Label countryLabel;
	private Combo countryCombo;
	private Composite personDataPersonComposite;
	private Button intitutionalButton;
	private Button personButton;
	private Composite intitutionalComposite;
	private Composite personComposite;
	private Label firstPhoneNumberLabel1;
	private Text firstPhoneNumberText1;
	private Label secondPhoneNumberLabel1;
	private Text secondPhoneNumberText1;
	private Label faxLabel1;
	private Text faxText1;
	private Label emailLabel1;
	private Text emailText1;
	private Label webPageLabel1;
	private Text webPageText1;
	private Label addreesLabel1;
	private Text addreesText1;
	private Combo providerStateCombo1;
	private Label providerStateLabel1;
	private Label countryLabel1;
	private Combo countryCombo1;
	private Composite midle;
	private Library library;

	public RegisterProviderFragment(Provider provider,
			ViewController controller, int dimension, int option) {
		this.controller = controller;
		this.provider = provider;
		this.personService = ServiceProviderUtil
				.getService(IPersonService.class);
		this.dimension = dimension;
		this.option = option;
	}

	public RegisterProviderFragment(Provider provider,
			ViewController controller, Person representant, int dimension,
			int option) {
		this.controller = controller;
		this.provider = provider;
		this.representant = representant;
		this.personService = ServiceProviderUtil
				.getService(IPersonService.class);
		this.dimension = dimension;
		this.option = option;
	}

	@Override
	public Control createUIControl(final Composite shell) {
		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		painter = new FormPagePainter();
		validatorIntitutional = new ValidatorUtils(
				new CustomControlDecoration());
		validatorPersonal = new ValidatorUtils(new CustomControlDecoration());
		painter.setDimension(dimension);
		painter.addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");
		parent = new Composite(shell, SWT.NORMAL);
		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		registerProviderLabel = new Label(parent, SWT.NONE);
		painter.addHeader(registerProviderLabel);

		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);

		providerData = new Label(parent, SWT.NONE);
		painter.addHeader(providerData);

		intitutionalButton = new Button(parent, SWT.RADIO);
		intitutionalButton.setSelection(true);
		controls.put("intitutionalButton", intitutionalButton);
		painter.add(intitutionalButton);

		personButton = new Button(parent, SWT.RADIO);
		controls.put("personButton", personButton);
		painter.add(personButton);
		painter.reset();

		personButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			public void widgetSelected(SelectionEvent e) {
				showCompositePerson();

			}
		});

		intitutionalButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			public void widgetSelected(SelectionEvent e) {
				showCompositeIntitutional();

			}
		});

		midle = new Composite(shell, SWT.NONE);
		painter.addComposite(midle);
		controls.put("midle", midle);
		intitutionalComposite = new Composite(midle, SWT.NORMAL);
		painter.addComposite(intitutionalComposite);
		intitutionalComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		controls.put("intitutionalComposite", intitutionalComposite);

		providerNameLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(providerNameLabel);

		providerNameText = new Text(intitutionalComposite, SWT.NONE);
		controls.put("providerNameText", providerNameText);
		painter.add(providerNameText);

		firstPhoneNumberLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(firstPhoneNumberLabel);

		firstPhoneNumberText = new Text(intitutionalComposite, SWT.NONE);
		controls.put("firstPhoneNumberText", firstPhoneNumberText);
		painter.add(firstPhoneNumberText);

		painter.reset();

		rifLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(rifLabel);

		rifText = new Text(intitutionalComposite, SWT.NONE);
		controls.put("rifText", rifText);
		painter.add(rifText);

		secondPhoneNumberLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(secondPhoneNumberLabel);

		secondPhoneNumberText = new Text(intitutionalComposite, SWT.NONE);
		controls.put("secondPhoneNumberText", secondPhoneNumberText);
		painter.add(secondPhoneNumberText);

		painter.reset();

		nitLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(nitLabel);

		nitText = new Text(intitutionalComposite, SWT.NONE);
		controls.put("nitText", nitText);
		painter.add(nitText);

		faxLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(faxLabel);

		faxText = new Text(intitutionalComposite, SWT.NONE);
		controls.put("faxText", faxText);
		painter.add(faxText);

		painter.reset();

		emailLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(emailLabel);

		emailText = new Text(intitutionalComposite, SWT.NONE);
		controls.put("emailText", emailText);
		painter.add(emailText);

		webPageLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(webPageLabel);

		webPageText = new Text(intitutionalComposite, SWT.NONE);
		controls.put("webPageText", webPageText);
		painter.add(webPageText);

		painter.reset();

		providerStateLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(providerStateLabel);

		providerStateCombo = new Combo(intitutionalComposite, SWT.READ_ONLY);
		controls.put("providerStateCombo", providerStateCombo);
		painter.add(providerStateCombo);

		addreesLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(addreesLabel);

		addreesText = new Text(intitutionalComposite, SWT.NONE | SWT.WRAP
				| SWT.V_SCROLL);
		controls.put("addreesText", addreesText);
		painter.add(addreesText);

		painter.reset();
		if (((FormPagePainter) painter).getDimension() < 840) {
			painter.add(new Label(intitutionalComposite, SWT.NONE));
			painter.add(new Label(intitutionalComposite, SWT.NONE));
		}
		countryLabel = new Label(intitutionalComposite, SWT.NONE);
		painter.add(countryLabel);

		countryCombo = new Combo(intitutionalComposite, SWT.READ_ONLY);
		controls.put("countryCombo", countryCombo);
		painter.add(countryCombo);
		painter.reset();

		personComposite = new Composite(midle, SWT.NORMAL);
		painter.addComposite(personComposite);
		controls.put("personComposite", personComposite);
		personComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");

		personComposite.setSize(parent.getSize().x, 0);
		FormData tempo = (FormData) personComposite.getLayoutData();
		tempo.height = 0;
		personComposite.setLayoutData(tempo);

		personComposite.setLayoutData(tempo);
		personComposite.layout(true, true);
		personComposite.redraw();
		personComposite.update();

		personDataPersonComposite = new Composite(personComposite, SWT.NONE);
		personDataPersonComposite.setLayout(new FormLayout());
		personDataPersonComposite
				.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personDataPersonComposite)
				.atTopTo(personComposite, 10).atLeft(15).atRight(15);

		viewPersonDataPersonComposite = new Composite(
				personDataPersonComposite, SWT.NONE);
		viewPersonDataPersonComposite.setLayout(new FormLayout());
		viewPersonDataPersonComposite.setVisible(false);
		viewPersonDataPersonComposite.setData(RWT.CUSTOM_VARIANT,
				"gray_background");
		FormDatas.attach(viewPersonDataPersonComposite)
				.atTopTo(personDataPersonComposite).withHeight(100);

		personDataPersonLabel = new Label(viewPersonDataPersonComposite,
				SWT.NONE);
		FormDatas.attach(personDataPersonLabel)
				.atTopTo(viewPersonDataPersonComposite, 15).atLeft(0);

		personPersonGroup = new Group(viewPersonDataPersonComposite, SWT.NONE);
		personPersonGroup.setLayout(new FormLayout());
		personPersonGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personPersonGroup).atTopTo(personDataPersonLabel, 5)
				.atLeft(0);

		picturePersonLabel = new Label(personPersonGroup, SWT.BORDER);
		FormDatas.attach(picturePersonLabel).atTopTo(personPersonGroup, 10)
				.atLeft(15);
		Image image1 = new Image(Display.getDefault(), AbosImageUtil
				.loadImage(null, Display.getCurrent(),
						"abcdconfig/resources/photo.png", false).getImageData()
				.scaledTo(100, 100));
		picturePersonLabel.setImage(image1);

		fullNamePersonLabel = new Label(personPersonGroup, SWT.NONE);
		fullNamePersonLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(fullNamePersonLabel).atTopTo(personPersonGroup, 10)
				.atLeftTo(picturePersonLabel, 15);

		fullPersonName = new Label(personPersonGroup, SWT.NONE);
		FormDatas.attach(fullPersonName).atTopTo(personPersonGroup, 10)
				.atLeftTo(fullNamePersonLabel, 5).atRight(15);

		Label firstSeparator1 = new Label(personPersonGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(firstSeparator1).atTopTo(fullPersonName, 1)
				.atLeftTo(picturePersonLabel, 5).atRight(5);

		userPersonLabel = new Label(personPersonGroup, SWT.NONE);
		userPersonLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(userPersonLabel).atTopTo(fullNamePersonLabel, 6)
				.atRightTo(fullPersonName, 5);

		userPerson = new Label(personPersonGroup, SWT.NONE);
		FormDatas.attach(userPerson).atTopTo(fullNamePersonLabel, 6)
				.atLeftTo(userPersonLabel, 5);

		Label secondSeparator1 = new Label(personPersonGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(secondSeparator1).atTopTo(userPerson, 1)
				.atLeftTo(picturePersonLabel, 5).atRight(5);

		birthdayPersonLabel = new Label(personPersonGroup, SWT.NONE);
		birthdayPersonLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(birthdayPersonLabel).atTopTo(userPersonLabel, 6)
				.atRightTo(userPerson, 5);

		birthdayPerson = new Label(personPersonGroup, SWT.NONE);
		FormDatas.attach(birthdayPerson).atTopTo(userPersonLabel, 6)
				.atLeftTo(birthdayPersonLabel, 5);

		Label thirdSeparator1 = new Label(personPersonGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(thirdSeparator1).atTopTo(birthdayPerson, 1)
				.atLeftTo(picturePersonLabel, 5).atRight(5);

		sexPersonLabel = new Label(personPersonGroup, SWT.NONE);
		sexPersonLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(sexPersonLabel).atTopTo(birthdayPersonLabel, 6)
				.atRightTo(birthdayPerson, 5);

		sexPerson = new Label(personPersonGroup, SWT.NONE);
		FormDatas.attach(sexPerson).atTopTo(birthdayPersonLabel, 6)
				.atLeftTo(sexPersonLabel, 5);

		Label fourthSeparator1 = new Label(personPersonGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(fourthSeparator1).atTopTo(sexPerson, 1)
				.atLeftTo(picturePersonLabel, 5).atRight(5);

		identificationPersonLabel = new Label(personPersonGroup, SWT.NONE);
		identificationPersonLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(identificationPersonLabel).atTopTo(sexPersonLabel, 6)
				.atRightTo(sexPerson, 5);

		identificationPerson = new Label(personPersonGroup, SWT.NONE);
		FormDatas.attach(identificationPerson).atTopTo(sexPersonLabel, 6)
				.atLeftTo(identificationPersonLabel, 5);

		unassociatePerson = new Button(viewPersonDataPersonComposite, SWT.NONE);
		FormDatas.attach(unassociatePerson).atTopTo(personPersonGroup, -25)
				.withHeight(23).atLeftTo(personPersonGroup, 5);
		unassociatePerson.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 7932356419459592823L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				dissociatePerson();
			}
		});

		searchPersonPersonComposite = new Composite(personDataPersonComposite,
				SWT.NONE);
		searchPersonPersonComposite.setLayout(new FormLayout());
		searchPersonPersonComposite.setData(RWT.CUSTOM_VARIANT,
				"gray_background");
		FormDatas.attach(searchPersonPersonComposite).atTopTo(
				personDataPersonComposite);

		sugestPersonLabel = new Label(searchPersonPersonComposite, SWT.NONE);
		FormDatas.attach(sugestPersonLabel).atTopTo(parent, 10).atLeft(0);

		searchPersonText = new Text(searchPersonPersonComposite, 0);
		FormDatas.attach(searchPersonText).atTopTo(sugestPersonLabel, 5)
				.withHeight(10).atLeft(0).withWidth(250);

		searchPersonText.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void focusLost(FocusEvent arg0) {
				searchPersonButton.getShell().setDefaultButton(null);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				searchPersonButton.getShell().setDefaultButton(
						searchPersonButton);
			}
		});

		searchPersonButton = new Button(searchPersonPersonComposite, SWT.NONE);
		FormDatas.attach(searchPersonButton).atTopTo(sugestPersonLabel, 5)
				.withHeight(23).atLeftTo(searchPersonText, 10);

		searchPersonButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8115718469128343452L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				SearchPerson();
			}
		});

		tablePersonPersonComposite = new Composite(personDataPersonComposite,
				SWT.NONE);
		tablePersonPersonComposite.setLayout(new FormLayout());
		tablePersonPersonComposite.setData(RWT.CUSTOM_VARIANT,
				"gray_background");
		FormDatas.attach(tablePersonPersonComposite)
				.atTopTo(searchPersonPersonComposite).atLeft(0).atRight(0);

		tablePersonPerson = new CRUDTreeTable(tablePersonPersonComposite,
				SWT.NONE);
		tablePersonPerson.setEntityClass(Person.class);
		tablePersonPerson.setSearch(false);
		tablePersonPerson.setSaveAll(false);
		tablePersonPerson.setPageSize(10);

		Column columnPerson1 = new Column("associate", parent.getDisplay(),
				new TreeColumnListener() {
					public void handleEvent(TreeColumnEvent event) {
						representantPerson = (Person) event.entity.getRow();
						associatePerson();
						showPersonPerson();
					}
				});
		columnPerson1
				.setToolTipText(MessageUtil.unescape(AbosMessages.get().BUTTON_ASSOCIATE));
		tablePersonPerson.addActionColumn(columnPerson1);

		TreeTableColumn columnsPersonPerson[] = {
				new TreeTableColumn(60, 0, "getFullName"),
				new TreeTableColumn(20, 1, "getDNI"),
				new TreeTableColumn(20, 2, "getSex.getNomenclatorDescription") };
		tablePersonPerson.createTable(columnsPersonPerson);

		tablePersonPerson.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					switch (event.sortData.columnIndex) {
					case 1:
						break;
					}
				}

				searchPersonPerson(event.currentPage - 1, event.pageSize);
				int rowsCount = tablePersonPerson.getRows().size();

				heightTablePersonPerson = (rowsCount * 28) + 105;
				ajustRezise(tablePersonPersonComposite, heightTablePersonPerson);
				ajustRezise(personDataPersonComposite,
						getHeightSearchPersonComposite()
								+ getHeightTablePersonPersonComposite());
				ajustRezise(personComposite, getHeightSearchPersonComposite()
						+ getHeightTablePersonPersonComposite()
						+ otherComposite.getSize().y + 10);
				refresh(midle);
				refresh(personDataPersonComposite.getParent().getShell());
			}

		});

		FormDatas.attach(tablePersonPerson).atTopTo(tablePersonPersonComposite)
				.atLeft().atRight();
		otherComposite = new Composite(personComposite, SWT.NONE);
		otherComposite.setLayout(new FormLayout());
		otherComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(otherComposite).atTopTo(personDataPersonComposite)
				.atLeft(0).atRight(0);

		firstPhoneNumberLabel1 = new Label(otherComposite, SWT.NONE);
		painter.add(firstPhoneNumberLabel1);

		firstPhoneNumberText1 = new Text(otherComposite, SWT.NONE);
		controls.put("firstPhoneNumberText1", firstPhoneNumberText1);
		painter.add(firstPhoneNumberText1);

		providerStateLabel1 = new Label(otherComposite, SWT.NONE);
		painter.add(providerStateLabel1);

		providerStateCombo1 = new Combo(otherComposite, SWT.READ_ONLY);
		controls.put("providerStateCombo1", providerStateCombo1);
		painter.add(providerStateCombo1);

		painter.reset();

		secondPhoneNumberLabel1 = new Label(otherComposite, SWT.NONE);
		painter.add(secondPhoneNumberLabel1);

		secondPhoneNumberText1 = new Text(otherComposite, SWT.NONE);
		controls.put("secondPhoneNumberText1", secondPhoneNumberText1);
		painter.add(secondPhoneNumberText1);

		countryLabel1 = new Label(otherComposite, SWT.NONE);
		painter.add(countryLabel1);

		countryCombo1 = new Combo(otherComposite, SWT.READ_ONLY);
		controls.put("countryCombo1", countryCombo1);
		painter.add(countryCombo1);

		painter.reset();

		emailLabel1 = new Label(otherComposite, SWT.NONE);
		painter.add(emailLabel1);

		emailText1 = new Text(otherComposite, SWT.NONE);
		controls.put("emailText1", emailText1);
		painter.add(emailText1);

		addreesLabel1 = new Label(otherComposite, SWT.NONE);
		painter.add(addreesLabel1);

		addreesText1 = new Text(otherComposite, SWT.NONE | SWT.WRAP
				| SWT.V_SCROLL);
		controls.put("addreesText1", addreesText1);
		painter.add(addreesText1);

		painter.reset();

		if (((FormPagePainter) painter).getDimension() < 840) {
			painter.add(new Label(otherComposite, SWT.NONE));
			painter.add(new Label(otherComposite, SWT.NONE));
		}

		faxLabel1 = new Label(otherComposite, SWT.NONE);
		painter.add(faxLabel1);

		faxText1 = new Text(otherComposite, SWT.NONE);
		controls.put("faxText1", faxText1);
		painter.add(faxText1);

		painter.reset();

		webPageLabel1 = new Label(otherComposite, SWT.NONE);
		painter.add(webPageLabel1);

		webPageText1 = new Text(otherComposite, SWT.NONE);
		controls.put("webPageText1", webPageText1);
		painter.add(webPageText1);

		painter.reset();

		Composite downComposite = new Composite(shell, SWT.NONE);
		painter.addComposite(downComposite);
		FormDatas.attach(downComposite).atTopTo(midle).atLeft().atRight();
		downComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");

		providerTypeLabel1 = new Label(downComposite, SWT.NONE);
		painter.add(providerTypeLabel1);
		painter.reset();

		canjeCheckButton1 = new Button(downComposite, SWT.CHECK);
		donateCheckButton1 = new Button(downComposite, SWT.CHECK);
		commercialCheckButton1 = new Button(downComposite, SWT.CHECK);

		controls.put("canjeCheckButton", canjeCheckButton1);
		controls.put("donateCheckButton", donateCheckButton1);
		controls.put("commercialCheckButton", commercialCheckButton1);
		Integer dimension = ((FormPagePainter) painter).getDimension();
		if (dimension < 840) {
			painter.add(canjeCheckButton1);
			painter.reset();
			painter.add(donateCheckButton1);
			painter.reset();
			painter.add(commercialCheckButton1);
			br();
		} else {
			painter.add(new Label(downComposite, SWT.NONE));
			painter.add(canjeCheckButton1);
			painter.add(donateCheckButton1);
			painter.add(commercialCheckButton1);
		}

		painter.add(new Label(downComposite, SWT.NONE));

		separador = new Label(downComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separador);

		personDataComposite = new Composite(downComposite, SWT.NONE);
		personDataComposite.setLayout(new FormLayout());
		personDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personDataComposite).atTopTo(separador).atLeft(0)
				.atRight(0);

		viewPersonDataComposite = new Composite(personDataComposite, SWT.NONE);
		viewPersonDataComposite.setLayout(new FormLayout());
		viewPersonDataComposite.setVisible(false);
		viewPersonDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(viewPersonDataComposite).atTopTo(personDataComposite)
				.withHeight(100);

		personDataLabel = new Label(viewPersonDataComposite, SWT.NONE);
		personDataLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(personDataLabel).atTopTo(viewPersonDataComposite, 15)
				.atLeft(15);

		personGroup = new Group(viewPersonDataComposite, SWT.NONE);
		personGroup.setLayout(new FormLayout());
		personGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personGroup).atTopTo(personDataLabel, 5).atLeft(15);

		pictureLabel = new Label(personGroup, SWT.BORDER);
		FormDatas.attach(pictureLabel).atTopTo(personGroup, 10).atLeft(15);
		Image image = new Image(Display.getDefault(), AbosImageUtil
				.loadImage(null, Display.getCurrent(),
						"abcdconfig/resources/photo.png", false).getImageData()
				.scaledTo(100, 100));
		pictureLabel.setImage(image);

		fullNameLabel = new Label(personGroup, SWT.NONE);
		fullNameLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(fullNameLabel).atTopTo(personGroup, 10)
				.atLeftTo(pictureLabel, 15);

		fullName = new Label(personGroup, SWT.NONE);
		FormDatas.attach(fullName).atTopTo(personGroup, 10)
				.atLeftTo(fullNameLabel, 5).atRight(15);

		Label firstSeparator = new Label(personGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(firstSeparator).atTopTo(fullName, 1)
				.atLeftTo(pictureLabel, 5).atRight(5);

		userLabel = new Label(personGroup, SWT.NONE);
		userLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(userLabel).atTopTo(fullNameLabel, 6)
				.atRightTo(fullName, 5);

		user = new Label(personGroup, SWT.NONE);
		FormDatas.attach(user).atTopTo(fullNameLabel, 6).atLeftTo(userLabel, 5);

		Label secondSeparator = new Label(personGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(secondSeparator).atTopTo(user, 1)
				.atLeftTo(pictureLabel, 5).atRight(5);

		birthdayLabel = new Label(personGroup, SWT.NONE);
		birthdayLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(birthdayLabel).atTopTo(userLabel, 6)
				.atRightTo(user, 5);

		birthday = new Label(personGroup, SWT.NONE);
		FormDatas.attach(birthday).atTopTo(userLabel, 6)
				.atLeftTo(birthdayLabel, 5);

		Label thirdSeparator = new Label(personGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(thirdSeparator).atTopTo(birthday, 1)
				.atLeftTo(pictureLabel, 5).atRight(5);

		sexLabel = new Label(personGroup, SWT.NONE);
		sexLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(sexLabel).atTopTo(birthdayLabel, 6)
				.atRightTo(birthday, 5);

		sex = new Label(personGroup, SWT.NONE);
		FormDatas.attach(sex).atTopTo(birthdayLabel, 6).atLeftTo(sexLabel, 5);

		Label fourthSeparator = new Label(personGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(fourthSeparator).atTopTo(sex, 1)
				.atLeftTo(pictureLabel, 5).atRight(5);

		identificationLabel = new Label(personGroup, SWT.NONE);
		identificationLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(identificationLabel).atTopTo(sexLabel, 6)
				.atRightTo(sex, 5);

		identification = new Label(personGroup, SWT.NONE);
		FormDatas.attach(identification).atTopTo(sexLabel, 6)
				.atLeftTo(identificationLabel, 5);

		unassociate = new Button(viewPersonDataComposite, SWT.NONE);
		FormDatas.attach(unassociate).atTopTo(personGroup, -25).withHeight(23)
				.atLeftTo(personGroup, 5);
		unassociate.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 7932356419459592823L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				dissociate();
			}
		});
		searchPersonComposite = new Composite(personDataComposite, SWT.NONE);
		searchPersonComposite.setLayout(new FormLayout());
		searchPersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(searchPersonComposite).atTopTo(personDataComposite);

		sugestLabel = new Label(searchPersonComposite, SWT.NONE);
		FormDatas.attach(sugestLabel).atTopTo(parent, 15).atLeft(15);

		searchText = new Text(searchPersonComposite, 0);
		FormDatas.attach(searchText).atTopTo(sugestLabel, 5).withHeight(10)
				.atLeft(15).withWidth(250);
		searchButton = new Button(searchPersonComposite, SWT.NONE);

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

		FormDatas.attach(searchButton).atTopTo(sugestLabel, 3).withHeight(23)
				.atLeftTo(searchText, 10);
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
		FormDatas.attach(tablePersonComposite).atTopTo(searchPersonComposite)
				.atLeft(15).atRight(15);
		tablePerson = new CRUDTreeTable(tablePersonComposite, SWT.NONE);
		tablePerson.setEntityClass(Person.class);
		tablePerson.setSearch(false);
		tablePerson.setSaveAll(false);
		tablePerson.setPageSize(10);
		Column columnPerson = new Column("associate", parent.getDisplay(),
				new TreeColumnListener() {
					public void handleEvent(TreeColumnEvent event) {
						representant = (Person) event.entity.getRow();
						associate();
						showPerson();

					}
				});
		columnPerson
				.setToolTipText(MessageUtil.unescape(AbosMessages.get().BUTTON_ASSOCIATE));
		tablePerson.addActionColumn(columnPerson);
		TreeTableColumn columnsPerson[] = {
				new TreeTableColumn(60, 0, "getFullName"),
				new TreeTableColumn(20, 1, "getDNI"),
				new TreeTableColumn(20, 2, "getSex.getNomenclatorDescription") };
		tablePerson.createTable(columnsPerson);
		tablePerson.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					switch (event.sortData.columnIndex) {
					case 1:

						break;
					}
				}
				searchPerson(event.currentPage - 1, event.pageSize);
				int rowsCount = tablePerson.getRows().size();
				heightTablePerson = (rowsCount * 28) + 105;
				ajustRezise(tablePersonComposite, heightTablePerson);
				ajustRezise(personDataComposite,
						getHeightSearchPersonComposite()
								+ getHeightTablePersonComposite());
				refresh(personDataComposite.getParent().getShell());
			}

		});
		FormDatas.attach(tablePerson).atTopTo(tablePersonComposite).atLeft()
				.atRight();

		initialize(
				providerStateCombo,
				((ProviderViewController) controller)
						.getAllManagementLibraryViewController()
						.getLibraryService()
						.findNomenclatorByCode(library.getLibraryID(),
								Nomenclator.PROVIDER_STATE));

		initialize(
				countryCombo,
				((ProviderViewController) controller)
						.getAllManagementLibraryViewController()
						.getLibraryService()
						.findNomenclatorByCode(library.getLibraryID(),
								Nomenclator.COUNTRY));

		initialize(
				providerStateCombo1,
				((ProviderViewController) controller)
						.getAllManagementLibraryViewController()
						.getLibraryService()
						.findNomenclatorByCode(library.getLibraryID(),
								Nomenclator.PROVIDER_STATE));

		initialize(
				countryCombo1,
				((ProviderViewController) controller)
						.getAllManagementLibraryViewController()
						.getLibraryService()
						.findNomenclatorByCode(library.getLibraryID(),
								Nomenclator.COUNTRY));

		validatorIntitutional.applyValidator(providerNameText,
				"providerNameTextValidate",
				DecoratorType.ALPHA_NUMERICS_SPACES, true);

		validatorIntitutional.applyValidator(providerStateCombo,
				"providerStateCombo", DecoratorType.REQUIRED_FIELD, true);
		validatorPersonal.applyValidator(providerStateCombo1,
				"providerStateCombo1", DecoratorType.REQUIRED_FIELD, true);

		validatorIntitutional.applyValidator(countryCombo, "countryCombo",
				DecoratorType.REQUIRED_FIELD, true);
		validatorPersonal.applyValidator(countryCombo1, "countryCombo1",
				DecoratorType.REQUIRED_FIELD, true);

		validatorIntitutional.applyValidator(providerNameText, 50);

		validatorIntitutional.applyValidator(providerNameText,
				"providerNameText1", DecoratorType.REQUIRED_FIELD, true);

		validatorIntitutional.applyValidator(firstPhoneNumberText,
				"firstPhoneNumberTextNumber", DecoratorType.NUMBER_ONLY, true);
		validatorPersonal.applyValidator(firstPhoneNumberText1,
				"firstPhoneNumberTextNumber1", DecoratorType.NUMBER_ONLY, true);

		validatorIntitutional.applyValidator(firstPhoneNumberText, 20);
		validatorPersonal.applyValidator(firstPhoneNumberText1, 20);

		validatorIntitutional.applyValidator(rifText, 30);
		validatorIntitutional.applyValidator(rifText, "rifTextAlfanumeric",
				DecoratorType.ALPHA_NUMERIC, true);

		validatorIntitutional.applyValidator(secondPhoneNumberText,
				"secondPhoneNumberTextNumber", DecoratorType.NUMBER_ONLY, true);
		validatorPersonal
				.applyValidator(secondPhoneNumberText1,
						"secondPhoneNumberTextNumber1",
						DecoratorType.NUMBER_ONLY, true);

		validatorIntitutional.applyValidator(secondPhoneNumberText, 20);
		validatorPersonal.applyValidator(secondPhoneNumberText1, 20);

		validatorIntitutional.applyValidator(nitText, 30);
		validatorIntitutional.applyValidator(nitText, "nitTextAlfanumeric",
				DecoratorType.ALPHA_NUMERIC, true);

		validatorIntitutional.applyValidator(faxText, 20);
		validatorPersonal.applyValidator(faxText1, 20);

		validatorIntitutional.applyValidator(faxText, "faxTextNumber",
				DecoratorType.NUMBER_ONLY, true);
		validatorPersonal.applyValidator(faxText1, "faxTextNumber1",
				DecoratorType.NUMBER_ONLY, true);

		validatorIntitutional.applyValidator(emailText, "emailTextFormat",
				DecoratorType.EMAIL, true, 50);
		validatorPersonal.applyValidator(emailText1, "emailTextFormat1",
				DecoratorType.EMAIL, true, 50);

		validatorIntitutional.applyValidator(webPageText, "webPageText1",
				DecoratorType.URL, true, 100);

		validatorPersonal.applyValidator(webPageText1, "webPageText2",
				DecoratorType.URL, true, 100);

		validatorIntitutional.applyValidator(addreesText, 250);
		validatorPersonal.applyValidator(addreesText1, 250);

		//validatorIntitutional.applyValidator(addreesText, "addreesText1",
			//	DecoratorType.ALL_BUT_NO_OTHER_REPEAT_CONSECUTIVE, true);

		//validatorPersonal.applyValidator(addreesText1, "addreesText2",
		//		DecoratorType.ALL_BUT_NO_OTHER_REPEAT_CONSECUTIVE, true);
		LoadProviderData();
		l10n();
		return shell;
	}

	private void LoadProviderData() {

		if (provider != null) {
			UiUtils.selectValue(providerStateCombo, provider.getProviderState());
			UiUtils.selectValue(providerStateCombo1,
					provider.getProviderState());
			UiUtils.selectValue(countryCombo, provider.getCountry());
			UiUtils.selectValue(countryCombo1, provider.getCountry());

			if (provider.isIntitutional()) {
				providerNameText
						.setText((provider.getProviderName() != null) ? provider
								.getProviderName() : "");
			}
			rifText.setText((provider.getRif() != null) ? provider.getRif()
					: "");
			nitText.setText((provider.getNit() != null) ? provider.getNit()
					: "");

			emailText.setText((provider.getEmail() != null) ? provider
					.getEmail() : "");
			emailText1.setText((provider.getEmail() != null) ? provider
					.getEmail() : "");

			firstPhoneNumberText
					.setText((provider.getFirstPhoneNumber() != null) ? provider
							.getFirstPhoneNumber() : "");
			firstPhoneNumberText1
					.setText((provider.getFirstPhoneNumber() != null) ? provider
							.getFirstPhoneNumber() : "");

			secondPhoneNumberText
					.setText((provider.getSecondPhoneNumber() != null) ? provider
							.getSecondPhoneNumber() : "");
			secondPhoneNumberText1
					.setText((provider.getSecondPhoneNumber() != null) ? provider
							.getSecondPhoneNumber() : "");

			faxText.setText((provider.getFax() != null) ? provider.getFax()
					: "");
			faxText1.setText((provider.getFax() != null) ? provider.getFax()
					: "");

			webPageText.setText((provider.getWebPage() != null) ? provider
					.getWebPage() : "");
			webPageText1.setText((provider.getWebPage() != null) ? provider
					.getWebPage() : "");

			addreesText.setText((provider.getAddress() != null) ? provider
					.getAddress() : "");
			addreesText1.setText((provider.getAddress() != null) ? provider
					.getAddress() : "");

			List<Nomenclator> list = provider.getTypes();
			Nomenclator comercial = ((ProviderViewController) controller)
					.getAllManagementLibraryViewController()
					.getLibraryService()
					.findNomenclatorById(Nomenclator.PROVIDER_TYPE_COMMERCIAL);
			Nomenclator change = ((ProviderViewController) controller)
					.getAllManagementLibraryViewController()
					.getLibraryService()
					.findNomenclatorById(Nomenclator.PROVIDER_TYPE_CANGE);

			Nomenclator donation = ((ProviderViewController) controller)
					.getAllManagementLibraryViewController()
					.getLibraryService()
					.findNomenclatorById(Nomenclator.PROVIDER_TYPE_DONATION);

			for (Nomenclator type : list) {
				if (type.equals(comercial)) {
					commercialCheckButton1.setSelection(true);
				}
				if (type.equals(change)) {
					canjeCheckButton1.setSelection(true);
				}
				if (type.equals(donation)) {
					donateCheckButton1.setSelection(true);
				}

			}
			representant = provider.getRepresentative();
			if (representant != null) {
				associate();
			}
			if (!provider.isIntitutional()) {
				representantPerson = provider.getPerson();
				if (representantPerson != null) {
					associatePerson();
					showPersonPerson();
				}
				personButton.setSelection(true);
				intitutionalButton.setSelection(false);

				showCompositePerson();

			} else {
				personButton.setSelection(false);
				intitutionalButton.setSelection(true);
				showCompositeIntitutional();
			}

		}
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		if (option == 1) {
			registerProviderLabel.setText(MessageUtil.unescape(AbosMessages
					.get().LABEL_REGISTER_PROVIDER));
		} else {
			registerProviderLabel.setText(MessageUtil.unescape(AbosMessages
					.get().LABEL_UPDATE_PROVIDER));
		}
		personDataLabel
				.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PERSON));

		personDataPersonLabel
				.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PERSON));

		personGroup
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA));
		personPersonGroup
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA));

		fullNameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME));
		fullNamePersonLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME));

		userLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER));
		userPersonLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER));

		birthdayLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_BIRTHDAY));
		birthdayPersonLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_BIRTHDAY));

		sexLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEX));
		sexPersonLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEX));
		identificationLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		identificationPersonLabel.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_IDENTIFICATION));

		searchButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		searchPersonButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));

		firstPhoneNumberLabel
				.setText(MessageUtil.unescape(AbosMessages.get().FIRST_PHONE_NUMBER));
		secondPhoneNumberLabel
				.setText(MessageUtil.unescape(AbosMessages.get().SECOND_PHONE_NUMBER));
		faxLabel.setText(MessageUtil.unescape(AbosMessages.get().FAX));
		emailLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EMAIL));
		webPageLabel.setText(MessageUtil.unescape(AbosMessages.get().WEB_PAGE));
		addreesLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ADDREES));
		countryLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COUNTRY));

		firstPhoneNumberLabel1
				.setText(MessageUtil.unescape(AbosMessages.get().FIRST_PHONE_NUMBER));
		secondPhoneNumberLabel1
				.setText(MessageUtil.unescape(AbosMessages.get().SECOND_PHONE_NUMBER));
		faxLabel1.setText(MessageUtil.unescape(AbosMessages.get().FAX));
		emailLabel1
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EMAIL));
		webPageLabel1
				.setText(MessageUtil.unescape(AbosMessages.get().WEB_PAGE));
		addreesLabel1
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ADDREES));
		countryLabel1
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COUNTRY));

		intitutionalButton
				.setText(MessageUtil.unescape(AbosMessages.get().INTITUTION));

		personButton.setText(MessageUtil.unescape(AbosMessages.get().PERSON));
		unassociate
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_UNASSOCIATE));
		sugestLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_INDICATE_PERSON_AS_REPRESENTANT));

		searchText
				.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_NAME)
						+ " | "
						+ MessageUtil.unescape(AbosMessages.get().LABEL_LAST_NAME)
						+ " | "
						+ MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));

		tablePerson.setColumnHeaders(Arrays.asList(MessageUtil
				.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME),
				MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION),
				MessageUtil.unescape(AbosMessages.get().LABEL_SEX)));

		unassociatePerson
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_UNASSOCIATE));
		sugestPersonLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_INDICATE_PERSON));

		searchPersonText
				.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_NAME)
						+ " | "
						+ MessageUtil.unescape(AbosMessages.get().LABEL_LAST_NAME)
						+ " | "
						+ MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));

		tablePersonPerson.setColumnHeaders(Arrays.asList(MessageUtil
				.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME),
				MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION),
				MessageUtil.unescape(AbosMessages.get().LABEL_SEX)));
		providerData
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATA_PROVIDER));
		providerNameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_PROVIDER));
		rifLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_RIF));
		nitLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NIT));
		providerStateLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		providerStateLabel1
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));

		providerTypeLabel1
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SERVICE));
		canjeCheckButton1
				.setText(MessageUtil.unescape(AbosMessages.get().CHECKBUTTON_CHANGE));
		donateCheckButton1
				.setText(MessageUtil.unescape(AbosMessages.get().CHECKBUTTON_DONATIONS));

		commercialCheckButton1
				.setText(MessageUtil.unescape(AbosMessages.get().CHECKBUTTON_COMMERCIAL));
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

	private void searchPerson(int page, int size) {
		tablePerson.clearRows();
		Library library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		Page<Person> list = personService.findAll(library, searchTextConsult,
				page, size, size, orderByStringPerson);
		tablePerson.setTotalElements((int) list.getTotalElements());
		tablePerson.setRows(list.getContent());
		tablePerson.refresh();
	}

	private void searchPersonPerson(int page, int size) {
		tablePersonPerson.clearRows();
		Library library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		Page<Person> list = personService.findAll(library,
				searchTextPersonConsult, page, size, size,
				orderByStringPersonPerson);
		tablePersonPerson.setTotalElements((int) list.getTotalElements());
		tablePersonPerson.setRows(list.getContent());
		tablePersonPerson.refresh();
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
		searchTextConsult = (searchText.getText() != "") ? searchText.getText()
				: null;
		orderByStringPerson = "firstName";
		tablePerson.getPaginator().goToFirstPage();
		if (tablePerson.getRows().isEmpty()) {
			RetroalimentationUtils
					.showInformationMessage(cu.uci.abos.core.l10n.AbosMessages
							.get().MSG_INF_NO_COINCIDENCES_FOUND);
		}
	}

	public void SearchPerson() {
		viewPersonDataPersonComposite.setVisible(false);
		tablePersonPersonComposite.setVisible(true);
		tablePersonPerson.clearRows();
		searchTextPersonConsult = (searchPersonText.getText() != "") ? searchPersonText
				.getText() : null;
		orderByStringPersonPerson = "firstName";
		tablePersonPerson.getPaginator().goToFirstPage();
		if (tablePersonPerson.getRows().isEmpty()) {
			RetroalimentationUtils
					.showInformationMessage(cu.uci.abos.core.l10n.AbosMessages
							.get().MSG_INF_NO_COINCIDENCES_FOUND);
		}
	}

	public void associate() {
		fullName.setText(getRepresentant().getFullName());
		Image image;
		if (getRepresentant().getPhoto() == null) {
			image = new Image(Display.getDefault(), AbosImageUtil
					.loadImage(null, Display.getCurrent(),
							"abcdconfig/resources/photo.png", false)
					.getImageData().scaledTo(100, 100));
		} else {
			image = getRepresentant().getPhoto().getImage();
		}
		pictureLabel.setImage(image);
		user.setText((getRepresentant().getUser() != null) ? getRepresentant()
				.getUser().getUsernameToString() : "-");
		birthday.setText((getRepresentant().getBirthDate() != null) ? new SimpleDateFormat(
				"dd-MM-yyyy").format(getRepresentant().getBirthDate()) : "-");

		identification.setText(getRepresentant().getDNI());
		sex.setText(getRepresentant().getSex().getNomenclatorName());
		viewPersonDataComposite.setVisible(true);
		ajustRezise(viewPersonDataComposite, 208);
		refresh(personDataComposite.getParent().getShell());
		viewPersonDataComposite.layout(true, true);
		viewPersonDataComposite.redraw();
		viewPersonDataComposite.update();
		searchPersonComposite.setVisible(false);
		tablePersonComposite.setVisible(false);
	}

	public void associatePerson() {
		fullPersonName.setText(getRepresentantPerson().getFullName());
		Image imagePerson;
		if (getRepresentantPerson().getPhoto() == null) {
			imagePerson = new Image(Display.getDefault(), AbosImageUtil
					.loadImage(null, Display.getCurrent(),
							"abcdconfig/resources/photo.png", false)
					.getImageData().scaledTo(100, 100));
		} else {
			imagePerson = getRepresentantPerson().getPhoto().getImage();
		}
		picturePersonLabel.setImage(imagePerson);
		userPerson
				.setText((getRepresentantPerson().getUser() != null) ? getRepresentantPerson()
						.getUser().getUsernameToString() : "-");
		birthdayPerson
				.setText((getRepresentantPerson().getBirthDate() != null) ? new SimpleDateFormat(
						"dd-MM-yyyy").format(getRepresentantPerson()
						.getBirthDate()) : "-");

		identificationPerson.setText(getRepresentantPerson().getDNI());
		sexPerson
				.setText(getRepresentantPerson().getSex().getNomenclatorName());
		viewPersonDataPersonComposite.setVisible(true);
		ajustRezise(viewPersonDataPersonComposite, 208);
		refresh(personDataPersonComposite.getParent().getShell());
		viewPersonDataPersonComposite.layout(true, true);
		viewPersonDataPersonComposite.redraw();
		viewPersonDataPersonComposite.update();
		searchPersonPersonComposite.setVisible(false);
		tablePersonPersonComposite.setVisible(false);
	}

	public void showPerson() {
		heightTablePerson = tablePerson.getSize().y;
		ajustRezise(personDataComposite, getHeightViewPersonDataComposite() + 5);
		refresh(personDataComposite.getParent().getShell());
	}

	public void showPersonPerson() {
		heightTablePersonPerson = tablePersonPerson.getSize().y;
		ajustRezise(personDataPersonComposite,
				getHeightViewPersonDataPersonComposite() + 5);
		ajustRezise(personComposite, personDataComposite.getSize().y
				+ otherComposite.getSize().y + 104);
		refresh(midle);
		refresh(personDataPersonComposite.getParent().getShell());
	}

	public void dissociate() {
		viewPersonDataComposite.setVisible(false);
		searchPersonComposite.setVisible(true);
		tablePersonComposite.setVisible(true);
		representant = null;
		ajustRezise(personDataComposite, getHeightSearchPersonComposite()
				+ getHeightTablePersonComposite());
		refresh(personDataComposite.getParent().getShell());
	}

	public void dissociatePerson() {
		viewPersonDataPersonComposite.setVisible(false);
		searchPersonPersonComposite.setVisible(true);
		tablePersonPersonComposite.setVisible(true);
		representantPerson = null;
		ajustRezise(personDataPersonComposite,
				getHeightSearchPersonPersonComposite()
						+ getHeightTablePersonPersonComposite());

		ajustRezise(personComposite, personDataPersonComposite.getSize().y
				+ otherComposite.getSize().y + 10);
		refresh(midle);

		refresh(personDataPersonComposite.getParent().getShell());
	}

	public ValidatorUtils getValidatorIntitutional() {
		return validatorIntitutional;
	}

	public void setValidatorIntitutional(ValidatorUtils validatorIntitutional) {
		this.validatorIntitutional = validatorIntitutional;
	}

	public ValidatorUtils getValidatorPersonal() {
		return validatorPersonal;
	}

	public void setValidatorPersona(ValidatorUtils validatorPersonal) {
		this.validatorPersonal = validatorPersonal;
	}

	public boolean isInstitutional() {
		return intitutionalButton.getSelection();
	}

	public void showCompositePerson() {
		ajustRezise(intitutionalComposite, 0);
		ajustRezise(personComposite, personDataPersonComposite.getSize().y + 10
				+ otherComposite.getSize().y + 10);
		refresh(midle);
		refresh(intitutionalComposite.getParent().getShell());
	}

	public void showCompositeIntitutional() {
		if (((FormPagePainter) painter).getDimension() < 840) {
			ajustRezise(intitutionalComposite, 450);
		} else {
			ajustRezise(intitutionalComposite, 235);
		}
		ajustRezise(personComposite, 0);
		refresh(intitutionalComposite.getParent().getShell());
	}

	public Composite getParent() {
		return parent;
	}

	public Person getRepresentantPerson() {
		return representantPerson;
	}

	public void setRepresentantPerson(Person representantPerson) {
		this.representantPerson = representantPerson;
	}

	public Person getRepresentant() {
		return representant;
	}

	public void setRepresentant(Person representant) {
		this.representant = representant;
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

	public int getHeightPersonPersonDataComposite() {
		return personDataPersonComposite.getSize().y;
	}

	public int getHeightViewPersonDataPersonComposite() {
		return viewPersonDataPersonComposite.getSize().y;
	}

	public int getHeightSearchPersonPersonComposite() {
		return searchPersonPersonComposite.getSize().y;
	}

	public int getHeightTablePersonPersonComposite() {
		return tablePersonPersonComposite.getSize().y;
	}

	
}
