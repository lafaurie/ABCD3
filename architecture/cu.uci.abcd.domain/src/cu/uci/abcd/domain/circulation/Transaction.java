package cu.uci.abcd.domain.circulation;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dtransactions", schema = "abcdn")
@SequenceGenerator(name = "seq_dtransactions", sequenceName = "sq_dtransactions", allocationSize = 1, schema = "abcdn")
public class Transaction implements Serializable, Row {
	private static final long serialVersionUID = -8260176059211124547L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dtransactions")
	@Column(name = "id")
	private Long transactionID;
	@ManyToOne
	@JoinColumn(name = "loanobject", nullable = false)
	private LoanObject loanObject;
	@ManyToOne
	@JoinColumn(name = "loanuser", nullable = false)
	private LoanUser loanUser;
	@ManyToOne
	@JoinColumn(name = "loantype")
	private Nomenclator loanType;
	@ManyToOne
	@JoinColumn(name = "loanstate")
	private Nomenclator state;
	
	@Column(name = "transactiondate", nullable = false)
	private Date transactionDateTime;
	@ManyToOne
	@JoinColumn(name = "parenttransaction")
	private Transaction parenttransaction;
	@ManyToOne
	@JoinColumn(name = "reservation")
	private Reservation reservation;
	@Column(name = "isparent", nullable = false)
	private boolean isparent;
	
	@Column(name = "endtransactiondate", nullable = false)
	private Date endTransactionDate;
	
	@ManyToOne
	@JoinColumn(name = "librarian", nullable = false)
	private User librarian;

	public User getLibrarian() {
		return librarian;
	}

	public void setLibrarian(User librarian) {
		this.librarian = librarian;
	}

	public Long getTransactionID() {
		return transactionID;
	}

	public Nomenclator getLoanType() {
		return loanType;
	}

	public void setLoanType(Nomenclator loanType) {
		this.loanType = loanType;
	}

	public Nomenclator getState() {
		return state;
	}

	public void setState(Nomenclator state) {
		this.state = state;
	}

	
	public Transaction getParenttransaction() {
		return parenttransaction;
	}

	public void setParenttransaction(Transaction parenttransaction) {
		this.parenttransaction = parenttransaction;
	}

	public void setTransactionID(Long transactionID) {
		this.transactionID = transactionID;
	}

	public LoanObject getLoanObject() {
		return loanObject;
	}

	public void setLoanObject(LoanObject loanObject) {
		this.loanObject = loanObject;
		if (!loanObject.getTransactions().contains(this)) {
			loanObject.getTransactions().add(this);
		}
	}

	public LoanUser getLoanUser() {
		return loanUser;
	}

	public void setLoanUser(LoanUser loanUser) {
		this.loanUser = loanUser;
		if (!loanUser.getTransactions().contains(this)) {
			loanUser.getTransactions().add(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loanType == null) ? 0 : loanType.hashCode());
		result = prime * result + ((transactionID == null) ? 0 : transactionID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Transaction other = (Transaction) obj;
		if (transactionID == null) {
			if (other.transactionID != null)
				return false;
		} else if (!transactionID.equals(other.transactionID))
			return false;
		return true;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public boolean isIsparent() {
		return isparent;
	}

	public void setIsparent(boolean isparent) {
		this.isparent = isparent;
	}
	
	@Override
	public Object getRowID() {
		return getTransactionID();
	}

	public Date getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Date transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public Date getEndTransactionDate() {
		return endTransactionDate;
	}

	public void setEndTransactionDate(Date endTransactionDate) {
		this.endTransactionDate = endTransactionDate;
	}

	
}