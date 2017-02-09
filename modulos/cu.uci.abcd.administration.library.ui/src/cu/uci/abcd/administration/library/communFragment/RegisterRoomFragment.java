package cu.uci.abcd.administration.library.communFragment;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.RegisterRoom;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterRoomFragment implements FragmentContributor {

	private Label registerRoomLabel;
	private ViewController controller;
	private Map<String, Control> controls = new HashMap<String, Control>();
	private Label lblNewLab;
	private Label nameLibraryLabel;
	private Text nameLibraryText;
	private Label surfaceLibraryLabel;
	private Text surfaceLibraryText;
	private Label separador;
	private Label readingPostsLibraryLabel;
	private Label readingSeatsLibraryLabel;
	private Text readingSeatsLibraryText;
	private Label userFormationLabel;
	private Text userFormationText;
	private Label workingGroupLabel;
	private Text workingGroupText;
	private Label separador1;
	private Label bookshelvesLabel;
	private Label openBookshelvesLabel;
	private Text openBookshelvesText;
	private Label depositBookshelvesLabel;
	private Text depositBookshelvesText;
	private Label separador2;
	private Label equipmentLabel;
	private Label userPcQuantityLabel;
	private Text userPcQuantityText;
	private Label workersPcQuantityLabel;
	private Text workersPcQuantityText;
	private Label readerPlayersQuantityLabel;
	private Text readerPlayersQuantityText;
	private boolean flag;
	private Room room;
	private Room roomSaved;
	private int dimension;
	private RegisterRoom registerRoom;
	private ContributorService contributorService;
	
	@SuppressWarnings("unused")
	private FormLayout form;
	@SuppressWarnings("unused")
	private TabItem workerTabItem;
	@SuppressWarnings("unused")
	private Label listWorkerLibraryLabel;
	@SuppressWarnings("unused")
	private CRUDTreeTable workerTable;
	@SuppressWarnings("unused")
	private CRUDTreeTable formationCourseTable;
	@SuppressWarnings("unused")
	private TabItem formationCourseTabItem;

	public ValidatorUtils getValidator() {
		return validator;
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	//ViewRoomFragment viewRoomFragment;
	Composite viewWorkerSave;
	PagePainter painter;

	Button cancelBtn;
	Button saveBtn;
	
	Composite register;
	Composite show;
	
	Label hourLabel;
	DateTime hourDateTime;

	private ValidatorUtils validator;

	public Room getRoomSaved() {
		return roomSaved;
	}

	public void setRoomSaved(Room roomSaved) {
		this.roomSaved = roomSaved;
	}
	
	//String contributionName;

	public RegisterRoomFragment(
			//String contributionName, 
			Room room, ViewController controller, int dimension, RegisterRoom registerRoom, ContributorService contributorService) {
		this.room = room;
		this.controller = controller;
		this.dimension = dimension;
		roomSaved = null;
		this.registerRoom = registerRoom;
		this.contributorService = contributorService;
		//this.contributionName = contributionName;
	}
	
	public RegisterRoomFragment(
			//String contributionName, 
			Room room, ViewController controller, int dimension) {
		this.room = room;
		this.controller = controller;
		this.dimension = dimension;
		roomSaved = null;
		//this.contributionName = contributionName;
	}
	
	public RegisterRoomFragment(Room room, ViewController controller) {
		this.room = room;
		this.controller = controller;
		roomSaved = null;
	}

	ViewRoomFragment viewRoomFragment;
	//FIXME CODIGO EXTENSO
	@Override
	public Control createUIControl(final Composite parent) {
		painter = new FormPagePainter();
		validator = new ValidatorUtils(new CustomControlDecoration());
		painter.addComposite(parent);
		painter.setDimension(dimension);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		//FIXME BORRAR CODIGO COMENTARIADO
		register = new Composite(parent, SWT.NONE);
		painter.addComposite(register);
		register.setData(RWT.CUSTOM_VARIANT, "gray_background");
		//register.setVisible(false);
		
		registerRoomLabel = new Label(register, SWT.NONE);
		painter.addHeader(registerRoomLabel);
		
		Label separator = new Label(register, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		
		lblNewLab = new Label(register, SWT.NONE);
		painter.addHeader(lblNewLab);

		nameLibraryLabel = new Label(register, SWT.NONE);
		painter.add(nameLibraryLabel);

		nameLibraryText = new Text(register, SWT.NONE);
		painter.add(nameLibraryText);
		controls.put("nameLibraryText", nameLibraryText);
		
		//validator.applyValidator(nameLibraryText, 5);
		
		
		validator.applyValidator(nameLibraryText, "nameLibraryAlphaNUmeric",
				DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);

		validator.applyValidator(nameLibraryText, "nameLibraryText",
				DecoratorType.REQUIRED_FIELD, true);
		
		
		surfaceLibraryLabel = new Label(register, SWT.NONE);
		painter.add(surfaceLibraryLabel);

		surfaceLibraryText = new Text(register, SWT.NONE);
		painter.add(surfaceLibraryText);
		controls.put("surfaceLibraryText", surfaceLibraryText);
		
		//validator.applyValidator(surfaceLibraryText, 5);
		
		validator.applyValidator(surfaceLibraryText, "surfaceLibraryDouble",
				DecoratorType.DOUBLE, true, 6);
				
		//painter.reset();
		hourLabel = new Label(register, SWT.NONE);
		painter.add(hourLabel);
		
		hourDateTime = new DateTime(register, SWT.BORDER | SWT.TIME | SWT.SHORT);
		painter.add(hourDateTime);
		
		painter.reset();

		Label space1 = new Label(register, SWT.NONE);
		painter.add(space1, Percent.W100);
		
		//painter.reset();
		
		separador = new Label(register, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separador);

		readingPostsLibraryLabel = new Label(register, SWT.NONE);
		painter.addHeader(readingPostsLibraryLabel);

		readingSeatsLibraryLabel = new Label(register, SWT.NONE);
		painter.add(readingSeatsLibraryLabel);

		readingSeatsLibraryText = new Text(register, SWT.NONE);
		painter.add(readingSeatsLibraryText);
		controls.put("readingSeatsLibraryText", readingSeatsLibraryText);
		validator.applyValidator(readingSeatsLibraryText, "readingSeatsLibraryText",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		userFormationLabel = new Label(register, SWT.NONE);
		painter.add(userFormationLabel);

		userFormationText = new Text(register, SWT.NONE);
		painter.add(userFormationText);
		controls.put("userFormationText", userFormationText);
		validator.applyValidator(userFormationText, "userFormationText",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		painter.reset();

		workingGroupLabel = new Label(register, SWT.NONE);
		painter.add(workingGroupLabel);

		workingGroupText = new Text(register, SWT.NONE);
		painter.add(workingGroupText);
		controls.put("workingGroupText", workingGroupText);
		validator.applyValidator(workingGroupText, "workingGroupText",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		Label space2 = new Label(register, SWT.NONE);
		painter.add(space2, Percent.W100);
		
		separador1 = new Label(register, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separador1);

		bookshelvesLabel = new Label(register, SWT.NONE);
		painter.addHeader(bookshelvesLabel);

		depositBookshelvesLabel = new Label(register, SWT.NONE);
		painter.add(depositBookshelvesLabel);

		depositBookshelvesText = new Text(register, SWT.NONE);
		painter.add(depositBookshelvesText);
		controls.put("depositBookshelvesText", depositBookshelvesText);
		validator.applyValidator(depositBookshelvesText, "depositBookshelvesText",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		openBookshelvesLabel = new Label(register, SWT.NONE);
		painter.add(openBookshelvesLabel);

		openBookshelvesText = new Text(register, SWT.NONE);
		painter.add(openBookshelvesText);
		controls.put("openBookshelvesText", openBookshelvesText);
		validator.applyValidator(openBookshelvesText, "openBookshelvesText",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		Label space3 = new Label(register, SWT.NONE);
		painter.add(space3, Percent.W100);

		separador2 = new Label(register, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separador2);

		equipmentLabel = new Label(register, SWT.NONE);
		painter.addHeader(equipmentLabel);

		userPcQuantityLabel = new Label(register, SWT.NONE);
		painter.add(userPcQuantityLabel);

		userPcQuantityText = new Text(register, SWT.NONE);
		controls.put("userPcQuantityText", userPcQuantityText);
		painter.add(userPcQuantityText);
		validator.applyValidator(userPcQuantityText, "userPcQuantityText",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		workersPcQuantityLabel = new Label(register, SWT.NONE);
		painter.add(workersPcQuantityLabel);

		workersPcQuantityText = new Text(register, SWT.NONE);
		controls.put("workersPcQuantityText", workersPcQuantityText);
		painter.add(workersPcQuantityText);
		validator.applyValidator(workersPcQuantityText, "workersPcQuantityText",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		painter.reset();

		readerPlayersQuantityLabel = new Label(register, SWT.NONE);
		painter.add(readerPlayersQuantityLabel);

		readerPlayersQuantityText = new Text(register, SWT.NONE);
		controls.put("readerPlayersQuantityText", readerPlayersQuantityText);
		painter.add(readerPlayersQuantityText);
		validator.applyValidator(readerPlayersQuantityText, "readerPlayersQuantityText",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		
		painter.reset();

		if (room == null) {

			cancelBtn = new Button(register, SWT.PUSH);
			painter.add(cancelBtn);
			
			
			cancelBtn.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;
				@Override
				public void widgetSelected(SelectionEvent e) {				
					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION),
							new DialogCallback() {
					private static final long serialVersionUID = 1L;
						@Override
						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
									registerRoom.notifyListeners(
											SWT.Dispose, new Event());										
							}						
						}					
					} );	
				}
			});
			/*
			cancelBtn.addSelectionListener(new SelectionAdapter() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					MessageDialogUtil.openConfirm(
							Display.getCurrent().getActiveShell(),
							MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA),
							new DialogCallback() {
								private static final long serialVersionUID = 1L;
								@Override
								public void dialogClosed(int returnCode) {
									if (returnCode == 0) {
										registerRoom.notifyListeners(
												SWT.Dispose, new Event());
									}
								}
							});
				}
			});
*/
			saveBtn = new Button(register, SWT.PUSH);
			painter.add(saveBtn);

			saveBtn.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 2615553092700551346L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					//boolean result = saveRoom();
					if (saveRoom()!=null &&  flag == false) {
						
						
						registerRoom.notifyListeners(SWT.Dispose, new Event());
						contributorService.selectContributor("viewRoom", roomSaved, registerRoom, contributorService);
						
						
						/*
                        viewRoomFragment = new ViewRoomFragment(
                        		//contributionName, 
                        		roomSaved, true, registerRoom, contributorService);
						show = (Composite) viewRoomFragment.createUIControl(parent);
						painter.addComposite(show);
						ajustRezise(register, 1);
						refresh(show);
						
						RetroalimentationUtils.showInformationMessage(viewRoomFragment.getMsg(), MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
						*/
						
						
					}else{
						/*
						String msgShow = cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NOT_SAVED_ITEM;
						RetroalimentationUtils
							.showErrorShellMessage(
									//register, 
									msgShow);
						*/
						if( flag == false){
							String msgShow = cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NOT_SAVED_ITEM;
							RetroalimentationUtils
								.showErrorShellMessage(
										//register, 
										msgShow);
						}
					}
				}
			});
			
			//saveBtn.getShell().setDefaultButton(saveBtn);
			
		}
		
		//ajustRezise(show, 0 );
		//refresh(show.getParent().getShell());
		
		LoadRoomData();
		l10n();
		return parent;

	}
	//FIXME BORRAR CODIGO COMENTARIADO

	public Composite getRegister() {
		return register;
	}
	public void LoadRoomData() {
		if (room != null) {
			nameLibraryText.setText(room.getRoomName());
			
			surfaceLibraryText.setText(Auxiliary.doubleValue(nameLibraryText, room.getSurface()));
			
			
			java.util.Date startHour = new java.util.Date(room.getHour().getTime());
			int hourStart = Integer.parseInt(new SimpleDateFormat("k").format(startHour));
			int minutesStart = Integer.parseInt(new SimpleDateFormat("m").format(startHour));
			hourDateTime.setHours(hourStart);
			hourDateTime.setMinutes(minutesStart);
			
			//java.util.Date endHour = new java.util.Date(schedule.getEndhour().getTime());
			//int hourEnd = Integer.parseInt(new SimpleDateFormat("k").format(endHour));
			//int minutesEnd = Integer.parseInt(new SimpleDateFormat("m").format(endHour));
			//toTimeDateTime.setHours(hourEnd);
			//toTimeDateTime.setMinutes(minutesEnd);
			
			//validator.applyValidator(surfaceLibraryText, "surfaceLibraryDouble",
				//	DecoratorType.DOUBLE, true);
			
			//getValidator().decorationFactory.
			
			//getValidator().decorationFactory.getControlByKey("surfaceLibraryDouble");
			
			//Object aaa = validator.decorationFactory.getControlByKey("aaa");
			//if(){
				
			//}
			readingSeatsLibraryText
					.setText(room.getAvailableReadingSeats() == null ? ""
							: room.getAvailableReadingSeats().toString());
			userFormationText
					.setText(room.getAvailableUserFormationSeats() == null ? ""
							: room.getAvailableUserFormationSeats().toString());
			workingGroupText
					.setText(room.getAvailableWorkGroupSeats() == null ? ""
							: room.getAvailableWorkGroupSeats().toString());
			openBookshelvesText.setText(room.getOpenBookShelves() == null ? ""
					: room.getOpenBookShelves().toString());
			depositBookshelvesText
					.setText(room.getDepositBookShelves() == null ? "" : room
							.getDepositBookShelves().toString());
			userPcQuantityText.setText(room.getUserPcQuantity() == null ? ""
					: room.getUserPcQuantity().toString());
			workersPcQuantityText
					.setText(room.getWorkerPcQuantity() == null ? "" : room
							.getWorkerPcQuantity().toString());
			readerPlayersQuantityText.setText(room
					.getDiverseReaderPlayerQuantity() == null ? "" : room
					.getDiverseReaderPlayerQuantity().toString());
			
			
		}
	}
	//FIXME METODO COMPLEJO
	public Room saveRoom() {
		flag = false;
		if (getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
			RetroalimentationUtils.showErrorShellMessage(
					//register, 
					MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
			flag = true;
		} else {
		if (getValidator().decorationFactory.AllControlDecorationsHide()) {
			String roomName = nameLibraryText.getText().replaceAll(" +", " ").trim();
			Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");	
			
			Room exist = ((LibraryViewController) controller)
						.getAllManagementLibraryViewController()
						.getRoomService().findRoomByName(library.getLibraryID(), roomName);

				//FIXME BORRAR CODIGO COMENTARIADO
				if(exist==null || ( room!=null && room.getRoomID()==exist.getRoomID() ) ){
					//boolean flag = false;
					if (room == null) {
						room = new Room();
						//flag = true;
					}
					if (surfaceLibraryText.getText().length() > 0) {
						String surfaceLibrary = surfaceLibraryText.getText().replace(",", ".");
						room.setSurface(Double.parseDouble(surfaceLibrary));
					}else{
						room.setSurface(null);
					}
					if (readingSeatsLibraryText.getText().length() > 0) {
						int readingSeatsLibrary = Integer
								.parseInt(readingSeatsLibraryText.getText());
						room.setAvailableReadingSeats(readingSeatsLibrary);
					}else{
						room.setAvailableReadingSeats(null);
					}
					if (userFormationText.getText().length() > 0) {
						int userFormation = Integer.parseInt(userFormationText
								.getText());
						room.setAvailableUserFormationSeats(userFormation);
					}else{
						room.setAvailableUserFormationSeats(null);
					}
					if (workingGroupText.getText().length() > 0) {
						int workingGroup = Integer.parseInt(workingGroupText.getText());
						room.setAvailableWorkGroupSeats(workingGroup);
					}else{
						room.setAvailableWorkGroupSeats(null);
					}
					if (openBookshelvesText.getText().length() > 0) {
						int openBookshelves = Integer.parseInt(openBookshelvesText
								.getText());
						room.setOpenBookShelves(openBookshelves);
					}else{
						room.setOpenBookShelves(null);
					}
					if (depositBookshelvesText.getText().length() > 0) {
						int depositBookshelves = Integer
								.parseInt(depositBookshelvesText.getText());
						room.setDepositBookShelves(depositBookshelves);
					}else{
						room.setDepositBookShelves(null);
					}
					if (userPcQuantityText.getText().length() > 0) {
						int userPcQuantity = Integer.parseInt(userPcQuantityText
								.getText());
						room.setUserPcQuantity(userPcQuantity);
					}else{
						room.setUserPcQuantity(null);
					}
					if (workersPcQuantityText.getText().length() > 0) {
						int workersPcQuantity = Integer.parseInt(workersPcQuantityText
								.getText());
						room.setWorkerPcQuantity(workersPcQuantity);
					}else{
						room.setWorkerPcQuantity(null);
					}
					if (readerPlayersQuantityText.getText().length() > 0) {
						int readerPlayersQuantity = Integer
								.parseInt(readerPlayersQuantityText.getText());
						room.setDiverseReaderPlayerQuantity(readerPlayersQuantity);
					}else{
						room.setDiverseReaderPlayerQuantity(null);
					}
					
                    @SuppressWarnings("deprecation")
					Time hourTime = new Time(hourDateTime.getHours(), hourDateTime.getMinutes(), 0);
                    Timestamp hourTimeTimestamp = new Timestamp(hourTime.getTime());
                    
					room.setLibrary(library);
					room.setRoomName(roomName);
					room.setHour(hourTimeTimestamp);

					roomSaved = ((LibraryViewController) controller).saveRoom(room);

					//List<Room> allRooms = ((LibraryViewController) controller).getAllManagementLibraryViewController().getRoomService().findAll(library
						//	.getLibraryID());
					
					
					return roomSaved;
				}else{
					RetroalimentationUtils.showErrorShellMessage(
							//register, 
							MessageUtil
							.unescape(AbosMessages
									.get().ELEMENT_EXIST));
					flag = true;
				}
			
		} else {
			RetroalimentationUtils.showErrorShellMessage(
					//register, 
					MessageUtil
					.unescape(cu.uci.abos.core.l10n.AbosMessages
							.get().MSG_ERROR_INCORRECT_DATA));
			flag = true;
		}
	}
		return null;
	}

	
	
	
	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		
		if (room == null) {
			registerRoomLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_ROOM));
		}else{
			registerRoomLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_UPDATE_ROOM));
		}
		
		lblNewLab
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_GENERAL_DATA));
		nameLibraryLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		surfaceLibraryLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SURFACE));
		hourLabel.setText(MessageUtil.unescape(AbosMessages.get().REPAYMENT_SCHEDULE));
		
		readingPostsLibraryLabel.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_READING_SETS));
		readingSeatsLibraryLabel.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_QUANTITY));
		userFormationLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_FORMATION));

		workingGroupLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_WORKING_GROUP));
		bookshelvesLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_BOOKSHELVES));
		depositBookshelvesLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DEPOSIT_BOOKSHELVES));

		openBookshelvesLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OPEN_BOOKSHELVES));
		equipmentLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EQUIPMENT));
		userPcQuantityLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_PC_QUANTITY));
		workersPcQuantityLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_WORKERS_PC_QUANTITY));
		readerPlayersQuantityLabel.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_READER_PLAYERS_QUANTITY));

		if (room == null) {
			saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
			cancelBtn
					.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		}
		if(viewRoomFragment!=null){
		viewRoomFragment.l10n();
		}
		lblNewLab.getParent().getParent().layout(true, true);
		lblNewLab.getParent().getParent().redraw();
		lblNewLab.getParent().getParent().update();
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public Map<String, Control> getControls() {
		return controls;
	}

	public void setControls(Map<String, Control> controls) {
		this.controls = controls;
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
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

}
