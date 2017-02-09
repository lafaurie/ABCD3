package cu.uci.abcd.opac.ui.contribution;

import java.util.List;
import java.util.Vector;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratedControl;
import cu.uci.abos.core.validation.DecoratorType;

public class OpacLoginProvider implements PlatformContributor {
	public static final String LOGIN_MENU_CONTROL = OpacLoginProvider.class.getName() + "#LOGINMENU";
	public static final int LOGIN_MENU_WIDTH = 140;
	public static final int LOGIN_MENU_HEIGHT = 200;

	private final ServiceProvider serviceProvider;
	private ContributorService pageService;
	private LoginService loginService;
	private CustomControlDecoration customControlDecorationFactory;
	private Composite composite;
	private Combo libraryList;
	private Combo domainList;

	private Text user;
	private Text password;
	private Button button;
	private Link registerLink;

	public OpacLoginProvider(ServiceProvider serviceProvider) {
		super();
		this.serviceProvider = serviceProvider;
	}

	@Override
	public Control createUIControl(final Composite parent) {
		composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new FormLayout());
		composite.setData(RWT.CUSTOM_VARIANT, "workspace_content");

		customControlDecorationFactory = new CustomControlDecoration();

		loginService = SecurityUtils.getService();

		user = new Text(composite, 0);
		user.setMessage(AbosMessages.get().USER);
		FormDatas.attach(user).withWidth(140).withHeight(10).atLeft(15).atRight(15).atTop(15);

		password = new Text(composite, SWT.PASSWORD);
		password.setMessage(MessageUtil.unescape(AbosMessages.get().PASSWORD));
		FormDatas.attach(password).withWidth(140).withHeight(10).atLeft(15).atRight(15).atTopTo(user, 10);

		libraryList = new Combo(composite, SWT.READ_ONLY);
		FormDatas.attach(libraryList).withWidth(140).withHeight(22).atLeft(15).atRight(15).atTopTo(password, 10);

		domainList = new Combo(composite, SWT.READ_ONLY);
		FormDatas.attach(domainList).withWidth(140).withHeight(22).atLeft(15).atRight(15).atTopTo(libraryList, 10);

		button = new Button(composite, SWT.PUSH);
		button.setText(AbosMessages.get().BUTTON_LOG_IN);
		FormDatas.attach(button).withWidth(40).withHeight(25).atLeft(40).atRight(40).atTopTo(domainList, 10);

		registerLink = new Link(composite, SWT.NONE);
		registerLink.setText("<a>Registrarse</a>");
		FormDatas.attach(registerLink).atLeft(10).atLeft(40).atRight(40).atTopTo(button, 5);

		pageService = this.serviceProvider.get(ContributorService.class);

		registerLink.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				pageService.selectContributor("RegisterID");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		libraryList.addSelectionListener(new SelectionListener() {

			/**
				 * 
				 */
			private static final long serialVersionUID = 1L;

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

		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				login(user, password);

			}
		});

		loadLibraries();
		l10n();
		composite.setVisible(false);
		return composite;
	}

	private void showLoginError(String msg, Text text, String controlKey) {
		if (customControlDecorationFactory.getControlByKey(controlKey) == null) {
			DecoratedControl[] decoratedControls = new DecoratedControl[] { new DecoratedControl(text, controlKey, msg) };
			customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedControls, DecoratorType.ERROR, 0, SWT.LEFT);
		}
		text.setBackground(new Color(text.getDisplay(), 255, 204, 153));
		customControlDecorationFactory.getControlByKey(controlKey).show();
	}

	private void textChanged(Text text, String controlKey) {
		text.setBackground(null);
		ControlDecoration decoration = customControlDecorationFactory.getControlByKey(controlKey);
		if (decoration != null) {
			decoration.hide();
			customControlDecorationFactory.removeControlDecoration(controlKey);
		}
		if (text.getText().length() == 0) {
			showLoginError("Campo requerido!", text, controlKey);
		}
	}

	private void loadLibraries() {
		UiUtils.initialize(libraryList, loginService.loadParams(), "- Biblioteca -");
		if (RWT.getSettingStore().getAttribute("library") != null) {
			libraryList.select(Integer.parseInt(RWT.getSettingStore().getAttribute("library")));
		}
	}

	private void loadDomains(Library library) {
		List<Ldap> ldapListTemp = new Vector<>();
		if (library != null) {
			if (library.getLdaps().size() > 0) {
				ldapListTemp = new Vector<>(library.getLdaps());
			}
		}
		Ldap localDomain = new Ldap();
		localDomain.setDomain("Local");
		ldapListTemp.add(localDomain);
		UiUtils.initialize(domainList, ldapListTemp, "- Dominio -");
		if (ldapListTemp.size() <= 3) {
			domainList.select(1);
		}
	}

	private void login(Text usernameText, Text passwordText) {
		textChanged(usernameText, "username");
		textChanged(passwordText, "password");
		if (usernameText.getData() == null && passwordText.getData() == null) {
			try {
				this.loginService.login(usernameText.getText(), passwordText.getText(), UiUtils.getSelected(libraryList), UiUtils.getSelected(domainList),"OPAC");

				OpacPerfilMenu opacPerfilMenu = serviceProvider.get(OpacPerfilMenu.class);
				MainContent mainContent = (MainContent) ((OpacContributorServiceImpl) pageService).getContributorMap().get("MainContentID");
				opacPerfilMenu.result.setVisible(true);
				opacPerfilMenu.rightLogo.setVisible(false);
				opacPerfilMenu.l10n();

				user.setText("");
				password.setText("");
				libraryList.select(0);
				domainList.select(0);

				if (!mainContent.records.isEmpty())
					mainContent.createPaged();

			} catch (Exception e1) {
				showLoginError("Credenciales incorrectas!", usernameText, "username");
				showLoginError("Credenciales incorrectas!", passwordText, "password");
			}
		}
	}

	@Override
	public String getID() {
		return LOGIN_MENU_CONTROL;
	}

	@Override
	public void l10n() {

		String[] values = new String[1];
		values[0] = "- Dominio -";
		domainList.setItems(values);
		domainList.setData(null);
		domainList.select(0);

	}

	public void compoVisible(boolean visible) {
		composite.setVisible(visible);
	}
}
