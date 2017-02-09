package cu.uci.abcd.dao.management.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Profile;

public interface ProfileDAO extends PagingAndSortingRepository<Profile, Long> ,JpaSpecificationExecutor<Profile> {

	@Query("select n from Profile n where n.library = ?1")
	public List<Profile> findAllByLibrary(Library library);
	
	public Profile findProfileByLibrary_LibraryIDAndProfileNameIgnoreCase(Long idLibrary, String profileName);
	
}
