package cu.uci.abos.platform;

import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.Cookie;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.internal.service.ContextProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.ILdapService;
import cu.uci.abos.api.ui.LayoutProvider;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.security.LoginChangedListener;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.security.LoginServiceFactory;
import cu.uci.abos.core.security.LoginServiceFactoryListener;
import cu.uci.abos.core.security.LoginServiceFactoryTracker;
import cu.uci.abos.core.ui.ShellConfigurator;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.platform.contribution.BackgroundProvider;
import cu.uci.abos.platform.contribution.ContentProvider;
import cu.uci.abos.platform.contribution.FooterProvider;
import cu.uci.abos.platform.contribution.HeaderProvider;
import cu.uci.abos.platform.contribution.MainMenu;
import cu.uci.abos.platform.l10n.AbosMessages;

public class MainEntryPoint implements EntryPoint, LoginServiceFactoryListener {
	private ServiceProvider serviceProvider;
	private Display display;
	private LoginService loginService;
	private Combo libraryList;
	private Combo domainList;
	//private Button chkrecordar;
	private ValidatorUtils validator;
	private Label mensajeLabel;

	public MainEntryPoint() {
		this.serviceProvider = ServiceProviderUtil.getService(ServiceProvider.class);
		LoginServiceFactoryTracker tracker = new LoginServiceFactoryTracker();
		tracker.setListener(this);
		tracker.open();
		this.display = new Display();
		RWT.setLocale(new Locale("es", "ES", ""));
	}

	@Override
	public int createUI() {
		synchronized (display) {
			
			if ((this.loginService == null || !this.loginService.isLoggedIn()) && this.loginService.isEnabled()) {

				validator = new ValidatorUtils(new CustomControlDecoration());
				this.createLoginUI();
			} else {
				this.createPlatformUI();
			}
			cookiesAndHeaders();
		}
		return 0;
	}

	private void cookiesAndHeaders() {
		RWT.getUISession().getHttpSession().setMaxInactiveInterval(900);
		Cookie[] cookies= ContextProvider.getRequest().getCookies();
		if (cookies!=null) {
			for (int i = 0; i < cookies.length; i++) {
				cookies[i].setHttpOnly(true);
				ContextProvider.getResponse().addCookie(cookies[i]);
			}
		}   
		    
		ContextProvider.getResponse().setHeader("X-Frame-Options", "SAMEORIGIN");
	//	ContextProvider.getResponse().setHeader("X-Frame-Options", "DENY");

		ContextProvider.getResponse().setHeader("X-Content-Type-Options", "nosniff");
		ContextProvider.getResponse().setHeader("X-XSS-Protection", "1; mode=block");
		ContextProvider.getResponse().setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' 'unsafe-eval'; style-src 'self' 'unsafe-inline'; frame-src *; img-src * data: blob:; font-src 'self' data:; media-src *; connect-src *");
			//new 2016-12-01
		ContextProvider.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		ContextProvider.getResponse().setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		ContextProvider.getResponse().setHeader("Access-Control-Max-Age", "3600");
	
	
	}

	private void createLoginUI() {
		Shell main = new Shell(this.display, SWT.NORMAL);
		main.setLayout(new FormLayout());
		int width = getMaximumwidht();

		main.setData(RWT.CUSTOM_VARIANT, "Login_Background");

		main.setMaximized(true);

		Composite parent = new Composite(main, SWT.BORDER);
		parent.setData(RWT.CUSTOM_VARIANT, "Login_Border");
		parent.setLayout(new FormLayout());
		FormDatas.attach(parent).atLeft(width);

		Composite loginTop = createLoginTop(parent);
		createLoginBottom(parent, loginTop);

		main.layout();
		main.open();
	}

	private int getMaximumwidht() {
		int width;
		Rectangle bounds = Display.getCurrent().getBounds();
		width = bounds.width;
		int mitad = width - 600;
		int result = mitad * 50 / 100;
		return result;
	}

	private int getCenterPosition(int componentSize) {
		int overSpace = 600 - componentSize;
		int result = overSpace * 50 / 100;
		return result;

	}

	private Composite createLoginTop(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);
		result.setLayout(new FormLayout());

		Label label = new Label(result, SWT.NONE);
		label.setData(RWT.CUSTOM_VARIANT, "Banner");

		FormDatas.attach(label).withWidth(139).withHeight(60).atLeft(130).atTop(50);

