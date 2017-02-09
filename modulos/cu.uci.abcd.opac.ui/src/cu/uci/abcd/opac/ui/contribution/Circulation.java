package cu.uci.abcd.opac.ui.contribution;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Spinner;

import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.auxiliary.Auxiliary;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abcd.opac.ui.listener.ViewLogDataListener;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;

public class Circulation extends ContributorPage {

	private Map<String, Control> controls;
	private ServiceProvider serviceProvider;
	public Composite result;
	private User user;
	private RecordIsis record;
	private LoanUser loanUser;
	private List<LoanObject> loanObjects;
	private Label cantReservationsLbl;
	private CirculationRule circulationRule;
	private String controlNumber;

	private List<Schedule> libraryHorary;
	private boolean dayYes = false;
	private boolean hourYes = false;
	private Nomenclator dayOfWeek;

	private Auxiliary auxiliary;

	private Button reservationBtn;
	private Button cancelBtn;

	private Reservation reservation;
	private List<LoanObject> toReserved = new ArrayList<LoanObject>();

	private Label subLb;
	private Link titleContentLk;
	private Link horSeparatorLk;
	private Label bookAutorLabel;
	private Label bookAutorContent;
	private Label currentAccountLabel;
	private Label currentAccountContent;
	private Label dateTimeLabel;
	private Label hourTimeLabel;
	private Label reservationTypeLabel;
	private Button externalReservation;
	private DateTime toDateTime;
	private DateTime toHour;
	private Button internalReservation;
	private Spinner cantReservationSpn;

	// FIXME FALTAN COMENTARIOS DE INTERFACE
	// FIXME REPETICION DE CODIGO

	public Circulation(ServiceProvider service) {
		this.controls = new HashMap<String, Control>();
		this.serviceProvider = service;
	}

