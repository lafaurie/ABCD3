package cu.uci.abcd.dao.management.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.CirculationRule;

public interface CirculationRuleDAO extends PagingAndSortingRepository<CirculationRule, Long>, JpaSpecificationExecutor<CirculationRule> {

	public List<CirculationRule> findDistinctCirculationRuleByLibrary_LibraryID(Long actorID);

	public CirculationRule findDistinctCirculationRuleBycirculationRuleStateAndLoanUserTypeAndRecordTypeAndLibrary_LibraryID(Nomenclator circulationRuleState, Nomenclator loanUserType, Nomenclator recordType, Long actorID);

	public CirculationRule findDistinctCirculationRuleByLoanUserTypeAndRecordTypeAndLibrary_LibraryID(Nomenclator loanUserType, Nomenclator recordType, Long actorID);

	
	
}
