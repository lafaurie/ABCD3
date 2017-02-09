package cu.uci.abcd.dao.test.management.library;

import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.dao.management.library.FormationCourseDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.domain.management.library.FormationCourse;

public class FormationCourseDAOImpl extends DaoUtil<FormationCourse> implements FormationCourseDAO{

	@Override
	public List<FormationCourse> findDistinctFormationCourseByCourseName(String name) {
		List<FormationCourse> result = new LinkedList<FormationCourse>();
		for (FormationCourse formationCourse : data) {
			if (name.equals(formationCourse.getCourseName())) {
				result.add(formationCourse);
			}
		}
		return result;
	}

	@Override
	public List<FormationCourse> findDistinctFormationCourseByLibrary_LibraryID(Long library) {
		List<FormationCourse> result = new LinkedList<FormationCourse>();
		for (FormationCourse formationCourse : data) {
			if (library.equals(formationCourse.getLibrary().getLibraryID())) {
				result.add(formationCourse);
			}
		}
		return result;
	}

	@Override
	public List<FormationCourse> findDistinctFormationCourseByLibrary_LibraryName(String libraryName) {
		List<FormationCourse> result = new LinkedList<FormationCourse>();
		for (FormationCourse formationCourse : data) {
			if (libraryName.equals(formationCourse.getLibrary().getLibraryName())) {
				result.add(formationCourse);
			}
		}
		return result;
	}
	
}
