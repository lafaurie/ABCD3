package cu.uci.abcd.administration.library.communFragment;

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

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.RegisterRoom;
import cu.uci.abcd.administration.library.ui.ViewRoom;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.management.library.Room;
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

public class ViewRoomFragment implements FragmentContributor {

	private PagePainter painter;
	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
	private Room room;
	private boolean buttonClose = false;
	private ViewRoom viewRoom;
	private Button newButton;
	private Button closeButton;
	private ContributorService contributorService;
	private Composite msg;

	public Composite getMsg() {
		return msg;
	}

	public ViewRoomFragment(Room room, boolean buttonClose, RegisterRoom registerRoom, ContributorService contributorService) {
		this.room = room;
		this.buttonClose = buttonClose;
		this.contributorService = contributorService;
	}
	
	public ViewRoomFragment(Room room, boolean buttonClose, ViewRoom viewRoom, ContributorService contributorService) {
		this.room = room;
		this.buttonClose = buttonClose;
		this.viewRoom = viewRoom;
		this.contributorService = contributorService;
	}
	
	public ViewRoomFragment(Room room) {
		this.room = room;
	}

	private int dimension;

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
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
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_ROOM));
		painter.addHeader(header);
		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_ROOM_DATA);
		painter.add(dataGroup);
		
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().REPAYMENT_SCHEDULE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SURFACE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_READING_SETS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_FORMATION));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_WORKING_GROUP));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DEPOSIT_BOOKSHELVES));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_OPEN_BOOKSHELVES));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_PC_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_WORKERS_PC_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_READER_PLAYERS_QUANTITY));
		List<String> values = new LinkedList<>();
		values.add((room.getRoomName() != null) ? room.getRoomName() : "-");
		values.add(Auxiliary.FormatHour(room.getHour()));
		values.add((room.getSurface() != null) ? room.getSurface().toString() : "-");
		values.add((room.getAvailableReadingSeats() != null) ? room.getAvailableReadingSeats().toString() : "-");
		values.add((room.getAvailableUserFormationSeats() != null) ? room.getAvailableUserFormationSeats().toString() : "-");
		values.add((room.getAvailableWorkGroupSeats() != null) ? room.getAvailableWorkGroupSeats().toString() : "-");
		values.add((room.getDepositBookShelves() != null) ? room.getDepositBookShelves().toString() : "-");
		values.add((room.getOpenBookShelves() != null) ? room.getOpenBookShelves().toString() : "-");
		values.add((room.getUserPcQuantity() != null) ? room.getUserPcQuantity().toString() : "-");
		values.add((room.getWorkerPcQuantity() != null) ? room.getWorkerPcQuantity().toString() : "-");
		values.add((room.getDiverseReaderPlayerQuantity() != null) ? room.getDiverseReaderPlayerQuantity().toString() : "-");
		grupControls = CompoundGroup.printGroup(dataGroup, titleGroup, leftList, values, 300);
		if (buttonClose) {
			painter.reset();
			closeButton = new Button(topGroup, SWT.NONE);
			closeButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CLOSE));
			painter.add(closeButton);
			closeButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = -7306583336037504034L;
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewRoom.notifyListeners(SWT.Dispose, new Event());
				}
			});
			newButton = new Button(topGroup, SWT.NONE);
			newButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
			painter.add(newButton);
			newButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 4276152810528140450L;
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewRoom.notifyListeners(SWT.Dispose, new Event());
					contributorService.selectContributor("addRoomID");
				}
			});
		}
		return parent;
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_ROOM));
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_ROOM_DATA);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().REPAYMENT_SCHEDULE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SURFACE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_READING_SETS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_FORMATION));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_WORKING_GROUP));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DEPOSIT_BOOKSHELVES));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_OPEN_BOOKSHELVES));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_PC_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_WORKERS_PC_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_READER_PLAYERS_QUANTITY));
		dataGroup.setText(titleGroup);
		CompoundGroup.l10n(grupControls, leftList);
		if (buttonClose) {
			closeButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CLOSE));
			newButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
		}
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

}
