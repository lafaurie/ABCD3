package cu.uci.abcd.cataloguing;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Room;


public interface ILoanObjectCreation {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public boolean addLoanObject(LoanObject loanObject);

	public boolean removedLoanObject(long id);

	public LoanObject findById(long id);

	public List<LoanObject> findAllByControlNumber(String controlNumber);

	public Page<LoanObject> findAllByControlNumber(String controlNumber, int page, int size, int direction, String orderByString);

	public Page<LoanObject> findAllByPrecataloguing(int page, int size, int direction, String orderByString);

	public List<Room> getRooms();

	public Nomenclator getNomenclator(long id);

	public List<Nomenclator> getNomenclatorByParent(long parent);

	public List<Provider> getProviders();

	public List<PurchaseOrder> getPurchaseOrders();

	public List<Suggestion> getSuggestion();
	
	public List<Transaction> findTranssactionByLoanObject(long loanObjectID);
	
	public List<Penalty> findPenaltyByLoanObject(long loanObjectID);
	
	public List<Reservation> findAllReservations();

}
