package cu.uci.abcd.opac.ui.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.auxiliary.Auxiliary;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ReservationUpdateArea extends BaseEditableArea {

	private ViewController controller;
	private Map<String, Control> controls;
	private Auxiliary auxiliary;
	private CRUDTreeTable table;

	private Group updateReservation;

	private List<Schedule> libraryHorary;
	private CirculationRule circulationRule;
	private boolean dayYes = false;
	private boolean hourYes = false;
	private boolean reservationAvailable = true;
	private Nomenclator dayOfWeek;

	private Label dateTimeLabel;
	private Label hourTimeLabel;

	private DateTime toDateTime;
	private DateTime toHour;
	private Reservation reservation;

	private Button saveBtn;

	public ReservationUpdateArea(ViewController controller, CRUDTreeTable table) {
		this.controls = new HashMap<String, Control>();
		this.controller = controller;
		this.table = table;

	}

	@SuppressWarnings("deprecation")
	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {

		reservation = (Reservation) entity.getRow();
		auxiliary = new Auxiliary(controller);
		libraryHorary = ((AllManagementOpacViewController) controller).findHorarybyLibrary(reservation.getLoanUser().getLibrary().getLibraryID());

		updateReservation = new Group(parent, SWT.NONE);
		updateReservation.setBackground(parent.getBackground());
		updateReservation.setLayout(new FormLayout());

		FormDatas.attach(updateReservation).atTop(0).atLeft(0).atRight(0).atBottom();

		Label temp = new Label(updateReservation, SWT.NORMAL);
		FormDatas.attach(temp).atLeftTo(updateReservation, 150);

		dateTimeLabel = new Label(updateReservation, SWT.NORMAL);
		FormDatas.attach(dateTimeLabel).atTopTo(updateReservation, 8).atRightTo(temp);

		toDateTime = new DateTime(updateReservation, SWT.BORDER | SWT.DROP_DOWN);
		toDateTime.setYear(reservation.getReservationDate().getYear() + 1900);
		toDateTime.setMonth(reservation.getReservationDate().getMonth());
		toDateTime.setDay(reservation.getReservationDate().getDate());
		FormDatas.attach(toDateTime).atTopTo(updateReservation, 4).atLeftTo(temp, 5).withWidth(223).withHeight(25);

		hourTimeLabel = new Label(updateReservation, SWT.NORMAL);
		FormDatas.attach(hourTimeLabel).atTopTo(dateTimeLabel, 15).atRightTo(temp);

		toHour = new DateTime(updateReservation, SWT.BORDER | SWT.TIME);
		toHour.setHours(reservation.getReservationHour().getHours());
		FormDatas.attach(toHour).atTopTo(toDateTime, 8).atLeftTo(temp, 5).withWidth(223).withHeight(25);
		this.controls.put("toHour", toHour);

		l10n();
		setMinutesAndHour();
		return parent;
	}

	@Override
	public Composite createButtons(Composite parent, final IGridViewEntity entity, final IVisualEntityManager manager) {

		saveBtn = new Button(parent, SWT.PUSH);

		saveBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent event) {
				synchronized (this) {
					try {

						checkDay();
						if (dayYes) {
							checkHour();
							if (hourYes) {

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

								circulationRule = ((AllManagementOpacViewController) controller).findCirculationRule(((AllManagementOpacViewController) controller).findNomenclator(Nomenclator.CIRCULATION_RULE_STATE_ACTIVE), reservation.getLoanUser().getLoanUserType(), reservation
										.getReservationList().get(0).getRecordType(), reservation.getLoanUser().getLibrary().getLibraryID());

								int daysPickUp = circulationRule.getQuantityOfDayToPickUpTheMaterial();
								Date cancellationDate = auxiliary.sumar_dias(temp, daysPickUp, reservation.getLoanUser().getLibrary().getLibraryID());
								int daysLoan = circulationRule.getQuantityOfLoanAllowed();
								Date reservationEndDate = auxiliary.sumar_dias(cancellationDate, daysLoan, reservation.getLoanUser().getLibrary().getLibraryID());

								for (int i = 0; i < reservation.getReservationList().size(); i++)
									if (!((AllManagementOpacViewController) controller).isAvailableByDate(temp, reservationEndDate, reservation.getReservationList().get(i).getLoanObjectID(), reservation.getLoanUser().getId())) {
										reservationAvailable = false;
										break;
									}

								if (reservationAvailable) {

									try {

										reservation.setCancellationDate(cancellationDate);
										reservation.setReservationEndDate(reservationEndDate);

										((AllManagementOpacViewController) controller).addReservation(reservation);

										BaseGridViewEntity<Reservation> reservationGridViewEntity = new BaseGridViewEntity<Reservation>(reservation);
										manager.add(reservationGridViewEntity);
										table.destroyEditableArea();
										showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);

										table.getPaginator().goToFirstPage();
										table.refresh();

									} catch (Exception e) {
										e.printStackTrace();
									}

								} else {
									showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_NOT_RESERVED));
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		l10n();
		setMinutesAndHour();
		return parent;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	public void dispose() {
		for (Control control : controls.values()) {
			control.dispose();
		}
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public void l10n() {

		updateReservation.setText(MessageUtil.unescape(AbosMessages.get().TABLE_EDIT_RESERVATION));
		dateTimeLabel.setText(MessageUtil.unescape(AbosMessages.get().RESERVATION_DATE));
		hourTimeLabel.setText(MessageUtil.unescape(AbosMessages.get().RESERVATION_TIME));

		if (saveBtn != null && !saveBtn.isDisposed())
			saveBtn.setText(MessageUtil.unescape(AbosMessages.get().ACCEPT));

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

			if (!dayYes) {
				showErrorMessage("Esta Sala no trabaja el dia para el cual usted desea realizar la reservación");
			}

		} else {
			showErrorMessage("No puede realizar reservaciones para ese día");

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

			if (!hourYes)
				showErrorMessage("Esta Sala no trabaja en el horario para el cual usted desea realizar la reservación");
		}
	}

	private void setMinutesAndHour() {
		toHour.setMinutes(00);
		toHour.setSeconds(00);
	}
}