package cu.uci.abcd.opac.ui.contribution;

import org.eclipse.rap.rwt.RWT;
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

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.controller.RegisterUserViewController;
import cu.uci.abcd.opac.ui.listener.LocaleResetListener;
import cu.uci.abcd.opac.ui.listener.OpacLoginListener;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.Digest;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;

public class ConfigurationOption extends ContributorPage {

	private ServiceProvider serviceProvider;
	private ContributorService pageService;
	private User user = null;
	private Composite parentContainer;
	   
	/*
	 
	private int selectedLocale;
	
	private Language[] languages;
	private Map<Integer, String> l10nCategory;
	private Map<Integer, String> l10nModule;
	
	private ToolItem dropDownItem;
*/	
	private MainContent mainContent;

	private Text lastPassword;
	private Text newPassword;
	private Text confirmNewPassword;

	private Label resultByPageLabel;
	private Label resultByPageInfo;
	private Label accountLabel;
	private Label accountInfo;
	private Button save;
	private Link singIn;

	Label searchResult;

	// Group
	private Group resultGroup;
//	private Group languageGroup;
	private Group accountGroup;
	private Combo cantResultByPage;

	public ConfigurationOption(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	//	initializel10n();
	}
/*
	void initializel10n() {
		l10nCategory = new HashMap<Integer, String>();
		l10nCategory.put(0, "categoryEs");
		l10nCategory.put(1, "categoryEn");

		l10nModule = new HashMap<Integer, String>();
		l10nModule.put(0, "moduleNameEs");
		l10nModule.put(1, "moduleNameEn");

	}*/
	

	@Override
	public Control createUIControl(Composite parent) {
     
		pageService = this.serviceProvider.get(ContributorService.class);

		mainContent = (MainContent) ((OpacContributorServiceImpl) pageService).getContributorMap().get("MainContentID");

		try {			
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");		
		} catch (Exception e) {
			user = null;		
		}
		
	//	initLanguages(parent.getDisplay());
		
		parentContainer = parent;
		parentContainer.addListener(SWT.Dispose, new LocaleResetListener());

		createContentArea();

		l10n();
		return parentContainer;

	}