		Label grafica = new Label(result, SWT.NONE);
		grafica.setData(RWT.CUSTOM_VARIANT, "applicationName");
		grafica.setText(MessageUtil.unescape(AbosMessages.get().HEADER_SYSTEM_NAME));

		FormDatas.attach(grafica).atLeftTo(label, 15).atTop(67);

		Label loginGraficaL = new Label(result, SWT.NONE);
		loginGraficaL.setData(RWT.CUSTOM_VARIANT, "Login_grafica");
		FormDatas.attach(loginGraficaL).withHeight(129).atTopTo(label, 10).withWidth(600);

		return result;
	}

	@SuppressWarnings("serial")
	private Composite createLoginBottom(Composite parent, Composite loginTop) {

		parent.setData(RWT.CUSTOM_VARIANT, "Login_Bottom_Background");

		Composite result = new Composite(parent, SWT.None);
		FormDatas.attach(result).atTopTo(loginTop);
		result.setData(RWT.CUSTOM_VARIANT, "Login_Bottom_Background");
		result.setLayout(new FormLayout());

		libraryList = new Combo(result, SWT.READ_ONLY);
		int centerText = getCenterPosition(210);
		FormDatas.attach(libraryList).withWidth(147).withHeight(23).atLeft(centerText).atRight(centerText).atTop(8);
		libraryList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String[] values = new String[1];
				values[0] = "- Dominio -";
				domainList.setItems(values);
				domainList.setData(null);
				domainList.select(0);
				Library library = (Library) UiUtils.getSelected(libraryList);
				loadDomains(library);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		final Text text = new Text(result, SWT.NORMAL);
		text.setMessage(AbosMessages.get().USER);
		validator.applyValidator(text, "textRequired", DecoratorType.REQUIRED_FIELD, false);
		if (RWT.getSettingStore().getAttribute("username") != null) {
			text.setText(RWT.getSettingStore().getAttribute("username"));
		}
		FormDatas.attach(text).withWidth(147).withHeight(10).atLeft(centerText).atRight(centerText).atTopTo(libraryList, 20);

		

		final Text clave = new Text(result, SWT.PASSWORD);
		validator.applyValidator(clave, "claveRequired", DecoratorType.REQUIRED_FIELD, false);

		if (RWT.getSettingStore().getAttribute("password") != null) {
			clave.setText(RWT.getSettingStore().getAttribute("password"));
		}
		clave.setMessage(MessageUtil.unescape(AbosMessages.get().PASSWORD));

		text.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				mensajeLabel.setVisible(false);

			}
		});

		

		clave.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				mensajeLabel.setVisible(false);

			}
		});

		FormDatas.attach(clave).withWidth(147).withHeight(10).atLeft(centerText).atRight(centerText).atTopTo(text, 8);

		
		domainList = new Combo(result, SWT.READ_ONLY);
		FormDatas.attach(domainList).withWidth(147).withHeight(23).atLeft(centerText).atRight(centerText).atTopTo(clave, 8);

		//chkrecordar = new Button(result, SWT.CHECK);
		//chkrecordar.setText(MessageUtil.unescape(AbosMessages.get().REMEMBER_PASSWORD));
		//int centerChkrecordar = getCenterPosition(210);
		//FormDatas.attach(chkrecordar).withWidth(210).withHeight(20).atLeft(centerChkrecordar + 10).atRight(centerChkrecordar - 10).atTopTo(domainList, 15);
		//if (RWT.getSettingStore().getAttribute("library") != null) {
			//chkrecordar.setSelection(true);
		//}

		final Button button = new Button(result, SWT.PUSH);

		button.setText(AbosMessages.get().BUTTON_LOG_IN);

		int centerButton = getCenterPosition(64);
		FormDatas.attach(button).withWidth(65).withHeight(24).atLeft(centerButton).atRight(centerButton).atTopTo(domainList, 10);

		clave.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent event) {
			}

			@Override
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == 13) {
					login(text, clave);
				}
			}
		});

		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				login(text, clave);
			}
		});

		// Composite msgComposite = new Composite(parent, style)
		mensajeLabel = new Label(parent, SWT.CENTER | SWT.WRAP);
		mensajeLabel.setText(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_INCORRECT_PASSWORD));
		mensajeLabel.setData(RWT.CUSTOM_VARIANT, "loginErrorMessage");
		int centerLabel = getCenterPosition(210);
		FormDatas.attach(mensajeLabel).withWidth(140).atLeft(centerLabel).atRight(centerLabel).atTopTo(result, 15).atBottom(30);
		mensajeLabel.setVisible(false);

		loadLibraries();
		Library selectedLibrary = (UiUtils.getSelected(libraryList) == null) ? null : (Library) UiUtils.getSelected(libraryList);
		loadDomains(selectedLibrary);

		// DecoratedControl[] decoratedControls = new DecoratedControl[] { new
		// DecoratedControl(text, "username",
		// MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED))
		// };
		// customControlDecorationFactory.createDecorator(customControlDecorationFactory,
		// decoratedControls, DecoratorType.ERROR, 0, SWT.LEFT);

		// DecoratedControl[] decoratedControls1 = new DecoratedControl[] { new
		// DecoratedControl(clave, "password",
		// MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED))
		// };
		// customControlDecorationFactory.createDecorator(customControlDecorationFactory,
		// decoratedControls1, DecoratorType.ERROR, 0, SWT.LEFT);

		button.getShell().setDefaultButton(button);
		// Display.getDefault().getActiveShell().setDefaultButton(button);
		return result;
	}

	private void createPlatformUI() {
		RWT.setLocale(new Locale("es", "ES", ""));
		Shell shell = this.configureShell();
		shell.open();
		shell.setData(RWT.CUSTOM_VARIANT, "Principal");
	}

	private Shell configureShell() {
		MainMenu mainMenu = new MainMenu(serviceProvider, loginService);
		HeaderProvider header =new HeaderProvider(serviceProvider);
		PlatformContributor footer = new FooterProvider();
		header.setFooter(footer);
		PlatformContributor[] pageStructureProviders = new PlatformContributor[] { header, mainMenu, new ContentProvider(serviceProvider), footer,
				new BackgroundProvider() };
		serviceProvider.register(MainMenu.class, mainMenu);
		LayoutProvider layoutProvider = new LayoutProviderImpl();
		ShellConfigurator configurator = new ShellConfigurator(serviceProvider, this.loginService.isEnabled());
		Shell shell = configurator.configure(pageStructureProviders, layoutProvider);
		shell.layout(true);
		return shell;
	}

	private void login(Text usernameText, Text passwordText) {
		if (!validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
	
			
			if (usernameText.getData() == null
					&& passwordText.getData() == null
					&& usernameText.getText().length() > 0
					&& passwordText.getText().length() > 0 ) {
	            try {
					this.loginService.login(usernameText.getText(), passwordText.getText(), UiUtils.getSelected(libraryList), UiUtils.getSelected(domainList),"ABCD");
					mensajeLabel.setVisible(false);
				} catch (Exception e1) {
					mensajeLabel.setVisible(true);
				}
			}else{
				mensajeLabel.setVisible(true);
			}
		}
		
	}

	private void loadLibraries() {
		UiUtils.initialize(libraryList, loginService.loadParams(), "- " + AbosMessages.get().LIBRARY + " -");
		if (loginService.loadParams().size() == 1) {
			libraryList.select(1);
		}
	}

	private void loadDomains(Library library) {
		List<Ldap> ldapList = new Vector<>();
		if (library != null) {
			ILdapService ldapService = ServiceProviderUtil.getService(ILdapService.class);
			ldapList = new Vector<>(ldapService.findAllEnabledByLibrary(library.getLibraryID()));
		}
		Ldap localDomain = new Ldap();
		localDomain.setDomain("Local");
		ldapList.add(localDomain);
		UiUtils.initialize(domainList, ldapList, "- " + AbosMessages.get().DOMAIN + " -");
		if (ldapList.size() <= 3) {
			domainList.select(1);
		}
		
	}

	@Override
	public void setLoginServiceFactory(LoginServiceFactory loginServiceFactoryParam) {

		final LoginService login;

		if (SecurityUtils.getService() == null) {
			login = loginServiceFactoryParam.createLoginService();
			RWT.getUISession().getHttpSession().setMaxInactiveInterval(login.getSessionTimeout());
			SecurityUtils.setService(login);
		} else {
			login = SecurityUtils.getService();
		}

		this.loginService = login;
		this.serviceProvider.register(LoginService.class, login);
		this.loginService.onLoginChanged(new LoginChangedListener() {
			@Override
			public void handle() {
				if (!display.isDisposed()) {
					MainEntryPoint.this.createUI();
				}
			}
		});

	}
}
