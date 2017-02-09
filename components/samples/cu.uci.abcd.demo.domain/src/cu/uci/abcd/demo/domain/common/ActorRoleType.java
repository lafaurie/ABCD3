package cu.uci.abcd.demo.domain.common;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "dactorroletype", schema = "abcd")
@PrimaryKeyJoinColumn(name = "actor_role_type_id")
@DiscriminatorValue(value = "ACTOR_ROLE_TYPE")
public class ActorRoleType extends Actor {
	@Column(name = "actor_role_type_description")
	private String actorRoleTypeDescription;
	@OneToMany(mappedBy = "actorRoleType")
	private List<ActorRole> tangibleActorList;

	public ActorRoleType() {
		super();
	}

	public List<ActorRole> getTangibleActorList() {
		return tangibleActorList;
	}

	public void setTangibleActorList(List<ActorRole> tangibleActorList) {
		this.tangibleActorList = tangibleActorList;
	}

	public String getActorRoleTypeDescription() {
		return actorRoleTypeDescription;
	}

	public void setActorRoleTypeDescription(String actorRoleTypeDescription) {
		this.actorRoleTypeDescription = actorRoleTypeDescription;
	}

}
