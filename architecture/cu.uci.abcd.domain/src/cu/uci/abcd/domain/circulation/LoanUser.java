package cu.uci.abcd.domain.circulation;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.Picture;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dloanuser", schema = "abcdn")
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "dbtype", discriminatorType = DiscriminatorType.INTEGER)
//@DiscriminatorValue(value = "750")
//@Customizer(LoanUserCustomizer.class)
@SequenceGenerator(name = "seq_dloanuser", sequenceName = "sq_dloanuser", schema = "abcdn", allocationSize = 1)
public class LoanUser implements Serializable, Row {
	private static final long serialVersionUID = -8230875663889565847L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dloanuser")
	@Column(name = "id")
	protected Long id;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "person", referencedColumnName = "id", nullable = false)
	protected Person person;

	@Column(name = "remarks")
	protected String remarks;
	
	@Column(name = "disabilitationremarks")
	protected String disabilitationremarks;
	
	@Column(name = "creationdate")
	protected Date registrationDate;
	
	@Column(name = "expirationdate")
	protected Date expirationDate;
	
	@Column(name = "code")
	protected String loanUserCode;
	
	@Column(name = "authorizingofficial")
	protected String authorizingOfficial;
	
	@Column(name = "country")
	protected String country;

	@ManyToOne
	@JoinColumn(name = "modality")
	private Nomenclator modality;
	
	@ManyToOne
	@JoinColumn(name = "faculty")
	private Nomenclator faculty;
	
	@ManyToOne
	@JoinColumn(name = "speciality")
	private Nomenclator speciality;
	
	@Column(name = "university")
	private String university;
		
	@ManyToOne
	@JoinColumn(name = "registerby")
	private Worker registerby;

	@ManyToOne
	@JoinColumn(name = "department")
	private Nomenclator department;
	
	@ManyToOne
	@JoinColumn(name = "state")
	protected Nomenclator loanUserState;
	
	@ManyToOne
	@JoinColumn(name = "loanusertype")
	protected Nomenclator loanUserType;
	
	@ManyToOne
	@JoinColumn(name = "registeredatroom")
	protected Room registeredatroom;

	@ManyToOne
	@JoinColumn(name = "roomlibrarian")
	protected Room roomlibrarian;

	
	//FIXME OIGRES estas listas mapearlas como set para que no tengan datos repetidos ni haya que comprobar para add.
	@OneToMany(mappedBy = "loanUser")
	protected List<Reservation> reservationList;
	
	@OneToMany(mappedBy = "loanUser")
	protected List<Transaction> transactions;
	
	@OneToMany(mappedBy = "loanUser")
	protected List<Penalty> penalties;

	public LoanUser() {
		super();
		reservationList = new ArrayList<>();
		transactions = new ArrayList<>();
		penalties = new ArrayList<>();
		person = new Person();
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getLoanUserCode() {
		return loanUserCode;
	}

	public void setLoanUserCode(String loanUserCode) {
		this.loanUserCode = loanUserCode;
	}

	public Nomenclator getLoanUserState() {
		return loanUserState;
	}

	public void setLoanUserState(Nomenclator loanUserState) {
		this.loanUserState = loanUserState;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDisabilitationremarks() {
		return disabilitationremarks;
	}

	public void setDisabilitationremarks(String disabilitationremarks) {
		this.disabilitationremarks = disabilitationremarks;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Nomenclator getLoanUserType() {
		return loanUserType;
	}

	public void setLoanUserType(Nomenclator loanUserType) {
		this.loanUserType = loanUserType;
	}

	@PrePersist
	public void generateCreationDate() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loanUserCode == null) ? 0 : loanUserCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanUser other = (LoanUser) obj;
		if (loanUserCode == null) {
			if (other.loanUserCode != null)
				return false;
		} else if (!loanUserCode.equals(other.loanUserCode))
			return false;
		return true;
	}

	public List<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(List<Reservation> reservationList) {
		this.reservationList = reservationList;
	}

	public Reservation addReservation(Reservation reservation) {
		reservationList.add(reservation);
		if (!reservation.getLoanUser().equals(this)) {
			reservation.setLoanUser(this);
		}
		return reservation;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Penalty> getPenalties() {
		return penalties;
	}

	public void setPenalties(List<Penalty> penalties) {
		this.penalties = penalties;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;

	}

	public String getEmailAddress() {
		return person.getEmailAddress();
	}

	public void setEmailAddress(String emailAddress) {
		person.setEmailAddress(emailAddress);
	}

	public String getAddress() {
		return person.getAddress();
	}

	public void setAddress(String address) {
		person.setAddress(address);
	}

	public String getFirstName() {
		return person.getFirstName();
	}

	public void setFirstName(String firstName) {
		person.setFirstName(firstName);
	}

	public String getSecondName() {
		return person.getSecondName();
	}

	public void setSecondName(String secondName) {
		person.setSecondName(secondName);
	}

	public String getFirstSurname() {
		return person.getFirstSurname();
	}

	public void setFirStsurname(String firstSurname) {
		person.setFirStsurname(firstSurname);
	}

	public String getSecondSurname() {
		return person.getSecondSurname();
	}

	public void setSecondSurname(String secondSurname) {
		person.setSecondSurname(secondSurname);
	}

	public Date getBirthDate() {
		return person.getBirthDate();
	}

	public void setBirthDate(Date birthDate) {
		person.setBirthDate(birthDate);
	}

	public String getDNI() {
		return person.getDNI();
	}

	public void setDNI(String dNI) {
		person.setDNI(dNI);
	}

	public Nomenclator getSex() {
		return person.getSex();
	}

	public void setSex(Nomenclator sex) {
		person.setSex(sex);
	}

	public Library getLibrary() {
		return person.getLibrary();
	}

	public void setLibrary(Library library) {
		person.setLibrary(library);
	}

	public Picture getPhoto() {
		return person.getPhoto();
	}

	public void setPhoto(Picture photo) {
		person.setPhoto(photo);
	}

	public User getUser() {
		return person.getUser();
	}

	public void setUser(User user) {
		person.setUser(user);
	}

	public String fullName() {
		return person.getFullName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Room getRegisteredatroom() {
		return registeredatroom;
	}

	public void setRegisteredatroom(Room registeredatroom) {
		this.registeredatroom = registeredatroom;
	}

	public Long getPersonID() {
		return person.getPersonID();
	}

	public void setPersonID(Long personID) {
		person.setPersonID(personID);
	}

	public Nomenclator getFaculty() {
		return faculty;
	}

	public void setFaculty(Nomenclator faculty) {
		this.faculty = faculty;
	}

	public Nomenclator getModality() {
		return modality;
	}

	public void setModality(Nomenclator modality) {
		this.modality = modality;
	}

	public Nomenclator getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Nomenclator speciality) {
		this.speciality = speciality;
	}
	
	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public Nomenclator getDepartment() {
		return department;
	}

	public void setDepartment(Nomenclator department) {
		this.department = department;
	}
	
	
	public String getAuthorizingOfficial() {
		return authorizingOfficial;
	}

	public void setAuthorizingOfficial(String authorizingOfficial) {
		this.authorizingOfficial = authorizingOfficial;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Worker getRegisterby() {
		return registerby;
	}

	public void setRegisterby(Worker registerby) {
		this.registerby = registerby;
	}

	public Room getRoomlibrarian() {
		return roomlibrarian;
	}

	public void setRoomlibrarian(Room roomlibrarian) {
		this.roomlibrarian = roomlibrarian;
	}

	@Override
	public Object getRowID() {
		return getId();
	}
	
}
