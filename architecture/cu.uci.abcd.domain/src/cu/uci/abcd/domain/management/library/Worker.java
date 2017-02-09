package cu.uci.abcd.domain.management.library;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dworker", schema = "abcdn")
@SequenceGenerator(name = "seq_dworker", sequenceName = "sq_dworker", schema = "abcdn", allocationSize = 1)
public class Worker implements Serializable, Row {
	
	private static final long serialVersionUID = 6385505561590055735L;

	public Worker() {
		super();
		rooms = new ArrayList<>();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dworker")
	@Column(name = "id", nullable = false)
	private Long workerID;
	
	@ManyToOne
	@JoinColumn(name = "person", nullable = false)
	private Person person;
	
	@Column(name = "registrationdate", nullable = false)
	private Date registerDate;
	
	@Column(name = "observations", length = 200)
	private String observation;

	@ManyToOne
	@JoinColumn(name = "workertype", nullable = false)
	private Nomenclator workerType;

	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "dworkerroom", schema = "abcdn", joinColumns = { @JoinColumn(name = "worker", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "room", referencedColumnName = "id", nullable = false) })
	private List<Room> rooms;
	
	@ManyToOne
	@JoinColumn(name = "workeractivity", nullable = false)
	private Nomenclator workerActivity;

	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;
	
	public Nomenclator getWorkerActivity() {
		return workerActivity;
	}

	public void setWorkerActivity(Nomenclator workerActivity) {
		this.workerActivity = workerActivity;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Nomenclator getWorkerType() {
		return workerType;
	}

	public void setWorkerType(Nomenclator workerType) {
		this.workerType = workerType;
	}

	public Long getWorkerID() {
		return workerID;
	}

	public void setWorkerID(Long workerID) {
		this.workerID = workerID;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}
	
	public String getPersonToString() {
		return getPerson().getFullName();
	}
	
	public String getRegisterDateToString() {
		return Auxiliary.FormatDate(getRegisterDate());
	}
	
	@Override
	public Object getRowID() {
		return getWorkerID();
	}

	@Override
	public String toString() {
		return getPerson().getFullName() ;
	}
	

}
