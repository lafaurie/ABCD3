package cu.uci.abcd.domain.opac;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dopacaction", schema = "abcdn")
@SequenceGenerator(name = "seq_dopacaction", sequenceName = "sq_dopacaction", schema = "abcdn", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING, length = 20)
@MappedSuperclass
public abstract class OPACAction implements Serializable, Row {
	private static final long serialVersionUID = 4223821362640287259L;
     
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dopacaction")
	@Column(name = "id", nullable = false)
	protected Long id;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "duser", nullable = false)
	protected User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	@Column(name = "material")
	protected String material;

	@Column(name = "databasename")
	protected String databasename;

	@Column(name = "actiondate")
	protected Date actionDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Date getActionDate() {
		return actionDate;
	}     

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public User getDuser() {
		return user;
	}

	public void setDuser(User user) {
		this.user = user;
	}

	public String getDatabasename() {
		return databasename;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
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
