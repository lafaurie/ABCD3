package cu.uci.abcd.management.security.communFragment;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.domain.management.security.AccessRecord;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.RegisterAccessControl;
import cu.uci.abcd.management.security.ui.ViewAccessControl;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ViewAccessControlFragment implements FragmentContributor{

	private AccessRecord accessRecord;
	boolean buttonClose;
	RegisterAccessControl registerAccessControl;
	ContributorService contributorService;
	@SuppressWarnings("unused")
	private int dimension;
	PagePainter painter;
	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
	private Button newButton;
	private Button closeButton;
	Composite msg;
	List<String> values;
	
	private ViewAccessControl viewAccessControl;
	
	public Composite getMsg() {
		return msg;
	}

	public void setMsg(Composite msg) {
		this.msg = msg;
	}

	public ViewAccessControlFragment(AccessRecord accessRecord, boolean buttonClose, ViewAccessControl viewAccessControl, ContributorService contributorService) {
		this.accessRecord = accessRecord;
		this.buttonClose = buttonClose;
		this.viewAccessControl = viewAccessControl;
		this.contributorService = contributorService;
	}
	
	public ViewAccessControlFragment(AccessRecord accessRecord, boolean buttonClose, RegisterAccessControl registerAccessControl, ContributorService contributorService){
		this.accessRecord = accessRecord;
		this.buttonClose = buttonClose;
		this.registerAccessControl = registerAccessControl;
		this.contributorService = contributorService;
	}
	
	public ViewAccessControlFragment(AccessRecord accessRecord){
		this.accessRecord = accessRecord;
		
	}
	
	@Override
	public Control createUIControl(Composite parent) {
		dimension = parent.getParent().getParent().getBounds().width;
		painter = new FormPagePainter();
		painter.addComposite(parent);
		Composite topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(topGroup);
		
		msg  = new Composite(topGroup, SWT.NONE);
		msg.setLayout(new FormLayout());
		msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(msg).atTopTo(topGroup).withWidth(320).withHeight(50).atRight(0);
		
		header = new Label(topGroup, SWT.NORMAL);
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_ACCESS_RECORD));
		painter.addHeader(header);
		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_ACCESS_RECORD_DATA);
		painter.add(dataGroup);
		
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().ROOM));
		leftList.add(MessageUtil.unescape(AbosMessages.get().REASON));
		leftList.add(MessageUtil.unescape(AbosMessages.get().FULL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().BIRTHDAY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().AGE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().SEX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ID_CARNE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ADDRESS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().EMAIL));
		
		values = new LinkedList<>();
		values.add(accessRecord.getRoom().getRoomName());
		values.add(accessRecord.getMotivation());
		values.add(accessRecord.getPerson().getFullName());
		values.add(new SimpleDateFormat("dd-MM-yyyy").format(accessRecord.getPerson().getBirthDate()));
		values.add(accessRecord.getPerson().getAge());
		values.add(accessRecord.getPerson().getSex().getNomenclatorName());
		values.add(accessRecord.getPerson().getDNI());
		values.add(accessRecord.getPerson().getAddress() != null ? accessRecord.getPerson().getAddress() : "-");
		values.add(accessRecord.getPerson().getEmailAddress() != null ? accessRecord.getPerson().getEmailAddress() : "");
		

		Image image = accessRecord.getPerson().getPhoto().getImage();
		
		grupControls = CompoundGroup.printGroup(image, dataGroup, titleGroup,
				leftList, values);
		
		if (buttonClose) {
			painter.reset();
			closeButton = new Button(topGroup, SWT.NONE);
			closeButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CLOSE));
			painter.add(closeButton);
			closeButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = -7306583336037504034L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					viewAccessControl.notifyListeners(SWT.Dispose, new Event());
				}
			});

			newButton = new Button(topGroup, SWT.NONE);
			newButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
			painter.add(newButton);
			newButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 4276152810528140450L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					viewAccessControl.notifyListeners(SWT.Dispose, new Event());
					contributorService.selectContributor("addAccessControlID");
				}
			});
		}
		 l10n();
		return parent;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void l10n() {
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_ACCESS_RECORD));
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_ACCESS_RECORD_DATA);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().ROOM));
		leftList.add(MessageUtil.unescape(AbosMessages.get().REASON));
		leftList.add(MessageUtil.unescape(AbosMessages.get().FULL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().BIRTHDAY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().AGE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().SEX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ID_CARNE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ADDRESS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().EMAIL));
		dataGroup.setText(titleGroup);
		CompoundGroup.l10n(grupControls, leftList);

		if (buttonClose) {
			closeButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CLOSE));
			newButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
		}
		
	}

	@Override
	public Control getControl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
