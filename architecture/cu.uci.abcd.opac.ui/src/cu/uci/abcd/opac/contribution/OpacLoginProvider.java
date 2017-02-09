package cu.uci.abcd.opac.contribution;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.ModifyEvent;
//import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.l10n.platform.AbosMessages;
import cu.uci.abos.l10n.util.MessageUtil;
import cu.uci.abos.ui.api.IPlatformContributor;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.util.api.IServiceProvider;
//import cu.uci.abos.validation.ui.CustomControlDecoration;

public class OpacLoginProvider implements IPlatformContributor {
	public static final String LOGIN_MENU_CONTROL = OpacLoginProvider.class
			.getName() + "#LOGINMENU";
	public static final int LOGIN_MENU_WIDTH = 170;
	private final IServiceProvider serviceProvider;

	public Composite composite;

	public OpacLoginProvider(IServiceProvider serviceProvider) {
		super();
		this.serviceProvider = serviceProvider;
	}

	@Override
	public Control createUIControl(Composite parent) {
		composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new FormLayout());
		composite.setData(RWT.CUSTOM_VARIANT, "body_background");
		final Text user = new Text(composite, 0);
		user.setMessage(AbosMessages.get().USER);

		FormDatas.attach(user).withWidth(140).withHeight(10).atLeft(15)
				.atRight(15).atTop(30);

		final Text password = new Text(composite, SWT.PASSWORD);
		password.setMessage(MessageUtil.unescape(AbosMessages.get().PASSWORD));

		FormDatas.attach(password).withWidth(140).withHeight(10).atLeft(15)
				.atRight(15).atTopTo(user, 10);

		/*user.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				textChanged(user);
				if (password.getText().length() > 0
						&& password.getData() != null) {
					ControlDecoration decoration = (ControlDecoration) password
							.getData();
					decoration.hide();
					password.setData(null);
					password.setBackground(null);
				}
			}
		});

		password.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				textChanged(password);
				if (user.getText().length() > 0 && user.getData() != null) {
					ControlDecoration decoration = (ControlDecoration) user
							.getData();
					decoration.hide();
					user.setData(null);
					user.setBackground(null);
				}
			}
		});*/

		final Button button = new Button(composite, SWT.PUSH);

		button.setText(AbosMessages.get().BUTTON_LOG_IN);

		FormDatas.attach(button).withWidth(40).withHeight(24).atLeft(50)
				.atRight(50).atTopTo(password, 20);

		// el parametro user es de tipo object , permitiendo pasar tanto Clases
		// del Dominio
		// como tipo de datos primitivos.

		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				login(user, password);
			}
		});

		composite.setVisible(false);
		return composite;
	}

	private void showLoginError(String msg, Text text) {
		/*CustomControlDecoration factory = new CustomControlDecoration();
		ControlDecoration decoration = factory
				.CreateDefaultErrorDecorateToField(text, msg);
		text.setData(decoration);
		text.setBackground(new Color(text.getDisplay(), 255, 204, 153));
		decoration.show();*/
	}

	private void textChanged(Text text) {
		text.setBackground(null);
		ControlDecoration decoration = (ControlDecoration) text.getData();
		if (decoration != null) {
			decoration.hide();
			text.setData(null);
		}
		if (text.getText().length() == 0) {
			showLoginError("Campo requerido!", text);
		}
	}

	private void login(Text usernameText, Text passwordText) {
		textChanged(usernameText);
		textChanged(passwordText);
		if (usernameText.getData() == null && passwordText.getData() == null) {
			composite.setVisible(false);

			OpacHeaderProvider headerProvider = serviceProvider
					.get(OpacHeaderProvider.class);
			// se oculta la opción entrar
			headerProvider.login.setVisible(false);
			headerProvider.usuario.setVisible(true);
			headerProvider.toolBarPerfil.setVisible(true);
			
		

			headerProvider.Login(usernameText.getText());

			// se muestra el area definida para la adquiscion
			OpacAdquisitionMenu adquisitionMenu = serviceProvider
					.get(OpacAdquisitionMenu.class);
			adquisitionMenu.composite.setVisible(true);

			OpacCirculationMenu circulationMenu = serviceProvider
					.get(OpacCirculationMenu.class);
			circulationMenu.composite.setVisible(true);

		}

	}

	@Override
	public String getID() {
		return LOGIN_MENU_CONTROL;
	}

	@Override
	public void l10n() {

	}
}
