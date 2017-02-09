package cu.uci.abcd.demo.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "daddressroleactor", schema = "abcd")
public class AddressRoleActor {
	@Id
	@GeneratedValue(generator = "GEN_ADDRESS_ROLE_ACTOR")
	@SequenceGenerator(name = "GEN_ADDRESS_ROLE_ACTOR", sequenceName = "seq_address_role_actor")
	@Column(name = "address_role_actor_id")
	private Long addressRoleID;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_role_id", referencedColumnName = "address_role_id")
	private AddressRole addressRole;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actor_id", referencedColumnName = "actor_id")
	private Actor actor;

	public AddressRoleActor() {
		super();
	}

	public Long getAddressRoleID() {
		return addressRoleID;
	}

	public void setAddressRoleID(Long addressRoleID) {
		this.addressRoleID = addressRoleID;
	}

	public AddressRole getAddressRole() {
		return addressRole;
	}

	public void setAddressRole(AddressRole addressRole) {
		this.addressRole = addressRole;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
