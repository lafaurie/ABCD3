package cu.uci.abcd.demo.domain.management.library;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "dformationcourse", schema = "abcd")
public class FormationCourse {
	@Id
	@GeneratedValue(generator = "GEN_FORMATION_COURSE")
	@SequenceGenerator(name = "GEN_FORMATION_COURSE", sequenceName = "seq_formation_course")
	@Column(name = "formation_course_id")
	private Long formationCourseID;
	@Column(name = "course_name")
	private String courseName;
	@Column(name = "hour_quantity")
	private Integer hourQuantity;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	@Column(name = "external_teacher_quantity")
	private Integer externalTeacherQuantity;
	@Column(name = "worker_teacher_quantity")
	private Integer workerTeacherQuantity;
	@Column(name = "student_quantity")
	private Integer externalStudentQuantity;
	@Column(name = "worker_student_quantity")
	private Integer workerStudentQuantity;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_library_id")
	private Library ownerLibrary;

	public Long getFormationCourseID() {
		return formationCourseID;
	}

	public void setFormationCourseID(Long formationCourseID) {
		this.formationCourseID = formationCourseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getHourQuantity() {
		return hourQuantity;
	}

	public void setHourQuantity(Integer hourQuantity) {
		this.hourQuantity = hourQuantity;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getExternalTeacherQuantity() {
		return externalTeacherQuantity;
	}

	public void setExternalTeacherQuantity(Integer externalTeacherQuantity) {
		this.externalTeacherQuantity = externalTeacherQuantity;
	}

	public Integer getWorkerTeacherQuantity() {
		return workerTeacherQuantity;
	}

	public void setWorkerTeacherQuantity(Integer workerTeacherQuantity) {
		this.workerTeacherQuantity = workerTeacherQuantity;
	}

	public Integer getExternalStudentQuantity() {
		return externalStudentQuantity;
	}

	public void setExternalStudentQuantity(Integer externalStudentQuantity) {
		this.externalStudentQuantity = externalStudentQuantity;
	}

	public Integer getWorkerStudentQuantity() {
		return workerStudentQuantity;
	}

	public void setWorkerStudentQuantity(Integer workerStudentQuantity) {
		this.workerStudentQuantity = workerStudentQuantity;
	}

	public Library getOwnerLibrary() {
		return ownerLibrary;
	}

	public void setOwnerLibrary(Library ownerLibrary) {
		this.ownerLibrary = ownerLibrary;
		if (!ownerLibrary.getFormationCourseList().contains(this)) {
			ownerLibrary.getFormationCourseList().add(this);
		}
	}

}
