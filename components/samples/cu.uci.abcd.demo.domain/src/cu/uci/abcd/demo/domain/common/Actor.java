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
@Table(name = "dactor", schema = "abcd")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "actor_discriminator")
public abstract class Actor {
	@Id
	@GeneratedValue(generator = "GEN_ACTOR")
	@SequenceGenerator(name = "GEN_ACTOR", sequenceName = "seq_actor")
	@Column(name = "actor_id")
	protected Long actorID;
	@Column(name = "effective_date")
	protected Date effectiveDate;
	@Column(name = "expiration_date")
	protected Date expirationDate;
	@OneToMany(mappedBy = "actor")
	protected List<AddressRoleActor> addressList;

	public Actor() {
		super();
		addressList = new ArrayList<AddressRoleActor>();
	}

	public Long getActorID() {
		return actorID;
	}

	public void setActorID(Long actorID) {
		this.actorID = actorID;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public AddressRole addAddressRole(AddressRole addressRole) {
		AddressRoleActor addressRoleActor = new AddressRoleActor();
		addressRoleActor.setAddressRole(addressRole);
		addressRoleActor.setActor(this);
		this.addressList.add(addressRoleActor);
		if (!addressRole.getAddressList().contains(addressRoleActor)) {
			addressRole.getAddressList().add(addressRoleActor);
		}
		return addressRole;
	}

	public AddressRole removeAddressRole(AddressRole addressRole) {
		for (AddressRoleActor addressRoleActor : addressList) {
			if (addressRoleActor.getAddressRole() == addressRole) {
				addressList.remove(addressRoleActor);
				addressRole.getAddressList().remove(addressRoleActor);
				addressRoleActor.setActor(null);
				addressRoleActor.setAddressRole(null);
				break;
			}
		}
		return addressRole;
	}
}