	@Override
	public Control createUIControl(Composite parent) {

		try {
			auxiliary = new Auxiliary(controller);
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
			e.printStackTrace();
		}

		libraryHorary = ((AllManagementOpacViewController) controller).findHorarybyLibrary(user.getLibrary().getLibraryID());

		result = new Composite(parent, 0);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		result.setLayout(new FormLayout());

		FormDatas.attach(result).atLeft(0).atRight(0).atTop(0).atBottom(0);

		subLb = new Label(result, 0);
		subLb.setFont(new Font(parent.getDisplay(), "Arial", 16, SWT.BOLD));
		FormDatas.attach(subLb).atTop(15).atLeft(30);

		titleContentLk = new Link(result, SWT.WRAP);
		titleContentLk.setText("<a>" + loanObjects.get(0).getTitle() + "</a>");
		titleContentLk.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(titleContentLk).atTop(13).atLeftTo(subLb, 5).atRight();

		horSeparatorLk = new Link(result, SWT.NORMAL);
		horSeparatorLk
				.setText("___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
		horSeparatorLk.setFont(new Font(parent.getDisplay(), "Arial", 4, SWT.NONE));
		FormDatas.attach(horSeparatorLk).atTopTo(titleContentLk, -7).atLeft(28);

		Label pointLb = new Label(result, SWT.NORMAL);
		FormDatas.attach(pointLb).atLeft(160);

		bookAutorLabel = new Label(result, SWT.NORMAL);
		bookAutorLabel.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(bookAutorLabel).atTopTo(horSeparatorLk, 10).atRightTo(pointLb);

		bookAutorContent = new Label(result, SWT.NORMAL);
		bookAutorContent.setText(loanObjects.get(0).getAuthor());
		FormDatas.attach(bookAutorContent).atTopTo(horSeparatorLk, 10).atLeftTo(pointLb, 5);

		currentAccountLabel = new Label(result, SWT.NORMAL);
		currentAccountLabel.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(currentAccountLabel).atTopTo(bookAutorLabel, 5).atRightTo(pointLb);

		currentAccountContent = new Label(result, SWT.NORMAL);
		currentAccountContent.setText("" + user.getPerson().getFullName());
		FormDatas.attach(currentAccountContent).atTopTo(bookAutorLabel, 6).atLeftTo(pointLb, 5);

		dateTimeLabel = new Label(result, SWT.NORMAL);
		dateTimeLabel.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(dateTimeLabel).atTopTo(currentAccountLabel, 15).atRightTo(pointLb);

		toDateTime = new DateTime(result, SWT.BORDER | SWT.DROP_DOWN);
		FormDatas.attach(toDateTime).atTopTo(currentAccountLabel, 8).atLeftTo(pointLb, 5).withWidth(223).withHeight(25);
		this.controls.put("toDateTime", toDateTime);

		hourTimeLabel = new Label(result, SWT.NORMAL);
		hourTimeLabel.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(hourTimeLabel).atTopTo(dateTimeLabel, 15).atRightTo(pointLb);

		toHour = new DateTime(result, SWT.BORDER | SWT.TIME);
		FormDatas.attach(toHour).atTopTo(toDateTime, 8).atLeftTo(pointLb, 5).withWidth(223).withHeight(25);
		this.controls.put("toHour", toHour);

		reservationTypeLabel = new Label(result, SWT.NORMAL);
		reservationTypeLabel.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(reservationTypeLabel).atTopTo(toHour, 15).atRightTo(pointLb);

		externalReservation = new Button(result, SWT.RADIO);
		externalReservation.setSelection(true);
		FormDatas.attach(externalReservation).atTopTo(toHour, 10).atLeftTo(pointLb, 10);

		internalReservation = new Button(result, SWT.RADIO);
		FormDatas.attach(internalReservation).atTopTo(toHour, 10).atLeftTo(externalReservation, 10);

		cantReservationsLbl = new Label(result, 0);
		cantReservationsLbl.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(cantReservationsLbl).atTopTo(reservationTypeLabel, 15).atRightTo(pointLb);

		cantReservationSpn = new Spinner(result, SWT.BORDER);
		cantReservationSpn.setMinimum(1);
		cantReservationSpn.setMaximum(10000);
		FormDatas.attach(cantReservationSpn).atTopTo(reservationTypeLabel, 10).atLeftTo(pointLb, 10).withHeight(23).withWidth(70);

		reservationBtn = new Button(result, 0);
		FormDatas.attach(reservationBtn).atTopTo(cantReservationSpn, 10).atLeft(130);

		cancelBtn = new Button(result, 0);
		FormDatas.attach(cancelBtn).atTopTo(cantReservationSpn, 10).atLeftTo(reservationBtn, 5);

		titleContentLk.addListener(SWT.Selection, new ViewLogDataListener(serviceProvider, record));

		try {
			controlNumber = record.getRecord().getField(1).getStringFieldValue();
		} catch (Exception e) {
			e.printStackTrace();
		}

		reservationBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				synchronized (this) {
					try {
						checkDay();
						if (dayYes) {
							checkHour();
							if (hourYes) {

								toReserved = new ArrayList<LoanObject>();
								loanObjects = ((AllManagementOpacViewController) controller).findLoanObjectsByControlNumberAndLibrary(controlNumber, user.getLibrary().getLibraryID());
								circulationRule = ((AllManagementOpacViewController) controller).findCirculationRule(((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.CIRCULATION_RULE_STATE_ACTIVE), loanUser.getLoanUserType(), loanObjects.get(0).getRecordType(), user
										.getLibrary().getLibraryID());

								int quantity = Integer.parseInt(cantReservationSpn.getText().replaceAll(" +", " ").trim());

								if (quantity <= circulationRule.getQuantityOfLoanAllowed()) {

									reservation = new Reservation();
									reservation.setLoanUser(loanUser);

									if (internalReservation.getSelection())
										reservation.setReservationType(((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.RESERVATION_TYPE_INTERN));
									else
										reservation.setReservationType(((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.RESERVATION_TYPE_EXTERN));

									reservation.setCreationDate(new Date(new java.util.Date().getTime()));
									reservation.setTitle(loanObjects.get(0).getTitle());
									reservation.setObjecttype(loanObjects.get(0).getRecordType());

									int fromYear = toDateTime.getYear() - 1900;
									int fromMonth = toDateTime.getMonth();
									int fromDay = toDateTime.getDay();

									Date temp = new Date(fromYear, fromMonth, fromDay);
									reservation.setReservationDate(temp);

									java.util.Date tempHour = new java.util.Date();
									tempHour.setHours(toHour.getHours());
									tempHour.setMinutes(00);
									tempHour.setSeconds(00);
									reservation.setReservationHour(tempHour);

									int daysPickUp = circulationRule.getQuantityOfDayToPickUpTheMaterial();
									Date cancellationDate = auxiliary.sumar_dias(temp, daysPickUp, user.getLibrary().getLibraryID());
									int daysLoan = circulationRule.getQuantityOfLoanAllowed();
									Date reservationEndDate = auxiliary.sumar_dias(cancellationDate, daysLoan, user.getLibrary().getLibraryID());

									for (int i = 0; i < loanObjects.size(); i++)
										if (((AllManagementOpacViewController) controller).isAvailableByDate(temp, reservationEndDate, loanObjects.get(i).getLoanObjectID())) {
											toReserved.add(loanObjects.get(i));
											if (toReserved.size() == quantity)
												break;
										}

									reservation.setReservationList(toReserved);
									reservation.setCancellationDate(cancellationDate);
									reservation.setReservationEndDate(reservationEndDate);
									reservation.setState(((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.RESERVATION_STATE_PENDING));

									DialogCallback callbackOne = new DialogCallback() {
										private static final long serialVersionUID = 1L;

										public void dialogClosed(int returnCode) {
											if (returnCode == 0) {

												((AllManagementOpacViewController) controller).addReservation(reservation);
												showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_INF_MATERIAL_RESERVED));

											}
										}
									};

									DialogCallback callbackAll = new DialogCallback() {
										private static final long serialVersionUID = 1L;

										public void dialogClosed(int returnCode) {
											if (returnCode == 0) {

												((AllManagementOpacViewController) controller).addReservation(reservation);
												showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_INF_MATERIALS_RESERVED));
											}
										}
									};

									if (toReserved.isEmpty()) {
										if (quantity == 1)
											showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MATERIAL_ALREADY_RESERVED));
										else
											showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MATERIALS_ALREADY_RESERVED));
									} else if (!toReserved.isEmpty()) {
										if (toReserved.size() == 1) {
											if (quantity == toReserved.size()) {
												((AllManagementOpacViewController) controller).addReservation(reservation);
												showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_INF_MATERIAL_RESERVED));
											} else if (quantity > 1)
												MessageDialogUtil.openQuestion(Display.getCurrent().getActiveShell(), "Error",
														MessageUtil.unescape(AbosMessages.get().MSG_QUESTION_JUST_ONE_RESERVED_PART_1) + "\n" + "\n" + MessageUtil.unescape(AbosMessages.get().MSG_QUESTION_JUST_ONE_RESERVED_PART_2), callbackOne);
										} else if (quantity == toReserved.size()) {
											((AllManagementOpacViewController) controller).addReservation(reservation);
											showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_INF_MATERIALS_RESERVED));
										} else if (quantity > 1)
											MessageDialogUtil.openQuestion(
													Display.getCurrent().getActiveShell(),
													"Error",
													MessageUtil.unescape(AbosMessages.get().MSG_QUESTION_QUANTITY_RESERVED_PART_1) + " " + toReserved.size() + " " + MessageUtil.unescape(AbosMessages.get().MSG_QUESTION_QUANTITY_RESERVED_PART_2) + "\n" + "\n"
															+ MessageUtil.unescape(AbosMessages.get().MSG_QUESTION_QUANTITY_RESERVED_PART_3), callbackAll);
									}
								} else
									showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_NOT_RESERVED_QUANTITY));
							} else {
								toHour.setFocus();
								showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_ROOM_NOT_WORK_IN_THE_SCHEDULE));
							}
						} else {
							toDateTime.setFocus();
							showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_ROOM_NOT_WORK_THIS_DAY));

						}

					} catch (Exception e1) {
						showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_NOT_RESERVED));
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		cancelBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				Circulation.this.notifyListeners(SWT.Dispose, new Event());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		setMinutesAndHour();
		l10n();
		return result;
	}

	private void setMinutesAndHour() {
		toHour.setMinutes(00);
		toHour.setSeconds(00);
		refresh();
	}

	@Override
	public String getID() {
		return "CirculationID";
	}

	@Override
	public void l10n() {

		reservationBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CIRCULATION_RESERVE));
		cancelBtn.setText(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().BUTTON_CANCEL));
		subLb.setText(MessageUtil.unescape(AbosMessages.get().MAKE_RESERVATION));
		bookAutorLabel.setText(MessageUtil.unescape(AbosMessages.get().AUTHOR));
		currentAccountLabel.setText(MessageUtil.unescape(AbosMessages.get().FOR));
		dateTimeLabel.setText(MessageUtil.unescape(AbosMessages.get().RESERVATION_DATE));
		hourTimeLabel.setText(MessageUtil.unescape(AbosMessages.get().RESERVATION_TIME));
		reservationTypeLabel.setText(MessageUtil.unescape(AbosMessages.get().RESERVATION_KIND));
		externalReservation.setText(MessageUtil.unescape(AbosMessages.get().EXTERNAL));
		internalReservation.setText(MessageUtil.unescape(AbosMessages.get().INTERNAL));
		cantReservationsLbl.setText(MessageUtil.unescape(AbosMessages.get().RESERVATION_NUMBER));

		refresh();

	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_CIRCULATION);
	}

	@SuppressWarnings("deprecation")
	private void checkDay() {

		Date tempDate = new Date(toDateTime.getYear() - 1900, toDateTime.getMonth(), toDateTime.getDay());
		Date today = new Date(new java.util.Date().getTime());

		if (tempDate.after(today) || (tempDate.getYear() == today.getYear() && tempDate.getMonth() == today.getMonth() && tempDate.getDate() == today.getDate())) {

			switch (tempDate.getDay()) {
			case 0:
				dayOfWeek = ((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.DAY_WEEK_SUNDAY);
				break;
			case 1:
				dayOfWeek = ((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.DAY_WEEK_MONDAY);
				break;
			case 2:
				dayOfWeek = ((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.DAY_WEEK_TUESDAY);
				break;
			case 3:
				dayOfWeek = ((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.DAY_WEEK_WEDNESDAY);
				break;
			case 4:
				dayOfWeek = ((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.DAY_WEEK_THURSDAY);
				break;
			case 5:
				dayOfWeek = ((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.DAY_WEEK_FRIDAY);
				break;
			default:
				dayOfWeek = ((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.DAY_WEEK_SATURDAY);
				break;
			}

			for (int i = 0; i < libraryHorary.size(); i++)
				if (dayOfWeek.getNomenclatorID().equals(libraryHorary.get(i).getDayOfWeek().getNomenclatorID())) {
					dayYes = true;
					break;
				}
		}
	}

	@SuppressWarnings("deprecation")
	private void checkHour() {

		if (dayYes) {
			for (int i = 0; i < libraryHorary.size(); i++)
				if (dayOfWeek.getNomenclatorID().equals(libraryHorary.get(i).getDayOfWeek().getNomenclatorID()))
					if (toHour.getHours() >= libraryHorary.get(i).getStartHour().getHours() && toHour.getHours() <= libraryHorary.get(i).getEndhour().getHours()) {
						hourYes = true;
						break;
					}
		}
	}

	public List<LoanObject> getLoanObject() {
		return loanObjects;
	}

	public void setLoanObject(List<LoanObject> loanObject) {
		this.loanObjects = loanObject;
	}

	public LoanUser getLoanUser() {
		return loanUser;
	}

	public void setLoanUser(LoanUser loanUser) {
		this.loanUser = loanUser;
	}

	public RecordIsis getRecord() {
		return record;
	}

	public void setRecord(RecordIsis record) {
		this.record = record;
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

}
