package cu.uci.abcd.administration.library.ui.model;

import java.sql.Date;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.administration.library.communFragment.RegisterWorkerFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.administration.library.util.Auxiliary;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class WorkerUpdateArea extends BaseEditableArea {
	private ViewController controller;
	private Composite parentComposite;
	private RegisterWorkerFragment registerWorkerFragment;
	private Worker worker;
	private Button saveBtn;

	//@SuppressWarnings("unused")
	private CRUDTreeTable workerTable;
	private Person person;
	private IPersonService personService;

	public WorkerUpdateArea(ViewController controller, CRUDTreeTable workerTable) {
		this.controller = controller;
		this.workerTable = workerTable;
		this.personService = ServiceProviderUtil
				.getService(IPersonService.class);
	}

	@Override
	public Composite createUI(Composite shell, IGridViewEntity entity,
			IVisualEntityManager manager) {
		Worker workerToView = (Worker) entity.getRow();
		worker = ((LibraryViewController) controller)
				.getWorkerById(workerToView.getWorkerID());
		person = worker.getPerson();
		Library library = (Library) SecurityUtils
				.getService().getPrincipal()
				.getByKey("library");
		registerWorkerFragment = new RegisterWorkerFragment(worker, controller,
				library, person);
		parentComposite = (Composite) registerWorkerFragment
				.createUIControl(shell);
		// loadPersonData();
		// loadDataWorker();
		return parentComposite;
	}

	// private void loadPersonData() {
	// registerWorkerFragment.associate();
	// }

	// private void loadDataWorker() {
	/*
	 * int pos = -1; for (int i = 0; i <
	 * registerWorkerFragment.getWorkerTypeCombo().getItemCount(); i++) { if
	 * (registerWorkerFragment.getWorkerTypeCombo().getItems()[i] ==
	 * worker.getWorkerType().getNomenclatorName()) { pos = i; } }
	 * registerWorkerFragment.getWorkerTypeCombo().select(pos); int
	 * posActivityCombo = -1; for (int i = 0; i <
	 * registerWorkerFragment.getActivityCombo().getItemCount(); i++) { if
	 * (registerWorkerFragment.getActivityCombo().getItems()[i] ==
	 * worker.getWorkerActivity().getNomenclatorName()) { posActivityCombo = i;
	 * } } registerWorkerFragment.getActivityCombo().select(posActivityCombo);
	 */
	// registerWorkerFragment.getDescriptionText().setText(worker.getObservation());
	// java.util.Date utilDate = new
	// java.util.Date(worker.getRegisterDate().getTime());
	// int year = Integer.parseInt(new
	// SimpleDateFormat("yyyy").format(utilDate));
	// int month = Integer.parseInt(new
	// SimpleDateFormat("MM").format(utilDate));
	// int day = Integer.parseInt(new SimpleDateFormat("dd").format(utilDate));

	// registerWorkerFragment.getRegisterDateTime().setDate(year, month - 1,
	// day);
	// registerWorkerFragment.setAsignedRooms(worker.getRooms());
	// registerWorkerFragment.getSelectedRoomsTable().setRows(registerWorkerFragment.getAsignedRooms());
	// registerWorkerFragment.getSelectedRoomsTable().refresh();
	// }

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity,
			final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2615553092700551346L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				Composite msg = registerWorkerFragment.getRegister();
				
				if (registerWorkerFragment.getValidator().decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(
							//msg, 
							MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}else{
					if (registerWorkerFragment.getValidator().decorationFactory
							.AllControlDecorationsHide()) {
						
						if(Auxiliary.dateLessOrEqualToday(registerWorkerFragment
								.getRegisterDateTime(), "La Fecha de registro debe ser anterior o igual a la fecha actual.")){
							
						if (!(registerWorkerFragment.getPerson() == null)) {
							if(Auxiliary.getDate(registerWorkerFragment.getRegisterDateTime()).after(person.getBirthDate())){
							Long personId = registerWorkerFragment.getPerson()
									.getPersonID();
							
							Nomenclator workerType;
							if (UiUtils.getSelected(registerWorkerFragment.getWorkerTypeCombo()) == null) {
								workerType = null;
							} else {
								workerType = (Nomenclator) UiUtils.getSelected(registerWorkerFragment.getWorkerTypeCombo());
							}
							
							Nomenclator workerActivity;
							if (UiUtils.getSelected(registerWorkerFragment.getActivityCombo()) == null) {
								workerActivity = null;
							} else {
								workerActivity = (Nomenclator) UiUtils.getSelected(registerWorkerFragment.getActivityCombo());
							}
							
							String description = registerWorkerFragment
									.getDescriptionText().getText().replaceAll(" +", " ").trim();
							int fromYear = registerWorkerFragment
									.getRegisterDateTime().getYear() - 1900;
							int fromMonth = registerWorkerFragment
									.getRegisterDateTime().getMonth();
							int fromDay = registerWorkerFragment
									.getRegisterDateTime().getDay();
							@SuppressWarnings("deprecation")
							Date registerDate = new Date(fromYear, fromMonth,
									fromDay);
							Person personWorker = personService
									.findOnePerson(personId);
							worker.setRegisterDate(registerDate);
							worker.setObservation(description);
							worker.setWorkerType(workerType);
							worker.setPerson(personWorker);
							worker.setWorkerActivity(workerActivity);
							worker.setRooms(registerWorkerFragment
									.getAsignedRooms());
							worker.setLibrary(registerWorkerFragment.getPerson().getLibrary());
							
							Worker WorkerSaved = ((LibraryViewController) controller)
									.saveWorker(worker);
							worker = null;
							manager.save(new BaseGridViewEntity<Worker>(
									WorkerSaved));
							manager.refresh();

							Composite viewSmg = ((WorkerViewArea)workerTable.getActiveArea()).getViewWorkerFragment().getMsg();
							
							//Composite viewSmg = ((WorkerViewArea)workerTable.getActiveArea()).getMsg();
							
							RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_INF_UPDATE_DATA));
						}else{
							//REGISTER_DATE_MORE_BIRTHDATE
							registerWorkerFragment
							.getRegisterDateTime().setBackground(new Color(registerWorkerFragment
									.getRegisterDateTime().getDisplay(), 255, 204, 153));
							RetroalimentationUtils
							.showErrorShellMessage(MessageUtil
									.unescape(AbosMessages
											.get().REGISTER_DATE_MORE_BIRTHDATE));
						}
						} else {
							RetroalimentationUtils.showErrorShellMessage(
									//msg, 
									MessageUtil
									.unescape(AbosMessages.get().SHOULD_SELECT_PERSON_AS_WORKER));
							
							
						}
					}
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
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public void l10n() {
		saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		registerWorkerFragment.l10n();
	}

	@Override
	public String getID() {
		return "updateWorkerID";
	}

}
