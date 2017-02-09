package cu.uci.abcd.administration.library.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.IEnrollmentService;
import cu.uci.abcd.administration.library.communFragment.RegisterEnrollmentFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.RegisterFormationCourse;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Enrollment;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
import cu.uci.abos.core.widget.grid.NotPaginateTable;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class EnrollmentSaveArea extends BaseEditableArea {

	private ViewController controller;
	private CRUDTreeTable tableEnrollment;
	private Enrollment enrollment = null;
	private RegisterEnrollmentFragment saveEnrollmentFragment;
	private Composite parentComposite;
	private Map<String, Control> controlsMaps;
	private Button saveButton;
	private FormationCourse formationCourse = null;
	private List<Enrollment> entities = new ArrayList<>();
	private List<Enrollment> entitiesAux = new ArrayList<>();
	private int dimension;
	private List<Enrollment> savedEntities = new ArrayList<>();
	private IEnrollmentService enrollmentService;
	@SuppressWarnings("unused")
	private Library library;

	public List<Enrollment> getSavedEntities() {
		savedEntities.clear();
		for (IGridViewEntity iGridViewEntity : tableEnrollment.getRows()) {
			savedEntities.add((Enrollment) iGridViewEntity.getRow());
		}
		return savedEntities;
	}

	public List<Enrollment> getEntities() {
		return entities;
	}

	public void setEntities(List<Enrollment> entities) {
		this.entities = entities;
	}
	RegisterFormationCourse registerFormationCourse = null;
	//Button saveBtn;
	
	public EnrollmentSaveArea(RegisterFormationCourse registerFormationCourse, ViewController controller, CRUDTreeTable tableEnrollment, FormationCourse formationCourse) {
		this.controller = controller;
		this.registerFormationCourse = registerFormationCourse;
		this.tableEnrollment = tableEnrollment;
		this.setFormationCourse(formationCourse);
		this.setEnrollmentService(ServiceProviderUtil.getService(IEnrollmentService.class));
	}
	FormationCourseUpdateArea formationCourseUpdateArea = null;
	
	public EnrollmentSaveArea(FormationCourseUpdateArea formationCourseUpdateArea, ViewController controller, CRUDTreeTable tableEnrollment, FormationCourse formationCourse) {
		this.controller = controller;
		this.formationCourseUpdateArea = formationCourseUpdateArea;
		this.tableEnrollment = tableEnrollment;
		this.setFormationCourse(formationCourse);
		this.setEnrollmentService(ServiceProviderUtil.getService(IEnrollmentService.class));
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {
		
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveButton = new Button(parent, SWT.PUSH);
		saveButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Composite msg = saveEnrollmentFragment.getMsg();
				boolean a = saveEnrollmentFragment.getValidator().decorationFactory
						.isRequiredControlDecorationIsVisible();
						boolean b = saveEnrollmentFragment.getValidatorPrincipal().decorationFactory
								.isRequiredControlDecorationIsVisible();
				if (a
						|| b ) {
					RetroalimentationUtils.showErrorShellMessage(
					MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages
							.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
					if (saveEnrollmentFragment.getValidator().decorationFactory
							.AllControlDecorationsHide()
							&& saveEnrollmentFragment.getValidatorPrincipal().decorationFactory
									.AllControlDecorationsHide()) {
						if (enrollment == null) {
							enrollment = new Enrollment();
						}
						
						enrollment.setAddressedTo(null);
						enrollment.setFirstUbication(null);
						enrollment.setProgramm(null);
						enrollment.setprogramName(null);
						enrollment.setQuantity(null);
						enrollment.setSecondUbication(null);
						enrollment.setThirdUbication(null);
						
					Nomenclator addressedTo = (Nomenclator) UiUtils.getSelected(((Combo) controlsMaps.get("adreesedToCombo")));
					enrollment.setAddressedTo(addressedTo);
					
					int quantity = Integer.parseInt(((Text) controlsMaps.get("quantityText")).getText());
					enrollment.setQuantity(quantity);
					int selectedIndexFirstUbication = ((Combo) controlsMaps.get("firstUbicationToCombo")).getSelectionIndex();
					if (selectedIndexFirstUbication >0 && ((Combo) controlsMaps.get("firstUbicationToCombo")).isVisible()) {
					Nomenclator firstUbication = (Nomenclator) UiUtils.getSelected(((Combo) controlsMaps.get("firstUbicationToCombo")));
					enrollment.setFirstUbication(firstUbication);
					}
					int selectedIndexSecondUbication = ((Combo) controlsMaps.get("secondUbicationToCombo")).getSelectionIndex();
					if (selectedIndexSecondUbication > 0 && ((Combo) controlsMaps.get("secondUbicationToCombo")).isVisible()) {
					Nomenclator secondUbication = (Nomenclator) UiUtils.getSelected(((Combo) controlsMaps.get("secondUbicationToCombo")));
					enrollment.setSecondUbication(secondUbication);
					}
					int selectedIndexThirdUbication = ((Combo) controlsMaps.get("thirdUbicationToCombo")).getSelectionIndex();
					if (selectedIndexThirdUbication >0 && ((Combo) controlsMaps.get("thirdUbicationToCombo")).isVisible()) {
					Nomenclator thirdUbication = (Nomenclator) UiUtils.getSelected(((Combo) controlsMaps.get("thirdUbicationToCombo")));
					enrollment.setThirdUbication(thirdUbication);
					}
					int selectedIndexProgram = ((Combo) controlsMaps.get("programCombo")).getSelectionIndex();
					if (selectedIndexProgram > 0 && ((Combo) controlsMaps.get("programCombo")).isVisible()) {
					Nomenclator program = (Nomenclator) UiUtils.getSelected(((Combo) controlsMaps.get("programCombo")));
					enrollment.setProgramm(program);
					}
					String programName = ((Text) controlsMaps.get("programNameText")).getText().replaceAll(" +", " ").trim();
					if (programName.length() > 0) {
						enrollment.setprogramName(programName);
					}
					for (IGridViewEntity iGridViewEntity : tableEnrollment.getRows()) {
						entities.add((Enrollment) iGridViewEntity.getRow());
					}
					if (entities.contains(enrollment)) {
						entities.remove(enrollment);
					}
					if (!enrollment.exist(entities)) {
						entities.add(enrollment);
						if(  registerFormationCourse!=null ){
							if(!registerFormationCourse.getEntities().contains(enrollment)){
								registerFormationCourse.getEntities().add(enrollment);
							}else{
								for (int i = 0; i < registerFormationCourse.getEntities().size(); i++) {
									if(registerFormationCourse.getEntities().get(i).equals(enrollment)){
										registerFormationCourse.getEntities().remove(i);
										registerFormationCourse.getEntities().add(i, enrollment);
									}
								}
							}
							
							//registerFormationCourse.setEntities(entities);
							registerFormationCourse.refreshTable();
						}else{
							//formationCourseUpdateArea.getEntities().add(enrollment);
							if(!formationCourseUpdateArea.getEntities().contains(enrollment)){
								formationCourseUpdateArea.getEntities().add(enrollment);
							}else{
								for (int i = 0; i < formationCourseUpdateArea.getEntities().size(); i++) {
									if(formationCourseUpdateArea.getEntities().get(i).equals(enrollment)){
										formationCourseUpdateArea.getEntities().remove(i);
										formationCourseUpdateArea.getEntities().add(i, enrollment);
									}
								}
							}
							//formationCourseUpdateArea.setEntities(entities);
							formationCourseUpdateArea.refreshTable();
						}
						
						//registerFormationCourse.getSaveBtn().setEnabled(true);
						
						/*
						tableEnrollment.setTotalElements((int) entities.size());
						
						if (entities.size() <= page * size + size) {
							tableEnrollment.setRows(entities.subList(page * size, entities.size()));
						} else {
							tableEnrollment.setRows(entities.subList(page * size, page * size + size));
						}
						tableEnrollment.refresh();
						*/
						//refreshEnrolmentTable();
						/*
						tableEnrollment.clearRows();
						tableEnrollment.setRows(entities);
						tableEnrollment.refresh();
						tableEnrollment.destroyEditableArea();
						enrollment = null;
						*/
					} else {
						RetroalimentationUtils.showErrorShellMessage(
								//msg, 
								MessageUtil
								.unescape(AbosMessages
										.get().ELEMENT_EXIST));
						
					}
					entities.clear();
					entitiesAux.clear();
					refresh();
					//ServiceProviderUtil.getService(ContributorService.class).refresh();
					
				} else {
					RetroalimentationUtils.showErrorShellMessage(
							//msg, 
							MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages
									.get().MSG_ERROR_INCORRECT_DATA));
				}
			}
			}
		});
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		//registerFormationCourse.getSaveBtn().setEnabled(false);
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		dimension = parent.getParent().getParent().getBounds().width;
		addComposite(parent);
		boolean isPostgradeEdit = false;
		if (entity != null) {
			enrollment = (Enrollment) entity.getRow();
			Nomenclator postgradeStudent = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById( Nomenclator.POSTGRADE_STUDENT);
			if (enrollment.getAddressedTo().equals(postgradeStudent)) {
				isPostgradeEdit = true;
			}
		} else {
			enrollment = null;

		}
		saveEnrollmentFragment = new RegisterEnrollmentFragment(enrollment, controller, isPostgradeEdit, dimension);
		parentComposite = (Composite) saveEnrollmentFragment.createUIControl(parent);
		controlsMaps = saveEnrollmentFragment.getControls();
		return parentComposite;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {
		saveEnrollmentFragment.l10n();
		saveButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
	}

	public FormationCourse getFormationCourse() {
		return formationCourse;
	}

	public void setFormationCourse(FormationCourse formationCourse) {
		this.formationCourse = formationCourse;
	}

	public IEnrollmentService getEnrollmentService() {
		return enrollmentService;
	}

	public void setEnrollmentService(IEnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}
	
	//public void refreshEnrrolmentTable(){
	//	;entities
	//}

}
