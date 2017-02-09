package cu.uci.abcd.demo.domain.circulation;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.demo.domain.common.TangibleActor;

@Entity
@Table(name = "dloanuser", schema = "abcd")
public class LoanUser {
	@Id
	@GeneratedValue(generator = "GEN_LOAN_USER")
	@SequenceGenerator(name = "GEN_LOAN_USER", sequenceName = "seq_loan_user")
	@Column(name = "loan_user_id")
	private Long loanUserID;
	@Column(name = "registration_name")
	private Date registrationDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_user_type_id")
	private LoanUserType loanUserType;
	@Column(name = "loan_user_code")
	private String loanUserCode;
	@Enumerated(EnumType.STRING)
	@Column(name = "loan_user_state")
	private LoanUserState loanUserState;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private TangibleActor owner;

	public LoanUser() {
		super();
	}

	public Long getLoanUserID() {
		return loanUserID;
	}

	public void setLoanUserID(Long loanUserID) {
		this.loanUserID = loanUserID;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public LoanUserType getLoanUserType() {
		return loanUserType;
	}

	public void setLoanUserType(LoanUserType loanUserType) {
		this.loanUserType = loanUserType;
	}

	public String getLoanUserCode() {
		return loanUserCode;
	}

	public void setLoanUserCode(String loanUserCode) {
		this.loanUserCode = loanUserCode;
	}

	public LoanUserState getLoanUserState() {
		return loanUserState;
	}

	public void setLoanUserState(LoanUserState loanUserState) {
		this.loanUserState = loanUserState;
	}

	public TangibleActor getOwner() {
		return owner;
	}

	public void setOwner(TangibleActor owner) {
		this.owner = owner;
	}

}
