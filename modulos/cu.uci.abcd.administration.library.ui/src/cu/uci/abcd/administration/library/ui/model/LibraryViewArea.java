package cu.uci.abcd.administration.library.ui.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class LibraryViewArea extends BaseEditableArea {
	
	private Composite msg;
	public Composite getMsg() {
		return msg;
	}
	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
	private ViewController controller;
	private Composite topGroup;
	Library library;
	

	public LibraryViewArea(ViewController controller){
		this.controller = controller;
	}
	List<String> values;
	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {

		Long idLibrary = ((Library) entity.getRow()).getLibraryID();
		library = ((LibraryViewController) controller).getLibraryById(idLibrary);
		setDimension(parent.getParent().getParent().getBounds().width);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(parent);
		topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(topGroup);
		
		msg  = new Composite(topGroup, SWT.NONE);
		msg.setLayout(new FormLayout());
		msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(msg).atTopTo(topGroup).withWidth(320).withHeight(50).atRight(0);
		
		
		header = new Label(topGroup, SWT.NORMAL);
		addHeader(header);
		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil
				.unescape(AbosMessages.get().LABEL_LIBRARY_DATA);
		add(dataGroup, Percent.W75);
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ADDREES));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ROOM_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_WORKER_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PROVIDER_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ISIS_HOME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ACTIVE));
		values = new LinkedList<>();
		values.add((library.getLibraryName() != null) ? library
				.getLibraryName() : "-");
		values.add((library.getAddress() != null) ? library.getAddress()
				.toString() : "-");
		values.add(String.valueOf(library.getRooms().size()));
		values.add(String.valueOf(library.getWorkers().size()));
		values.add(String.valueOf(library.getProviders().size()));
		values.add((library.getIsisDefHome() != null) ? library
				.getIsisDefHome() : "-");
		values.add((library.isEnabled()) ? MessageUtil.unescape(AbosMessages
				.get().YES) : MessageUtil.unescape(AbosMessages.get().NO));
		grupControls = CompoundGroup.printGroup(dataGroup, titleGroup,
				leftList, values, 300);
		return parent;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1,
			IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public void l10n() {
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_LIBRARY));
		titleGroup = MessageUtil
				.unescape(AbosMessages.get().LABEL_LIBRARY_DATA);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ADDREES));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ROOM_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_WORKER_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PROVIDER_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ISIS_HOME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ACTIVE));
		dataGroup.setText(titleGroup);
		CompoundGroup.l10n(grupControls, leftList);
	}
	
	@Override
	public String getID() {
		return "viewLibraryID";
	}

}