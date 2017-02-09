package cu.uci.abcd.dao.circulation;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.common.Nomenclator;

public interface LoanUserDAO extends PagingAndSortingRepository<LoanUser, Long>, JpaSpecificationExecutor<LoanUser> {
      
	public LoanUser findByPerson_PersonIDAndLoanUserState_NomenclatorID(Long personID, Long nomenclatorID);
                       
	@Query("select n from LoanUser n where n.person.personID = ?1 and n.loanUserState.nomenclatorID = ?2 and n.person.library.libraryID = ?3")
	public LoanUser findByPersonIDAndLoanUserStateAndLibraryID(Long personID, Long nomenclatorID, Long libraryID);

	public LoanUser findByLoanUserCode(String loanUserCode);
     
	public LoanUser findByPerson_PersonID(Long personID);    

	@Query("select n from LoanUser n where n.expirationDate < ?1 and n.loanUserState = ?2")
	public List<LoanUser> searchLoanUserByDate(Date system, Nomenclator state);
}
