/*
 * @(#)HeaderProvider.java 1.0.0 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abcd.opac.ui.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.listener.DropDownSelectionListener;
import cu.uci.abcd.opac.ui.listener.OpacLoginListener;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.security.LoginChangedListener;
import cu.uci.abos.core.security.LoginException;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.security.LoginServiceFactory;
import cu.uci.abos.core.security.LoginServiceFactoryListener;
import cu.uci.abos.core.security.LoginServiceFactoryTracker;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;

public class OpacHeaderProvider implements PlatformContributor, LoginServiceFactoryListener {
	public static final String HEADER_CONTROL = OpacHeaderProvider.class.getName() + "#HEADER";

	public static final int HEADER_HEIGHT = 30;
	private Composite parentContainer;
	private Composite contentContainer;
	private ServiceProvider serviceProvider;
	private ContributorService pageService;
	private MainContent mainContent;
	private LoginService loginService;
	private User user;
	public Label userPicture;

	public ToolBar toolBarPerfil;

	public Composite result;

	public Link login;

	public OpacHeaderProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
		LoginServiceFactoryTracker tracker = new LoginServiceFactoryTracker();
		tracker.setListener(this);
		tracker.open();
	}

	@Override
	public Control createUIControl(Composite parent) {
		parentContainer = parent;
		pageService = this.serviceProvider.get(ContributorService.class);
		mainContent = (MainContent) ((OpacContributorServiceImpl) pageService).getContributorMap().get("MainContentID");
		createContentArea();
		return contentContainer;
	}

	private void createBanner() {
		Label logo = new Label(contentContainer, SWT.NONE);
		logo.setData(RWT.CUSTOM_VARIANT, "opac-banner");

		FormDatas.attach(logo).withWidth(139).withHeight(30).atLeft(20);

		Label grafica = new Label(contentContainer, SWT.NONE);
		grafica.setData(RWT.CUSTOM_VARIANT, "opac-application-name");
		grafica.setText(MessageUtil.unescape(AbosMessages.get().HEADER_OPAC_TEXT));

		FormDatas.attach(grafica).atLeftTo(logo, -10).atTop(8);

	}

	public void login() {

		userPicture.setVisible(true);
		toolBarPerfil.setVisible(true);
		OpacPerfilMenu adquisitionMenu = serviceProvider.get(OpacPerfilMenu.class);		

		if (adquisitionMenu != null) {
			adquisitionMenu.result.setVisible(true);
		}

		String[] User = { AbosMessages.get().HEADER_PROFILE, AbosMessages.get().HEADER_LOG_OFF };

		FormDatas.attach(toolBarPerfil).atRight(30);
		ToolItem dropDownProfileItem = new ToolItem(toolBarPerfil, SWT.DROP_DOWN);
		dropDownProfileItem.setData(RWT.CUSTOM_VARIANT, "opac-profile");

		FormDatas.attach(userPicture).withWidth(40).withHeight(30).atRightTo(toolBarPerfil);

		if (user.getPerson() != null) {
			Image image = user.getPerson().getPhoto().getImage(40, 30);
			userPicture.setImage(image);
		}
		String usuarioA = "";
		try {
			usuarioA = user.getUsername();
		} catch (Exception e) {
			e.printStackTrace();
		}

		dropDownProfileItem.setText(usuarioA);
		Menu dropDownMenuProfile = createDropDownMenuText1(dropDownProfileItem, User);
		dropDownProfileItem.addListener(SWT.Selection, new DropDownSelectionListener(dropDownMenuProfile));

	}

	public void logout() {

		if (loginService != null && loginService.isEnabled()) {

			try {
				loginService.logout();
				userPicture.setVisible(false);
				toolBarPerfil.setVisible(false);  
				login.setVisible(true);
				// loginService.fireLoginChangedEvent();
				
				// Se muestra el area definida para la adquiscion
				OpacPerfilMenu adquisitionMenu = serviceProvider.get(OpacPerfilMenu.class);
				adquisitionMenu.rightLogo.setVisible(true);
				if (!mainContent.records.isEmpty())
					mainContent.createPaged();

			} catch (LoginException e) {
				e.printStackTrace();
			}
		}
	}

	private Menu createDropDownMenuText1(ToolItem dropDown, String[] options) {
		Menu menu = new Menu(dropDown.getParent().getShell(), SWT.POP_UP);
	//	for (int i = 0; i < options.length; i++) {
			MenuItem item = new MenuItem(menu, SWT.PUSH);
			item.setText(options[1]);
			item.setData(options[1]);
			//if (i == 1) {
				item.addSelectionListener(new SelectionAdapter() {

					private static final long serialVersionUID = 1L;

					@Override
					public void widgetSelected(SelectionEvent e) {
						logout();

					}
				});
	//		}
	//	}

		return menu;
	}

	private Composite createProfileMenu(Composite parent) {
		result = new Composite(parent, SWT.NONE);
		result.setLayout(new FormLayout());
		result.setData(RWT.CUSTOM_VARIANT, "blue-profile");
		FormDatas.attach(result).atRight(-10);
		initializeProfileComponents();
		if (this.loginService == null || !this.loginService.isLoggedIn() && this.loginService.isEnabled()) {
			login.setVisible(true);
		} else {
			login.setVisible(false);
			try {
				user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
			} catch (NullPointerException e) {
			}

			login();
		}
		return result;
	}

	private void initializeProfileComponents() {
		if (login == null || login.isDisposed()) {
			login = new Link(result, SWT.NONE);
			login.setText("<a>" + MessageUtil.unescape(AbosMessages.get().HEADER_LOGIN_TEXT) + "</a>");
			login.setData(RWT.CUSTOM_VARIANT, "link-blue-profile");
			login.setVisible(false);
			FormDatas.attach(login).withWidth(140).atTop(6).atLeft(20);
			login.addListener(SWT.Selection, new OpacLoginListener(serviceProvider));
		}

		if (userPicture == null || userPicture.isDisposed()) {
			userPicture = new Label(result, SWT.NONE);
			userPicture.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
			userPicture.setVisible(false);
		}
		if (toolBarPerfil == null || toolBarPerfil.isDisposed()) {
			toolBarPerfil = new ToolBar(result, SWT.NONE);
			toolBarPerfil.setVisible(false);

		}
	}

	@Override
	public String getID() {
		return HEADER_CONTROL;
	}

	public void createContentArea() {
		if (contentContainer != null) {
			Control[] controls = contentContainer.getChildren();
			for (Control auxControl : controls) {
				auxControl.dispose();
			}
		} else if (contentContainer == null) {
			contentContainer = new Composite(parentContainer, SWT.INHERIT_DEFAULT);
			contentContainer.setLayout(new FormLayout());
			contentContainer.setData(RWT.CUSTOM_VARIANT, "header");
		}
		createBanner();
		createProfileMenu(contentContainer);
		contentContainer.layout();
	}

	@Override
	public void l10n() {

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
		if (serviceProvider.get(LoginService.class) == null) {
			serviceProvider.register(LoginService.class, loginService);
		}
		this.loginService.onLoginChanged(new LoginChangedListener() {
			@Override
			public void handle() {
				final ServerPushSession pushSession = new ServerPushSession();
				final Display display = Display.getCurrent();
				Runnable bgRunnable = new Runnable() {
					@Override
					public void run() {
						// do some background work ...
						// schedule the UI update
						display.asyncExec(new Runnable() {
							@Override
							public void run() {
								if (!contentContainer.isDisposed()) {
									createContentArea();
									pushSession.stop();
								}
							}
						});
					}
				};
				pushSession.start();
				Thread bgThread = new Thread(bgRunnable);
				bgThread.setDaemon(true);
				bgThread.start();
			}
		});
	}
}