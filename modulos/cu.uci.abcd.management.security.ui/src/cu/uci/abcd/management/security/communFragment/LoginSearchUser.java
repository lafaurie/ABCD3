package cu.uci.abcd.management.security.communFragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.login.LoginException;


import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPResponse;
import com.novell.ldap.LDAPResponseQueue;
import com.novell.ldap.LDAPSearchResults;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.ILdapService;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.util.UserClass;
import cu.uci.abos.core.ui.Popup;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class LoginSearchUser extends Popup {

	private static final long serialVersionUID = 4196021109430953068L;
	private Combo domainList;
	private Text passwordText;
	private Button chkrecordar;
	private UserClass userClass = null;
	boolean noSelect = true;
	private Text userText;
	private String userName;
	private final SelectionListener listener;
	private ValidatorUtils validator;

	public LoginSearchUser(Shell parent, int shellStyle, String title, Control contentProxy,
			SelectionListener listener) {
		super(parent, shellStyle, title, contentProxy, listener);
		this.listener = listener;
		
	}

	public Control createUI(Composite parent) {
		validator = new ValidatorUtils(new CustomControlDecoration());
		parent.setLayout(new FormLayout());
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(parent).atLeft(0).atRight(0).atBottom(0).atTop(0);
		userText = new Text(parent, SWT.NONE);
		FormDatas.attach(userText).atRight(15).atTop(15).withWidth(130)
				.withHeight(11);
		validator.applyValidator(userText, "userTextRequired",
				DecoratorType.REQUIRED_FIELD, true);

		if (RWT.getSettingStore().getAttribute("userSearch") != null) {
			userText.setText(RWT.getSettingStore().getAttribute("userSearch"));
		}

		passwordText = new Text(parent, SWT.PASSWORD);
		FormDatas.attach(passwordText).atRight(15).atTopTo(userText, 15)
				.withWidth(130).withHeight(11);
		validator.applyValidator(passwordText, "passwordTextRequired",
				DecoratorType.REQUIRED_FIELD, true);

		if (RWT.getSettingStore().getAttribute("passWordSearch") != null) {
			passwordText.setText(RWT.getSettingStore().getAttribute(
					"passWordSearch"));
		}

		domainList = new Combo(parent, SWT.READ_ONLY);
		FormDatas.attach(domainList).atRight(15).atTopTo(passwordText, 15)
				.withWidth(151).withHeight(23);
		validator.applyValidator(domainList, "domainListRequired",
				DecoratorType.REQUIRED_FIELD, true);

		ILdapService ldapService = ServiceProviderUtil
				.getService(ILdapService.class);
		Library library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		List<Ldap> listLdap = ldapService.findAllByLibrary(library
				.getLibraryID());
		UiUtils.initialize(domainList, listLdap);

		if (RWT.getSettingStore().getAttribute("domainSearch") != null) {
			for (int i = 0; i < domainList.getItemCount(); i++) {
				if (domainList
						.getItem(i)
						.toString()
						.equals(RWT.getSettingStore().getAttribute(
								"domainSearch"))) {
					domainList.select(i);
					noSelect = false;
				}
			}
		}
		chkrecordar = new Button(parent, SWT.CHECK);
		chkrecordar.setText(AbosMessages.get().REMEMBER);
		FormDatas.attach(chkrecordar).atRight(150).atTopTo(domainList, 10);
		if (RWT.getSettingStore().getAttribute("userSearch") != null) {
			chkrecordar.setSelection(true);
		}
		Label userLabel = new Label(parent, SWT.NONE);
		userLabel.setText(MessageUtil.unescape(AbosMessages.get().USER));
		FormDatas.attach(userLabel).atRightTo(userText, 5).atTop(20);

		Label passwordLabel = new Label(parent, SWT.NONE);
		passwordLabel
				.setText(MessageUtil.unescape(AbosMessages.get().PASSWORD));
		FormDatas.attach(passwordLabel).atRightTo(passwordText, 5)
				.atTopTo(userLabel, 21);

		Label domainLabel = new Label(parent, SWT.NONE);
		domainLabel.setText(MessageUtil.unescape(AbosMessages.get().DOMAIN));
		FormDatas.attach(domainLabel).atRightTo(domainList, 5)
				.atTopTo(passwordLabel, 22);

		Button cancelAcept = new Button(parent, SWT.NONE);
		cancelAcept
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		FormDatas.attach(cancelAcept).atTopTo(chkrecordar, 10).withHeight(23)
				.atRight(15);

		Button btnAcept = new Button(parent, SWT.NONE);
		btnAcept.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		FormDatas.attach(btnAcept).atTopTo(chkrecordar, 10).withHeight(23)
				.atRightTo(cancelAcept, 15);
		btnAcept.addSelectionListener((SelectionListener) listener);

		cancelAcept.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -1016351923992643625L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});

		if (noSelect) {
			userText.setText("");
			passwordText.setText("");
			domainList.select(0);
			chkrecordar.setSelection(false);
		}
		return parent;
	}

	public void search() throws LoginException, UnsupportedEncodingException,
			LDAPException {
		LDAPConnection connection = new LDAPConnection();
		LDAPResponseQueue responseQueue = null;
		byte[] passwordBytes = passwordText.getText().getBytes("UTF8");
		String username = userText.getText();
		Ldap ldap = (Ldap) UiUtils.getSelected(domainList);
		String loginDN = String.format("%s@%s", username, ldap.getDomain());

		connection.connect(ldap.getHost(), ldap.getPort());
		responseQueue = connection.bind(LDAPConnection.LDAP_V3, loginDN,
				passwordBytes, (LDAPResponseQueue) null);
		LDAPResponse response = (LDAPResponse) responseQueue.getResponse();
		int bindResult = response.getResultCode();
		if (bindResult == LDAPException.SUCCESS) {
			String domainn = ldap.getDomain();
			String[] baseDnArrayy = domainn.split("\\.");
			String baseDn = "dc=" + baseDnArrayy[0];
			for (int i = 1; i < baseDnArrayy.length; i++) {
				baseDn = baseDn + ", dc=" + baseDnArrayy[i];
			}
			// String baseDn = "dc=uci,dc=cu";
			LDAPSearchResults results = connection.search(baseDn,
					LDAPConnection.SCOPE_SUB, "(&(sAMAccountName="
							+ getUserName() + "))", null, false);
			try {
				LDAPEntry userEntry = results.next();
				LDAPAttributeSet aaaaaa = userEntry.getAttributeSet();

				List<String> aaaaa = new ArrayList<>();
				for (Iterator<?> iterator = aaaaaa.iterator(); iterator
						.hasNext();) {
					LDAPAttribute ldapAttribute = (LDAPAttribute) iterator
							.next();
					String attributeName = ldapAttribute.getName();
					String attributeValue = ldapAttribute.getStringValue();

					aaaaa.add(attributeName + ": ");
					aaaaa.add(attributeValue);
				}
				connection.disconnect();
				close();

			} catch (LDAPException ex) {
				RetroalimentationUtils.showInformationMessage(MessageUtil
						.unescape(AbosMessages.get().USER_NOT_FOUND));
			}
		} else {
			RetroalimentationUtils.showInformationMessage(MessageUtil
					.unescape(AbosMessages.get().USER_OR_PASSWORD_INCORRECT));
		}
	}

	public UserClass getUserClass() {
		return userClass;
	}

	public void setUserClass(UserClass userClass) {
		this.userClass = userClass;
	}

	public Button getChkrecordar() {
		return chkrecordar;
	}

	public void setChkrecordar(Button chkrecordar) {
		this.chkrecordar = chkrecordar;
	}

	public Combo getDomainList() {
		return domainList;
	}

	public void setDomainList(Combo domainList) {
		this.domainList = domainList;
	}

	public Text getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(Text passwordText) {
		this.passwordText = passwordText;
	}

	public Text getUserText() {
		return userText;
	}

	public void setUserText(Text userText) {
		this.userText = userText;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

}
