package cu.uci.abcd.management.security.ui.model;

import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.communFragment.RegisterUserFragment;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.controller.UserViewController;
import cu.uci.abcd.management.security.util.Auxiliary;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.Digest;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class UserUpdateArea extends BaseEditableArea {

	private ViewController controller;
	private CRUDTreeTable userTable;
	private RegisterUserFragment updateUser;
	private User user;
	private Composite parentComposite;
	@SuppressWarnings("unused")
	private Map<String, Control> controlsMaps;

	public UserUpdateArea(ViewController controller, CRUDTreeTable userTable) {
		this.controller = controller;
		this.setUserTable(userTable);
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public CRUDTreeTable getUserTable() {
		return userTable;
	}

	public void setUserTable(CRUDTreeTable userTable) {
		this.userTable = userTable;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Button saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.setText("Aceptar");
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2615553092700551346L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateUser.getValidator().decorationFactory.removeControlDecoration("userPasswordTextRequired");
				updateUser.getValidator().decorationFactory.removeControlDecoration("userPasswordConfirmTextRequired");

				if (updateUser.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
					if (updateUser.getPerson() != null) {
						if ((updateUser.getGestionSystem().getSelection() && updateUser.getProfileSelected().size() > 0) || (!updateUser.getGestionSystem().getSelection() && updateUser.getOpac().getSelection())) {
							if ((updateUser.getUser() != null && Auxiliary.getValue(updateUser.getUserNameText().getText()) != null) && updateUser.getValidator().decorationFactory.AllControlDecorationsHide()) {
								String userName = updateUser.getUserNameText().getText().replaceAll(" +", " ").trim();

								if (updateUser.getLdapList().isEmpty() || (!updateUser.getLdapList().isEmpty() && updateUser.getIconLabel().getImage() == updateUser.getCorrectImage()) || !updateUser.getIconLabel().isVisible()) {
									Ldap ldap;
									User userExist;
									if (updateUser.getLdapList().isEmpty()) {
										ldap = null;
										userExist = ((UserViewController) controller).findLocalUser(userName, null, updateUser.getLibrary());
									} else {
										ldap = (updateUser.getLastSelectedLdap() != null) ? updateUser.getLastSelectedLdap() : user.getDomain();
										if (updateUser.isFlagDomain()) {
											userExist = ((UserViewController) controller).findDomainUser(userName, updateUser.getLibrary(), ldap);
										} else {
											ldap = null;
											userExist = ((UserViewController) controller).findLocalUser(userName, null, updateUser.getLibrary());
										}
									}
									if (userExist != null && user.getUserID() != userExist.getUserID()) {
										RetroalimentationUtils.showErrorMessage(MessageUtil.unescape(AbosMessages.get().USER_ALREADY_EXIST));
									} else {

										if ((updateUser.isFlagLocal() && updateUser.getUserPasswordText().getText().equals(updateUser.getUserPasswordConfirmText().getText())) || updateUser.isFlagDomain()) {
											String userPassword = null;
											if (updateUser.isFlagDomain()) {
												userPassword = null;
											} else {
												userPassword = Digest.digest(updateUser.getUserPasswordText().getText(), "SHA1");
											}
											// user = new User();;
											user.setDomain(ldap);
											user.setPerson(updateUser.getPerson());
											user.setLibraryOwner(updateUser.getPerson().getLibrary());
											user.setSystemuser(updateUser.getGestionSystem().getSelection());
											user.setOpacuser(updateUser.getOpac().getSelection());

											user.setUsername(userName);
											user.setUserPassword(userPassword);
											java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
											user.setCreationDate(sqlDate);

											// user.setAsignedProfiless(updateUser.getProfileSelected());

											// if(
											// updateUser.getOpac().getSelection()
											// &&
											// !updateUser.getGestionSystem().getSelection()
											// ){

											// List<Profile> tmp = new
											// ArrayList<>();
											// user.setAsignedProfiless(tmp);
											// }else{
											user.setAsignedProfiless(updateUser.getProfileSelected());
											// }

											user.setEnabled(updateUser.getActiveButton().getSelection());
											User userSaved = ((UserViewController) controller).addUser(user);
											updateUser.getUserNameText().setText("");
											updateUser.getUserPasswordText().setText("");
											updateUser.getUserPasswordConfirmText().setText("");
											updateUser.getProfileSelected().clear();
											for (Button button : updateUser.getCheckedList()) {
												button.setSelection(false);
											}
											manager.save(new BaseGridViewEntity<User>(userSaved));
											manager.refresh();
											RetroalimentationUtils.showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));
											userTable.getPaginator().goToPage(userTable.getPaginator().getCurrentPage());

										} else {
											RetroalimentationUtils.showErrorMessage(MessageUtil.unescape(AbosMessages.get().PASSWORD_NOT_MATCH));
										}

									}
								} else {
									RetroalimentationUtils.showErrorMessage(MessageUtil.unescape(AbosMessages.get().ENTERED_USER_IS_NOT_CORRECT));
								}
							} else {
								RetroalimentationUtils.showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
							}
						} else {
							RetroalimentationUtils.showErrorMessage(MessageUtil.unescape(AbosMessages.get().SHOULD_SELECT_PROFILE));
						}
					} else {
						RetroalimentationUtils.showErrorMessage(MessageUtil.unescape(AbosMessages.get().SHOULD_INDICATE_A_PERSON_AS_USER));
					}
				}

			}
		});
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		user = (User) entity.getRow();
		Person person = user.getPerson();
		int dimension = parent.getParent().getParent().getParent().getBounds().width;
		updateUser = new RegisterUserFragment(userTable, user, person, controller, dimension);
		parentComposite = (Composite) updateUser.createUIControl(parent);
		controlsMaps = updateUser.getControls();
		return parentComposite;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {

	}

	@Override
	public String getID() {
		return "updateUserID";
	}

}
