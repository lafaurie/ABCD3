package cu.uci.abcd.cataloguing.impl;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.springframework.data.domain.Page;

import cu.uci.abcd.cataloguing.ILoanObjectCreation;
import cu.uci.abcd.dao.acquisition.PurchaseOrderDAO;
import cu.uci.abcd.dao.acquisition.SuggestionDAO;
import cu.uci.abcd.dao.circulation.PenaltyDAO;
import cu.uci.abcd.dao.circulation.ReservationDAO;
import cu.uci.abcd.dao.circulation.TransactionDAO;
import cu.uci.abcd.dao.common.LoanObjectDAO;
import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.management.library.ProviderDAO;
import cu.uci.abcd.dao.management.library.RoomDAO;
import cu.uci.abcd.dao.specification.CataloguingSpecification;
import cu.uci.abcd.dao.specification.CirculationSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Room;

public class LoanObjectCreation implements ILoanObjectCreation {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private LoanObjectDAO loanObjectDAO;
	private Shell sell;
	private RoomDAO roomDAO;
	private NomenclatorDAO nomenclatorDAO;
	private ProviderDAO providerDAO;
	private PurchaseOrderDAO purchaseOrderDAO;
	private SuggestionDAO suggestionDAO;
	private TransactionDAO transactionDAO;
	private PenaltyDAO penaltyDAO;
	private ReservationDAO reservationDAO;

	@Override
	public boolean addLoanObject(LoanObject loanObject) {

		boolean result = false;

		loanObjectDAO.save(loanObject);

		return result;
	}

	@Override
	public boolean removedLoanObject(long id) {

		boolean result = false;

		loanObjectDAO.delete(id);

		return result;
	}

	@Override
	public LoanObject findById(long id){

		return loanObjectDAO.findOne(id);

	}

	@Override
	public List<Room> getRooms() {

		return (List<Room>)roomDAO.findAll();
	}

	@Override
	public Nomenclator getNomenclator(long id) {

		return (Nomenclator) nomenclatorDAO.findOne(id);

	}

	@Override
	public List<Nomenclator> getNomenclatorByParent(long parent) {

		return nomenclatorDAO.findByNomenclator(parent);
	}

	@Override
	public List<Provider> getProviders() {

		return (List<Provider>) providerDAO.findAll();
	}

	@Override
	public List<PurchaseOrder> getPurchaseOrders() {

		return (List<PurchaseOrder>) purchaseOrderDAO.findAll();
	}

	@Override
	public List<Suggestion> getSuggestion() {

		return (List<Suggestion>) suggestionDAO.findAll();
	}

	public void bind(LoanObjectDAO loanObjectDAO, Map<?, ?> properties) {
		this.loanObjectDAO = loanObjectDAO;
	}

	public void bindRoom(RoomDAO roomDao, Map<?, ?> properties) {
		this.roomDAO = roomDao;
	}
	
	public void bindTransaction(TransactionDAO transactionDao, Map<?, ?> properties) {
		this.transactionDAO = transactionDao;
	}
	
	public void bindPenalty(PenaltyDAO penaltyDao, Map<?, ?> properties) {
		this.penaltyDAO = penaltyDao;
	}
	
	public void bindReservation(ReservationDAO reservationDao, Map<?, ?> properties) {
		this.reservationDAO = reservationDao;
	}

	public void bindNomenclator(NomenclatorDAO nomenclatorDAO, Map<?, ?> properties) {
		this.nomenclatorDAO = nomenclatorDAO;
	}

	public void bindProvider(ProviderDAO providerDAO, Map<?, ?> properties) {
		this.providerDAO = providerDAO;
	}

	public void bindPurchaseOrder(PurchaseOrderDAO purchaseOrderDAO, Map<?, ?> properties) {
		this.purchaseOrderDAO = purchaseOrderDAO;
	}

	public void bindSuggestion(SuggestionDAO suggestionDao, Map<?, ?> properties) {
		this.suggestionDAO = suggestionDao;
	}

	public Shell getSell() {
		return sell;
	}

	public void setSell(Shell sell) {
		this.sell = sell;
	}

	@Override
	public List<LoanObject> findAllByControlNumber(String controlNumber) {
		return loanObjectDAO.findAll(CataloguingSpecification.searchLoanObjectByControlNumber(controlNumber));
	}

	@Override
	public Page<LoanObject> findAllByPrecataloguing(int page, int size, int direction, String orderByString) {
		return loanObjectDAO.findAll(CataloguingSpecification.searchLoanObjectByPrecataloguedSituation(),
				PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<LoanObject> findAllByControlNumber(String controlNumber, int page, int size, int direction, String orderByString) {

		return loanObjectDAO.findAll(CataloguingSpecification.searchLoanObjectByControlNumber(controlNumber),
				PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public List<Transaction> findTranssactionByLoanObject(long loanObjectID) {
		return transactionDAO.findAll(CirculationSpecification.searchTransactionByLoanObject2(loanObjectID));
	}

	@Override
	public List<Penalty> findPenaltyByLoanObject(long loanObjectID) {
		return penaltyDAO.findAll(CirculationSpecification.searchPenaltyByLoanObject2(loanObjectID));
	}

	@Override
	public List<Reservation> findAllReservations() {
		return (List<Reservation>) reservationDAO.findAll();
	}

}
