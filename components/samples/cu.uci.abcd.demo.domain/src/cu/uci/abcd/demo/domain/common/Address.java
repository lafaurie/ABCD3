package cu.uci.abcd.demo.domain.common;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "daddress", schema = "abcd")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "address_discriminator")
public abstract class Address {
	@Id
	@GeneratedValue(generator = "GEN_ADDRESS")
	@SequenceGenerator(name = "GEN_ADDRESS", sequenceName = "seq_address")
	@Column(name = "address_id")
	protected Long addressID;
	@Column(name = "creation_date")
	protected Date creationDate;
	@Column(name = "comment")
	protected String comment;
	@OneToMany(mappedBy = "address")
	protected List<AddressRole> addressRoleList;

	public Address() {
		super();
		this.addressRoleList = new ArrayList<>();
	}

	public Long getAddressID() {
		return addressID;
	}

	public void setAddressID(Long addressID) {
		this.addressID = addressID;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<AddressRole> getAddressRoleList() {
		return addressRoleList;
	}

	public void setAddressRoleList(List<AddressRole> addressRoleList) {
		this.addressRoleList = addressRoleList;
	}

	public AddressRoleType addAddressRoleType(AddressRoleType addressRoleType) {
		AddressRole addressRole = new AddressRole();
		addressRole.setAddressRoleType(addressRoleType);
		addressRole.setAddress(this);
		this.addressRoleList.add(addressRole);
		if (!addressRoleType.getAddressRoleList().contains(addressRole)) {
			addressRoleType.getAddressRoleList().add(addressRole);
		}
		return addressRoleType;
	}

	public AddressRoleType removeAddressRoleType(AddressRoleType addressRoleType) {
		for (AddressRole addressRole : addressRoleList) {
			if (addressRole.getAddressRoleType() == addressRoleType) {
				addressRoleList.remove(addressRole);
				addressRoleType.getAddressRoleList().remove(addressRole);
				addressRole.setAddress(null);
				addressRole.setAddressRoleType(null);
				break;
			}
		}
		return addressRoleType;
	}
}
