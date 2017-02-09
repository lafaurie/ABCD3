package cu.uci.abcd.domain.common;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.l10n.AbosMessages;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abos.core.domain.Row;
import cu.uci.abos.core.util.MessageUtil;

@Entity
@Table(name = "duser", schema = "abcdn")
@SequenceGenerator(name = "seq_duser", sequenceName = "sq_duser", allocationSize = 1, schema = "abcdn")
public class User implements Serializable, Row {

	private static final long serialVersionUID = -5467379352766715147L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_duser")
	@Column(name = "id", nullable = false)
	private Long userID;

	@Column(name = "creationdate", nullable = false)
	private Date creationDate;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled;

	@Column(name = "username", nullable = false, length = 20)
	private String username;

	@Column(name = "userpassword", nullable = false, length = 1000)
	private String userPassword;

	@Column(name = "opacuser")
	private Boolean opacuser;

	@Column(name = "systemuser")
	private Boolean systemuser;

	@Column(name = "opacloginquantity")
	private Integer opacloginquantity;

	@Column(name = "expirationdate", nullable = true)
	private Date expirationDate;
	
	@ManyToOne
	@JoinColumn(name = "domain")
	private Ldap domain;

	public Ldap getDomain() {
		return domain;
	}

	public void setDomain(Ldap domain) {
		this.domain = domain;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@OneToOne
	@JoinColumn(name = "person")
	private Person person;

	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	public User() {
		super();
		setSystemuser(false);
		setOpacuser(false);
		setEnabled(true);
		setOpacloginquantity(0);
		asignedProfiless = new ArrayList<>();
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibraryOwner(Library library) {
		this.library = library;
	}

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "duserprofile", schema = "abcdn", joinColumns = { @JoinColumn(name = "duser", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "profile", referencedColumnName = "id", nullable = false) })
	private List<Profile> asignedProfiless;

	public List<Profile> getAsignedProfiless() {
		return asignedProfiless;
	}

	public void setAsignedProfiless(List<Profile> asignedProfiless) {
		this.asignedProfiless = asignedProfiless;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Integer getOpacloginquantity() {
		return opacloginquantity;
	}

	public void setOpacloginquantity(Integer opacloginquantity) {
		this.opacloginquantity = opacloginquantity;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public String getUsernameToString() {
		String otherPart = getDomain()==null?"":" / "+getDomain();
		return getUsername()+otherPart;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Boolean getOpacuser() {
		return opacuser;
	}

	public void setOpacuser(Boolean opacuser) {
		this.opacuser = opacuser;
	}

	public Boolean getSystemuser() {
		return systemuser;
	}

	public void setSystemuser(Boolean systemuser) {
		this.systemuser = systemuser;
	}

	@Override
	public Object getRowID() {
		return getUserID();
	}

	@Override
	public String toString() {
		return getPerson() != null? getPerson().getFullName(): getUsername();
	}
	
	public String nameIfHavePerson() {
		return getPerson() == null? "< "+ MessageUtil.unescape(AbosMessages.get().NOT_EXIST_ASSOCIATE_PERSON) +" >":getPerson().getFullName();
	}
	
	public String identificationIfHavePerson() {
		return getPerson() == null? " - ":getPerson().getDNI();
	}
	
	
	public String getEnabledToString() {
		return enabled?MessageUtil.unescape(AbosMessages.get().ACTIVE):MessageUtil.unescape(AbosMessages.get().ACTIVE);
		//MessageUtil.unescape(AbosMessages.get().REGISTER_DATE)
	}
	
}
