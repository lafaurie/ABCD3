package cu.uci.abcd.demo.domain.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "daddressrole", schema = "abcd")
public class AddressRole {
	@Id
	@GeneratedValue(generator = "GEN_ADDRESS_ROLE")
	@SequenceGenerator(name = "GEN_ADDRESS_ROLE", sequenceName = "seq_address_role")
	@Column(name = "address_role_id")
	private Long addressRoleID;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_role_type_id")
	private AddressRoleType addressRoleType;
	@OneToMany(mappedBy = "addressRole")
	private List<AddressRoleActor> addressList;

	public AddressRole() {
		super();
		this.addressList = new ArrayList<>();
	}

	public Long getAddressRoleID() {
		return addressRoleID;
	}

	public void setAddressRoleID(Long addressRoleID) {
		this.addressRoleID = addressRoleID;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public AddressRoleType getAddressRoleType() {
		return addressRoleType;
	}

	public void setAddressRoleType(AddressRoleType addressRoleType) {
		this.addressRoleType = addressRoleType;
	}

	public List<AddressRoleActor> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<AddressRoleActor> addressList) {
		this.addressList = addressList;
	}

}
