package cu.uci.abcd.dao.management.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.library.Library;

public interface LibraryDAO extends PagingAndSortingRepository<Library, Long>, JpaSpecificationExecutor<Library> {

	@Query("select l from Library l, User a where a.userID = ?1 and a.person.library.libraryID = l.libraryID")
	public Library findLibraryByAcountIDMember(Long userID);
	
	@Query("select l from Library l where l.enabled = true")
	public List<Library> findDistinctLibraryByEnabled();
	
	public Library findLibraryByLibraryNameIgnoreCase(String libraryName);
	
	@Query("select l from Library l where l.isisDefHome = ?1 and l.isisDefHome!=''")
	public Library findLibraryByIsisDefHomeIgnoreCase(String isisHome);
	
}
