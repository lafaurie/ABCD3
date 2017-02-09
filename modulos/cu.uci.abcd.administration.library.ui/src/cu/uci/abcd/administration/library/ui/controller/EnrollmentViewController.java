package cu.uci.abcd.administration.library.ui.controller;

import java.util.List;

import cu.uci.abcd.domain.management.library.Enrollment;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class EnrollmentViewController implements ViewController {

	private AllManagementLibraryViewController allManagementLibraryViewController;

	public AllManagementLibraryViewController getAllManagementLibraryViewController() {
		return allManagementLibraryViewController;
	}

	public void setAllManagementLibraryViewController(AllManagementLibraryViewController allManagementLibraryViewController) {
		this.allManagementLibraryViewController = allManagementLibraryViewController;
	}

	public Enrollment getEnrollmentById(Long idEnrollment) {
		return allManagementLibraryViewController.getEnrollmentService().readEnrollment(idEnrollment);
	}

	public Enrollment saveEnrollment(Enrollment enrollment) {
		return allManagementLibraryViewController.getEnrollmentService().addEnrollment(enrollment);
	}

	public void deleteEnrollmentById(Long idEnrollment) {
		allManagementLibraryViewController.getEnrollmentService().deleteEnrollment(idEnrollment);
	}

	public List<Enrollment> findAll() {
		return allManagementLibraryViewController.getEnrollmentService().findAll();
	}

	public List<Enrollment> findByFormationCourse(long idFormationCourse) {
		return allManagementLibraryViewController.getEnrollmentService().findByFormationCourse(idFormationCourse);
	}

}
