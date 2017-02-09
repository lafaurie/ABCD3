package cu.uci.abcd.dao.management.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.library.Enrollment;

public interface EnrollmentDAO extends PagingAndSortingRepository<Enrollment, Long>, JpaSpecificationExecutor<Enrollment>{

	public List<Enrollment> findDistinctEnrollmentByFormationCourse_FormationCourseID(Long formationCourseID);
	
	
}
