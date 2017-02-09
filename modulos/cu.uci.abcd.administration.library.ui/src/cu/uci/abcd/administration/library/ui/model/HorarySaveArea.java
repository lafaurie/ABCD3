package cu.uci.abcd.administration.library.ui.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.ConfigureSchedule;
import cu.uci.abcd.administration.library.ui.controller.ScheduleViewController;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
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

public class HorarySaveArea extends BaseEditableArea {
	private Label registerHoraryLabel;
	private ViewController controller;
	private Combo dayOfWeekCombo;
	private SecurityCRUDTreeTable horaryTable;
	private int optionAddOrUpdate;
	Label lblCriiteriosDeBsqueda1;
	Label lblCriiteriosDeBsqueda2;
	
	public SecurityCRUDTreeTable getHoraryTable() {
		return horaryTable;
	}

	public void setHoraryTable(SecurityCRUDTreeTable horaryTable) {
		this.horaryTable = horaryTable;
	}

	public Schedule getWorkSchedule() {
		return workSchedule;
	}

	public void setWorkSchedule(Schedule workSchedule) {
		this.workSchedule = workSchedule;
	}

	private DateTime fromTimeDateTime;
	private DateTime toTimeDateTime;
	private Schedule workSchedule;
	private Library library;
	private ValidatorUtils validator;
	int dimension;
	int option;
	private Button saveBtn;
	Label lblNombreDelRegistro;
	//private Composite msg;
	private Label horaryData;
	ConfigureSchedule configureSchedule;
	
	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public HorarySaveArea(ConfigureSchedule configureSchedule, ViewController controller, SecurityCRUDTreeTable horaryTable, int optionAddOrUpdate) {
		this.configureSchedule = configureSchedule;
		this.controller = controller;
		this.horaryTable = horaryTable;
		this.optionAddOrUpdate = optionAddOrUpdate;
	}
	
	private Schedule schedule;
	
	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		setDimension(dimension);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(parent);
		option = 1;
		if (entity != null) {
			option = 2;
			Schedule scheduleToView = (Schedule) entity.getRow();
			schedule = ((ScheduleViewController) controller).getScheduleById(scheduleToView.getWorkScheduleID());
		} else {
			schedule = null;
		}
		
		library = (Library) SecurityUtils
				.getService().getPrincipal()
				.getByKey("library");
		validator = new ValidatorUtils(new CustomControlDecoration());

		//msg  = new Composite(parent, SWT.NONE);
		//msg.setLayout(new FormLayout());
		//msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		//FormDatas.attach(msg).atTopTo(parent).withWidth(320).withHeight(50).atRight(0);
		
		registerHoraryLabel = new Label(parent, SWT.NONE);
		addHeader(registerHoraryLabel);
		
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		

		horaryData = new Label(parent, SWT.NORMAL);
		addHeader(horaryData);
		
		lblNombreDelRegistro = new Label(parent, SWT.NONE);
		add(lblNombreDelRegistro);

		dayOfWeekCombo = new Combo(parent, SWT.READ_ONLY);
		add(dayOfWeekCombo);
		
		br();

		lblCriiteriosDeBsqueda1 = new Label(parent, SWT.NONE);
		add(lblCriiteriosDeBsqueda1);
		
		fromTimeDateTime = new DateTime(parent, SWT.BORDER | SWT.TIME | SWT.SHORT );
		add(fromTimeDateTime);
		
		lblCriiteriosDeBsqueda2 = new Label(parent, SWT.NONE);
		add(lblCriiteriosDeBsqueda2);
		
		toTimeDateTime = new DateTime(parent, SWT.BORDER | SWT.TIME | SWT.SHORT);
		add(toTimeDateTime);
		
