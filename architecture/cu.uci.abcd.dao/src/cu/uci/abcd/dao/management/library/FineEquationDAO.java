package cu.uci.abcd.dao.management.library;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.library.FineEquation;

public interface FineEquationDAO extends PagingAndSortingRepository<FineEquation, Long>, JpaSpecificationExecutor<FineEquation> {

	public FineEquation findDistinctFineEquationByLibrary_LibraryID(Long actorID);

}
