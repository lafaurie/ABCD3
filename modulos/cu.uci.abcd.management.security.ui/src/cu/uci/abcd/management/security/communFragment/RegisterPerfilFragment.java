package cu.uci.abcd.management.security.communFragment;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.RegisterProfile;
import cu.uci.abcd.management.security.ui.controller.ProfileViewController;
import cu.uci.abcd.management.security.util.ModulePermission;
import cu.uci.abcd.management.security.util.TreeModulePermissions;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterPerfilFragment extends FragmentPage implements FragmentContributor {

	private ViewController controller;
	private ValidatorUtils validator;
	private List<Composite> listModules = new ArrayList<Composite>();
	private Text perfilName;
	private Library library;
	private Profile profile;
	private ViewProfileFragment viewProfileFragment;
	private Composite show;
	private Label permissionFilterLabel;
	private List<Permission> listPermissions = new ArrayList<Permission>();
	final List<Button> allCheckDispaly = new ArrayList<Button>();
	List<Button> selectedCheck = new ArrayList<Button>();

	private RegisterProfile registerProfile;
	private ContributorService contributorService;
	@SuppressWarnings("unused")
	private int dimension;

	public RegisterPerfilFragment(Profile profile, ViewController controller,
			int dimension, RegisterProfile registerProfile,
			ContributorService contributorService) {
		this.controller = controller;
		this.profile = profile;
		this.registerProfile = registerProfile;
		this.dimension = dimension;
		this.contributorService = contributorService;
		listPermissions = (List<Permission>) ((ProfileViewController) controller)
				.getAllManagementSecurityViewController()
				.getPermissionService().findAllIfModuleIsNotNull();
	}

	public RegisterPerfilFragment(Profile profile, ViewController controller,
			int dimension) {
		this.controller = controller;
		this.profile = profile;
		this.dimension = dimension;
		listPermissions = (List<Permission>) ((ProfileViewController) controller)
				.getAllManagementSecurityViewController()
				.getPermissionService().findAllIfModuleIsNotNull();
		
		
	}

	

	List<Permission> listSelectedPermissions = new ArrayList<Permission>();
	
    

	public List<Permission> getListSelectedPermissions() {
		listSelectedPermissions.clear();
		for (int i = 0; i < selectedCheck.size(); i++) {
			if (selectedCheck.get(i).getSelection() == true) {
				listSelectedPermissions.add((Permission) selectedCheck.get(i)
						.getData());
			}
		}
		return listSelectedPermissions;
	}

	private PagePainter painter;
	Composite register;
	Composite msg;

	public Composite getMsg() {
		return msg;
	}

	public void setMsg(Composite msg) {
		this.msg = msg;
	}

	@Override
	public Control createUIControl(final Composite parent) {

		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		
		validator = new ValidatorUtils(new CustomControlDecoration());
		painter = new FormPagePainter();

		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite scroll = new Composite(parent,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(parent, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		
		register = new Composite(parent, SWT.NONE);
		painter.addComposite(register);
		register.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		permissionFilterLabel = new Label(register, SWT.BOLD | SWT.ALL);
		painter.addHeader(permissionFilterLabel);
		
		Label separator1 = new Label(register, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator1);
		
		Label profileData = new Label(register, SWT.BOLD | SWT.ALL);
		profileData.setText(AbosMessages.get().LABEL_PROFILE_DATA);
		painter.addHeader(profileData);
		

		Label perfilNameLabel = new Label(register, SWT.NONE);
		perfilNameLabel.setText(AbosMessages.get().NAME);
		painter.add(perfilNameLabel);

		perfilName = new Text(register, SWT.NONE);
		painter.add(perfilName);

		validator.applyValidator(perfilName, "perfilNameRequired",
				DecoratorType.REQUIRED_FIELD, false);

		validator.applyValidator(perfilName, "perfilNameAlpaNumericSpaces",
				DecoratorType.ALPHA_NUMERICS_SPACES, false,  20);

		final Button allPermissions = new Button(register, SWT.CHECK);
		allPermissions.setText(AbosMessages.get().LABEL_ALL_PERMISSIONS);
		FormDatas.attach(allPermissions).atTopTo(perfilName, 15).atLeft(15);

		allPermissions.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (allPermissions.getSelection()) {
					for (int i = 0; i < allCheckDispaly.size(); i++) {
						allCheckDispaly.get(i).setSelection(true);
					}
				} else {
					for (int i = 0; i < allCheckDispaly.size(); i++) {
						allCheckDispaly.get(i).setSelection(false);
					}
				}
			}

		});

		Label top = new Label(register, SWT.BOLD | SWT.ALL);
		FormDatas.attach(top).atTopTo(perfilName, 30)
				.atLeftTo(allPermissions, 5).withHeight(0);

		//List<Permission> listPermissionsTemp = new ArrayList<>();
         // for(int i=0; i<listPermissions.size(); i++){
			//if(listPermissions.get(i).getModule()!=null){
			//	listPermissionsTemp.add(listPermissions.get(i));
			//}
		//}

		TreeModulePermissions treeModulePermissions = new TreeModulePermissions(
				listPermissions, controller);
		
		
		
		ModulePermission libraryModulePermission = treeModulePermissions
				.getModulePermissionLibrary();
		ModulePermission securityModulePermission = treeModulePermissions
				.getModulePermissionSecurity();
		ModulePermission circulationModulePermission = treeModulePermissions
				.getModulePermissionCirculation();
		ModulePermission adquititionModulePermission = treeModulePermissions
				.getModulePermissionAdquitition();
		ModulePermission cataloguingModulePermission = treeModulePermissions
				.getModulePermissionCataloguing();
		ModulePermission nomenclatorAdministrationModulePermission = treeModulePermissions
				.getModulePermissionNomenclatorAdministration();
		/*
		ModulePermission databaseAdministrationModulePermission = treeModulePermissions
				.getModulePermissionDatabaseAdministration();
		ModulePermission iSISAdministrationModulePermission = treeModulePermissions
				.getModulePermissionISISAdministration();
				*/
		ModulePermission stadisticModulePermission = treeModulePermissions
				.getModulePermissionStadistic();

		boolean open = false;
		if (profile != null) {
			open = true;
		}
		
		Label adquititionLabel = buildComposite(register, top,
				adquititionModulePermission, open);
		
		Label cataloguingLabel = buildComposite(register, adquititionLabel,
				cataloguingModulePermission, open);
		Label circulationLabel = buildComposite(register, cataloguingLabel,
				circulationModulePermission, open);
		Label stadisticLabel = buildComposite(register,
				circulationLabel, stadisticModulePermission, open);
        Label nomenclatorAdministrationLabel = buildComposite(register,
        		stadisticLabel, nomenclatorAdministrationModulePermission,
				open);
		Label libraryLabel = buildComposite(register, nomenclatorAdministrationLabel,
				libraryModulePermission, open);
		Label securityLabel = buildComposite(register, libraryLabel,
				securityModulePermission, open);
		
		refresh();
	
		if (profile == null) {
			Button cancelBtn = new Button(register, SWT.PUSH);
			cancelBtn.setText(AbosMessages.get().BUTTON_CANCEL);
			FormDatas.attach(cancelBtn).atTopTo(securityLabel, 15).atRight(15)
					.withHeight(23);

			cancelBtn.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION),
							new DialogCallback() {
					private static final long serialVersionUID = 1L;
						@Override
						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
								registerProfile.notifyListeners(
										SWT.Dispose, new Event());									
							}						
						}					
					} );
					
				}
			});

			Button saveBtn = new Button(register, SWT.PUSH);
			saveBtn.setText(AbosMessages.get().BUTTON_ACEPT);
			FormDatas.attach(saveBtn).atTopTo(securityLabel, 15)
					.atRightTo(cancelBtn, 5).withHeight(23);
			saveBtn.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = -7204282042997560772L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (getValidator().decorationFactory
							.isRequiredControlDecorationIsVisible()) {
						RetroalimentationUtils.showErrorMessage(msg, MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_ERROR_FIELD_REQUIRED));
					} else {
						if (getValidator().decorationFactory
								.AllControlDecorationsHide()) {
							String perfilNameConsult = perfilName.getText().replaceAll(" +", " ").trim();
							
							Profile exist = ((ProfileViewController) controller).findProfileByName(library.getLibraryID(), perfilNameConsult);
							Profile exist1 = ((ProfileViewController) controller).findProfileByPermissions(library.getLibraryID(), getListSelectedPermissions());
                            if((exist==null || ( profile!=null && profile.getId()==exist.getId() ))  ){
                            	if (exist1==null) {
							       if (getListSelectedPermissions().size() > 0) {

									profile = new Profile();
					
								java.sql.Date sqlDate = new java.sql.Date(
										new java.util.Date().getTime());

								profile.setCreationDate(sqlDate);
								profile.setProfileName(perfilNameConsult);

								profile.setAsignedPermissions(getListSelectedPermissions());
								profile.setLibrary(library);

					
								Profile profileSaved = ((ProfileViewController) controller)
										.addProfile(profile);
								
								registerProfile.notifyListeners(SWT.Dispose, new Event());
								contributorService.selectContributor("viewProfile", profileSaved, registerProfile, contributorService);
								
								
							} else {
								
								RetroalimentationUtils.showErrorMessage(msg, MessageUtil
										.unescape(AbosMessages
												.get().SHOULD_SELECT_PERMISSION));
							}
                        }else {
                        	RetroalimentationUtils.showErrorMessage(msg, MessageUtil
									.unescape(AbosMessages
											.get().PROFILE_SAME_PERMISSION));
								}
							
						}else{
							RetroalimentationUtils.showErrorMessage(msg, MessageUtil
									.unescape(AbosMessages
											.get().ELEMENT_EXIST));
						}
						} else {
							RetroalimentationUtils.showErrorMessage(msg, MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_ERROR_INCORRECT_DATA));
						}
					}
				}

			});
			saveBtn.getShell().setDefaultButton(saveBtn);
		}
		LoadProfileData();
		l10n();
		return parent;
	}

	private void LoadProfileData() {
		if (profile != null) {
			perfilName.setText(profile.getProfileName());
			List<String> asignedPermissions = new ArrayList<String>();
			for (Permission permission : profile.getAsignedPermissions()) {
				asignedPermissions.add(permission.getPermissionName());
			}
			for (int i = 0; i < selectedCheck.size(); i++) {
				if (asignedPermissions.contains(((Permission) selectedCheck
						.get(i).getData()).getPermissionName())) {
					selectedCheck.get(i).setSelection(true);
				} else {
					selectedCheck.get(i).setSelection(false);
				}
			}
		}
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		if(profile==null){
			permissionFilterLabel.setText(AbosMessages.get().REGISTER_PROFILE);
		}else{
			permissionFilterLabel.setText(AbosMessages.get().UPDATE_PROFILE);
		}
		
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public void ajustRezise(Composite parent, int height) {
		parent.setSize(parent.getSize().x, height);
		FormData tempo = (FormData) parent.getLayoutData();
		tempo.height = height;
		parent.setLayoutData(tempo);

		parent.setLayoutData(tempo);
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	public void refresh(Composite parent) {
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	public Label buildComposite(Composite parent, Control top,
			final ModulePermission modulePermission, boolean open) {
		final Composite moduleComposite = new Composite(parent, SWT.NONE);
		moduleComposite.setLayout(new FormLayout());
		FormDatas.attach(moduleComposite).atTopTo(top, 10).atLeft(65)
				.atRight(15);
		
		listModules.add(moduleComposite);

		final Label expandLibrary = new Label(moduleComposite, SWT.BOLD
				| SWT.ALL);
		FormDatas.attach(expandLibrary).atTopTo(moduleComposite, 3)
				.atLeftTo(moduleComposite);
		expandLibrary.setImage(new Image(Display.getDefault(), AbosImageUtil
				.loadImage(null, Display.getCurrent(),
						"abcdconfig/resources/angle-right.png", false)
				.getImageData().scaledTo(20, 20)));

		Label expandLibraryLabel = new Label(moduleComposite, SWT.BOLD
				| SWT.ALL);
		expandLibraryLabel.setText(modulePermission.getTitle());
		FormDatas.attach(expandLibraryLabel).atTopTo(moduleComposite, 5)
				.atLeftTo(expandLibrary, 5);

		final Button allPermissionLibraryButton = new Button(moduleComposite,
				SWT.CHECK);
		FormDatas.attach(allPermissionLibraryButton)
				.atTopTo(expandLibraryLabel, 5).atLeftTo(expandLibrary, 5);
		allCheckDispaly.add(allPermissionLibraryButton);

		final List<Button> allCheckUI = new ArrayList<Button>();

		allPermissionLibraryButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (allPermissionLibraryButton.getSelection()) {
					for (int i = 0; i < allCheckUI.size(); i++) {
						allCheckUI.get(i).setSelection(true);
					}
				} else {
					for (int i = 0; i < allCheckUI.size(); i++) {
						allCheckUI.get(i).setSelection(false);
					}
				}
			}
		});

		Label allPermissionLibrary = new Label(moduleComposite, SWT.BOLD
				| SWT.ALL);
		allPermissionLibrary.setText(AbosMessages.get().LABEL_ALL);
		FormDatas.attach(allPermissionLibrary).atTopTo(expandLibraryLabel, 8)
				.atLeftTo(allPermissionLibraryButton, 5);

		Label topOne = allPermissionLibrary;
		for (int i = 0; i < modulePermission.getListModuleCategory().size(); i++) {
			final Button registerPermissionLibraryButton = new Button(
					moduleComposite, SWT.CHECK);
			FormDatas.attach(registerPermissionLibraryButton)
					.atTopTo(topOne, 5).atLeftTo(allPermissionLibraryButton, 5);
			allCheckUI.add(registerPermissionLibraryButton);
			allCheckDispaly.add(registerPermissionLibraryButton);
			final List<Button> children = new ArrayList<Button>();

			registerPermissionLibraryButton
					.addSelectionListener(new SelectionAdapter() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public void widgetSelected(SelectionEvent e) {
							if (registerPermissionLibraryButton.getSelection()) {
								for (int j = 0; j < children.size(); j++) {
									children.get(j).setSelection(true);
								}
							} else {
								for (int j = 0; j < children.size(); j++) {
									children.get(j).setSelection(false);
								}
							}
						}

					});

			Label registerPermissionLibrary = new Label(moduleComposite,
					SWT.BOLD | SWT.ALL);
			registerPermissionLibrary.setText(modulePermission
					.getListModuleCategory().get(i).getTitle());
			FormDatas.attach(registerPermissionLibrary).atTopTo(topOne, 8)
					.atLeftTo(registerPermissionLibraryButton, 5);

			Label topTwo = registerPermissionLibrary;
			for (int j = 0; j < modulePermission.getListModuleCategory().get(i)
					.getListValues().size(); j++) {
				final Button valueButton = new Button(moduleComposite,
						SWT.CHECK);
				FormDatas.attach(valueButton).atTopTo(topTwo, 5)
						.atLeftTo(registerPermissionLibraryButton, 5);
				valueButton.setData(modulePermission.getListModuleCategory()
						.get(i).getListPermissions().get(j));
				children.add(valueButton);
				allCheckUI.add(valueButton);
				allCheckDispaly.add(valueButton);
				selectedCheck.add(valueButton);
				Label valueLabel = new Label(moduleComposite, SWT.BOLD
						| SWT.ALL);
				valueLabel.setText(modulePermission.getListModuleCategory().get(i).getListValues().get(j)
						);
				FormDatas.attach(valueLabel).atTopTo(topTwo, 8)
						.atLeftTo(valueButton, 5);

				topTwo = valueLabel;
			}
			topOne = topTwo;
		}
		Label space = new Label(parent, SWT.BOLD | SWT.ALL);
		FormDatas.attach(space).atTopTo(moduleComposite, -15).atLeft(15);

		int c = 0;
		for (int j = 0; j < modulePermission.getListModuleCategory().size(); j++) {
			c++;
			for (int j2 = 0; j2 < modulePermission.getListModuleCategory()
					.get(j).getListPermissions().size(); j2++) {
				c++;
			}
		}
		final int aaaa = 26 * c;
		if (!open) {
			ajustRezise(moduleComposite, 24);
		}
			expandLibrary.addMouseListener(new MouseListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void mouseUp(MouseEvent arg0) {
					expandContract(moduleComposite, aaaa+55, 
							24);
				}
				@Override public void mouseDown(MouseEvent arg0) {}
				@Override public void mouseDoubleClick(MouseEvent arg0) {}
			});
			
			expandLibraryLabel.addMouseListener(new MouseListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void mouseUp(MouseEvent arg0) {
					expandContract(moduleComposite, aaaa+55, 24);
				}
				@Override public void mouseDown(MouseEvent arg0) {}
				@Override public void mouseDoubleClick(MouseEvent arg0) {}
			});
			
		return space;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public Text getPerfilName() {
		return perfilName;
	}
	
	public void expandContract(Composite composite, int heightMax, int heightMin){
		if (composite.getSize().y == heightMin) {
			ajustRezise(composite, heightMax);
		} else {
			ajustRezise(composite, heightMin);
		}
		int alto = 0;
		for (int j = 0; j < listModules.size(); j++) {
			alto = alto + listModules.get(j).getSize().y;
		}
		ajustRezise(register.getParent(), 230 + alto);
		refresh(register.getParent().getShell());
	}
}
