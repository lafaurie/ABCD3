package cu.uci.abcd.management.security.communFragment;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.Picture;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.RegisterPerson;
import cu.uci.abcd.management.security.ui.RegisterProfile;
import cu.uci.abcd.management.security.ui.RegisterUser;
import cu.uci.abcd.management.security.ui.ViewUser;
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
public class ViewUserFragment implements FragmentContributor{

	Composite show;
	Composite register;
	public Composite getShow() {
		return show;
	}

	public void setShow(Composite show) {
		this.show = show;
	}

	public Composite getRegister() {
		return register;
	}

	public void setRegister(Composite register) {
		this.register = register;
	}

	private User user;
	private boolean buttonClose;
	private RegisterUser registerUser;
	private ContributorService contributorService;
	//private int dimension;
	PagePainter painter;
	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
	private Button newButton;
	private Button closeButton;
	private ViewUser viewUser;

	
	public ViewUserFragment(User user, boolean buttonClose, ViewUser viewUser, ContributorService contributorService){
		this.user = user;
		this.buttonClose = buttonClose;
		this.viewUser = viewUser;
		this.contributorService = contributorService;
	}
	
	public ViewUserFragment(User user, boolean buttonClose, RegisterUser registerUser, ContributorService contributorService){
		this.user = user;
		this.buttonClose = buttonClose;
		this.registerUser = registerUser;
		this.contributorService = contributorService;
	}
	
	public ViewUserFragment(User user){
		this.user = user;
		
	}
Composite msg;
	public Composite getMsg() {
	return msg;
}

public void setMsg(Composite msg) {
	this.msg = msg;
}

	@Override
	public Control createUIControl(Composite parent) {

		//dimension = parent.getParent().getParent().getBounds().width;
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
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_USER));
		painter.addHeader(header);
		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_USER_DATA);
		painter.add(dataGroup);
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().USER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().CREATION_DATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().FULL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().SEX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ID_CARNE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ADDRESS));
		
		List<String> values = new LinkedList<>();
		values.add(user.getUsernameToString());
		values.add(user.getEnabled()?MessageUtil.unescape(AbosMessages.get().LABEL_STATE_ACTIVE):MessageUtil.unescape(AbosMessages.get().LABEL_STATE_INACTIVE));
		//values.add(   new SimpleDateFormat("dd-MM-yyyy").format(user.getCreationDate())   );
		values.add((user.getCreationDate()!=null?new SimpleDateFormat("dd-MM-yyyy").format(user.getCreationDate()):"< No hay datos >"));
		values.add((user.getPerson()!=null?user.getPerson().getFullName():"< No hay datos >"));
		values.add((user.getPerson()!=null?user.getPerson().getSex().getNomenclatorName():"< No hay datos >"));
		values.add((user.getPerson()!=null?user.getPerson().getDNI():"< No hay datos >"));
		values.add((user.getPerson()!=null?(user.getPerson().getAddress()!=null?user.getPerson().getAddress():""):"< No hay datos >"));
		
		if(user.getPerson()!=null){
			Image image = user.getPerson().getPhoto().getImage();
			grupControls = CompoundGroup.printGroup(image, dataGroup, titleGroup,
					leftList, values);
		}else{
			grupControls = CompoundGroup.printGroup(dataGroup, titleGroup,
					leftList, values);
		}
		
		if (buttonClose) {
			
			painter.reset();
			closeButton = new Button(topGroup, SWT.NONE);
			closeButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CLOSE));
			painter.add(closeButton);
			closeButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = -7306583336037504034L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					viewUser.notifyListeners(SWT.Dispose, new Event());
				}
			});

			newButton = new Button(topGroup, SWT.NONE);
			newButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
			painter.add(newButton);
			
			newButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 4276152810528140450L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					viewUser.notifyListeners(SWT.Dispose, new Event());
					contributorService.selectContributor("addUserID");
					
					//ajustRezise(show, 0);
					//ajustRezise(register, 100);
					
					//refresh(show.getParent());
				}
			});
		
		}
		l10n();
		return parent;
	}

	public Button getNewButton(){
	  return (newButton!=null)?newButton:null;	
	}
	
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void l10n() {
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_USER));
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_USER_DATA);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().USER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().CREATION_DATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().FULL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().SEX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ID_CARNE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ADDRESS));
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
		//if(parent!=null){
		parent.layout(true, true);
		parent.redraw();
		parent.update();
		//refresh(parent.getParent());
		//}
	}
}
