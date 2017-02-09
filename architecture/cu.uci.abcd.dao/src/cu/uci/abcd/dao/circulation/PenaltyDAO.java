package cu.uci.abcd.dao.circulation;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;

public interface PenaltyDAO extends PagingAndSortingRepository<Penalty, Long>, JpaSpecificationExecutor<Penalty> {

	public List<Penalty> findDistinctPenaltyByLoanUser_Person_PersonID(Long personID);

	public List<Penalty> findDistinctPenaltyByLoanObject_LoanObjectID(Long loanObjectID);
	
	public List<Penalty> findDistinctPenaltyByLoanUser_Person_PersonIDAndPenaltyTypeAndPenaltyState(Long personID,Nomenclator penaltyType, Nomenclator penaltyState);

	@Query("select n from Penalty n where n.expirationDate < ?1 and n.penaltyState = ?2 and n.penaltyType = ?3")
	public List<Penalty> searchPenaltyByDate(Date system, Nomenclator state, Nomenclator penaltyType);
	
	@Query("select n from Penalty n where n.penaltyState = ?1 and n.loanUser = ?2 and n.loanObject = ?3")
	public Penalty searchPenaltyByLoanUserAndLoanObject(Nomenclator state, LoanUser loanuserID,LoanObject loanObjectID);

}