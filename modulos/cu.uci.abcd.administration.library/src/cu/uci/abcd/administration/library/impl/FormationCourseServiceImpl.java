package cu.uci.abcd.administration.library.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.IFormationCourseService;
import cu.uci.abcd.dao.management.library.FormationCourseDAO;
import cu.uci.abcd.dao.specification.LibrarySpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class FormationCourseServiceImpl implements IFormationCourseService {

	private FormationCourseDAO formationCourseDAO;

	public void bind(FormationCourseDAO formationCourseDAO, Map<?, ?> properties) {
		this.formationCourseDAO = formationCourseDAO;
	}

	@Override
	public FormationCourse addFormationCourse(FormationCourse formationCourse) {
		return formationCourseDAO.save(formationCourse);
	}

	@Override
	public void deleteFormationCourse(Long idFormationCourse) {
		formationCourseDAO.delete(idFormationCourse);

	}

	@Override
	public FormationCourse readFormationCourse(Long idFormationCourse) {
		return formationCourseDAO.findOne(idFormationCourse);
	}

	@Override
	public Page<FormationCourse> findAll(Library library, String courseName,
			int clasification, Room room, int addressedTo, Person proffesor,
			Date fromDate, Date toDate, int page, int size, int direction,
			String order) {
		return formationCourseDAO.findAll(LibrarySpecification
				.searchFormationCourse(library, courseName, clasification,
						room, addressedTo, proffesor, fromDate, toDate),
				PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public List<FormationCourse> findFormationCourseByLibrary(Long libraryID) {
		return formationCourseDAO
				.findDistinctFormationCourseByLibrary_LibraryID(libraryID);
	}

	@Override
	public FormationCourse findFormationCourseByName(Long idLibrary,
			String formationCourseName) {
		return formationCourseDAO
				.findFormationCourseByLibrary_LibraryIDAndCourseNameIgnoreCase(
						idLibrary, formationCourseName);
	}

}
