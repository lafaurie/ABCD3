package cu.uci.abcd.demo.domain.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import cu.uci.abcd.demo.domain.circulation.LoanUser;

@Entity
@Table(name = "dtangibleactor", schema = "abcd")
@DiscriminatorValue(value = "TANGIBLE_ACTOR")
@PrimaryKeyJoinColumn(name = "tangible_actor_id")
@DiscriminatorColumn(name = "tangibleactor_discriminator")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TangibleActor extends Actor {
	@OneToMany(mappedBy = "tangibleActor")
	protected List<ActorRole> actorRoleTypeList;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "owner")
	protected Account account;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "owner")
	protected LoanUser loanUser;

	public TangibleActor() {
		super();
		actorRoleTypeList = new ArrayList<ActorRole>();
	}

	public List<ActorRole> getActorRoleTypeList() {
		return actorRoleTypeList;
	}

	public void setActorRoleTypeList(List<ActorRole> actorRoleTypeList) {
		this.actorRoleTypeList = actorRoleTypeList;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public LoanUser getLoanUser() {
		return loanUser;
	}

	public void setLoanUser(LoanUser loanUser) {
		this.loanUser = loanUser;
	}

	public ActorRoleType addActorRoleType(ActorRoleType actorRoleType) {
		ActorRole actorRole = new ActorRole();
		actorRole.setActorRoleType(actorRoleType);
		actorRole.setTangibleActor(this);
		actorRoleTypeList.add(actorRole);
		if (!actorRoleType.getTangibleActorList().contains(actorRole)) {
			actorRoleType.getTangibleActorList().add(actorRole);
		}
		return actorRoleType;
	}

	public ActorRoleType removeActorRoleType(ActorRoleType actorRoleType) {
		for (ActorRole actorRole : actorRoleTypeList) {
			if (actorRole.getActorRoleType() == actorRoleType) {
				actorRoleTypeList.remove(actorRole);
				actorRoleType.getTangibleActorList().remove(actorRole);
				actorRole.setActorRoleType(null);
				actorRole.setTangibleActor(null);
				break;
			}
		}
		return actorRoleType;
	}
}
