package cu.uci.abcd.dao.test.circulation;

import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.dao.circulation.ReservationDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.circulation.Reservation;

public class ReservationDAOImpl extends DaoUtil<Reservation> implements ReservationDAO {

	public ReservationDAOImpl() {
		super();
		data= new LinkedList<Reservation>(DataGenerator.getInstance().getReservations());
	}

	@Override
	public List<Reservation> findDistinctReservationByLoanObject_LoanObjectID(Long key) {
		List<Reservation> result = new LinkedList<Reservation>();
		for (Reservation reservation : data) {
			if (key.equals(reservation.getLoanObject().getLoanObjectID())) {
				result.add(reservation);
			}
		}
		return result;
	}

	@Override
	public List<Reservation> findDistinctReservationByLoanUser_PersonID(Long key) {
		List<Reservation> result = new LinkedList<Reservation>();
		for (Reservation reservation : data) {
			if (key.equals(reservation.getLoanUser().getPersonID())) {
				result.add(reservation);
			}
		}
		return result;
	}

}
