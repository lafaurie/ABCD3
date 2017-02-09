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

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.Picture;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.RegisterPerson;
import cu.uci.abcd.management.security.ui.RegisterProfile;
import cu.uci.abcd.management.security.ui.ViewPerson;
import cu.uci.abcd.management.security.ui.model.Item;
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

@SuppressWarnings("unused")
public class ViewPersonFragment implements FragmentContributor{

	private Person person;
	private boolean buttonClose;
	private RegisterPerson registerPerson;
	private ContributorService contributorService;
	private int dimension;
	PagePainter painter;
	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
	private Button newButton;
	private Button closeButton;
	private Composite msg;
	private ViewPerson viewPerson;
	List<String> values;

	public Composite getMsg() {
		return msg;
	}

	public ViewPersonFragment(Person person, boolean buttonClose, ViewPerson viewPerson, ContributorService contributorService){
		this.person = person;
		this.buttonClose = buttonClose;
		this.viewPerson = viewPerson;
		this.contributorService = contributorService;
	}
	
	public ViewPersonFragment(Person person, boolean buttonClose, RegisterPerson registerPerson, ContributorService contributorService){
		this.person = person;
		this.buttonClose = buttonClose;
		this.registerPerson = registerPerson;
		this.contributorService = contributorService;
	}
	
	public ViewPersonFragment(Person person){
		this.person = person;
		
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
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PERSON));
		painter.addHeader(header);
		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA);
		painter.add(dataGroup);
		
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().FULL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().BIRTHDAY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().AGE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().SEX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().IDENTIFICATION));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ADDRESS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().EMAIL));
		
		values = new LinkedList<>();
		values.add(person.getFullName());
		values.add(new SimpleDateFormat("dd-MM-yyyy").format(person.getBirthDate()));
		values.add(person.getAge());
		values.add(person.getSex().getNomenclatorName());
		values.add(person.getDNI());
		values.add(person.getAddress() != null ? person.getAddress() : "-");
		values.add(person.getEmailAddress() != null ? person.getEmailAddress() : "-");
		
		Image image = person.getPhoto().getImage();
		
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
					viewPerson.notifyListeners(SWT.Dispose, new Event());
				}
			});

			newButton = new Button(topGroup, SWT.NONE);
			newButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
			painter.add(newButton);
			newButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 4276152810528140450L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					viewPerson.notifyListeners(SWT.Dispose, new Event());
					contributorService.selectContributor("addPersonID");
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
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PERSON));
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().FULL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().BIRTHDAY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().AGE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().SEX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().IDENTIFICATION));
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
