package cu.uci.abcd.management.security.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.communFragment.RegisterLdapFragment;
import cu.uci.abcd.management.security.ui.ConfigureLdap;
import cu.uci.abcd.management.security.ui.controller.LdapViewController;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class LdapUpdateArea extends BaseEditableArea {

	private ViewController controller;
	private int dimention;
	private Ldap ldap;

	private RegisterLdapFragment saveLdapFragment;
	private Composite parentComposite;
	private Button saveButton;
	private Button testButton;
	ConfigureLdap configureLdap;
	CRUDTreeTable table;

	public LdapUpdateArea(ViewController controller, ConfigureLdap configureLdap, CRUDTreeTable table) {
		super();
		this.controller = controller;
		this.configureLdap = configureLdap;
		this.table = table;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		dimention = parent.getParent().getParent().getBounds().width;
		addComposite(parent);
		Ldap ldapToView = (Ldap) entity.getRow();
		ldap = ((LdapViewController) controller).getLdapById(ldapToView
				.getLdapID());
		saveLdapFragment = new RegisterLdapFragment(ldap, dimention);
		parentComposite = (Composite) saveLdapFragment.createUIControl(parent);
		return parentComposite;
	}

	@Override
	public Composite createButtons(Composite parent,
			IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveButton = new Button(parent, SWT.PUSH);
		saveButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (saveLdapFragment.getValidator().decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorMessage(MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}else{
					if (saveLdapFragment.getValidator().decorationFactory
							.AllControlDecorationsHide()) {
						//if (saveLdapFragment.isChecked()) {
							saveLdapFragment.saveLdap(controller, ldap, manager, table);
							//configureLdap.searchLdaps();
						//}
					}else{
						RetroalimentationUtils.showErrorMessage(MessageUtil
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
		return "updateLdapID";
	}

}
