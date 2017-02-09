package cu.uci.abcd.domain.management.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Internationalization;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dpermission", schema = "abcdn")
public class Permission implements Serializable, Row {
	private static final long serialVersionUID = -7772781490878588430L;

	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "module")
	private Nomenclator module;

	@Column(name = "permissionname", nullable = false)
	private String permissionName;

	@OneToOne
	@JoinColumn(name = "internationalization")
	private Internationalization internationalization;

	@ManyToMany(mappedBy = "asignedPermissions", fetch = FetchType.LAZY)
	private List<Profile> profiles;

	public Permission() {
		super();
		profiles = new ArrayList<>();
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public Long getId() {
		return id;
	}

	public Nomenclator getModule() {
		return module;
	}

	public String getPermissionName() {
		return permissionName;
	}

	@Override
	public String toString() {
		// FIXME OIGRES quitar lo suma suma string para mejorar rendimiento con
		// StringBuilder.
		return "Permission [id=" + id + ", module=" + module + ", permissionName=" + permissionName + ", internationalization=" + internationalization + "]";
	}

	public Internationalization getInternationalization() {
		return internationalization;
	}

	public void setInternationalization(Internationalization internationalization) {
		this.internationalization = internationalization;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setModule(Nomenclator module) {
		this.module = module;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	@Override
	public Object getRowID() {
		return getId();
	}

	public String getValue() {
		if (internationalization != null) {
			return getInternationalization().getValue() != null ? getInternationalization().getValue() : getPermissionName();
		}
		return getPermissionName();
	}

}
