package cu.uci.abcd.management.security.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.communFragment.RegisterPerfilFragment;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.controller.ProfileViewController;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ProfileUpdateArea extends BaseEditableArea{

	private RegisterPerfilFragment updatePerfil;
	private ViewController controller;
	private Composite parentComposite;
	private Profile profile;
	private CRUDTreeTable profileTable;
	
	public ProfileUpdateArea(ViewController controller, CRUDTreeTable profileTable){
		this.controller = controller;
		this.profileTable = profileTable;
	}
	
	@Override
	public Composite createUI(Composite shell, IGridViewEntity entity,
			IVisualEntityManager manager) {
		profile = (Profile)entity.getRow();
		int dimension = shell.getParent().getParent().getParent().getBounds().width;
		updatePerfil =  new RegisterPerfilFragment(profile, controller, dimension);
		parentComposite =  (Composite) updatePerfil.createUIControl(shell);
		return parentComposite;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity,
			final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		Button saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.setText("Aceptar");
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2615553092700551346L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if (updatePerfil.getValidator().decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorMessage(updatePerfil.getMsg(), MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
					if (updatePerfil.getValidator().decorationFactory
							.AllControlDecorationsHide()) {
						Library library = (Library) SecurityUtils.getPrincipal().getByKey("library");
						String perfilNameConsult = updatePerfil.getPerfilName().getText().replaceAll(" +", " ").trim();
						
						//String perfilNameConsult = perfilName.getText();
						
						Profile exist = ((ProfileViewController) controller).findProfileByName(library.getLibraryID(), perfilNameConsult);
						 if(exist==null || ( profile!=null && profile.getId()==exist.getId() ) ){
							 
							 if (updatePerfil.getListSelectedPermissions().size() > 0) {
						java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
						profile.setCreationDate(sqlDate);
						profile.setProfileName(perfilNameConsult);
						//List<Permission> permissionList = new ArrayList<Permission>();
						//for (Permission permission : updatePerfil.getListSelectedPermissions()) {
							//permissionList.add(entityPermission.getPermission());
						//}
						
						
						//List<Permission> ggg = updatePerfil.getListSelectedPermissions();
						
						profile.setAsignedPermissions(updatePerfil.getListSelectedPermissions());
						
						profile.setLibrary(library);
						
						Profile profileSaved =  ((ProfileViewController) controller).addProfile(profile);
						
						manager.save(new BaseGridViewEntity<Profile>(
								profileSaved));
						manager.refresh();
						
						Composite viewSmg = ((ProfileViewArea)profileTable.getActiveArea()).getMsg();
						
						RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_INF_UPDATE_DATA));
						 }else{
							 RetroalimentationUtils.showErrorMessage(updatePerfil.getMsg(), MessageUtil
										.unescape(AbosMessages
												.get().SHOULD_SELECT_PERMISSION));
						 }
						 }else{
								RetroalimentationUtils.showErrorMessage(updatePerfil.getMsg(), MessageUtil
										.unescape(AbosMessages
												.get().ELEMENT_EXIST));
							}
					}else {
						RetroalimentationUtils.showErrorMessage(updatePerfil.getMsg(), MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_ERROR_INCORRECT_DATA));
					}
				}
				
				/*
				
				String perfilNameConsult = ((Text) controlsMaps.get("perfilName")).getText();
				
				java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
				//Profile profile = new Profile();
				profile.setCreationDate(sqlDate);
				profile.setProfileName(perfilNameConsult);
				List<Permission> permissionList = new ArrayList<>();
				for (EntityPermission entityPermission : updatePerfil.getAsignedPermissions()) {
					permissionList.add(entityPermission.getPermission());
				}
				profile.setAsignedPermissions(permissionList);
				LoginService log = (LoginService) RWT.getUISession().getHttpSession()
						.getAttribute("loginService");
				Library library = (Library) log.getSecurityDataStore().getSecurityDataObject(
						"library");
				profile.setLibrary(library);
				
				Profile profileSaved =  ((ProfileViewController) controller).addProfile(profile);
				
				entityProfile.setProfile(profileSaved);
				manager.save(entityProfile);
					ProfileViewArea profileViewArea = new ProfileViewArea(controller);
					profileTable.createEditableArea(profileViewArea, entityProfile, manager);
					*/
			}
		});
		
		return parent;
	
	
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getID() {
		return "updateProfileID";
	}

}