	private void createContentArea() {

		try {
			Control[] temp = parentContainer.getChildren();

			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
			e.printStackTrace();
		}

		parentContainer.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		parentContainer.setLayout(new FormLayout());

		addComposite(parentContainer);

		searchResult = new Label(parentContainer, 0);
		addHeader(searchResult);
		resultGroup = new Group(parentContainer, SWT.NONE);
		resultGroup.setBackground(parentContainer.getBackground());
		resultGroup.setLayout(new FormLayout());
		add(resultGroup);

		cantResultByPage = new Combo(resultGroup, SWT.READ_ONLY);
		cantResultByPage.setItems(new String[] { "10", "20", "50", "100" });
		cantResultByPage.select(0);
//		add(cantResultByPage);
		FormDatas.attach(cantResultByPage).atTop(10).atLeft(30).withHeight(18).withWidth(80);

//		br();
		
		resultByPageInfo = new Label(resultGroup, SWT.WRAP);
//		add(resultByPageInfo);
		FormDatas.attach(resultByPageInfo).atTopTo(resultByPageLabel, 12).atLeftTo(cantResultByPage, 10).atRight();
/*
		// // Lenguaje////

		br();

		languageGroup = new Group(parentContainer, SWT.NONE);
		languageGroup.setBackground(parentContainer.getBackground());
		languageGroup.setLayout(new FormLayout());
		add(languageGroup, Percent.W75);

		ToolBar toolBarLanguages = new ToolBar(languageGroup, SWT.WRAP | SWT.FLAT);
		toolBarLanguages.setData(RWT.CUSTOM_VARIANT, "opacI10n");
		FormDatas.attach(toolBarLanguages).atTop(10).atLeft(30);
		dropDownItem = new ToolItem(toolBarLanguages, SWT.DROP_DOWN);
		dropDownItem.setImage(languages[selectedLocale].flag);
		Menu dropDownMenu = createDropDownMenu(dropDownItem);
		dropDownItem.addListener(SWT.Selection, new DropDownSelectionListener(dropDownMenu));
 		
 		*/
		
		// // Cuenta////

		br();
		accountGroup = new Group(parentContainer, SWT.NONE);
		accountGroup.setBackground(parentContainer.getBackground());
		accountGroup.setLayout(new FormLayout());
		add(accountGroup);

		accountLabel = new Label(accountGroup, 0);
		accountLabel.setFont(new Font(accountGroup.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(accountLabel).atTop(10).atLeft(30);

		if (user == null) {

			accountLabel.setVisible(false);
			accountInfo = new Label(accountGroup, 0);
			accountInfo.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LOGGED_IN_OPAC));
			FormDatas.attach(accountInfo).atTop(10).atLeft(30);

			singIn = new Link(accountGroup, SWT.NONE);
			FormDatas.attach(singIn).atTopTo(accountInfo, 12).atLeft(30);
			singIn.addListener(SWT.Selection, new OpacLoginListener(serviceProvider));

		} else {

			lastPassword = new Text(accountGroup, SWT.PASSWORD);
			lastPassword.setMessage(MessageUtil.unescape(AbosMessages.get().MSG_CURRENT_PASS));
			FormDatas.attach(lastPassword).atTopTo(accountLabel, 5).atLeft(30).withWidth(200);

			newPassword = new Text(accountGroup, SWT.PASSWORD);
			newPassword.setMessage(MessageUtil.unescape(AbosMessages.get().MSG_NEW_PASS));

			FormDatas.attach(newPassword).atTopTo(lastPassword, 5).atLeft(30).withWidth(200);

			confirmNewPassword = new Text(accountGroup, SWT.PASSWORD);
			confirmNewPassword.setMessage(MessageUtil.unescape(AbosMessages.get().CONFIRM_PASSWORD));
			FormDatas.attach(confirmNewPassword).atTopTo(newPassword, 5).atLeft(30).withWidth(200);

			save = new Button(accountGroup, SWT.NORMAL);
			save.setText(MessageUtil.unescape(AbosMessages.get().ACCEPT));
			FormDatas.attach(save).atTopTo(confirmNewPassword, 5).atLeft(30);

			save.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					try {
						if (user.getUserPassword() != null) {
							if (lastPassword.getText().isEmpty()||newPassword.getText().isEmpty()||confirmNewPassword.getText().isEmpty()) {
								lastPassword.forceFocus();
								showErrorMessage(cu.uci.abcd.opac.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_PASS);
							}
							else if ((Digest.digest(lastPassword.getText(), "SHA1").equals(user.getUserPassword()))) {
								if (newPassword.getText().equals(confirmNewPassword.getText())) {
									user.setUserPassword(Digest.digest(newPassword.getText(), "SHA1"));

									((RegisterUserViewController) controller).addUser(user);
									lastPassword.setText("");
									newPassword.setText("");
									confirmNewPassword.setText("");
									showInformationMessage(cu.uci.abcd.opac.l10n.AbosMessages.get().MSG_INF_UPDATE_PASS);

								} else {
									confirmNewPassword.forceFocus();
									showErrorMessage(cu.uci.abcd.opac.l10n.AbosMessages.get().MSG_ERROR_PASS_D_MATCH);
								}
							} else {
								lastPassword.forceFocus();
								showErrorMessage(cu.uci.abcd.opac.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_PASS_ACTUAL);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

		}

		cantResultByPage.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				switch (cantResultByPage.getSelectionIndex()) {
				case 0:
					mainContent.setNumberResultsOfUserOnPage(10);
					break;
				case 1:
					mainContent.setNumberResultsOfUserOnPage(20);
					break;
				case 2:
					mainContent.setNumberResultsOfUserOnPage(50);
					break;
				case 3:
					mainContent.setNumberResultsOfUserOnPage(100);
					break;
				default:
					break;
				}

				try {
					mainContent.createPaged();
				} catch (Exception e) {
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

	}

	/*
	private void initLanguages(Display display) {
		languages = new Language[] { new Language(new Locale("es", "ES", ""), AbosImageUtil.loadImage(null, display, "abcdconfig/resources/es-flag.png", false), 0), new Language(Locale.ENGLISH, AbosImageUtil.loadImage(null, display, "abcdconfig/resources/en-flag.png", false), 1) };

	}

	private final class LocaleMenuItemSelectionListener implements Listener {

		private static final long serialVersionUID = 3996024991064201932L;
		private final ToolItem dropDown;

		public LocaleMenuItemSelectionListener(ToolItem dropDown) {
			this.dropDown = dropDown;
		}

		public void handleEvent(Event event) {
			MenuItem item = (MenuItem) event.widget;
			Language language = (Language) item.getData();
			dropDown.setText(language.name);
			dropDown.setImage(language.flag);
			RWT.setLocale(language.locale);
			selectedLocale = language.index;
			createContentArea();
			l10n();

			try {
				mainContent.l10n();

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				OpacPerfilMenu opacAdquisitionMenu = serviceProvider.get(OpacPerfilMenu.class);
				opacAdquisitionMenu.createPerfilMenu();

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				OpacHeaderProvider opacHeaderProvider = serviceProvider.get(OpacHeaderProvider.class);
				opacHeaderProvider.createContentArea();

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				OpacMenuBarProvider opacMenuBarProvider = serviceProvider.get(OpacMenuBarProvider.class);
				opacMenuBarProvider.createMenuBar();

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				OpacFilterMenu opacFilterMenu = serviceProvider.get(OpacFilterMenu.class);
				opacFilterMenu.l10n();

			} catch (Exception e) {
				e.printStackTrace();
			}	   	

			try {

				MyCurrentState myCurrentState = (MyCurrentState) ((OpacContributorServiceImpl) pageService).getContributorMap().get("MyCurrentStateID");
				myCurrentState.l10n();

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				MyHistoryState myHistoryState = (MyHistoryState) ((OpacContributorServiceImpl) pageService).getContributorMap().get("MyHistoryStateID");
				myHistoryState.l10n();

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				RecentAcquisitions recentAcquisitions = (RecentAcquisitions) ((OpacContributorServiceImpl) pageService).getContributorMap().get("RecentAcquisitionsID");
				recentAcquisitions.l10n();
			} catch (Exception e) {
				e.printStackTrace();
			}
			    
			try {
				MySelectionLists mySelectionLists = (MySelectionLists) ((OpacContributorServiceImpl) pageService).getContributorMap().get("ListasDeSeleccionID");
				mySelectionLists.l10n();
			} catch (Exception e) {
				e.printStackTrace();
			}

			refresh();
		}
	}

	private Menu createDropDownMenu(ToolItem dropDown) {
		Menu menu = new Menu(dropDown.getParent().getShell(), SWT.POP_UP);
		for (int i = 0; i < languages.length; i++) {
			MenuItem item = new MenuItem(menu, SWT.PUSH);
			item.setText(languages[i].name);
			item.setImage(languages[i].flag);
			item.setData(languages[i]);
			item.addListener(SWT.Selection, new LocaleMenuItemSelectionListener(dropDown));
		}
		return menu;
	}
*/
	@Override
	public String getID() {
		return "ConfigurationOptionID";
	}    

	@Override
	public void l10n() {
		searchResult.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CONFIGURATION));
		resultGroup.setText(MessageUtil.unescape(AbosMessages.get().LABEL_RESULTS_PER_PAGE));
	//	languageGroup.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LANGUAGE_OPAC));
		accountGroup.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACCOUNT));
		resultByPageInfo.setText(MessageUtil.unescape(AbosMessages.get().LABEL_RESULTS_BY_PAGE));
		accountLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CHANGE_PASS));

		if (singIn != null)
			singIn.setText("<a>" + MessageUtil.unescape(AbosMessages.get().HEADER_LOGIN) + "</a>");

	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_CONFIGURATION_OPTIONS);
	}

}
