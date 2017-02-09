package cu.uci.abcd.circulation;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;

public interface ILoanObjectService {

	/**
	 * Add Loan Object
	 * 
	 * @param loanObject
	 * @return
	 */
	public LoanObject addLoanObject(LoanObject loanObject);

	/**
	 * Find one Loan Object
	 * 
	 * @param idLoanObject
	 * @return
	 */
	public LoanObject findOneLoanObject(Long idLoanObject);

	
	public List<String> findAllControlNumbersFromAvailableLoanObjects(List<LoanObject> listInventoryNumber);	
	
	/**
	 * RF_CI17_Find All Loan Object
	 * 
	 * @param specification
	 * @param pageable
	 * @return
	 */	
	//FIXME EXCESO DE PARAMETROS
	public Page<LoanObject> findAllLoanObject(String title, String author,
			Nomenclator record_type_id, Nomenclator loan_object_state,
			String inventory_number, String control_number, Date fromDate,
			Date toDate, Room room, Library library,int page, int size, int direction,
			String order);

	/**
	 * find All LoanObject By Inventory Number
	 * @param inventory_number
	 * @param page
	 * @param size
	 * @param direction
	 * @param orderByString
	 * @return
	 */
	public Page<LoanObject> findAllLoanObjectByInventoryNumber(
			String inventory_number,Library library,List<Room> listIDRoomWorker, int page, int size, int direction,
			String orderByString);

	public List<LoanObject> findLoanObjectLost(String controlNumber, Nomenclator stateLoanObject);
	
	public List<LoanObject> findControlNumberLoanObject(String controlNumber);
	
	public List<LoanObject> findAvailableControlNumberLoanObject(String controlNumber);


}
