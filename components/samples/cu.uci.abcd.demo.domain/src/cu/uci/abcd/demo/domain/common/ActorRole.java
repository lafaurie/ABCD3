package cu.uci.abcd.demo.domain.common;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "dactorrole", schema = "abcd")
@PrimaryKeyJoinColumn(name = "actor_role_id")
@DiscriminatorValue(value = "ACTOR_ROLE")
public class ActorRole extends Actor {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tangible_actor_id")
	private TangibleActor tangibleActor;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actor_role_type_id")
	private ActorRoleType actorRoleType;

	public ActorRole() {
		super();
	}

	public TangibleActor getTangibleActor() {
		return tangibleActor;
	}

	public void setTangibleActor(TangibleActor tangibleActor) {
		this.tangibleActor = tangibleActor;
	}

	public ActorRoleType getActorRoleType() {
		return actorRoleType;
	}

	public void setActorRoleType(ActorRoleType actorRoleType) {
		this.actorRoleType = actorRoleType;
	}

}
