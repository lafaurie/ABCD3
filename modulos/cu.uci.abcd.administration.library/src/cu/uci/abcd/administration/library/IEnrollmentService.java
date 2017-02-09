package cu.uci.abcd.administration.library;

import java.util.List;

import cu.uci.abcd.domain.management.library.Enrollment;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IEnrollmentService {

	public Enrollment addEnrollment(Enrollment enrollment);

	public Enrollment readEnrollment(Long idEnrollment);

	public void deleteEnrollment(Long idEnrollment);

	public List<Enrollment> findAll();

	public List<Enrollment> findByFormationCourse(long idFormationCourse);

}
