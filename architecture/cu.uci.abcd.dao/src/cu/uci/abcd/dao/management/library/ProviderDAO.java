package cu.uci.abcd.dao.management.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.library.Provider;

public interface ProviderDAO extends PagingAndSortingRepository<Provider, Long>, JpaSpecificationExecutor<Provider> {

	public List<Provider> findProviderByLibrary_LibraryID(Long actorID);
	
	public Provider findProviderByLibrary_LibraryIDAndIsIntitutionalAndProviderNameIgnoreCase(Long idLibrary, boolean intitutional, String providerName);
	
	public Provider findProviderByLibrary_LibraryIDAndIsIntitutionalAndPerson_PersonID(Long idLibrary, boolean intitutional, Long idPerson);
	
	
	
}
