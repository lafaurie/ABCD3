package cu.uci.abcd.domain.common;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dperson", schema = "abcdn")
@SequenceGenerator(name = "seq_dperson", sequenceName = "sq_dperson", schema = "abcdn", allocationSize = 1)
public class Person implements Serializable, Row {
	private static final long serialVersionUID = -5012730440430938231L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dperson")
	@Column(name = "id", nullable = false)
	private Long personID;

	@Column(name = "emailaddress", length = 30)
	private String emailAddress;

	@Column(name = "address", length = 500)
	private String address;

	@Column(name = "firstname", nullable = false, length = 30)
	private String firstName;

	@Column(name = "secondname", length = 30)
	private String secondName;

	@Column(name = "firstsurname", nullable = false, length = 30)
	private String firstSurname;

	@Column(name = "secondSurname", length = 30)
	private String secondSurname;

	@Column(name = "birthdate")
	private Date birthDate;

	@Column(name = "dni", length = 20)
	private String dni;

	@ManyToOne
	@JoinColumn(name = "sex")
	private Nomenclator sex;

	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "photo")
	private Picture photo;

	@OneToOne(mappedBy = "person")
	private User user;

	@OneToOne(mappedBy = "person")
	private LoanUser loanUser;

	public LoanUser getLoanUser() {
		return loanUser;
	}

	public void setLoanUser(LoanUser loanUser) {
		this.loanUser = loanUser;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public Long getPersonID() {
		return personID;
	}

	public void setPersonID(Long personID) {
		this.personID = personID;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getFirstSurname() {
		return firstSurname;
	}

	public void setFirStsurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getDNI() {
		return dni;
	}

	public void setDNI(String dNI) {
		dni = dNI;
	}

	public Nomenclator getSex() {
		return sex;
	}

	public void setSex(Nomenclator sex) {
		this.sex = sex;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public Picture getPhoto() {
		//FIXME  OIGRES BUGS
		if(photo==null){
			return new Picture();
		}else{
			return photo;
		}
		
	}

	public void setPhoto(Picture photo) {
		this.photo = photo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public String getFullName() {
		StringBuilder sb = new StringBuilder(getFirstName());
		String fullName = getFirstName();
		if (getSecondName() != null && !getSecondName().isEmpty()) {
			sb.append(" ");
			sb.append(getSecondName());
		}
		sb.append(" ");
		sb.append(getFirstSurname());
		fullName = fullName + " " + getFirstSurname();
		if (getSecondSurname() != null && !getSecondSurname().isEmpty()) {
			sb.append(" ");
			sb.append(getSecondSurname());
		}
		return sb.toString();
	}
	
	public String getAge() {
		return String.valueOf(Auxiliary.getAge(getBirthDate()));		
	}
	
	@Override
	public Object getRowID() {
		return getPersonID();
	}
	
	@Override
	public String toString() {
		return getFullName();
	}

}
