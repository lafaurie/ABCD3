package cu.uci.abcd.domain.circulation;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dpenalty", schema = "abcdn")
@SequenceGenerator(name = "seq_penalty", sequenceName = "sq_dpenalty", allocationSize = 1, schema = "abcdn")
public class Penalty implements Serializable, Row {
	private static final long serialVersionUID = 9024749492683536654L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_penalty")
	@Column(name = "id")
	private Long penaltyID;

	@Column(name = "effectivedate")
	private Date effectiveDate;

	@Column(name = "expirationdate")
	private Date expirationDate;

	@Column(name = "motivation")
	private String motivation;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "paychecknumber")
	private String paychecknumber;

	@ManyToOne
	@JoinColumn(name = "penaltytype")
	private Nomenclator penaltyType;

	@ManyToOne
	@JoinColumn(name = "state")
	private Nomenclator penaltyState;

	@ManyToOne
	@JoinColumn(name = "coinType")
	private Nomenclator coinType;

	@ManyToOne
	@JoinColumn(name = "loanobject")
	private LoanObject loanObject;

	@ManyToOne
	@JoinColumn(name = "loanuser", nullable = false)
	private LoanUser loanUser;

	@Transient
	private long diferencia=0;
	@Transient
	private Date fechaTransaction = null; 
	@Transient
	private Timestamp FechaSistema =null; 
	@Transient
	private	int dias=0;
	
	@ManyToOne
	@JoinColumn(name = "library")
	private Library library;
	
	@ManyToOne
	@JoinColumn(name = "librarian", nullable = false)
	private User librarian;

	@Column(name = "cancellationdate")
	private Date cancellationdate;

	public Long getPenaltyID() {
		return penaltyID;
	}

	public void setPenaltyID(Long penaltyID) {
		this.penaltyID = penaltyID;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LoanObject getLoanObject() {
		return loanObject;
	}

	public void setLoanObject(LoanObject loanObject) {
		this.loanObject = loanObject;
		if (!loanObject.getPenalties().contains(this)) {
			loanObject.getPenalties().add(this);
		}
	}

	public LoanUser getLoanUser() {
		return loanUser;
	}

	public void setLoanUser(LoanUser loanUser) {
		this.loanUser = loanUser;
		if (!loanUser.getPenalties().contains(this)) {
			loanUser.getPenalties().add(this);
		}
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public Nomenclator getPenaltyType() {
		return penaltyType;
	}

	public void setPenaltyType(Nomenclator penaltyType) {
		this.penaltyType = penaltyType;
	}

	public Nomenclator getPenaltyState() {
		return penaltyState;
	}

	public void setPenaltyState(Nomenclator penaltyState) {
		this.penaltyState = penaltyState;
	}

	public Nomenclator getCoinType() {
		return coinType;
	}

	public void setCoinType(Nomenclator coinType) {
		this.coinType = coinType;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	@Override
	public int hashCode() {
		return penaltyID.intValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Penalty) {
			return ((Penalty) obj).getPenaltyID() == penaltyID;
		}
		return super.equals(obj);
	}

	@Override
	public Object getRowID() {
		return getPenaltyID();
	}
	
	
	public int getDay() {	
		if (penaltyType.getNomenclatorID().equals(CirculationNomenclator.PENALTY_TYPE_FINE)) {
			FechaSistema = new Timestamp(new java.util.Date().getTime());
			fechaTransaction=getExpirationDate();
			diferencia = (FechaSistema.getTime()-fechaTransaction.getTime()  ); 
			
			 dias = (int) ((diferencia)/ (1000 * 60 * 60 * 24));
			 
			 if (dias<0) 
			 {
				dias= 0;
			 }
		}
		else
			dias= 0;
		
		 return dias;
	}

	public User getLibrarian() {
		return librarian;
	}

	public void setLibrarian(User librarian) {
		this.librarian = librarian;
	}

	public Date getCancellationdate() {
		return cancellationdate;
	}

	public void setCancellationdate(Date cancellationdate) {
		this.cancellationdate = cancellationdate;
	}

	public String getPaychecknumber() {
		return paychecknumber;
	}

	public void setPaychecknumber(String paychecknumber) {
		this.paychecknumber = paychecknumber;
	}

}
