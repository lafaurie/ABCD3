package cu.uci.abcd.dao.acquisition;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.acquisition.Suggestion;

public interface SuggestionDAO extends PagingAndSortingRepository<Suggestion, Long>, JpaSpecificationExecutor<Suggestion> {

	public List<Suggestion> findDistinctSuggestionByAssociateDesiderata_desiderataID(Long desiderataID);
	
}