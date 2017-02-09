package cu.uci.abcd.management.security.communFragment;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.controller.LdapViewController;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

@SuppressWarnings("unused")
public class RegisterLdapFragment implements FragmentContributor {

	private Ldap ldap;
	private int dimention;
	private PagePainter painter;
	private ValidatorUtils validator;
	private Composite shell;
	private Label registerLdapLabel;
	private Label ldapHostLabel;
	private Text ldapHostText;
	private Label ldapPortLabel;
	private Text ldapPortText;
	private Label ldapDomainLabel;
	private Text ldapDomainText;
	boolean checked = true;
	Button activeButton;
	public Button getActiveButton() {
		return activeButton;
	}

	public void setActiveButton(Button activeButton) {
		this.activeButton = activeButton;
	}

	Button inactiveButton;
	private Map<String, Control> controls = new HashMap<String, Control>();

	public Composite getShell() {
		return shell;
	}

	public void setShell(Composite shell) {
		this.shell = shell;
	}

	public Map<String, Control> getControls() {
		return controls;
	}

	public void setControls(Map<String, Control> controls) {
		this.controls = controls;
	}

	public RegisterLdapFragment(Ldap ldap, int dimention) {
		this.ldap = ldap;
		this.dimention = dimention;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public Control createUIControl(Composite parent) {
		painter = new FormPagePainter();
		validator = new ValidatorUtils(new CustomControlDecoration());

		painter.setDimension(dimention);

		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		shell = new Composite(parent, SWT.NORMAL);
		painter.addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		registerLdapLabel = new Label(shell, SWT.NONE);
		painter.addHeader(registerLdapLabel);
		
		Label separator1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator1);

		activeButton = new Button(shell, SWT.RADIO);
		activeButton.setSelection(true);
		activeButton.setText(AbosMessages.get().LABEL_STATE_ACTIVE);
		painter.add(activeButton, Percent.W25);
		
		inactiveButton = new Button(shell, SWT.RADIO);
		inactiveButton.setText(AbosMessages.get().LABEL_STATE_INACTIVE);
		painter.add(inactiveButton);
		
		painter.reset();
		
		ldapHostLabel = new Label(shell, SWT.NONE);
		painter.add(ldapHostLabel);

		ldapHostText = new Text(shell, SWT.NONE);
		painter.add(ldapHostText);
		
		validator.applyValidator(ldapHostText, "ldapUrlText1",
				DecoratorType.IP, true, 20);
		
		validator.applyValidator(ldapHostText, "ldapUrlText",
				DecoratorType.REQUIRED_FIELD, true);
		
		controls.put("ldapHostText", ldapHostText);

		painter.reset();

		ldapPortLabel = new Label(shell, SWT.NONE);
		painter.add(ldapPortLabel);

		ldapPortText = new Text(shell, SWT.NONE);
		painter.add(ldapPortText);
		
		validator.applyValidator(ldapPortText, "ldapPortText1",
				DecoratorType.PORT, true, 4);
		
		validator.applyValidator(ldapPortText, "ldapPortText",
				DecoratorType.REQUIRED_FIELD, true);
		
		controls.put("ldapPortText", ldapPortText);

		ldapPortText.addModifyListener(new ModifyListener() {
			private static final long serialVersionUID = 3559864360605416678L;

			@Override
			public void modifyText(ModifyEvent arg0) {
				checked = false;
			}
		});

		painter.reset();

		ldapDomainLabel = new Label(shell, SWT.NONE);
		painter.add(ldapDomainLabel);

		ldapDomainText = new Text(shell, SWT.NONE);
		painter.add(ldapDomainText);
		
		validator.applyValidator(ldapDomainText, "ldapDomainText1",
				DecoratorType.DOMAIN, true, 20);
		
		validator.applyValidator(ldapDomainText, "ldapDomainText",
				DecoratorType.REQUIRED_FIELD, true);
		
		
		controls.put("ldapDomainText", ldapDomainText);

		ldapDomainText.addModifyListener(new ModifyListener() {
			private static final long serialVersionUID = 3559864360605416678L;

			@Override
			public void modifyText(ModifyEvent arg0) {
				checked = false;
			}
		});
		painter.reset();
		LoadLdapData();
		l10n();
		return shell;
	}

	private void LoadLdapData() {
		if (ldap != null) {
			ldapHostText.setText(ldap.getHost());
			ldapPortText.setText(ldap.getPort().toString());
			ldapDomainText.setText(ldap.getDomain());
			if( ldap.isEnabled() ){
				activeButton.setSelection(true);
				inactiveButton.setSelection(false);
			}else{
				activeButton.setSelection(false);
				inactiveButton.setSelection(true);
			}
			
		}
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		if (ldap == null) {
			registerLdapLabel.setText(AbosMessages.get().LABEL_REGISTER_LDAP);
		} else {
			registerLdapLabel.setText(AbosMessages.get().LABEL_UPDATE_LDAP);
		}
		ldapHostLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_IP_DIRECTION));
		ldapPortLabel.setText(AbosMessages.get().LABEL_PORT);
		ldapDomainLabel.setText(AbosMessages.get().DOMAIN);
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public void saveLdap(ViewController controller, Ldap ldap,
			IVisualEntityManager manager, CRUDTreeTable table) {
		Library libraryMy = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		
		String domain = ldapDomainText.getText().replaceAll(" +", " ").trim();
		
		Ldap existLdapWithDomain = ((LdapViewController) controller).getLdapByDomain(libraryMy.getLibraryID(), domain);
				//.getAllManagementLibraryViewController()
				//.getRoomService().findRoomByName(library.getLibraryID(), roomName);

		
		if(existLdapWithDomain==null || ( ldap!=null && ldap.getLdapID()==existLdapWithDomain.getLdapID() ) ){
			String host = ldapHostText.getText();
			int port = Integer.parseInt(ldapPortText.getText());
			Ldap exist = ((LdapViewController) controller).getLdapByHostAndPort(libraryMy.getLibraryID(), host, port);
			
			if(exist==null || ( ldap!=null && ldap.getLdapID()==exist.getLdapID() ) ){
		boolean add = ldap == null;

		if (ldap == null) {
			ldap = new Ldap();
		}
		ldap.setHost(ldapHostText.getText());
		ldap.setDomain(ldapDomainText.getText());
		ldap.setPort(Integer.parseInt(ldapPortText.getText()));
		ldap.setLibrary(libraryMy);
		ldap.setEnabled(activeButton.getSelection());
		((LdapViewController) controller).saveLdap(ldap);

		table.getPaginator().goToFirstPage();
		ldap = null;
		table.destroyEditableArea();

		if (add) {
			RetroalimentationUtils
					.showInformationMessage(MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
		} else {
			RetroalimentationUtils
					.showInformationMessage(MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));
		}
		}else{
			RetroalimentationUtils.showErrorShellMessage(
					//register, 
					MessageUtil
					.unescape(AbosMessages
							.get().ELEMENT_EXIST));
		}
	}else{
		RetroalimentationUtils
		.showErrorShellMessage(AbosMessages.get().MESSAGE_LDAP + MessageUtil
				.unescape(": ") + domain + MessageUtil
				.unescape("."));
	}
	}

}
