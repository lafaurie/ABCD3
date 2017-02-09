package cu.uci.abcd.circulation;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;

public interface ILoanUserService {

	/**
	 * RF_CI1_Loan user register
	 * 
	 * @param loanUser
	 * @return
	 */

	public LoanUser addLoanUser(LoanUser loanUser);

	/**
	 * RF_CI4_Loan user delete
	 * 
	 * @param idLoanUser
	 */

	public void deleteLoanUser(Long idLoanUser);

	/**
	 * Loan user one
	 * 
	 * @return
	 */

	public LoanUser findOneLoanUser(Long idLoanUser);

	/**
	 * Loan user type list
	 * 
	 * @return
	 */

	public List<Nomenclator> findByNomenclator(Long idLibrary, Long code);

	/**
	 * Find nomenclators by IDs
	 * @param idLibrary
	 * @param code1
	 * @param code2
	 * @return
	 */
	public List<Nomenclator> findByNomenclators(Long idLibrary, Long code1, Long code2);
	/**
	 * Find nomenclator by ID
	 * 
	 * @param nomenclatorID
	 * @return
	 */
	public Nomenclator findByID(Long nomenclatorID);

	/**
	 * Find User by Person ID
	 * 
	 * @param personID
	 * @return
	 */
	public User findUserByPersonID(Long personID);

	/**
	 * Find LoanUser by Person ID and LoanUser state
	 * @param personID
	 * @param nomenclatorID
	 * @return
	 */
	public LoanUser findLoanUserByPersonIDAndState(Long personID, Long nomenclatorID);

	/**
	 * Find room by Library ID
	 * @param libraryID
	 * @return
	 */
	public List<Room> findRoomByLibrary(Long libraryID);

	/**
	 * Find all LoanUser
	 * @return
	 */
	public List<LoanUser> findAllLoanUserList();

	/**
	 * Find LoanUser by LoanUser code
	 * @param code
	 * @return
	 */
	public LoanUser findByLoanUserCode(String code);
	
	/**
	 * Consult LoanUser by search criteria
	 * @param loanUserCode
	 * @param roomUser
	 * @param loanUserType
	 * @param loanUserState
	 * @param firstName
	 * @param secondName
	 * @param firstLastName
	 * @param secondLastName
	 * @param fromDate
	 * @param toDate
	 * @param DNI
	 * @param page
	 * @param size
	 * @param direction
	 * @param order
	 * @return
	 */
	public Page<LoanUser> findLoanUserConsult(String loanUserCode,
			Room roomUser, Nomenclator loanUserType, Nomenclator loanUserState,
			Nomenclator faculty, Nomenclator career,
			String firstName, String secondName, String firstLastName,
			String secondLastName, Date fromDate, Date toDate, String DNI,Library library,
			int page, int size, int direction, String order);

	/**
	 * Find LoanUser by advanced search criteria 
	 * @param firstName
	 * @param DNI
	 * @param loanUserCode
	 * @param page
	 * @param size
	 * @param direction
	 * @param order
	 * @return
	 */
	public Page<LoanUser> findLoanUserFragment(String params,Library library, int page,
			int size, int direction, String order);

	public Page<LoanUser> findLoanUserFragmentInterLibrarian(String params,Library library,
			int page, int size, int direction, String order);

	public Page<LoanUser> findLoanUserFragmentOtherType(String params,Library library,
			int page, int size, int direction, String order);

	/**
	 * Scheduled task to update state LoanUser 
	 */
	public void updateStateLoanUser();
}
