package cu.uci.abcd.domain.management.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dlibrary", schema = "abcdn")
@SequenceGenerator(name = "seq_dlibrary", sequenceName = "sq_dlibrary", allocationSize = 1, schema = "abcdn")
public class Library implements Serializable, Row {

	private static final long serialVersionUID = 8082694030183918588L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dlibrary")
	@Column(name = "id", nullable = false)
	private Long libraryID;

	@Column(name = "address", nullable = true, length = 100)
	private String address;

	@Column(name = "libraryname", nullable = false, length = 100)
	private String libraryName;
	
	@Column(name = "enabled")
	private boolean enabled;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@OneToOne(mappedBy = "library")
	private FineEquation fineEquation;

	@OneToMany(mappedBy = "library")
	private List<Room> rooms;
	
	@OneToMany(mappedBy = "library")
	private List<Worker> workers;
	
	@OneToMany(mappedBy = "library")
	private List<Provider> providers;
	
	@OneToMany(mappedBy = "library")
	private List<FormationCourse> formationCourses;

	@Column(name = "isisdefhome", length = 500)
	private String isisDefHome;

	@OneToMany(mappedBy = "library")
	private List<Ldap> ldaps;

	public Library() {
		super();
		rooms = new ArrayList<>();
		ldaps = new ArrayList<>();
		formationCourses = new ArrayList<>();
		workers = new ArrayList<>();
		providers = new ArrayList<>();
	}
	
	public List<Ldap> getLdaps() {
		return ldaps;
	}

	public void setLdaps(List<Ldap> ldaps) {
		this.ldaps = ldaps;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void addLdap(Ldap ldap){
		ldaps.add(ldap);
	}
	
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public FineEquation getFineEquation() {
		return fineEquation;
	}

	public void setFineEquation(FineEquation fineEquation) {
		this.fineEquation = fineEquation;
	}

	public Long getLibraryID() {
		return libraryID;
	}

	public void setLibraryID(Long libraryID) {
		this.libraryID = libraryID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public String getIsisDefHome() {
		return isisDefHome;
	}

	public void setIsisDefHome(String isisDefHome) {
		this.isisDefHome = isisDefHome;
	}
	
	public List<Provider> getProviders() {
		return providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}

	public List<FormationCourse> getFormationCourses() {
		return formationCourses;
	}

	public void setFormationCourses(List<FormationCourse> formationCourses) {
		this.formationCourses = formationCourses;
	}

	@Override
	public Object getRowID() {
		return getLibraryID();
	}

	@Override
	public String toString() {
		return getLibraryName();
	}
	
}
