package cu.uci.abcd.administration.library.communFragment;

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

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.RegisterWorker;
import cu.uci.abcd.administration.library.ui.ViewWorker;
import cu.uci.abcd.domain.management.library.Worker;
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

public class ViewWorkerFragment implements FragmentContributor {

	private PagePainter painter;
	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
	private Worker worker;
	private boolean cancelButton = false;
	private Button newButton;
	private Button closeButton;
	private ContributorService contributorService;
	private Composite msg;
	private ViewWorker viewWorker;

	public ViewWorkerFragment(Worker worker, boolean cancelButton,
			ViewWorker viewWorker, ContributorService contributorService) {
		this.worker = worker;
		this.cancelButton = cancelButton;
		this.viewWorker = viewWorker;
		this.contributorService = contributorService;
	}

	public ViewWorkerFragment(Worker worker, boolean cancelButton,
			RegisterWorker registerWorker, ContributorService contributorService) {
		this.worker = worker;
		this.cancelButton = cancelButton;
		this.contributorService = contributorService;
	}

	public ViewWorkerFragment(Worker worker) {
		this.worker = worker;
	}

	@Override
	public Control createUIControl(Composite parent) {
		painter = new FormPagePainter();
		painter.addComposite(parent);
		Composite topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(topGroup);
		msg = new Composite(topGroup, SWT.NONE);
		msg.setLayout(new FormLayout());
		msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(msg).atTopTo(topGroup).withWidth(320).withHeight(50)
				.atRight(0);
		header = new Label(topGroup, SWT.NORMAL);
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_WORKER));
		painter.addHeader(header);
		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_WORKER_DATA);
		painter.add(dataGroup);
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_BIRTHDAY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().AGE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SEX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftList.add(MessageUtil.unescape(AbosMessages.get().EMAIL));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ADDRESS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_WORKER_TYPE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ACTIVITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_DATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_OBSERVATIONS));
		List<String> values = new LinkedList<>();
		values.add(worker.getPerson().getFullName());
		values.add((worker.getPerson().getUser() != null) ? worker.getPerson()
				.getUser().getUsernameToString() : "-");
		values.add(new SimpleDateFormat("dd-MM-yyyy").format(worker.getPerson()
				.getBirthDate()));
		values.add(worker.getPerson().getAge());
		values.add(worker.getPerson().getSex().getNomenclatorName());
		values.add(worker.getPerson().getDNI());
		values.add(worker.getPerson().getEmailAddress() != null ? worker
				.getPerson().getEmailAddress() : "");
		values.add(worker.getPerson().getAddress() != null ? worker.getPerson()
				.getAddress() : "-");
		values.add(worker.getWorkerType().getNomenclatorName());
		values.add(worker.getWorkerActivity().getNomenclatorName());
		values.add(new SimpleDateFormat("dd-MM-yyyy").format(worker
				.getRegisterDate()));
		values.add((worker.getObservation() != null) ? worker.getObservation()
				: "-");
		Image image = worker.getPerson().getPhoto().getImage();
		grupControls = CompoundGroup.printGroup(image, dataGroup, titleGroup,
				leftList, values);

		if (cancelButton) {
			painter.reset();
			closeButton = new Button(topGroup, SWT.NONE);
			closeButton
					.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CLOSE));
			painter.add(closeButton);
			closeButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 2615553092700551346L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					viewWorker.notifyListeners(SWT.Dispose, new Event());
				}
			});
			newButton = new Button(topGroup, SWT.NONE);
			newButton
					.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
			painter.add(newButton);
			newButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 4276152810528140450L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					viewWorker.notifyListeners(SWT.Dispose, new Event());
					contributorService.selectContributor("addWorkerID");
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
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_WORKER));
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_WORKER_DATA);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_BIRTHDAY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().AGE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SEX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftList.add(MessageUtil.unescape(AbosMessages.get().EMAIL));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ADDRESS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_WORKER_TYPE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ACTIVITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_DATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_OBSERVATIONS));
		dataGroup.setText(titleGroup);
		CompoundGroup.l10n(grupControls, leftList);
		if (cancelButton) {
			closeButton
					.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CLOSE));
			newButton
					.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
		}
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public Composite getMsg() {
		return msg;
	}

	public void setMsg(Composite msg) {
		this.msg = msg;
	}

}
