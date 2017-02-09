package cu.uci.abcd.circulation.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.ILoanObjectService;
import cu.uci.abcd.dao.common.LoanObjectDAO;
import cu.uci.abcd.dao.specification.CirculationSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;

public class LoanObjectServiceImpl implements ILoanObjectService {

	private LoanObjectDAO loanObjectDAO;

	@Override
	public LoanObject addLoanObject(LoanObject loanObject) {
		return loanObjectDAO.save(loanObject);
	}

	@Override
	public LoanObject findOneLoanObject(Long idLoanObject) {
		return loanObjectDAO.findOne(idLoanObject);
	}

	public void bindLoanObjectDao(LoanObjectDAO loanObjectDAO, Map<?, ?> properties) {
		this.loanObjectDAO = loanObjectDAO;
	}

	@Override
	public Page<LoanObject> findAllLoanObject(String title, String author,
			Nomenclator recordTypeId, Nomenclator loanObjectState,
			String inventoryNumber, String controlNumber, Date fromDate,
			Date toDate, Room room, Library library,int page, int size, int direction,
			String order) {
		return loanObjectDAO.findAll(CirculationSpecification.searchLoanObject(
				title, author, recordTypeId, loanObjectState, inventoryNumber,
				controlNumber, fromDate, toDate, room,library), PageSpecification
				.getPage(page, size, direction, order));
	}

	@Override
	public Page<LoanObject> findAllLoanObjectByInventoryNumber(
			String inventoryNumber,Library library,List<Room> listIDRoomWorker, int page, int size, int direction,
			String orderByString) {
		return loanObjectDAO
				.findAll(CirculationSpecification
						.searchLoanObjectInventoryNumber(inventoryNumber,library,listIDRoomWorker),
						PageSpecification.getPage(page, size, direction,
								orderByString));
	}

	@Override
	public List<LoanObject> findLoanObjectLost(String controlNumber,
			Nomenclator stateLoanObject) {
		
		return loanObjectDAO.findDistinctLoanObjectByControlNumberAndLoanObjectState(controlNumber, stateLoanObject);
	}

	@Override
	public List<LoanObject> findControlNumberLoanObject(String controlNumber) {
		
		return loanObjectDAO.findDistinctLoanObjectByControlNumber(controlNumber);
	}
	
	@Override
	public List<LoanObject> findAvailableControlNumberLoanObject(String controlNumber) {
		
		List<LoanObject> temp = loanObjectDAO.findDistinctLoanObjectByControlNumber(controlNumber);
		    
		for (int i = 0; i < temp.size(); i++) 
			if(temp.get(i).getLoanObjectState().getNomenclatorID() != Nomenclator.LOANOBJECT_STATE_AVAILABLE)
				temp.remove(i--);			
		
		return temp;
	}

	@Override
	public List<String> findAllControlNumbersFromAvailableLoanObjects(List<LoanObject> listInventoryNumber) {
		
		//List<LoanObject> temp = (List<LoanObject>) loanObjectDAO.findAll();	
		List<String> toReturns = new ArrayList<String>();
		Set<String> controlNumbers = new HashSet<String>();
		
		for (int i = 0; i < listInventoryNumber.size(); i++)
			controlNumbers.add(listInventoryNumber.get(i).getControlNumber());
		
		Iterator<String> controlNumberIterator = controlNumbers.iterator();
		
		
		for (int i = 0; i < controlNumbers.size(); i++)
			toReturns.add(controlNumberIterator.next());		
		
		return toReturns;
	}

	

}
