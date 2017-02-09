package cu.uci.abcd.management.security.communFragment;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.RegisterProfile;
import cu.uci.abcd.management.security.ui.ViewProfile;
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

public class ViewProfileFragment implements FragmentContributor{

	PagePainter painter;
	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
	private Profile profile;
	private boolean buttonClose = false;
	private RegisterProfile registerProfile;
	private Button newButton;
	private Button closeButton;
	ContributorService contributorService;
	private ViewProfile viewProfile;
	
	public ViewProfileFragment(Profile profile, boolean buttonClose, ViewProfile viewProfile, ContributorService contributorService) {
		this.profile = profile;
		this.buttonClose = buttonClose;
		this.viewProfile = viewProfile;
		this.contributorService = contributorService;
	}
	
	public ViewProfileFragment(Profile profile, boolean buttonClose, RegisterProfile registerProfile, ContributorService contributorService) {
		this.profile = profile;
		this.buttonClose = buttonClose;
		this.registerProfile = registerProfile;
		this.contributorService = contributorService;
	}

	public ViewProfileFragment(Profile profile) {
		this.profile = profile;
	}

	private int dimension;

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	private Composite msg;

	public Composite getMsg() {
		return msg;
	}
	
	@Override
	public Control createUIControl(Composite parent) {
		// Room room = (Room) entity.getRow();
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
				header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PROFILE));
				painter.addHeader(header);
				Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
				painter.addSeparator(separator);
				dataGroup = new Group(topGroup, SWT.NORMAL);
				dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
				titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_PROFILE_DATA);
				painter.add(dataGroup);
				leftList = new LinkedList<>();
				leftList.add(MessageUtil.unescape(AbosMessages.get().PROFILE_NAME));
				leftList.add(MessageUtil.unescape(AbosMessages.get().PERMISSIONS));
				
				
				
				
				List<String> values = new LinkedList<>();
				values.add(profile.getProfileName());
				//values.add(profile.getProfileName());
				
				//Label xxx = new Label(topGroup, SWT.WRAP);
				//xxx.setText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				Item item = new Item(profile);
				values.add(item.Order(400));
				//values.add(xxx.getText());
				
				grupControls = CompoundGroup.printGroup(dataGroup, titleGroup,
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
							viewProfile.notifyListeners(SWT.Dispose, new Event());
						}
					});

					newButton = new Button(topGroup, SWT.NONE);
					newButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
					painter.add(newButton);
					newButton.addSelectionListener(new SelectionAdapter() {
						private static final long serialVersionUID = 4276152810528140450L;

						@Override
						public void widgetSelected(SelectionEvent e) {
							viewProfile.notifyListeners(SWT.Dispose, new Event());
							contributorService.selectContributor("addProfileID");
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
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PROFILE));
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_PROFILE_DATA);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().PROFILE_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().PERMISSIONS));
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
