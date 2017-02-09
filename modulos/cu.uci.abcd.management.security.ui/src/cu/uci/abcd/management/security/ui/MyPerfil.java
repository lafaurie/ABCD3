package cu.uci.abcd.management.security.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.controller.UserViewController;
import cu.uci.abcd.management.security.util.Auxiliary;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.Digest;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratedControl;
import cu.uci.abos.core.validation.DecoratorType;

public class MyPerfil extends ContributorPage implements Contributor {

	Button changeButton;
	User me;
	Text oldPassword;
	Text newPassword;
	Text newPasswordConfirm;
	Label ContrasennaAnterior;
	Label newPass;
	Label passwordConfirm;
	Label myPerfilLabel;
	Label nameAndSurname;
	Label identification;
	Label sex;
	Label age;
	Label birthDate;
	Label email;
	Label address;
	Label userName;
	Link changePass;
	Composite change;

	private CustomControlDecoration customControlDecorationFactory;

	@Override
	public Control createUIControl(Composite parent) {
		customControlDecorationFactory = new CustomControlDecoration();
		int total = parent.getDisplay().getBounds().width;
		double middle1 = (total * 0.375);
		int middle = Integer.valueOf((int) Math.round(middle1)) + 250;
		me = ((UserViewController) controller).readUser(((User) SecurityUtils.getPrincipal().getByKey("user")).getUserID());
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		myPerfilLabel = new Label(parent, SWT.NONE);
		addHeader(myPerfilLabel);

		Label sep = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(sep).atTopTo(myPerfilLabel, 5).atLeft(15).atRight(15);

		Label pictureLabel = new Label(parent, SWT.BORDER);
		FormDatas.attach(pictureLabel).atTopTo(sep, 20).atLeft(25);
		
		Image photo = SecurityUtils.getService().getPrincipal().getPhoto();
		if (photo==null) {
			photo = new Image(Display.getDefault(), AbosImageUtil
					.loadImage(null, Display.getCurrent(),
							"abcdconfig/resources/photo.png", false)
					.getImageData().scaledTo(100, 100));
		}
		
		pictureLabel.setImage(photo);
		
		nameAndSurname = new Label(parent, SWT.NONE);
		nameAndSurname.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(nameAndSurname).atTopTo(pictureLabel, 20).atLeft(25);

		Label nameAndSurnameValue = new Label(parent, SWT.NONE);
		nameAndSurnameValue.setText((me.getPerson()==null)?"-":me.getPerson().getFullName());
		FormDatas.attach(nameAndSurnameValue).atTopTo(pictureLabel, 20).atLeftTo(nameAndSurname);

		identification = new Label(parent, SWT.NONE);
		identification.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(identification).atTopTo(nameAndSurname, 5).atRightTo(nameAndSurnameValue);

		Label identificationValue = new Label(parent, SWT.NONE);
		identificationValue.setText((me.getPerson()==null)?"-":me.getPerson().getDNI());
		FormDatas.attach(identificationValue).atTopTo(nameAndSurname, 5).atLeftTo(identification);

		sex = new Label(parent, SWT.NONE);
		sex.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(sex).atTopTo(identification, 5).atRightTo(nameAndSurnameValue);

		Label sexValue = new Label(parent, SWT.NONE);
		sexValue.setText((me.getPerson()==null)?"-":me.getPerson().getSex().getNomenclatorName());
		FormDatas.attach(sexValue).atTopTo(identification, 5).atLeftTo(sex);

		age = new Label(parent, SWT.NONE);
		age.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(age).atTopTo(sex, 5).atRightTo(nameAndSurnameValue);

		Label ageValue = new Label(parent, SWT.NONE);
		
		ageValue.setText((me.getPerson()==null)?"-":String.valueOf(Auxiliary.getAge(me.getPerson().getBirthDate())));
		FormDatas.attach(ageValue).atTopTo(sex, 5).atLeftTo(age);

		birthDate = new Label(parent, SWT.NONE);
		birthDate.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(birthDate).atTopTo(age, 5).atRightTo(nameAndSurnameValue);

		Label birthDateValue = new Label(parent, SWT.NONE);
		
		birthDateValue.setText((me.getPerson()==null)?"-":Auxiliary.FormatDate(me.getPerson().getBirthDate()));
		FormDatas.attach(birthDateValue).atTopTo(age, 5).atLeftTo(birthDate);

		email = new Label(parent, SWT.NONE);
		email.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(email).atTopTo(birthDate, 5).atRightTo(nameAndSurnameValue);

		Label emailValue = new Label(parent, SWT.NONE);
		emailValue.setText((me.getPerson()==null)?"-":me.getPerson().getEmailAddress());
		FormDatas.attach(emailValue).atTopTo(birthDate, 5).atLeftTo(email);

		address = new Label(parent, SWT.NONE);
		address.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(address).atTopTo(email, 5).atRightTo(nameAndSurnameValue);

		Label addressValue = new Label(parent, SWT.NONE);
		addressValue.setText((me.getPerson()==null)?"-":me.getPerson().getAddress());
		FormDatas.attach(addressValue).atTopTo(email, 5).atLeftTo(address);

		userName = new Label(parent, SWT.NONE);
		userName.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(userName).atTopTo(address, 5).atRightTo(nameAndSurnameValue);

		Label userNameValue = new Label(parent, SWT.NONE);
		userNameValue.setText(me.getUsernameToString());
		FormDatas.attach(userNameValue).atTopTo(address, 5).atLeftTo(userName);

		
		if(me.getDomain()==null){
		changePass = new Link(parent, SWT.NONE);
		changePass.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
		changePass.setVisible(false);
		FormDatas.attach(changePass).atTopTo(address, 20).atLeftTo(userNameValue, 5);
		changePass.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = -4334635919782812250L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				/*
				changeButton.setVisible(true);
				oldPassword.setVisible(true);
				newPassword.setVisible(true);
				newPasswordConfirm.setVisible(true);
				ContrasennaAnterior.setVisible(true);
				newPass.setVisible(true);
				passwordConfirm.setVisible(true);
				*/
				change.setVisible(true);
				
				customControlDecorationFactory.removeControlDecoration("oldPassword");
				customControlDecorationFactory.removeControlDecoration("newPassword");
				customControlDecorationFactory.removeControlDecoration("newPasswordConfirm");
				
				//customControlDecorationFactory.getControlByKey("oldPassword").r
				oldPassword.setText("");
				newPassword.setText("");
				newPasswordConfirm.setText("");
				
				
				DecoratedControl[] decoratedRequiredOldPass = new DecoratedControl[] { new DecoratedControl(oldPassword, "oldPassword", "Este campo es obligatorio") };
				customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequiredOldPass, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT, 25);
				DecoratedControl[] decoratedRequirednewPassword = new DecoratedControl[] { new DecoratedControl(newPassword, "newPassword", "Este campo es obligatorio") };
				customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequirednewPassword, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT, 25);
				DecoratedControl[] decoratedRequirednewPasswordConfirm = new DecoratedControl[] { new DecoratedControl(newPasswordConfirm, "newPasswordConfirm", "Este campo es obligatorio") };
				customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequirednewPasswordConfirm, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT, 25);

				
				
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		Ldap domain = (Ldap) SecurityUtils.getService().getPrincipal().getByKey("domain");
		if(domain.getLdapID()==null){
			changePass.setVisible(true);
		}
		
		change = new Composite(parent, SWT.BORDER);
		change.setLayout(new FormLayout());
		change.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(change).atTopTo(myPerfilLabel, 20).atRight(15);
		
		
		
		oldPassword = new Text(change, SWT.PASSWORD);
		FormDatas.attach(oldPassword).atTopTo(change, 25).atRight(15).withWidth(250).withHeight(10);
		
		ContrasennaAnterior = new Label(change, SWT.NONE);
		FormDatas.attach(ContrasennaAnterior).atTopTo(change, 30).atRightTo(oldPassword, 5);
		
        newPassword = new Text(change, SWT.PASSWORD);
		FormDatas.attach(newPassword).atTopTo(oldPassword, 15).atRight(15).withWidth(250).withHeight(10);

		newPass = new Label(change, SWT.NONE);
		FormDatas.attach(newPass).atTopTo(oldPassword, 20).atRightTo(newPassword, 5);
		
		newPasswordConfirm = new Text(change, SWT.PASSWORD);
		FormDatas.attach(newPasswordConfirm).atTopTo(newPassword, 15).atRight(15).withWidth(250).withHeight(10);

		passwordConfirm = new Label(change, SWT.NONE);
		FormDatas.attach(passwordConfirm).atTopTo(newPassword, 20).atRightTo(newPasswordConfirm, 5).atLeft(15);
		
		
		changeButton = new Button(change, SWT.PUSH);
		// changeButton.setVisible(false);
		FormDatas.attach(changeButton).atTopTo(newPasswordConfirm, 20).atRight(15).withWidth(130).withHeight(23).atBottom(15);

		changeButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				User user = ((UserViewController) controller).readUser(me.getUserID());

				String newPas = newPassword.getText();
				String newPassConfirm = newPasswordConfirm.getText();

				String password = Digest.digest(oldPassword.getText(), "SHA1");
				if (customControlDecorationFactory.AllControlDecorationsHide()) {
					if (user.getUserPassword().equals(password)) {
						if (newPas.equals(newPassConfirm) || newPas == newPassConfirm) {
							user.setUserPassword(Digest.digest(newPassword.getText(), "SHA1"));
							((UserViewController) controller).addUser(user);
							RetroalimentationUtils.showInformationShellMessage(
									MessageUtil
											.unescape("Su contrase&ntilde;a ha sido cambiada satisfactoriamente."));
							/*
							changeButton.setVisible(false);
							oldPassword.setVisible(false);
							newPassword.setVisible(false);
							newPasswordConfirm.setVisible(false);
							ContrasennaAnterior.setVisible(false);
							newPass.setVisible(false);
							passwordConfirm.setVisible(false);
							*/
							change.setVisible(false);
							
						} else {
							RetroalimentationUtils.showErrorShellMessage(
									MessageUtil
											.unescape(AbosMessages
													.get().NOT_EQUAL_PASSWORD));
						}
					} else {
						RetroalimentationUtils.showErrorShellMessage(
								MessageUtil
										.unescape(AbosMessages
												.get().INCORRECT_PASSWORD));
					}
				} else {
					RetroalimentationUtils.showErrorShellMessage(
							MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_ERROR_FIELD_REQUIRED));
				}
			}

		});
		/*
		changeButton.setVisible(false);
		oldPassword.setVisible(false);
		newPassword.setVisible(false);
		newPasswordConfirm.setVisible(false);
		ContrasennaAnterior.setVisible(false);
		newPass.setVisible(false);
		passwordConfirm.setVisible(false);
		*/
		change.setVisible(false);
		
	}
		l10n();

		return parent;
	}

	@Override
	public String getID() {
		return "myPerfilID";
	}

	@Override
	public void l10n() {
		myPerfilLabel.setText(MessageUtil.unescape(AbosMessages.get().MY_PROFILE.toUpperCase()));
		nameAndSurname.setText(MessageUtil.unescape(AbosMessages.get().NAME_AND_SURNAME) + ": ");
		identification.setText(MessageUtil.unescape(AbosMessages.get().IDENTIFICATION) + ": ");
		sex.setText(MessageUtil.unescape(AbosMessages.get().SEX) + ": ");
		age.setText(MessageUtil.unescape(AbosMessages.get().AGE) + ": ");
		birthDate.setText(MessageUtil.unescape(AbosMessages.get().BIRTHDAY) + ": ");
		email.setText(MessageUtil.unescape(AbosMessages.get().EMAIL) + ": ");
		address.setText(MessageUtil.unescape(AbosMessages.get().ADDRESS) + ": ");
		userName.setText(MessageUtil.unescape(AbosMessages.get().USER) + ": ");
		if(me.getDomain()==null){
		changePass.setText("<a>" + MessageUtil.unescape(AbosMessages.get().CHANGE_PASSWORD) + "</a>");
		ContrasennaAnterior.setText(MessageUtil.unescape(AbosMessages.get().PASSWORD) );
		newPass.setText(MessageUtil.unescape(AbosMessages.get().LAST_PASSWORD) );
		passwordConfirm.setText(MessageUtil.unescape(AbosMessages.get().CONFIRM_PASSWORD) );
		changeButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		}
		
		myPerfilLabel.getParent().layout(true, true);
		myPerfilLabel.getParent().redraw();
		myPerfilLabel.getParent().update();
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().MY_PROFILE);
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

}
