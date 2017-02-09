package cu.uci.abcd.administration.library;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IFormationCourseService {

	public FormationCourse addFormationCourse(FormationCourse formationCourse);

	public FormationCourse readFormationCourse(Long idFormationCourse);

	public void deleteFormationCourse(Long idFormationCourse);

	public Page<FormationCourse> findAll(Library library, String courseName,
			int clasification, Room room, int addressedTo, Person proffesor,
			Date fromDate, Date toDate, int page, int size, int direction,
			String order);

	public List<FormationCourse> findFormationCourseByLibrary(Long libraryID);

	public FormationCourse findFormationCourseByName(Long idLibrary,
			String formationCourseName);

}
