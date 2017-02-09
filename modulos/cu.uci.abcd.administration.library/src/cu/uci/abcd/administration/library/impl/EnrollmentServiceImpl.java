package cu.uci.abcd.administration.library.impl;

import java.util.List;
import java.util.Map;

import cu.uci.abcd.administration.library.IEnrollmentService;
import cu.uci.abcd.dao.management.library.EnrollmentDAO;
import cu.uci.abcd.domain.management.library.Enrollment;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class EnrollmentServiceImpl implements IEnrollmentService {

	private EnrollmentDAO enrollmentDAO;

	public void bind(EnrollmentDAO enrollmentDAO, Map<?, ?> properties) {
		this.enrollmentDAO = enrollmentDAO;
	}

	@Override
	public Enrollment addEnrollment(Enrollment enrollment) {
		return enrollmentDAO.save(enrollment);
	}

	@Override
	public Enrollment readEnrollment(Long idEnrollment) {
		return enrollmentDAO.findOne(idEnrollment);
	}

	@Override
	public void deleteEnrollment(Long idEnrollment) {
		enrollmentDAO.delete(idEnrollment);

	}

	@Override
	public List<Enrollment> findAll() {
		return (List<Enrollment>) enrollmentDAO.findAll();
	}

	@Override
	public List<Enrollment> findByFormationCourse(long idFormationCourse) {
		return enrollmentDAO
				.findDistinctEnrollmentByFormationCourse_FormationCourseID(idFormationCourse);
	}

}
