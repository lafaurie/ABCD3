package cu.uci.abcd.opac.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.common.LoanObjectDAO;
import cu.uci.abcd.dao.specification.OpacSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.opac.IOpacLoanObjectService;

public class OpacLoanObjectServiceImpl implements IOpacLoanObjectService {

	LoanObjectDAO loanObjectDAO;

	@Override
	public List<LoanObject> findAllLoanObjectByDate() {

		return (List<LoanObject>) loanObjectDAO.findAll();
	}

	@Override
	public List<LoanObject> findRecentLoanObject(Long libraryId) {

		Date to = new Date(new java.util.Date().getTime());
		@SuppressWarnings("deprecation")
		Date since = new Date(to.getYear(), to.getMonth() - 1, to.getDate());
    
		List<LoanObject> temp = (List<LoanObject>) loanObjectDAO.findAll();
  
		for (int i = 0; i < temp.size(); i++)
			if (temp.get(i).getLibraryOwner().getLibraryID() != libraryId  ||!(temp.get(i).getRegistrationDate().after(since) && temp.get(i).getRegistrationDate().before(to)))
				temp.remove(i--);
			else
				for (int j = 0; j < i; j++)
					if (temp.get(j).getControlNumber().equals(temp.get(i).getControlNumber()) && temp.get(j).getIsisDataBaseName().equals(temp.get(i).getIsisDataBaseName())) {
						temp.remove(i--);
						break;
					}

		return temp;
	}

	public void bind(LoanObjectDAO loanObjectDAO, Map<String, Object> properties) {
		this.loanObjectDAO = loanObjectDAO;
		System.out.println("servicio registrado");
	}

	@Override
	public List<LoanObject> findAllAvailableLoanObjectByControlNumberAndLibrary(String controlNumber, String databaseName, Long libraryId) {

		List<LoanObject> tempLoandObjects = (List<LoanObject>) loanObjectDAO.findAll();

		for (int i = 0; i < tempLoandObjects.size(); i++) {
			if (!(tempLoandObjects.get(i).getControlNumber().equals(controlNumber) && tempLoandObjects.get(i).getIsisDataBaseName().equals(databaseName) && tempLoandObjects.get(i).getLibraryOwner().getLibraryID() == libraryId && (tempLoandObjects.get(i).getLoanObjectState().getNomenclatorID()
					.equals(Nomenclator.LOANOBJECT_STATE_AVAILABLE) || tempLoandObjects.get(i).getLoanObjectState().getNomenclatorID().equals(Nomenclator.LOANOBJECT_STATE_BORROWED)))) {
				tempLoandObjects.remove(i);
				i--;
			}
		}

		return tempLoandObjects;
	}

	@Override
	public int findLoanObjectByControlNumberAndLibrary(String controlNumber, Long libraryId) {

		List<LoanObject> tempLoandObjects = (List<LoanObject>) loanObjectDAO.findAll();

		for (int i = 0; i < tempLoandObjects.size(); i++) {
			if (!(tempLoandObjects.get(i).getControlNumber().equals(controlNumber) && tempLoandObjects.get(i).getLibraryOwner().getLibraryID() == libraryId)) {
				tempLoandObjects.remove(i);
				i--;
			}
		}

		return tempLoandObjects.size();
	}

	@Override
	public Page<LoanObject> findAllCoppysByLoanObjectAndLibrary(String controlNumber, Long librarId, int page, int size, int direction, String orderByString) {

		return loanObjectDAO.findAll(OpacSpecification.searchCoppysByLoanObjectAndLibrary(controlNumber, librarId), PageSpecification.getPage(page, size, direction, orderByString));

	}

	@Override
	public List<LoanObject> findLoanObjectsByControlNumberAndLibrary(String controlNumber, Long idLibrary) {

		List<LoanObject> tempLoandObjects = (List<LoanObject>) loanObjectDAO.findAll();

		for (int i = 0; i < tempLoandObjects.size(); i++)
			if (!(tempLoandObjects.get(i).getControlNumber().equals(controlNumber)) || !(tempLoandObjects.get(i).getLibraryOwner().getLibraryID().equals(idLibrary)))
				tempLoandObjects.remove(i--);

		return tempLoandObjects;
	}

}
