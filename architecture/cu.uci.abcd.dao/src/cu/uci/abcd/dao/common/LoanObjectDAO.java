package cu.uci.abcd.dao.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;

public interface LoanObjectDAO extends PagingAndSortingRepository<LoanObject, Long>, JpaSpecificationExecutor<LoanObject> {

	public List<LoanObject> findDistinctLoanObjectByControlNumberAndLoanObjectState(String controlNumber, Nomenclator loanObjectState);

	public List<LoanObject> findDistinctLoanObjectByControlNumber(String controlNumber);

	/* 
	@Query("select n from LoanObject n where n.person.personID = ?1 and n.loanUserState.nomenclatorID = ?2 and n.person.library.libraryID = ?3")
	public List<LoanObject> findDistinctLoanObjectByControlNumberAndIsisdatabasenameAndLoanObjectStateAndLibraryOwner(String controlNumber, String databaseName, Long loanObjectState, Long libraryId);
	
	
	 if (!(tempLoandObjects.get(i).getControlNumber().equals(controlNumber) &&
	    tempLoandObjects.get(i).getIsisDataBaseName().equals(databaseName) &&
	  tempLoandObjects.get(i).getLibraryOwner().getLibraryID() == libraryId &&
	 (tempLoandObjects.get(i).getLoanObjectState().getNomenclatorID()
	  .equals(Nomenclator.LOANOBJECT_STATE_AVAILABLE) ||
	  tempLoandObjects.get(i)
	  .getLoanObjectState().getNomenclatorID().equals(Nomenclator.LOANOBJECT_STATE_BORROWED))))
	   {
	    tempLoandObjects.remove(i); i--; 
	    }
	 */

}