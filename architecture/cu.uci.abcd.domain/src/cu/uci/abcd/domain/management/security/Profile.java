package cu.uci.abcd.domain.management.security;

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

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dprofile", schema = "abcdn")
@SequenceGenerator(name = "profile_gen", schema = "abcdn", allocationSize = 1, sequenceName = "sq_dprofile")
public class Profile implements Serializable, Row {
	private static final long serialVersionUID = 5095137160982501251L;
	
	@Id
	@GeneratedValue(generator = "profile_gen", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "creationdate", nullable = false)
	private Date creationDate;
	
	@Column(name = "profilename", nullable = false)
	private String profileName;
	
	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	//@ManyToMany(mappedBy = "asignedProfiless", fetch = FetchType.LAZY)
	//private List<User> users;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "dprofilepermission", schema = "abcdn", joinColumns = { @JoinColumn(name = "profile", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "permission", referencedColumnName = "id", nullable = false) })
	private List<Permission> asignedPermissions;

	public Profile() {
		super();
		asignedPermissions = new ArrayList<>();
		//users = new ArrayList<>();
	}

	public List<Permission> getAsignedPermissions() {
		return asignedPermissions;
	}

	public void setAsignedPermissions(List<Permission> asignedPermissions) {
		this.asignedPermissions = asignedPermissions;
	}

	//public List<User> getUsers() {
	//	return users;
	//}

	////public void setUsers(List<User> users) {
		//this.users = users;
	//}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	@Override
	public Object getRowID() {
		return getId();
	}
}
