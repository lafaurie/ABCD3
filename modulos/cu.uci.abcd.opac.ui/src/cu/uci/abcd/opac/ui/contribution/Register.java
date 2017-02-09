package cu.uci.abcd.opac.ui.contribution;

import java.util.Date;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abcd.opac.ui.controller.RegisterUserViewController;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.Digest;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

public class Register extends ContributorPage {

	private ValidatorUtils validator;
	private LoginService loginService;
	private Label userInfo;
	private Text userNameTxt;
	private Label userNameLb;
	private Label userPassLb;
	private Text userPasswordTxt;
	private Label userPassConfirmLb;
	private Text userPassConfirmTxt;
	private Label userLibrayLb;
	private Button save;
	private Button cancel;
	private Combo libraryList;
	private Group agregar;
	
	

	@Override
	public Control createUIControl(final Composite parent) {
	
		addComposite(parent);
		
		agregar = new Group(parent, SWT.NONE);
		agregar.setBackground(parent.getBackground());
		agregar.setLayout(new FormLayout());
		FormDatas.attach(agregar).atTop(0).atLeft(10).atRight(10);
		
		
		loginService = SecurityUtils.getService();
		validator = new ValidatorUtils(new CustomControlDecoration());

		userNameLb = new Label(agregar, SWT.NORMAL);
		add(userNameLb);
		          
		userNameTxt = new Text(agregar, SWT.NORMAL);
		add(userNameTxt);
		validator.applyValidator(userNameTxt, "userNameTxt", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(userNameTxt, "userNameTxt1", DecoratorType.USER_NAME, true, 20);
		br();
    
		userPassLb = new Label(agregar, SWT.NORMAL);
		add(userPassLb);

		userPasswordTxt = new Text(agregar, SWT.PASSWORD);
		add(userPasswordTxt);
		validator.applyValidator(userPasswordTxt, "userPasswordTxt", DecoratorType.REQUIRED_FIELD, true);
		br();
		
		userPassConfirmLb = new Label(agregar, SWT.NORMAL);
		add(userPassConfirmLb);

		userPassConfirmTxt = new Text(agregar, SWT.PASSWORD);
		add(userPassConfirmTxt);
		validator.applyValidator(userPassConfirmTxt, "userPassConfirmTxt", DecoratorType.REQUIRED_FIELD, true);
		br();

		userLibrayLb = new Label(agregar, SWT.NORMAL);
		add(userLibrayLb);
		
		libraryList = new Combo(agregar, SWT.READ_ONLY);
		add(libraryList);
	
		br();
		
		cancel = new Button(agregar, SWT.PUSH);
		add(cancel);
		
		save = new Button(agregar, SWT.PUSH);
		add(save);
		
		br();
		
		userInfo = new Label(agregar, SWT.WRAP);
		addHeader(userInfo);
		br();
		
		save.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
                    showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}
       
				if (validator.decorationFactory.AllControlDecorationsHide()) {

					if (userPasswordTxt.getText().equals(userPassConfirmTxt.getText())) {
     
						User user = new User();
                        
						user.setUsername(userNameTxt.getText());					
						user.setCreationDate(new java.sql.Date(new Date().getTime()));
						user.setEnabled(true);
						user.setOpacuser(true);    
						user.setSystemuser(false);    
						user.setUserPassword(Digest.digest(userPasswordTxt.getText(), "SHA1"));
						    
						int selectedIndex = libraryList.getSelectionIndex();
						Library library = null;

						if (selectedIndex >= 0) {
							library = ((List<Library>) ((Combo) libraryList).getData()).get(selectedIndex - 1);
							user.setLibraryOwner(library);
         
							try {    
								((AllManagementOpacViewController) controller).addUser(user);
								MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT), null);
								userNameTxt.setText("");
								userPasswordTxt.setText("");
								userPassConfirmTxt.setText("");
								libraryList.select(0);
								Register.this.notifyListeners(SWT.Dispose, new Event());

							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					} else
						showErrorMessage(cu.uci.abcd.opac.l10n.AbosMessages.get().MSG_ERROR_PASS_D_MATCH);
					} 
						else
						showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
					}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		cancel.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
					private static final long serialVersionUID = 1L;

					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {
							Register.this.notifyListeners(SWT.Dispose, new Event());
						}
					}
				});
			

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
    
		l10n();
		loadLibraries();
		return parent;
	}

	private void loadLibraries() {
		UiUtils.initialize(libraryList, loginService.loadParams(), "- Biblioteca -");
		if (RWT.getSettingStore().getAttribute("library") != null) {
			libraryList.select(Integer.parseInt(RWT.getSettingStore().getAttribute("library")));
		}
	}

	public Library getLibraryByUser(Long userID) {
		return ((RegisterUserViewController) controller).getLibraryByUser(userID);
	}

	@Override
	public String getID() {
		return "RegisterID";
	}

	@Override
	public void l10n() {
		userInfo.setText(MessageUtil.unescape(AbosMessages.get().NOTE_USER));
		userNameLb.setText(MessageUtil.unescape(AbosMessages.get().USER_RECOMMENDATION));
		userPassLb.setText(MessageUtil.unescape(AbosMessages.get().PASSWORD));
		userPassConfirmLb.setText(MessageUtil.unescape(AbosMessages.get().CONFIRM_PASSWORD));
		userLibrayLb.setText(MessageUtil.unescape(AbosMessages.get().LIBRARY));
		save.setText(MessageUtil.unescape(AbosMessages.get().ACCEPT));
		agregar.setText(MessageUtil.unescape(AbosMessages.get().NEW_USER));
		cancel.setText(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().BUTTON_CANCEL));
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_REGISTER_USER);
	}

}