		initialize(dayOfWeekCombo, ((ScheduleViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorIdOrderByCode(library.getLibraryID(), Nomenclator.DAY_OF_WEEK));
		
		validator.applyValidator(dayOfWeekCombo, "dayOfWeekCombo", DecoratorType.REQUIRED_FIELD, true);
		loadHoraryData();
		//l10n();
		
		return parent;
	}
	
	public void loadHoraryData(){
		
		if (schedule != null) {
			UiUtils.selectValue(dayOfWeekCombo, schedule.getDayOfWeek());
			
			java.util.Date startHour = new java.util.Date(schedule.getStartHour().getTime());
			int hourStart = Integer.parseInt(new SimpleDateFormat("k").format(startHour));
			int minutesStart = Integer.parseInt(new SimpleDateFormat("m").format(startHour));
			fromTimeDateTime.setHours(hourStart);
			fromTimeDateTime.setMinutes(minutesStart);
			
			java.util.Date endHour = new java.util.Date(schedule.getEndhour().getTime());
			int hourEnd = Integer.parseInt(new SimpleDateFormat("k").format(endHour));
			int minutesEnd = Integer.parseInt(new SimpleDateFormat("m").format(endHour));
			toTimeDateTime.setHours(hourEnd);
			toTimeDateTime.setMinutes(minutesEnd);
			
		}
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveBtn = new Button(parent, SWT.PUSH);
		
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -1883869606963280647L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(
							//msg, 
							MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
				if (getValidator().decorationFactory.AllControlDecorationsHide()) {	
					
				Nomenclator dayOfWeek = (Nomenclator) UiUtils.getSelected(dayOfWeekCombo);
				@SuppressWarnings("deprecation")
				Time fromTime = new Time(fromTimeDateTime.getHours(), fromTimeDateTime.getMinutes(), 0);
				@SuppressWarnings("deprecation")
				Time toTime = new Time(toTimeDateTime.getHours(), toTimeDateTime.getMinutes(), 0);
				Timestamp fromTimeTimestamp = new Timestamp(fromTime.getTime());
				Timestamp toTimeTimestamp = new Timestamp(toTime.getTime());
				
				if(fromTimeTimestamp.before(toTimeTimestamp)){
					
				List<Schedule> listSchedule = ((ScheduleViewController) controller)
							.findAll(library.getLibraryID(), dayOfWeek);
				if (schedule != null) {
				for (int i =0; i<listSchedule.size(); i++) {
					if(listSchedule.get(i).getWorkScheduleID()==schedule.getWorkScheduleID()){
						listSchedule.remove(i);
					}
				}
				}
					boolean flag = true;
					for (Schedule schedule : listSchedule) {
						if (    ((fromTimeTimestamp.before(schedule.getEndhour())||(fromTimeTimestamp.equals(schedule.getEndhour())))&&(fromTimeTimestamp.after(schedule.getStartHour())||(fromTimeTimestamp.equals(schedule.getStartHour()))))||((toTimeTimestamp.before(schedule.getEndhour())||(toTimeTimestamp.equals(schedule.getEndhour())))&&(toTimeTimestamp.after(schedule.getStartHour())||(toTimeTimestamp.equals(schedule.getStartHour())))) ) {
							flag = false;
						}
					}
					for (Schedule schedule : listSchedule) {
						//boolean pepe = ((schedule.getEndhour().before(toTimeTimestamp)||(schedule.getEndhour().equals(toTimeTimestamp)))&&(schedule.getStartHour().after(fromTimeTimestamp)||(schedule.getStartHour().equals(fromTimeTimestamp))))||((schedule.getEndhour().before(toTimeTimestamp)||(schedule.getEndhour().equals(toTimeTimestamp)))&&(schedule.getStartHour().after(fromTimeTimestamp)||(schedule.getStartHour().equals(fromTimeTimestamp))));  		  
						if (
								  ((schedule.getEndhour().before(fromTimeTimestamp)||(schedule.getEndhour().equals(fromTimeTimestamp)))&&(schedule.getStartHour().after(fromTimeTimestamp)||(schedule.getStartHour().equals(fromTimeTimestamp))))||((schedule.getEndhour().before(toTimeTimestamp)||(schedule.getEndhour().equals(toTimeTimestamp)))&&(schedule.getStartHour().after(toTimeTimestamp)||(schedule.getStartHour().equals(toTimeTimestamp))))  
						   ) {
							flag = false;
						}
					}
					if(flag){
						boolean update = true;
						if (schedule == null) {
							schedule = new Schedule();
							update= false;
						}
						schedule.setDayOfWeek(dayOfWeek);
						schedule.setStartHour(fromTimeTimestamp);
						schedule.setEndhour(toTimeTimestamp);
						library = (Library) SecurityUtils
								.getService().getPrincipal()
								.getByKey("library");
						schedule.setLibrary(library);
						@SuppressWarnings("unused")
						Schedule scheduleSaved = ((ScheduleViewController) controller).saveSchedule(schedule);
						//configureSchedule.searchHorary();
						horaryTable.getPaginator().goToFirstPage();
						schedule = null;
						horaryTable.destroyEditableArea();
						
						String msgShow;
						if(update){
							msgShow = MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_INF_UPDATE_DATA);
						}else{
							msgShow = MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_INF_CREATE_NEW_ELEMENT);
						}
						RetroalimentationUtils.showInformationMessage(horaryTable.getParent(),msgShow);
						
					}else{
						RetroalimentationUtils.showErrorShellMessage(
								//msg, 
								MessageUtil
								.unescape(AbosMessages
										.get().LABEL_SCHEDULE_OVERLAPS));
					}
				}else{
					RetroalimentationUtils.showErrorShellMessage(
							//msg, 
							MessageUtil
							.unescape(AbosMessages
									.get().LABEL_END_HOUR_GREATER));
				}
		        }else {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void l10n() {
		saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		lblNombreDelRegistro.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DAY_OF_WEEK));
		lblCriiteriosDeBsqueda1.setText(MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_FROM));
		lblCriiteriosDeBsqueda2.setText(MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_TO));
		horaryData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_HORARY_DATA));
		
		//int position = dayOfWeekCombo.getSelectionIndex();
		//initialize(dayOfWeekCombo, ((ScheduleViewController) controller)
		//		.getAllManagementLibraryViewController().getLibraryService()
		//		.findNomenclatorIdOrderByCode(library.getLibraryID(), Nomenclator.DAY_OF_WEEK));
		//dayOfWeekCombo.select(position);
		
		if(option==1){
		    registerHoraryLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_HORARY));
		}else{
			registerHoraryLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_UPDATE_HORARY));	
		}
		
		
		
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}
	
	@Override
	public String getID() {
		if(optionAddOrUpdate==1){
			return "addSheduleID";
		}else{
			return "updateSheduleID";
		}
		
	}

}
