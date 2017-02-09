package cu.uci.abcd.dao.management.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.library.FormationCourse;

public interface FormationCourseDAO extends PagingAndSortingRepository<FormationCourse, Long>, JpaSpecificationExecutor<FormationCourse> {

	public List<FormationCourse> findDistinctFormationCourseByLibrary_LibraryName(String libraryName);

	public List<FormationCourse> findDistinctFormationCourseByLibrary_LibraryID(Long actorID);

	public List<FormationCourse> findDistinctFormationCourseByCourseName(String courseName);
	
	public FormationCourse findFormationCourseByLibrary_LibraryIDAndCourseNameIgnoreCase(Long idLibrary, String courseName);
	
}
