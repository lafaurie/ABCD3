package cu.uci.abcd.demo.domain.circulation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.demo.domain.management.library.Library;

@Entity
@Table(name = "dloanusertype", schema = "abcd")
public class LoanUserType {
	@Id
	@GeneratedValue(generator = "GEN_LOAN_USER_TYPE")
	@SequenceGenerator(name = "GEN_LOAN_USER_TYPE", sequenceName = "seq_loan_user_type")
	@Column(name = "loan_user_type_id")
	private Long loanUserTypeID;
	@Column(name = "locan_user_type")
	private String loanUserType;
	@Column(name = "description")
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "library_owner_id")
	private Library libraryOwner;

	public LoanUserType() {
		super();
	}

	public Long getLoanUserTypeID() {
		return loanUserTypeID;
	}

	public void setLoanUserTypeID(Long loanUserTypeID) {
		this.loanUserTypeID = loanUserTypeID;
	}

	public String getLoanUserType() {
		return loanUserType;
	}

	public void setLoanUserType(String loanUserType) {
		this.loanUserType = loanUserType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Library getLibraryOwner() {
		return libraryOwner;
	}

	public void setLibraryOwner(Library libraryOwner) {
		this.libraryOwner = libraryOwner;
	}

}
