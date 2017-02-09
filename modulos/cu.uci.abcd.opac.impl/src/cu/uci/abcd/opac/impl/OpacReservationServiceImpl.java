package cu.uci.abcd.opac.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.circulation.ReservationDAO;
import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.common.StatementExecutorDAO;
import cu.uci.abcd.dao.management.library.CalendarDAO;
import cu.uci.abcd.dao.management.library.CirculationRuleDAO;
import cu.uci.abcd.dao.management.library.ScheduleDAO;
import cu.uci.abcd.dao.specification.OpacSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Calendar;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abcd.opac.IOpacReservationService;

public class OpacReservationServiceImpl implements IOpacReservationService {

	private ReservationDAO reservationDAO;
	private CirculationRuleDAO circulationRuleDAO;
	private NomenclatorDAO nomenclatorDAO;
	private StatementExecutorDAO executorDAO;
	private CalendarDAO calendarDAO;
	private ScheduleDAO scheduleDAO;

	@Override
	public Reservation addReservation(Reservation reservation) {
		try {
			return reservationDAO.save(reservation);
		} catch (Exception e) {
			return new Reservation();
		}
	}     

	@Override
	public void deleteReservation(Long idReservation) {
		reservationDAO.delete(idReservation);
	}

	@Override
	public Reservation findResertion(Long idReservation) {
		return reservationDAO.findOne(idReservation);
	}

	@Override
	public Reservation updateReservation(Long arg0) {
		return null;
	}

	@Override
	public Page<Reservation> findCurrentReservation(Long idLoanUser, int page, int size, int direction, String orderByString) {

		return reservationDAO.findAll(OpacSpecification.searchReservation(idLoanUser), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Reservation> findHistoricalReservationByUser(Long idLoanUser, int page, int size, int direction, String orderByString) {

		return reservationDAO.findAll(OpacSpecification.searchHistoricalReservation(idLoanUser), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Reservation> findHistoricalReservationByParameters(Long idLoanUser, String titleBook, String autorBook, Long state, int page, int size, int direction, String orderByString) {

		return reservationDAO.findAll(OpacSpecification.searchHistoricalReservationByParameters(idLoanUser, titleBook, autorBook, state), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public int CountReservationPendingByUser(Long idLoanUser) {

		List<Reservation> temp = (List<Reservation>) reservationDAO.findAll();

		for (int i = 0; i < temp.size(); i++)
			if (temp.get(i).getState().getNomenclatorID() != Nomenclator.RESERVATION_STATE_PENDING) {
				temp.remove(i);
				i--;
			}

		return temp.size();
	}

	public void bind(ReservationDAO reservationDAO, Map<String, Object> properties) {
		this.reservationDAO = reservationDAO;
		System.out.println("servicio registrado");
	}

	@Override
	public CirculationRule findCirculationRule(Nomenclator circulationRuleState, Nomenclator loanUserType, Nomenclator recordType, Long actorID) {

		CirculationRule sCirculationRule = circulationRuleDAO.findDistinctCirculationRuleBycirculationRuleStateAndLoanUserTypeAndRecordTypeAndLibrary_LibraryID(circulationRuleState, loanUserType, recordType, actorID);
		/*
		 * List<CirculationRule> sCirculationRule = (List<CirculationRule>)
		 * circulationRuleDAO.findAll();
		 * 
		 * for (int i = 0; i < sCirculationRule.size(); i++) if
		 * (sCirculationRule.get(i).getCirculationRuleState().getNomenclatorID()
		 * == circulationRuleState.getNomenclatorID() &&
		 * sCirculationRule.get(i).getLoanUserType().getNomenclatorID() ==
		 * loanUserType.getNomenclatorID() &&
		 * sCirculationRule.get(i).getRecordType().getNomenclatorID() ==
		 * recordType.getNomenclatorID() &&
		 * sCirculationRule.get(i).getLibrary().getLibraryID() == actorID)
		 * return sCirculationRule.get(i);
		 */
		return sCirculationRule;
	}

	public void bindCirculationRuleDao(CirculationRuleDAO circulationRuleDAO, Map<?, ?> properties) {
		this.circulationRuleDAO = circulationRuleDAO;
	}

	@Override
	public List<Calendar> findCalendar(Long libraryID) {
		return calendarDAO.findDistinctCalendarByLibrary_LibraryID(libraryID);
	}

	@Override
	public List<Schedule> findHorarybyLibrary(Long libraryID) {
		return scheduleDAO.findDistinctScheduleByLibrary_LibraryID(libraryID);
	}

	public void bindCalendarDao(CalendarDAO calendarDAO, Map<?, ?> properties) {
		this.calendarDAO = calendarDAO;
	}

	public void bindScheduleDao(ScheduleDAO scheduleDAO, Map<?, ?> properties) {
		this.scheduleDAO = scheduleDAO;
	}

	public void bindNomenclatorDao(NomenclatorDAO nomenclatorDAO, Map<?, ?> properties) {
		this.nomenclatorDAO = nomenclatorDAO;
	}

	@Override
	public boolean isAvailableByDate(Date startDate, Date endDate, Long loanObjectId) {

		List<Reservation> reservations = (List<Reservation>) reservationDAO.findAll();

		for (Reservation reservation : reservations)
			for (int i = 0; i < reservation.getReservationList().size(); i++) {
				if (reservation.getReservationList().get(i).getLoanObjectID() == loanObjectId && reservation.getState().getNomenclatorID() != Nomenclator.RESERVATION_STATE_CANCELLED)
					if ((startDate.after(reservation.getReservationDate()) && startDate.before(reservation.getReservationEndDate())) || startDate.equals(reservation.getReservationDate())
							|| ((endDate.after(reservation.getReservationDate()) && endDate.before(reservation.getReservationEndDate()) || endDate.equals(reservation.getReservationEndDate()))))
						return false;
			}

		return true;
	}

	@Override
	public boolean isAvailableByDate(Date startDate, Date endDate, Long loanObjectId, Long loanUserId) {
		List<Reservation> reservations = (List<Reservation>) reservationDAO.findAll();

		for (Reservation reservation : reservations)
			for (int i = 0; i < reservation.getReservationList().size(); i++) {
				if (reservation.getReservationList().get(i).getLoanObjectID() == loanObjectId && reservation.getState().getNomenclatorID() != Nomenclator.RESERVATION_STATE_CANCELLED && reservation.getLoanUser().getId() != loanUserId)
					if ((startDate.after(reservation.getReservationDate()) && startDate.before(reservation.getReservationEndDate())) || startDate.equals(reservation.getReservationDate())
							|| ((endDate.after(reservation.getReservationDate()) && endDate.before(reservation.getReservationEndDate()) || endDate.equals(reservation.getReservationEndDate()))))
						return false;
			}

		return true;
	}

	@Override
	public void updateStateReservationEnd() {

		java.util.Date fecha = new java.util.Date();
		Nomenclator reservationState = nomenclatorDAO.findOne(Nomenclator.RESERVATION_STATE_PENDING);
		Nomenclator reservationStateCancel = nomenclatorDAO.findOne(Nomenclator.RESERVATION_STATE_CANCELLED);

		executorDAO.executeUpdate("update abcdn.dreservation set state = ?1 where cancellationdate < ?2 and state = ?3", new Object[] { new Long(reservationStateCancel.getNomenclatorID()), new Date(fecha.getTime()), new Long(reservationState.getNomenclatorID()) });

	}

}
