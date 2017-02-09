package cu.uci.abcd.opac.ui.contribution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.auxiliary.Auxiliary;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

public class SendEmail extends ContributorPage {
   
	private Auxiliary aux;
	private Composite result;
   
	private List<RecordIsis> mySelectedRecord = new ArrayList<RecordIsis>();
;
	private Record record;

	private Label emailAddressLb;
	private Label emailCommentLb;

	private Text emailAddressTxt;
	private Text emailCommentTxt;

	private Button sendEmailBtn;
	private Button cancelBtn;
	private ValidatorUtils validator;

	@Override
	public Control createUIControl(Composite parent) {

		validator = new ValidatorUtils(new CustomControlDecoration());

		aux = new Auxiliary(controller);	

		result = parent;
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		result.setLayout(new FormLayout());

		Label point = new Label(result, SWT.NORMAL);
		FormDatas.attach(point).atLeft(200);

		emailAddressLb = new Label(result, SWT.NORMAL);
		FormDatas.attach(emailAddressLb).atTopTo(result, 35).atRightTo(point);
          
		emailAddressTxt = new Text(result, SWT.NORMAL);			
		FormDatas.attach(emailAddressTxt).atTopTo(result, 30).atLeftTo(point, 5).withWidth(200);

		emailCommentLb = new Label(result, SWT.NORMAL);
		FormDatas.attach(emailCommentLb).atTopTo(emailAddressTxt, 20).atRightTo(point);

		emailCommentTxt = new Text(result, SWT.NONE | SWT.WRAP | SWT.V_SCROLL);
		FormDatas.attach(emailCommentTxt).atTopTo(emailAddressTxt, 15).atLeftTo(point, 5).withWidth(195).withHeight(50);

		sendEmailBtn = new Button(result, SWT.PUSH);
		sendEmailBtn.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(sendEmailBtn).atTopTo(emailCommentTxt, 10).atLeftTo(point, 5);

		cancelBtn = new Button(result, SWT.PUSH);
		cancelBtn.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(cancelBtn).atTopTo(emailCommentTxt, 10).atLeftTo(sendEmailBtn, 5);
   
		sendEmailBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
       
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else if (validator.decorationFactory.AllControlDecorationsHide()) {
					aux.sendEmail(mySelectedRecord, emailAddressTxt.getText(), emailCommentTxt.getText(), SendEmail.this);
				} else
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		final DialogCallback cancelCallBack = new DialogCallback() {
			private static final long serialVersionUID = 1L;

			@Override
			public void dialogClosed(int returnCode) {
				if (returnCode == 0) {
					SendEmail.this.notifyListeners(SWT.Dispose, new Event());
				}
			}
		};

		cancelBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), cancelCallBack);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		
		validator.applyValidator(emailAddressTxt, "emailAddressTxt1", DecoratorType.EMAIL, true);
		validator.applyValidator(emailAddressTxt, "emailAddressTxt", DecoratorType.REQUIRED_FIELD, true);

		

		l10n();
		return result;
	}

	@Override
	public String getID() {
		return "sendEmailID";
	}

	@Override
	public void l10n() {
		emailAddressLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EMAIL_ADDRESS));
		emailAddressTxt.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_MSG_EMAIL_ADDRESS));
		emailCommentLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EMAIL_COMMENT));
		sendEmailBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEND_EMAIL));
		cancelBtn.setText(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().BUTTON_CANCEL));
		refresh();
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_SEND_EMAIL);
	}

	public List<RecordIsis> getMySelectedRecord() {
		return mySelectedRecord;
	}

	public void setMySelectedRecord(List<RecordIsis> mySelectedRecord) {
		this.mySelectedRecord = mySelectedRecord;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}
}
