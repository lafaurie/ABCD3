package cu.uci.abcd.management.security.communFragment;


import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abos.core.ui.Popup;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ResetPassword extends Popup {

	private static final long serialVersionUID = 4196021109430953068L;
	
	private Text passwordText;
	private Text passwordConfirmText;
	private ValidatorUtils validator;
    

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public Text getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(Text passwordText) {
		this.passwordText = passwordText;
	}

	public Text getPasswordConfirmText() {
		return passwordConfirmText;
	}

	public void setPasswordConfirmText(Text passwordConfirmText) {
		this.passwordConfirmText = passwordConfirmText;
	}

	private final SelectionListener listener;
	
	public ResetPassword(Shell parent, int shellStyle, String title,
			Control contentProxy, SelectionListener listener) {
		super(parent, shellStyle, title, contentProxy, listener);
		this.listener = listener;
	}

	public Control createUI(Composite parent) {
		validator = new ValidatorUtils(new CustomControlDecoration());
		parent.setLayout(new FormLayout());
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(parent).atLeft(0).atRight(0).atBottom(0).atTop(0);
		
		passwordText = new Text(parent, SWT.PASSWORD);
	    FormDatas.attach(passwordText).atRight(15).atTop(15).withWidth(130).withHeight(11);
	    validator.applyValidator(passwordText, "passwordText",
				DecoratorType.REQUIRED_FIELD, true);
	     
	    passwordConfirmText = new Text(parent, SWT.PASSWORD);
	    FormDatas.attach(passwordConfirmText).atRight(15).atTopTo(passwordText, 15).withWidth(130).withHeight(11);
	    validator.applyValidator(passwordConfirmText, "passwordConfirmText",
				DecoratorType.REQUIRED_FIELD, true);
	    
	    Label userLabel = new Label(parent, SWT.NONE);
	    userLabel.setText(MessageUtil.unescape(AbosMessages.get().PASSWORD));
	    FormDatas.attach(userLabel).atRightTo(passwordText, 5).atTop(20);
	     
	    Label passwordLabel = new Label(parent, SWT.NONE);
	    passwordLabel.setText(MessageUtil.unescape(AbosMessages.get().CONFIRM_PASSWORD));
	    FormDatas.attach(passwordLabel).atRightTo(passwordConfirmText, 5).atTopTo(userLabel, 21);
	    
	    Button cancelAcept = new Button(parent, SWT.NONE);
	    cancelAcept.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
	    FormDatas.attach(cancelAcept).atTopTo(passwordConfirmText, 10).withHeight(23).atRight(15);
	     
	    Button btnAcept = new Button(parent, SWT.NONE);
	    btnAcept.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
	    FormDatas.attach(btnAcept).atTopTo(passwordConfirmText, 10).withHeight(23).atRightTo(cancelAcept, 15);
	    btnAcept.addSelectionListener((SelectionListener) listener);
	    
	    cancelAcept.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6663485751521224220L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		return parent;
	}
}
