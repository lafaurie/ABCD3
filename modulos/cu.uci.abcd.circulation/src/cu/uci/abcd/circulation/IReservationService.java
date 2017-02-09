package cu.uci.abcd.circulation;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;

/**
 * 
 * @author Abelito
 * 
 */
public interface IReservationService {

	/**
	 * Add Reservation
	 * @param reservation
	 * @return
	 */
	public Reservation addReservation(Reservation reservation);

	/**
	 * delete Reservation
	 * @param idReservation
	 */
	public void deleteReservation(Long idReservation);

	/**
	 * find Reservation By LoanObject And Reservation State
	 * @param state
	 * @param idLoanObject
	 * @return
	 */
	public List<Reservation> findReservationByState(Nomenclator state);

	/**
	 * find Reservation by ID
	 * @param idReservation
	 * @return
	 */
	public Reservation findOneReservation(Long idReservation);

	/**
	 * RF_CI27_Find All Reservation
	 * @param loan_user_type_id
	 * @param loan_user_code
	 * @param first_Name
	 * @param second_Name
	 * @param first_Surname
	 * @param control_number
	 * @param title
	 * @param record_type_id
	 * @param reservation_state
	 * @param reservation_type
	 * @param dateRegister
	 * @param endDateRegister
	 * @param page
	 * @param size
	 * @param direction
	 * @param orderByString
	 * @return
	 */
	//FIXME EXCESO DE PARAMETROS
	public Page<Reservation> findAllReservationConsult(Nomenclator loan_user_type_id, 
			String loan_user_code, String first_Name, String second_Name, String first_Surname, 
			String second_Surname, String title, Nomenclator record_type_id, 
			Nomenclator reservation_state,Nomenclator reservation_type, Date dateRegister,
			Date endDateRegister,Library library, int page, int size, int direction, String orderByString);

	/**
	 * RF_CI6.1_Find All Reservation By LoanUser Current
	 * 
	 * @param specification
	 * @param pageable
	 * @return
	 */
	public Page<Reservation> findAllReservationByLoanUserCurrent(Long loanUserID, int page, int size, int direction, String orderByString);

	/**
	 * find All Reservation By LoanUser History
	 * @param loanUserID
	 * @param page
	 * @param size
	 * @param direction
	 * @param orderByString
	 * @return
	 */
	public Page<Reservation> findAllReservationByLoanUserHistory(Long loanUserID, int page, int size, int direction, String orderByString);

	/**
	 * RF_CI19.1_find All Reservation By LoanObject
	 * 
	 * @param specification
	 * @param pageable
	 * @return
	 */
	public Page<Reservation> findAllReservationByLoanObject(Long loanObjectID, int page, int size, int direction, String orderByString);

}
