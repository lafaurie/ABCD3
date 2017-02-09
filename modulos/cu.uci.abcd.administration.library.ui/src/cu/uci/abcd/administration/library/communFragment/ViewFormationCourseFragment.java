package cu.uci.abcd.administration.library.communFragment;

import java.text.SimpleDateFormat;
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

import cu.uci.abcd.administration.library.IEnrollmentService;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.RegisterFormationCourse;
import cu.uci.abcd.administration.library.ui.ViewFormationCourse;
import cu.uci.abcd.domain.management.library.Enrollment;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ViewFormationCourseFragment implements FragmentContributor {

	private ViewController controller;
	private FormationCourse formationCourse;
	private PagePainter painter;
	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
	private IEnrollmentService enrollmentService;
	private boolean buttonClose = false;
	private Button newButton;
	private Button closeButton;
	ContributorService contributorService;
	private Composite msg;
	private ViewFormationCourse viewFormationCourse;
	private int dimension;

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	public Composite getMsg() {
		return msg;
	}

	public void setMsg(Composite msg) {
		this.msg = msg;
	}

	public ViewFormationCourseFragment(FormationCourse formationCourse, boolean buttonClose, RegisterFormationCourse registerFormationCourse, ContributorService contributorService) {
		this.formationCourse = formationCourse;
		this.buttonClose = buttonClose;
		this.enrollmentService = ServiceProviderUtil.getService(IEnrollmentService.class);
		this.contributorService = contributorService;
	}
	
	public ViewFormationCourseFragment(FormationCourse formationCourse, boolean buttonClose, ViewFormationCourse viewFormationCourse, ContributorService contributorService) {
		this.formationCourse = formationCourse;
		this.buttonClose = buttonClose;
		this.viewFormationCourse = viewFormationCourse;
		this.enrollmentService = ServiceProviderUtil.getService(IEnrollmentService.class);
		this.contributorService = contributorService;
	}

	public ViewFormationCourseFragment(FormationCourse formationCourse) {
		this.formationCourse = formationCourse;
		this.enrollmentService = ServiceProviderUtil.getService(IEnrollmentService.class);
	}

	@Override
	public Control createUIControl(Composite parent) {
		dimension = parent.getParent().getParent().getBounds().width;
		painter = new FormPagePainter();
		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		Composite topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(topGroup);
		msg  = new Composite(topGroup, SWT.NONE);
		msg.setLayout(new FormLayout());
		msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(msg).atTopTo(topGroup).withWidth(320).withHeight(50).atRight(0);
		header = new Label(topGroup, SWT.NORMAL);
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_FORMATION_COURSE));
		painter.addHeader(header);
		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_FORMATION_COURSE_DATA);
		painter.add(dataGroup, Percent.W75);
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().CLASSIFICATION));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_TYPE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ROOM));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_LIBRARIAN_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EXTERNAL_STUDENT_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TO));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PROFESSOR));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ENROLLMENT));
		List<Enrollment> listEnrollment = enrollmentService.findByFormationCourse(formationCourse.getFormationCourseID());
		if (listEnrollment.size()>0) {
			for (int i = 1; i < listEnrollment.size(); i++) {
				leftList.add("");
			}
		}
		List<String> values = new LinkedList<>();
		values.add((formationCourse.isReceived()) ? MessageUtil.unescape(AbosMessages.get().EXTERNAL) : MessageUtil.unescape(AbosMessages.get().INTERNAL));
		values.add((formationCourse.getCourseName() != null) ? formationCourse.getCourseName() : "-");
		values.add((formationCourse.getHourQuantity() != null) ? formationCourse.getHourQuantity().toString() : "-");
		values.add((formationCourse.getCoursetype() != null) ? formationCourse.getCoursetype().getNomenclatorName() : "-");
		values.add((formationCourse.getRoom() != null) ? formationCourse.getRoom().getRoomName() : "-");
		values.add((formationCourse.getWorkersQuantity() != null) ? formationCourse.getWorkersQuantity().toString() : "-");
		values.add((formationCourse.getExternalWorkersQuantity() != null) ? formationCourse.getExternalWorkersQuantity().toString() : "-");
		values.add((formationCourse.getStartDate() != null) ? new SimpleDateFormat("dd-MM-yyyy").format(formationCourse.getStartDate()) : "-");
		values.add((formationCourse.getEndDate() != null) ? new SimpleDateFormat("dd-MM-yyyy").format(formationCourse.getEndDate()) : "-");
		values.add((formationCourse.getProfessor() != null) ? formationCourse.getProfessor().getFullName() : "-");
		values.add((formationCourse.getObservations().length()>0) ? formationCourse.getObservations() : "-");
		values.add((listEnrollment.size()>0) ? listEnrollment.get(0).getQuantity() + " " + listEnrollment.get(0).getAddressedTo().getNomenclatorName() + " - "
				+ listEnrollment.get(0).getArea() : "-");
		if (listEnrollment.size()>0) {
			for (int i = 1; i < listEnrollment.size(); i++) {
				Enrollment enrollemnt = listEnrollment.get(i);
				values.add((listEnrollment != null) ? enrollemnt.getQuantity() + " " + enrollemnt.getAddressedTo().getNomenclatorName() + " - " + enrollemnt.getArea() : "-");
			}
		}
		grupControls = CompoundGroup.printGroup(dataGroup, titleGroup, leftList, values, 300);
		if (buttonClose) {
			painter.reset();
			closeButton = new Button(topGroup, SWT.NONE);
			closeButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CLOSE));
			painter.add(closeButton);
			closeButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 2615553092700551346L;
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewFormationCourse.notifyListeners(SWT.Dispose, new Event());
				}
			});
			newButton = new Button(topGroup, SWT.NONE);
			newButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
			painter.add(newButton);
			newButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 4276152810528140450L;
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewFormationCourse.notifyListeners(SWT.Dispose, new Event());
					contributorService.selectContributor("addFormationCourseID");
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
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_FORMATION_COURSE));
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_FORMATION_COURSE_DATA);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().CLASSIFICATION));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_TYPE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ROOM));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_LIBRARIAN_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EXTERNAL_STUDENT_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TO));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PROFESSOR));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ENROLLMENT));
		List<Enrollment> listEnrollment = enrollmentService.findByFormationCourse(formationCourse.getFormationCourseID());
		if (listEnrollment != null) {
			for (int i = 1; i < listEnrollment.size(); i++) {
				leftList.add("");
			}
		}
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

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public FormationCourse getFormationCourse() {
		return formationCourse;
	}

	public void setFormationCourse(FormationCourse formationCourse) {
		this.formationCourse = formationCourse;
	}

}
