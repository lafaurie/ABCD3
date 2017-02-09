package cu.uci.abcd.demo.domain.common;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "daccount", schema = "abcd")
public class Account {
	@Id
	@GeneratedValue(generator = "GEN_ACCOUNT")
	@SequenceGenerator(name = "GEN_ACCOUNT", sequenceName = "seq_account")
	@Column(name = "account_id")
	private Long accountID;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "creation_date")
	private Date creationDate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private TangibleActor owner;

	public Account() {
		super();
	}

	public Long getAccountID() {
		return accountID;
	}

	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TangibleActor getOwner() {
		return owner;
	}

	public void setOwner(TangibleActor owner) {
		this.owner = owner;
		if (owner.getAccount() != this) {
			owner.setAccount(this);
		}
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
