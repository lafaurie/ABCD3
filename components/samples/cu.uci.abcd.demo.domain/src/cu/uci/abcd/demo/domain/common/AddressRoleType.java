package cu.uci.abcd.demo.domain.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "daddressroletype", schema = "abcd")
public class AddressRoleType {
	@Id
	@GeneratedValue(generator = "GEN_ADDRESS_ROLE_TYPE")
	@SequenceGenerator(name = "GEN_ADDRESS_ROLE_TYPE", sequenceName = "seq_address_role_type")
	@Column(name = "address_role_type_id")
	private Long addressRoleTypeID;
	@Column(name = "address_role_type_name")
	private String addressRoleTypeName;
	@OneToMany(mappedBy = "addressRoleType")
	private List<AddressRole> addressRoleList;

	public AddressRoleType() {
		super();
		this.addressRoleList = new ArrayList<AddressRole>();
	}

	public Long getAddressRoleTypeID() {
		return addressRoleTypeID;
	}

	public void setAddressRoleTypeID(Long addressRoleTypeID) {
		this.addressRoleTypeID = addressRoleTypeID;
	}

	public String getAddressRoleTypeName() {
		return addressRoleTypeName;
	}

	public void setAddressRoleTypeName(String addressRoleTypeName) {
		this.addressRoleTypeName = addressRoleTypeName;
	}

	public List<AddressRole> getAddressRoleList() {
		return addressRoleList;
	}

	public void setAddressRoleList(List<AddressRole> addressRoleList) {
		this.addressRoleList = addressRoleList;
	}

}
