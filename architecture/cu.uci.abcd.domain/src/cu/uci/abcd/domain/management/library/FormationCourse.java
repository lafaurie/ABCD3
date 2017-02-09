package cu.uci.abcd.domain.management.library;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dformationcourse", schema = "abcdn")
@SequenceGenerator(name = "seq_dformationcourse", sequenceName = "sq_dformationcourse", schema = "abcdn", allocationSize = 1)
public class FormationCourse implements Serializable, Row {
	private static final long serialVersionUID = 3431534225766098047L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dformationcourse")
	@Column(name = "id", nullable = false)
	private Long formationCourseID;

	@Column(name = "coursename", nullable = false, length = 50)
	private String courseName;

	@Column(name = "hourquantity", nullable = true)
	private Integer hourQuantity;

	@Column(name = "startdate", nullable = false)
	private Date startDate;

	@Column(name = "enddate", nullable = false)
	private Date endDate;

	@Column(name = "delayed", nullable = false)
	private boolean delayed;

	@Column(name = "observations", nullable = true, length = 500)
	private String observations;

	@Column(name = "received", nullable = false)
	private boolean received;
	
	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	@ManyToOne
	@JoinColumn(name = "professor", nullable = true)
	private Person professor;
	
	@ManyToOne
	@JoinColumn(name = "coursetype", nullable = false)
	private Nomenclator coursetype;
	

	@Column(name = "workersquantity", nullable = false)
	private Integer workersQuantity;

	@Column(name = "externalworkersquantity", nullable = false)
	private Integer externalWorkersQuantity;
	
	@OneToOne
	@JoinColumn(name = "room", nullable = true)
	private Room room;
	
	@Column(name = "code", nullable = false, length = 6)
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@OneToMany(mappedBy = "formationCourse")
	protected List<Enrollment> enrrollmentList;

	public List<Enrollment> getEnrrollmentList() {
		return enrrollmentList;
	}

	public void setEnrrollmentList(List<Enrollment> enrrollmentList) {
		this.enrrollmentList = enrrollmentList;
	}

	public FormationCourse() {
		super();
		this.delayed = false;
		enrrollmentList = new ArrayList<>();
	}

	public Integer getWorkersQuantity() {
		return workersQuantity;
	}

	public void setWorkersQuantity(Integer workersQuantity) {
		this.workersQuantity = workersQuantity;
	}

	public Integer getExternalWorkersQuantity() {
		return externalWorkersQuantity;
	}

	public void setExternalWorkersQuantity(Integer externalWorkersQuantity) {
		this.externalWorkersQuantity = externalWorkersQuantity;
	}

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

	public boolean isDelayed() {
		return delayed;
	}

	public void setDelayed(boolean delayed) {
		this.delayed = delayed;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public Person getProfessor() {
		return professor;
	}

	public void setProfessor(Person professor) {
		this.professor = professor;
	}

	public Nomenclator getCoursetype() {
		return coursetype;
	}

	public void setCoursetype(Nomenclator coursetype) {
		this.coursetype = coursetype;
	}

	public String getProfesorToString() {
		if(getProfessor()==null){
			return "-";
		}else{
			return getProfessor().getFullName();
		}
		
	}
	
	public String getStartDateToString() {
		return Auxiliary.FormatDate(getStartDate());
	}
	
	public String getEndDateToString() {
		return Auxiliary.FormatDate(getEndDate());
	}
	
	public Integer getEnrollment() {
		return ((getExternalWorkersQuantity() != null) ? getExternalWorkersQuantity()
				: 0)
				+ ((getWorkersQuantity() != null) ? getWorkersQuantity() : 0);
	}
	
	
	@Override
	public Object getRowID() {
		return getFormationCourseID();
	}

}
