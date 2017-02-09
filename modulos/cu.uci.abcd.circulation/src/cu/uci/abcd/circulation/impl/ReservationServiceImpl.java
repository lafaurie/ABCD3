package cu.uci.abcd.circulation.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.IReservationService;
import cu.uci.abcd.dao.circulation.ReservationDAO;
import cu.uci.abcd.dao.specification.CirculationSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;

/**
 * 
 * @author Abelito
 * 
 */
public class ReservationServiceImpl implements IReservationService {

	private ReservationDAO reservationDAO;

	@Override
	public Reservation addReservation(Reservation reservation) {
		return reservationDAO.save(reservation);
	}

	@Override
	public void deleteReservation(Long idReservation) {
		reservationDAO.delete(idReservation);
	}

	@Override
	public Reservation findOneReservation(Long idReservation) {
		return reservationDAO.findOne(idReservation);
	}

	public void bindReservationDao(ReservationDAO reservationDAO, Map<?, ?> properties) {
		this.reservationDAO = reservationDAO;
	}

	@Override
	public List<Reservation> findReservationByState(Nomenclator arg1) {
		return reservationDAO.findDistinctReservationByState(arg1);		
	}

	@Override
	public Page<Reservation> findAllReservationConsult(
			Nomenclator loanUserType, String loanUserCode, String firstName,
			String secondName, String firstSurname, String secondSurname,
			String title, Nomenclator record_type_id,
			Nomenclator reservation_state, Nomenclator reservation_type,
			Date dateRegister, Date endDateRegister,Library library, int page, int size,
			int direction, String orderByString) {
		return reservationDAO
				.findAll(CirculationSpecification.searchReservationConsult(
						loanUserType, loanUserCode, firstName, secondName,
						firstSurname, secondSurname, title, record_type_id,
						reservation_state, reservation_type, dateRegister,
						endDateRegister,library), PageSpecification.getPage(page, size,
						direction, orderByString));
	}

	@Override
	public Page<Reservation> findAllReservationByLoanUserCurrent(Long loanUserID, int page, int size, int direction, String orderByString) {
		return reservationDAO.findAll(CirculationSpecification.searchReservationLoanUserCurrent(loanUserID), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Reservation> findAllReservationByLoanUserHistory(Long loanUserID, int page, int size, int direction, String orderByString) {
		return reservationDAO.findAll(CirculationSpecification.searchReservationLoanUserHistory(loanUserID), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Reservation> findAllReservationByLoanObject(Long loanObjectID, int page, int size, int direction, String orderByString) {
		return reservationDAO.findAll(CirculationSpecification.searchReservationLoanObject(loanObjectID), PageSpecification.getPage(page, size, direction, orderByString));
	}
	
}
