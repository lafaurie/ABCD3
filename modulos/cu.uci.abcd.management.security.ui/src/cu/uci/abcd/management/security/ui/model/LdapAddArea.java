package cu.uci.abcd.management.security.ui.model;

import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.communFragment.RegisterLdapFragment;
import cu.uci.abcd.management.security.ui.ConfigureLdap;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class LdapAddArea extends BaseEditableArea {

	private ViewController controller;
	private RegisterLdapFragment saveLdapFragment;
	private Composite parentComposite;
	private Ldap ldap;
	private Button saveButton;
	private Button testButton;
	private int dimension;
	@SuppressWarnings("unused")
	private Map<String, Control> controlsMaps;
	ConfigureLdap configureLdap;
	CRUDTreeTable table;

	public LdapAddArea(ViewController controller, ConfigureLdap configureLdap, CRUDTreeTable table) {
		super();
		this.setController(controller);
		this.configureLdap = configureLdap;
		ldap = null;
		this.table = table;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		addComposite(parent);
		saveLdapFragment = new RegisterLdapFragment(ldap, dimension);
		parentComposite = (Composite) saveLdapFragment.createUIControl(parent);
		controlsMaps = saveLdapFragment.getControls();
		
		
		
		return parentComposite;
		
		
	}

	@Override
	public Composite createButtons(final Composite parent,
			IGridViewEntity entity, final IVisualEntityManager manager) {
		/*
		((Text)controlsMaps.get("ldapUserText")).addModifyListener(new ModifyListener() {
			private static final long serialVersionUID = 3559864360605416678L;
			@Override
			public void modifyText(ModifyEvent arg0) {
				saveButton.setEnabled(false);
			}
			
		});
		*/
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveButton = new Button(parent, SWT.PUSH);
		//saveButton.setEnabled(false);
		saveButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (saveLdapFragment.getValidator().decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorMessage(saveLdapFragment.getShell(), MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}else{
					if (saveLdapFragment.getValidator().decorationFactory
							.AllControlDecorationsHide()) {
							saveLdapFragment.saveLdap(controller, null, manager, table);
					}else{
						RetroalimentationUtils.showErrorMessage(saveLdapFragment.getShell(), MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_ERROR_INCORRECT_DATA));
					}
				}
			
			
			}
		});
		
		return parent;
	}

	@Override
	public void l10n() {
		saveButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT));
		//testButton.setText(MessageUtil.unescape("Comprobar"));
		saveButton.getParent().layout(true, true);
		saveButton.getParent().redraw();
		saveButton.getParent().update();
		saveLdapFragment.l10n();
	}

	@Override
	public String getID() {
		return "addLdapID";
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}
	/*
	public void saveLdap(){
		Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		Ldap ldap = new Ldap();
		ldap.setHost(((Text)controlsMaps.get("ldapHostText")).getText());
		ldap.setDomain(((Text)controlsMaps.get("ldapDomainText")).getText());
		ldap.setPort(Integer.parseInt(((Text)controlsMaps.get("ldapPortText")).getText()));
		library.addLdap(ldap);
		((LdapViewController) controller).saveLibrary(library);
	}
*/
}
