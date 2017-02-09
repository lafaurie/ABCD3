package cu.uci.abcd.domain.management.library;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "denrollment", schema = "abcdn")
@SequenceGenerator(name = "seq_denrollment", sequenceName = "sq_denrollment", schema = "abcdn", allocationSize = 1)
public class Enrollment implements Serializable, Row {
	private static final long serialVersionUID = 1747205571359002383L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_denrollment")
	@Column(name = "id", nullable = false)
	private Long enrollmentID;

	@ManyToOne
	@JoinColumn(name = "addressedto", nullable = false)
	private Nomenclator addressedTo;

	@ManyToOne
	@JoinColumn(name = "firstubication")
	private Nomenclator firstUbication;

	@ManyToOne
	@JoinColumn(name = "secondubication")
	private Nomenclator secondUbication;

	@ManyToOne
	@JoinColumn(name = "thirdubication")
	private Nomenclator thirdUbication;

	@ManyToOne
	@JoinColumn(name = "programm")
	private Nomenclator programm;

	@Column(name = "programname", length = 200)
	private String programName;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@ManyToOne
	@JoinColumn(name = "formationcourse")
	private FormationCourse formationCourse;

	synchronized void setProgramName(String programName) {
		this.programName = programName;
	}

	public Long getEnrollmentID() {
		return enrollmentID;
	}

	public void setEnrollmentID(Long enrollmentID) {
		this.enrollmentID = enrollmentID;
	}

	public Nomenclator getAddressedTo() {
		return addressedTo;
	}

	public void setAddressedTo(Nomenclator addressedTo) {
		this.addressedTo = addressedTo;
	}

	public Nomenclator getFirstUbication() {
		return firstUbication;
	}

	public void setFirstUbication(Nomenclator firstUbication) {
		this.firstUbication = firstUbication;
	}

	public Nomenclator getSecondUbication() {
		return secondUbication;
	}

	public void setSecondUbication(Nomenclator secondUbication) {
		this.secondUbication = secondUbication;
	}

	public Nomenclator getThirdUbication() {
		return thirdUbication;
	}

	public void setThirdUbication(Nomenclator thirdUbication) {
		this.thirdUbication = thirdUbication;
	}

	public Nomenclator getProgramm() {
		return programm;
	}

	public void setProgramm(Nomenclator programm) {
		this.programm = programm;
	}

	public String getProgramName() {
		return programName;
	}

	public void setprogramName(String programName) {
		this.programName = programName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public FormationCourse getFormationCourse() {
		return formationCourse;
	}

	public void setFormationCourse(FormationCourse formationCourse) {
		this.formationCourse = formationCourse;
	}

	public String getArea() {
		String result = "";
		if (!(firstUbication == null)) {
			result = result + getFirstUbication().getNomenclatorName();
		}
		if (!(secondUbication == null)) {
			result = result + " / " + getSecondUbication().getNomenclatorName();
		}

		if (!(thirdUbication == null)) {
			result = result + " / " + getThirdUbication().getNomenclatorName();
		}

		if (!(programm == null)) {
			result = result + getProgramm().getNomenclatorName() + ": "
					+ programName;
		}
		return result;
	}

	@Override
	public Object getRowID() {
		return getEnrollmentID();
	}

	public boolean equals(Enrollment enrollment) {
		int a = 0;
		int b = 0;
		if (!addressedTo.equals(enrollment.addressedTo)) {
			return false;
		}
		if (firstUbication != null && enrollment.firstUbication != null) {
			a++;
			if (firstUbication.equals(enrollment.firstUbication)) {
				b++;
			}
		}
		if (secondUbication != null && enrollment.secondUbication != null) {
			a++;
			if (secondUbication.equals(enrollment.secondUbication)) {
				b++;
			}
		}
		if (thirdUbication != null && enrollment.thirdUbication != null) {
			a++;
			if (thirdUbication.equals(enrollment.thirdUbication)) {
				b++;
			}
		}
		if (programm != null && enrollment.programm != null) {
			a++;
			if (programm.equals(enrollment.programm)) {
				b++;
			}
		}

		if (programName!= null && enrollment.programName!= null) {
			a++;
			if (programName.equals(enrollment.programName)) {
				b++;
			}
		}
		return (a>0 && a == b);
	}

	public boolean exist(List<Enrollment> list) {
		for (Enrollment enrollment : list) {
			if (this.equals(enrollment)) {
				return true;
			}
		}
		return false;
	}

}
