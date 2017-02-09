
package cu.uci.abcd.domain.management.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dldap", schema = "abcdn")
@SequenceGenerator(name = "seq_dldap", sequenceName = "sq_dldap", allocationSize = 1, schema = "abcdn")

public class Ldap implements Serializable, Row{
	private static final long serialVersionUID = -7918709470118909021L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dldap")
	@Column(name = "id", nullable = false)
	private Long ldapID;
	
	@Column(name = "host", nullable = false)
	private String host;
	
	@Column(name = "port", nullable = false)
	private Integer port;
	
	@Column(name = "domain", nullable = false)
	private String domain;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	//@ManyToMany(mappedBy = "ldaps", fetch = FetchType.LAZY)
	//private List<Library> libraries;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;
	
	public Ldap(){
		super();
	}
	
	public Long getLdapID() {
		return ldapID;
	}

	public void setLdapID(Long ldapID) {
		this.ldapID = ldapID;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	@Override
	public Object getRowID() {
		return getLdapID();
	}
	
	public String getState(){
		return (isEnabled())?"Activo":"Inactivo";
	}
	
	@Override
	public String toString() {
		return getDomain();
	}
}
